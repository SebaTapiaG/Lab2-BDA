package proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import proyecto.dto.ProductoMasCompradoDTO;
import proyecto.entities.ProductoEntity;
import proyecto.entities.ZonaEntity;
import proyecto.repositories.ZonaRepository;

import java.util.List;

@Service
public class ZonaService {

    @Autowired
    ZonaRepository zonaRepository;

    public ResponseEntity<List<Object>> findAll() {
        return zonaRepository.findAll();
    }

    public ResponseEntity<Object> findById(int id_zona) {
        return zonaRepository.findById(id_zona);
    }

    public ResponseEntity<List<Object>> findByEstado(String estado) {
        return zonaRepository.findByEstado(estado);
    }

    public ResponseEntity <Object> create(ZonaEntity zona) {
        return zonaRepository.create(zona);
    }
    public ResponseEntity<Object>  update(ZonaEntity zona) {
        return zonaRepository.update(zona);
    }

    public ResponseEntity<Object> delete(int id_zona) {
        return zonaRepository.delete(id_zona);
    }
}
