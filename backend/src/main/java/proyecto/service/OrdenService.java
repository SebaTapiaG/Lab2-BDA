package proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.entities.OrdenEntity;
import proyecto.repositories.ClienteRepository;
import proyecto.repositories.OrdenRepository;

import java.util.List;

@Service
public class OrdenService {

    @Autowired
    OrdenRepository ordenRepository;

    @Autowired
    ClienteRepository clienteRepository;

    public List<OrdenEntity> findAll(){
        return ordenRepository.findAll();
    }

    public OrdenEntity findById(int id_orden){
        return ordenRepository.findById(id_orden);
    }

    public OrdenEntity create(OrdenEntity orden){
        return ordenRepository.create(orden);
    }

    public OrdenEntity update(OrdenEntity orden){
        return ordenRepository.update(orden);
    }

    public boolean delete(int id_orden){
        return ordenRepository.delete(id_orden);
    }

    public List<OrdenEntity> findByCliente(int id_cliente){
        return ordenRepository.findByCliente(id_cliente);
    }
}
