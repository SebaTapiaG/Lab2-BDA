package proyecto.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import proyecto.entities.Detalle_OrdenEntity;

import java.util.List;

@Repository
public class Detalle_OrdenRepositoryImp implements Detalle_OrdenRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public ResponseEntity<List<Object>> findAll() {
        try(Connection conn = sql2o.open()){
            List<Detalle_OrdenEntity> detalle_ordenes = conn.createQuery("SELECT * FROM detalle_orden")
                    .executeAndFetch(Detalle_OrdenEntity.class);
            List<Object> result = (List) detalle_ordenes;
            if(detalle_ordenes.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }


    @Override
    public ResponseEntity<Object> findById(int id_detalle_orden) {
        try(Connection conn = sql2o.open()){
            Detalle_OrdenEntity detalle_orden = conn.createQuery("SELECT * FROM detalle_orden WHERE id_detalle_orden = :id_detalle_orden")
                    .addParameter("id_detalle_orden", id_detalle_orden)
                    .executeAndFetchFirst(Detalle_OrdenEntity.class);
            if(detalle_orden == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(detalle_orden);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<Object> create(Detalle_OrdenEntity detalle_orden) {
        try(Connection conn = sql2o.open()){
            Integer id = (Integer) conn.createQuery("INSERT INTO detalle_orden (cantidad, precio_unitario, id_producto, id_orden) VALUES (:cantidad, :precio_unitario, :id_producto, :id_orden)", true)
                    .addParameter("cantidad", detalle_orden.getCantidad())
                    .addParameter("precio_unitario", detalle_orden.getPrecio_unitario())
                    .addParameter("id_producto", detalle_orden.getId_producto())
                    .addParameter("id_orden", detalle_orden.getId_orden())
                    .executeUpdate().getKey();
            detalle_orden.setId_detalle(id);
            return ResponseEntity.ok(detalle_orden);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<Object> update(Detalle_OrdenEntity detalle_orden) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("UPDATE detalle_orden SET cantidad = :cantidad, precio = :precio, id_producto = :id_producto, id_orden = :id_orden WHERE id_detalle_orden = :id_detalle_orden")
                    .addParameter("cantidad", detalle_orden.getCantidad())
                    .addParameter("precio", detalle_orden.getPrecio_unitario())
                    .addParameter("id_producto", detalle_orden.getId_producto())
                    .addParameter("id_orden", detalle_orden.getId_orden())
                    .executeUpdate();
            return ResponseEntity.ok(detalle_orden);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<Object> delete(int id_detalle_orden) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("DELETE FROM detalle_orden WHERE id_detalle_orden = :id_detalle_orden")
                    .addParameter("id_detalle_orden", id_detalle_orden)
                    .executeUpdate();
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }
}
