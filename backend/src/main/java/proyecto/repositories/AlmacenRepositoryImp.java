package proyecto.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import proyecto.entities.AlmacenEntity;

import java.util.List;

@Repository
public class AlmacenRepositoryImp implements AlmacenRepository{
    @Autowired
    private Sql2o sql2o;

    @Override
    public ResponseEntity<List<Object>> findAll() {
        try(Connection conn = sql2o.open()){
            List<AlmacenEntity> almacenes = conn.createQuery("SELECT id_almacen, nombre_almacen," +
                            "ST_Y(ubicacion::geometry) AS latitud, ST_X(ubicacion::geometry) AS longitud "+
                            "FROM almacen")
                    .executeAndFetch(AlmacenEntity.class);
            List<Object> result = (List) almacenes;
            if(almacenes.isEmpty()){
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<Object> findById(int id_almacen) {
        try(Connection conn = sql2o.open()){
            AlmacenEntity almacen = conn.createQuery("SELECT id_almacen, nombre_almacen," +
                            "ST_Y(ubicacion::geometry) AS latitud, ST_X(ubicacion::geometry) AS longitud "+
                            "FROM almacen WHERE id_almacen = :id_almacen")
                    .addParameter("id_almacen", id_almacen)
                    .executeAndFetchFirst(AlmacenEntity.class);
            if(almacen == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(almacen);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<Object> create(AlmacenEntity almacen) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("INSERT INTO almacen (nombre_almacen, ubicacion) " +
                            "VALUES (:nombre_almacen, ST_SetSRID(ST_MakePoint(:longitud, :latitud), 4326)::geography)",
                            true)
                    .addParameter("nombre_almacen", almacen.getNombre_almacen())
                    .addParameter("latitud", almacen.getLatitud()) // Agregar la latitud
                    .addParameter("longitud", almacen.getLongitud()) // Agregar la longitud
                    .executeUpdate();
            return ResponseEntity.ok(almacen);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<Object> update(AlmacenEntity almacen){
        try(Connection conn = sql2o.open()){
            conn.createQuery("UPDATE almacen " +
                            "SET nombre_almacen = :nombre_almacen," +
                            " ubicacion = ST_SetSRID(ST_MakePoint(:longitud, :latitud), 4326)::geography " +
                            "WHERE id_almacen = :id_almacen")
                    .addParameter("nombre_almacen", almacen.getNombre_almacen())
                    .addParameter("latitud", almacen.getLatitud()) // Agregar la latitud
                    .addParameter("longitud", almacen.getLongitud()) // Agregar la longitud
                    .addParameter("id_almacen", almacen.getId_almacen())
                    .executeUpdate();
            return ResponseEntity.ok(almacen);
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<Object> delete(int id_almacen){
        try(Connection conn = sql2o.open()){
            conn.createQuery("DELETE FROM almacen WHERE id_almacen = :id_almacen")
                    .addParameter("id_almacen", id_almacen)
                    .executeUpdate();
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }


}
