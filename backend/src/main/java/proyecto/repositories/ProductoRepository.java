package proyecto.repositories;
import proyecto.entities.ProductoEntity;

import java.util.List;

public interface ProductoRepository {
    List<ProductoEntity> findAll();
    ProductoEntity findById(int id_producto);

    ProductoEntity create(ProductoEntity producto);
    ProductoEntity update(ProductoEntity producto);
    boolean delete(int id_producto);


}
