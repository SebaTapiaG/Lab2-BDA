package proyecto.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import proyecto.dto.RepartidorDTO;
import proyecto.entities.Detalle_OrdenEntity;
import proyecto.entities.OrdenEntity;
import proyecto.entities.ProductoEntity;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@Repository
public class OrdenRepositoryImp implements OrdenRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public ResponseEntity<List<Object>> findAll() {
        try(Connection conn = sql2o.open()){
            List<OrdenEntity> ordenes = conn.createQuery("SELECT id_orden, fecha_orden, estado, id_cliente, total," +
                    "ST_Y(ubicacion::geometry) AS latitud, ST_X(ubicacion::geometry) AS longitud " + "FROM orden")
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
            OrdenEntity orden = conn.createQuery("SELECT id_orden, fecha_orden, estado, id_cliente, total," +
                    "ST_Y(ubicacion::geometry) AS latitud, ST_X(ubicacion::geometry) AS longitud " + "FROM orden WHERE id_orden = :id_orden")
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
            List<OrdenEntity> ordenes = conn.createQuery("SELECT id_orden, fecha_orden, estado, id_cliente, total," +
                    "ST_Y(ubicacion::geometry) AS latitud, ST_X(ubicacion::geometry) AS longitud " + "FROM orden WHERE id_cliente = :id_cliente")
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
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try(Connection conn = sql2o.open()) {
            Integer ordenId = (Integer) conn.createQuery("INSERT INTO orden (fecha_orden, total, estado ,id_cliente,ubicacion) VALUES (:fecha_orden, :total,:estado, :id_cliente,ST_SetSRID(ST_MakePoint(:longitud, :latitud), 4326))", true)
                    .addParameter("fecha_orden", timestamp)
                    .addParameter("total", orden.getTotal())
                    .addParameter("estado", orden.getEstado())
                    .addParameter("id_cliente", orden.getId_cliente())
                    .addParameter("latitud", orden.getLatitud())
                    .addParameter("longitud", orden.getLongitud())
                    .executeUpdate().getKey();

            orden = conn.createQuery("SELECT id_orden, fecha_orden, estado, id_cliente, total," +
                            "ST_Y(ubicacion::geometry) AS latitud, ST_X(ubicacion::geometry) AS longitud " + "FROM orden WHERE id_orden = :id_orden")
                    .addParameter("id_orden", ordenId)
                    .executeAndFetchFirst(OrdenEntity.class);

            orden.setFecha_orden(timestamp);

            if (orden.getEstado().equals("pagada")) {

                // Recupera los productos y cantidades
                List<Detalle_OrdenEntity> detalles = conn.createQuery("SELECT * FROM detalle_orden WHERE id_orden = :id_orden")
                        .addParameter("id_orden", orden.getId_orden())
                        .executeAndFetch(Detalle_OrdenEntity.class);

                for (Integer i = 0; i < detalles.size(); i++) {

                    // Recupera producto
                    ProductoEntity producto = conn.createQuery("SELECT * FROM producto WHERE id_producto = :id_producto")
                            .addParameter("id_producto", detalles.get(i).getId_producto())
                            .executeAndFetchFirst(ProductoEntity.class);
                    producto.setStock(producto.getStock() - detalles.get(i).getCantidad());

                    // Actualiza stock
                    conn.createQuery("UPDATE producto SET nombre = :nombre, precio = :precio, stock = :stock, id_categoria = :id_categoria WHERE id_producto = :id_producto")
                            .addParameter("nombre", producto.getNombre())
                            .addParameter("precio", producto.getPrecio())
                            .addParameter("stock", producto.getStock())
                            .addParameter("id_categoria", producto.getId_categoria())
                            .addParameter("id_producto", producto.getId_producto())
                            .executeUpdate();
                }
            }
                return ResponseEntity.ok(orden);
            } catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.status(500).body(e.getMessage());
            }

    }

    @Override
    public ResponseEntity<Object> update(OrdenEntity orden) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try(Connection conn = sql2o.open()) {
            conn.createQuery("UPDATE orden SET fecha_orden = :fecha_orden, total = :total, estado= :estado, id_cliente = :id_cliente,ubicacion = ST_SetSRID(ST_MakePoint(:longitud, :latitud), 4326) WHERE id_orden = :id_orden")
                    .addParameter("fecha_orden", timestamp)
                    .addParameter("total", orden.getTotal())
                    .addParameter("estado", orden.getEstado())
                    .addParameter("id_cliente", orden.getId_cliente())
                    .addParameter("latitud", orden.getLatitud())
                    .addParameter("longitud", orden.getLongitud())
                    .addParameter("id_orden", orden.getId_orden())
                    .executeUpdate();

            orden = conn.createQuery("SELECT id_orden, fecha_orden, estado, id_cliente, total," +
                            "ST_Y(ubicacion::geometry) AS latitud, ST_X(ubicacion::geometry) AS longitud " + "FROM orden WHERE id_orden = :id_orden")
                    .addParameter("id_orden", orden.getId_orden())
                    .executeAndFetchFirst(OrdenEntity.class);

            orden.setFecha_orden(timestamp);

            if (orden.getEstado().equals("pagada")) {

                // Recupera los productos y cantidades
                List<Detalle_OrdenEntity> detalles = conn.createQuery("SELECT * FROM detalle_orden WHERE id_orden = :id_orden")
                        .addParameter("id_orden", orden.getId_orden())
                        .executeAndFetch(Detalle_OrdenEntity.class);

                for (Integer i = 0; i < detalles.size(); i++) {

                    // Recupera producto
                    ProductoEntity producto = conn.createQuery("SELECT * FROM producto WHERE id_producto = :id_producto")
                            .addParameter("id_producto", detalles.get(i).getId_producto())
                            .executeAndFetchFirst(ProductoEntity.class);
                    producto.setStock(producto.getStock() - detalles.get(i).getCantidad());

                    // Actualiza stock
                    conn.createQuery("UPDATE producto SET nombre = :nombre, precio = :precio, stock = :stock, id_categoria = :id_categoria WHERE id_producto = :id_producto")
                            .addParameter("nombre", producto.getNombre())
                            .addParameter("precio", producto.getPrecio())
                            .addParameter("stock", producto.getStock())
                            .addParameter("id_categoria", producto.getId_categoria())
                            .addParameter("id_producto", producto.getId_producto())
                            .executeUpdate();
                }
            }
            return ResponseEntity.ok(orden);
        } catch (Exception e){
            e.printStackTrace();
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

    @Override
    public ResponseEntity<Object> updateEstado(int id_orden, String estado) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("UPDATE orden SET estado = :estado WHERE id_orden = :id_orden")
                    .addParameter("estado", estado)
                    .addParameter("id_orden", id_orden)
                    .executeUpdate();
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    public ResponseEntity<List<RepartidorDTO>> findDeliveryCompletedInArea(int idZona) {
        String query = """
        SELECT DISTINCT r.nombre 
        FROM Orden o
        JOIN Repartidor r ON o.id_repartidor = r.id_repartidor
        JOIN Zonas z ON ST_Contains(z.area::GEOMETRY, o.ubicacion::GEOMETRY)
        WHERE z.id_zona = :idZona
        AND o.estado = 'completada';
        """;

        try (Connection conn = sql2o.open()) {
            List<RepartidorDTO> repartidores = conn.createQuery(query)
                    .addParameter("idZona", idZona)
                    .executeAndFetch(RepartidorDTO.class);

            System.out.println("Resultados encontrados: " + repartidores.size());

            return ResponseEntity.ok(repartidores);
        } catch (Exception e) {
            System.err.println("Error en la consulta: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }
}
