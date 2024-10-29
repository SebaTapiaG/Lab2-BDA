package proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.entities.ProductoEntity;
import proyecto.repositories.CategoriaRepository;
import proyecto.repositories.ProductoRepository;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    public List<ProductoEntity> findAll(){
        return productoRepository.findAll();
    }

    public ProductoEntity findById(int id_producto){
        return productoRepository.findById(id_producto);
    }

    public ProductoEntity create(ProductoEntity producto){
        return productoRepository.create(producto);
    }

    public ProductoEntity update(ProductoEntity producto){
        return productoRepository.update(producto);
    }

    public boolean delete(int id_producto){
        return productoRepository.delete(id_producto);
    }

    public List<ProductoEntity> findByCategoria(int id_categoria){
        return productoRepository.findByCategoria(id_categoria);
    }





}

