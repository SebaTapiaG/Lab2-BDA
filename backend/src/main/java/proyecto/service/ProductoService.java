package proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import proyecto.dto.ProductoMasCompradoDTO;
import proyecto.entities.ProductoEntity;
import proyecto.repositories.CategoriaRepository;
import proyecto.repositories.ProductoRepository;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;

    public ResponseEntity<List<Object>> findAll() {
        return productoRepository.findAll();
    }
    public ResponseEntity<Object> findById(int id_producto) {
        return productoRepository.findById(id_producto);
    }

    public ResponseEntity<Object> findByNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    public ResponseEntity <List<ProductoEntity>> findByCategoria(int id_categoria) {
        return productoRepository.findByCategoria(id_categoria);
    }


    public ResponseEntity<List<ProductoMasCompradoDTO>> getProductosMasComprados() {
        return productoRepository.productosMasCompradosPorClientes();
    }


    public ResponseEntity<Object> create(ProductoEntity producto) {
        return productoRepository.create(producto);
    }
    public ResponseEntity<Object>  update(ProductoEntity producto) {
        return productoRepository.update(producto);
    }

    public ResponseEntity<Object> delete(int id_producto) {
        return productoRepository.delete(id_producto);
    }

}


