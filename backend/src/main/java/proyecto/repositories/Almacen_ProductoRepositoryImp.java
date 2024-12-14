package proyecto.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import proyecto.entities.Almacen_ProductoEntity;

import java.util.List;

@Repository
public class Almacen_ProductoRepositoryImp implements Almacen_ProductoRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public ResponseEntity<List<Object>> findAll() {
        try(Connection conn = sql2o.open()){
            List<Almacen_ProductoEntity> almacen_productos = conn.createQuery("SELECT * FROM almacen_producto")
                    .executeAndFetch(Almacen_ProductoEntity.class);
            List<Object> result = (List) almacen_productos;
            if(almacen_productos.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<Object> findById(int id_almacen_producto) {
        try(Connection conn = sql2o.open()){
            Almacen_ProductoEntity almacen_producto = conn.createQuery("SELECT * FROM almacen_producto WHERE id_almacen_producto = :id_almacen_producto")
                    .addParameter("id_almacen_producto", id_almacen_producto)
                    .executeAndFetchFirst(Almacen_ProductoEntity.class);
            if(almacen_producto == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(almacen_producto);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<Object> create(Almacen_ProductoEntity almacen_producto) {
        try(Connection conn = sql2o.open()){
            Integer id = (Integer) conn.createQuery("INSERT INTO almacen_producto (id_almacen, id_producto) VALUES (:id_almacen, :id_producto)", true)
                    .addParameter("id_almacen", almacen_producto.getId_almacen())
                    .addParameter("id_producto", almacen_producto.getId_producto())
                    .executeUpdate().getKey();
            almacen_producto.setId_almacen_producto(id);
            return ResponseEntity.ok(almacen_producto);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<Object> update(Almacen_ProductoEntity almacen_producto) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("UPDATE almacen_producto SET id_almacen = :id_almacen, id_producto = :id_producto WHERE id_almacen_producto = :id_almacen_producto")
                    .addParameter("id_almacen", almacen_producto.getId_almacen())
                    .addParameter("id_producto", almacen_producto.getId_producto())
                    .addParameter("id_almacen_producto", almacen_producto.getId_almacen_producto())
                    .executeUpdate();
            return ResponseEntity.ok(almacen_producto);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<Object> delete(int id_almacen_producto) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("DELETE FROM almacen_producto WHERE id_almacen_producto = :id_almacen_producto")
                    .addParameter("id_almacen_producto", id_almacen_producto)
                    .executeUpdate();
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }
}
