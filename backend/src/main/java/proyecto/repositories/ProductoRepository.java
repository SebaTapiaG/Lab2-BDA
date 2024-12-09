package proyecto.repositories;
import org.springframework.http.ResponseEntity;
import proyecto.dto.ProductoMasCompradoDTO;
import proyecto.entities.ProductoEntity;

import java.util.List;

public interface ProductoRepository {

    ResponseEntity <List<Object>> findAll();
    public ResponseEntity<Object> findById(int id_producto);

    public ResponseEntity<Object> findByNombre(String nombre);

    ResponseEntity <List<ProductoEntity>> findByCategoria(int id_categoria);

    ResponseEntity<Object> create(ProductoEntity producto);
    ResponseEntity<Object>  update(ProductoEntity producto);
    ResponseEntity<Object> delete(int id_producto);
    ResponseEntity<List<ProductoMasCompradoDTO>> productosMasCompradosPorClientes();


}
