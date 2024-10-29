package proyecto.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.entities.ClienteEntity;
import proyecto.repositories.ClienteRepository;
import proyecto.utils.JwtUtil;
import proyecto.utils.EncriptacionUtil;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    JwtUtil jwtUtil;

    public ClienteEntity register(ClienteEntity cliente){
        //El cliente existe?
        if(clienteRepository.findByEmail(cliente.getEmail()) != null){
            return null;
        }
        //Encriptar contraseña
        cliente.setContrasena(EncriptacionUtil.encriptar(cliente.getContrasena()));

        //Guardar cliente
        return clienteRepository.create(cliente);
    }

    public String login(ClienteEntity cliente){
        //Buscar cliente
        ClienteEntity clienteDB = clienteRepository.findByEmail(cliente.getEmail());
        //Validar contraseña
       if(clienteDB != null && EncriptacionUtil.desencriptar(clienteDB.getContrasena()).equals(cliente.getContrasena())){
            return jwtUtil.generateToken(clienteDB);
        }
        return null;
    }

    public ClienteEntity getCliente(String token){
        if(jwtUtil.validateToken(token)){
            return clienteRepository.findByEmail(jwtUtil.extractName(token));
        }
        return null;
    }
}
