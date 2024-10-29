package proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    OrdenRepository ordenRepository;

    @Autowired
    ProductoRepository productoRepository;

    public List<Detalle_OrdenEntity> findAll(){
        return detalle_ordenRepository.findAll();
    }

    public Detalle_OrdenEntity findById(int id_detalle_orden){
        return detalle_ordenRepository.findById(id_detalle_orden);
    }

    public Detalle_OrdenEntity create(Detalle_OrdenEntity detalle_orden){
        return detalle_ordenRepository.create(detalle_orden);
    }

    public Detalle_OrdenEntity update(Detalle_OrdenEntity detalle_orden){
        return detalle_ordenRepository.update(detalle_orden);
    }

    public boolean delete(int id_detalle_orden){
        return detalle_ordenRepository.delete(id_detalle_orden);
    }


}
