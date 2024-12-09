package proyecto.repositories;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import proyecto.dto.ProductoMasCompradoDTO;
import proyecto.entities.ProductoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Collections;
import java.util.List;



@Repository
public class ProductoRepositoryImp implements ProductoRepository {

    @Autowired
    private Sql2o sql2o;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Override
    public ResponseEntity<List<Object>> findAll() {
        try (Connection conn = sql2o.open()) {
            List<ProductoEntity> productos = conn.createQuery("SELECT * FROM producto")
                    .executeAndFetch(ProductoEntity.class);
            List<Object> result = (List) productos;
            if (productos.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 No Content si no hay resultados
            }
            return ResponseEntity.ok(result); // 200 OK con los datos
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(null); // 500 Internal Server Error
        }
    }

    @Override
    public ResponseEntity<Object> findById(int id_producto) {
        // Validar el token si es necesario
            try (Connection conn = sql2o.open()) {
                // Consultar la base de datos para obtener el producto por su ID
                ProductoEntity producto = conn.createQuery("SELECT * FROM producto WHERE id_producto = :id_producto")
                        .addParameter("id_producto", id_producto)
                        .executeAndFetchFirst(ProductoEntity.class);

                // Si el producto existe, devolverlo con un código 200 OK
                if (producto != null) {
                    return ResponseEntity.ok(producto);
                } else {
                    // Si no se encuentra el producto, devolver 404 Not Found
                    return ResponseEntity.notFound().build();
                }
            } catch (Exception e) {
                e.printStackTrace(); // Imprimir el error para fines de depuración
                // Devolver error 500 con el mensaje de la excepción
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            }
    }


    @Override
    public ResponseEntity<Object> findByNombre(String name) {
        try (Connection conn = sql2o.open()) {
            ProductoEntity producto = conn.createQuery("SELECT * FROM producto WHERE nombre = :nombre")
                    .addParameter("nombre", name)
                    .executeAndFetchFirst(ProductoEntity.class);
            if (producto == null) {
                return ResponseEntity.status(404).body(null);
            }
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<List<ProductoEntity>> findByCategoria(int id_categoria) {
        try (Connection conn = sql2o.open()) {
            List<ProductoEntity> productos = conn.createQuery("SELECT * FROM producto WHERE id_categoria = :id_categoria")
                    .addParameter("id_categoria", id_categoria)
                    .executeAndFetch(ProductoEntity.class);

            if (productos.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }



    @Override
    public ResponseEntity<List<ProductoMasCompradoDTO>> productosMasCompradosPorClientes() {
        String sql = "SELECT p.id_producto, p.nombre, SUM(d.cantidad) AS total_comprado " +
                "FROM Cliente c " +
                "JOIN Orden o ON c.id_cliente = o.id_cliente " +
                "JOIN Detalle_Orden d ON o.id_orden = d.id_orden " +
                "JOIN Producto p ON d.id_producto = p.id_producto " +
                "WHERE o.fecha_orden >= CURRENT_DATE - INTERVAL '6 months' " +
                "AND o.total > 1000 " +
                "GROUP BY p.id_producto, p.nombre " +
                "ORDER BY total_comprado DESC";

        try (Connection conn = sql2o.open()) {
            List<ProductoMasCompradoDTO> result = conn.createQuery(sql)
                    .executeAndFetch(ProductoMasCompradoDTO.class);

            if (result.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }


    @Override
    public ResponseEntity<Object> create(ProductoEntity producto) {
        try (Connection conn = sql2o.open()) {
            conn.createQuery("INSERT INTO producto (nombre, precio, stock, id_categoria) VALUES (:nombre, :precio, :stock, :id_categoria)", true)
                    .addParameter("nombre", producto.getNombre())
                    .addParameter("precio", producto.getPrecio())
                    .addParameter("stock", producto.getStock())
                    .addParameter("id_categoria", producto.getId_categoria())
                    .executeUpdate();
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> update(ProductoEntity producto) {
        try (Connection conn = sql2o.open()) {
            conn.createQuery("UPDATE producto SET nombre = :nombre, precio = :precio, stock = :stock, id_categoria = :id_categoria WHERE id_producto = :id_producto")
                    .addParameter("nombre", producto.getNombre())
                    .addParameter("precio", producto.getPrecio())
                    .addParameter("stock", producto.getStock())
                    .addParameter("id_categoria", producto.getId_categoria())
                    .addParameter("id_producto", producto.getId_producto())
                    .executeUpdate();
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> delete(int id_producto) {
        try (Connection conn = sql2o.open()) {
            conn.createQuery("DELETE FROM producto WHERE id_producto = :id_producto")
                    .addParameter("id_producto", id_producto)
                    .executeUpdate();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }


}
