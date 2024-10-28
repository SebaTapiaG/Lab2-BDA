package proyecto.repositories;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Detalle_OrdenEntity> findAll() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM detalle_orden")
                    .executeAndFetch(Detalle_OrdenEntity.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Detalle_OrdenEntity findById(int id_detalle_orden) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM detalle_orden WHERE id_detalle_orden = :id_detalle_orden")
                    .addParameter("id_detalle_orden", id_detalle_orden)
                    .executeAndFetchFirst(Detalle_OrdenEntity.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Detalle_OrdenEntity create(Detalle_OrdenEntity detalle_orden) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("INSERT INTO detalle_orden (cantidad, precio, id_producto, id_orden) VALUES (:cantidad, :precio, :id_producto, :id_orden)", true)
                    .addParameter("cantidad", detalle_orden.getCantidad())
                    .addParameter("precio", detalle_orden.getPrecio_unitario())
                    .addParameter("id_producto", detalle_orden.getId_producto())
                    .addParameter("id_orden", detalle_orden.getId_orden())
                    .executeUpdate();
            return detalle_orden;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Detalle_OrdenEntity update(Detalle_OrdenEntity detalle_orden) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("UPDATE detalle_orden SET cantidad = :cantidad, precio = :precio, id_producto = :id_producto, id_orden = :id_orden WHERE id_detalle_orden = :id_detalle_orden")
                    .addParameter("cantidad", detalle_orden.getCantidad())
                    .addParameter("precio", detalle_orden.getPrecio_unitario())
                    .addParameter("id_producto", detalle_orden.getId_producto())
                    .addParameter("id_orden", detalle_orden.getId_orden())
                    .addParameter("id_detalle_orden", detalle_orden.getId_detalle())
                    .executeUpdate();
            return detalle_orden;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean delete(int id_detalle_orden) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("DELETE FROM detalle_orden WHERE id_detalle_orden = :id_detalle_orden")
                    .addParameter("id_detalle_orden", id_detalle_orden)
                    .executeUpdate();
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
