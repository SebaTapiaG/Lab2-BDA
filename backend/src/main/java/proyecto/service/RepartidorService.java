package proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import proyecto.entities.RepartidorEntity;
import proyecto.repositories.RepartidorRepository;

import java.util.List;

@Service
public class RepartidorService {
    @Autowired
    RepartidorRepository repartidorRepository;

    public ResponseEntity<List<Object>> findAll() {
        return repartidorRepository.findAll();
    }

    public ResponseEntity<Object> findById(Integer id) {
        return repartidorRepository.findById(id);
    }

    public ResponseEntity<Object> findByNombre(String nombre) {
        return repartidorRepository.findByNombre(nombre);
    }

    public ResponseEntity<Object> findByEmail(String email) {
        return repartidorRepository.findByEmail(email);
    }

    public ResponseEntity<List<RepartidorEntity>> findByAlmacen(int idAlmacen){
        return repartidorRepository.findByAlmacen(idAlmacen);
    }

    public ResponseEntity<Object> update(RepartidorEntity repartidor){
        return repartidorRepository.update(repartidor);
    }

    public ResponseEntity<Object> delete(int id) {
        return repartidorRepository.delete(id);
    }

    public ResponseEntity<Object> register(RepartidorEntity repartidor){
        return repartidorRepository.register(repartidor);
    }

    public ResponseEntity<Object> login(RepartidorEntity repartidor){
        return repartidorRepository.login(repartidor.getEmail(), repartidor.getContrasena());
    }

}
