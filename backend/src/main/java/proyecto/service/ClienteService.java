package proyecto.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.entities.ClienteEntity;
import proyecto.repositories.ClienteRepository;
import proyecto.utils.JwtUtil;
import proyecto.utils.EncriptacionUtil;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ClienteRepository clienteRepository;

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

    public ClienteEntity update(ClienteEntity cliente){
        return clienteRepository.update(cliente);
    }

    public boolean delete(int id_cliente){
        return clienteRepository.delete(id_cliente);
    }

    public ClienteEntity findById(int id_cliente){
        return clienteRepository.findById(id_cliente);
    }

    public ClienteEntity findByEmail(String email){
        return clienteRepository.findByEmail(email);
    }

    public List<ClienteEntity> findAll(){
        return clienteRepository.findAll();
    }

}
