package proyecto.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import proyecto.entities.OrdenEntity;

import java.util.List;

@Repository
public class OrdenRepositoryImp implements OrdenRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public ResponseEntity<List<Object>> findAll() {
        try(Connection conn = sql2o.open()){
            List<OrdenEntity> ordenes = conn.createQuery("SELECT * FROM orden")
                    .executeAndFetch(OrdenEntity.class);
            List<Object> result = (List) ordenes;
            if(ordenes.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }


    @Override
    public ResponseEntity<Object> findById(int id_orden) {
        try(Connection conn = sql2o.open()){
            OrdenEntity orden = conn.createQuery("SELECT * FROM orden WHERE id_orden = :id_orden")
                    .addParameter("id_orden", id_orden)
                    .executeAndFetchFirst(OrdenEntity.class);
            if(orden == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(orden);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<List<Object>> findByCliente(int id_cliente) {
        try(Connection conn = sql2o.open()){
            List<OrdenEntity> ordenes = conn.createQuery("SELECT * FROM orden WHERE id_cliente = :id_cliente")
                    .addParameter("id_cliente", id_cliente)
                    .executeAndFetch(OrdenEntity.class);
            List<Object> result = (List) ordenes;
            if(ordenes.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<Object> create(OrdenEntity orden) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("INSERT INTO orden (fecha, total, id_cliente) VALUES (:fecha, :total, :id_cliente)", true)
                    .addParameter("fecha", orden.getFecha_orden())
                    .addParameter("total", orden.getTotal())
                    .addParameter("id_cliente", orden.getId_cliente())
                    .executeUpdate();
            return ResponseEntity.ok(orden);
        } catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> update(OrdenEntity orden) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("UPDATE orden SET fecha = :fecha, total = :total, id_cliente = :id_cliente WHERE id_orden = :id_orden")
                    .addParameter("fecha", orden.getFecha_orden())
                    .addParameter("total", orden.getTotal())
                    .addParameter("id_cliente", orden.getId_cliente())
                    .addParameter("id_orden", orden.getId_orden())
                    .executeUpdate();
            return ResponseEntity.ok(orden);
        } catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> delete(int id_orden) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("DELETE FROM orden WHERE id_orden = :id_orden")
                    .addParameter("id_orden", id_orden)
                    .executeUpdate();
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }


}
