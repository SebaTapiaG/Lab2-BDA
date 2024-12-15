package proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import proyecto.dto.RepartidorDTO;
import proyecto.entities.OrdenEntity;
import proyecto.repositories.OrdenRepository;

import java.util.List;

@Service
public class OrdenService {

    @Autowired
    OrdenRepository ordenRepository;

    public ResponseEntity<List<Object>> findAll() {
        return ordenRepository.findAll();
    }

    public ResponseEntity findById(int id_orden) {
        return ordenRepository.findById(id_orden);
    }

    public ResponseEntity findPagadas() {
        return ordenRepository.findPagadas();
    }

    public ResponseEntity<List<Object>> findByCliente(int id_cliente) {
        return ordenRepository.findByCliente(id_cliente);
    }

    public ResponseEntity create(OrdenEntity orden) {
        return ordenRepository.create(orden);
    }

    public ResponseEntity update(OrdenEntity orden) {
        return ordenRepository.update(orden);
    }

    public ResponseEntity delete(int id_orden) {
        return ordenRepository.delete(id_orden);
    }

    public ResponseEntity updateEstado(int id_orden, String estado) {
        return ordenRepository.updateEstado(id_orden, estado);
    }
    public ResponseEntity<List<RepartidorDTO>> findDeliveryCompletedInArea(int id_zona) {
        return ordenRepository.findDeliveryCompletedInArea(id_zona);
    }
}
