package proyecto.repositories;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<OrdenEntity> findAll() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM orden")
                    .executeAndFetch(OrdenEntity.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public OrdenEntity findById(int id_orden) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM orden WHERE id_orden = :id_orden")
                    .addParameter("id_orden", id_orden)
                    .executeAndFetchFirst(OrdenEntity.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<OrdenEntity> findByCliente(int id_cliente) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM orden WHERE id_cliente = :id_cliente")
                    .addParameter("id_cliente", id_cliente)
                    .executeAndFetch(OrdenEntity.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public OrdenEntity create(OrdenEntity orden) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("INSERT INTO orden (fecha, total, id_cliente) VALUES (:fecha, :total, :id_cliente)", true)
                    .addParameter("fecha", orden.getFecha_orden())
                    .addParameter("total", orden.getTotal())
                    .addParameter("id_cliente", orden.getId_cliente())
                    .executeUpdate();
            return orden;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public OrdenEntity update(OrdenEntity orden) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("UPDATE orden SET fecha = :fecha, total = :total, id_cliente = :id_cliente WHERE id_orden = :id_orden")
                    .addParameter("fecha", orden.getFecha_orden())
                    .addParameter("total", orden.getTotal())
                    .addParameter("id_cliente", orden.getId_cliente())
                    .addParameter("id_orden", orden.getId_orden())
                    .executeUpdate();
            return orden;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean delete(int id_orden) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("DELETE FROM orden WHERE id_orden = :id_orden")
                    .addParameter("id_orden", id_orden)
                    .executeUpdate();
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }


}
