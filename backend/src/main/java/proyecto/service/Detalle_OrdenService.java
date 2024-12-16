package proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import proyecto.entities.Detalle_OrdenEntity;
import proyecto.repositories.Detalle_OrdenRepository;
import proyecto.repositories.OrdenRepository;
import proyecto.repositories.ProductoRepository;

import java.util.List;

@Service
public class Detalle_OrdenService {

    @Autowired
    Detalle_OrdenRepository detalle_ordenRepository;

    public ResponseEntity<List<Object>> findAll() {
        return detalle_ordenRepository.findAll();
    }

    public ResponseEntity<Object> findById(int id_detalle_orden) {
        return detalle_ordenRepository.findById(id_detalle_orden);
    }

    public ResponseEntity<Object> create(Detalle_OrdenEntity detalle_orden) {
        return detalle_ordenRepository.create(detalle_orden);
    }

    public ResponseEntity<Object> update(Detalle_OrdenEntity detalle_orden) {
        return detalle_ordenRepository.update(detalle_orden);
    }

    public ResponseEntity<Object> delete(int id_detalle_orden) {
        return detalle_ordenRepository.delete(id_detalle_orden);
    }

    public ResponseEntity<List<Object>> findProductosByOrdenId(Integer id_orden) {
        return detalle_ordenRepository.getDetallesByOrdenId(id_orden);
    }

}
