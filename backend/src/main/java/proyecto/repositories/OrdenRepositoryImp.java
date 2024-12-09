package proyecto.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import proyecto.entities.Detalle_OrdenEntity;
import proyecto.entities.OrdenEntity;
import proyecto.entities.ProductoEntity;

import java.text.SimpleDateFormat;
import java.sql.Timestamp;
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
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if (orden.getEstado().equals("pagada")) {
            try(Connection conn = sql2o.open()){
                Integer ordenId = (Integer) conn.createQuery("INSERT INTO orden (fecha_orden, total, estado ,id_cliente) VALUES (:fecha_orden, :total,:estado, :id_cliente)", true)
                        .addParameter("fecha_orden", timestamp)
                        .addParameter("total", orden.getTotal())
                        .addParameter("estado", orden.getEstado())
                        .addParameter("id_cliente", orden.getId_cliente())
                        .executeUpdate().getKey();
                orden.setId_orden(ordenId);
                orden.setFecha_orden(timestamp);

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
                return ResponseEntity.ok(orden);
            } catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.status(500).body(e.getMessage());
            }
        }else {
            try(Connection conn = sql2o.open()){
                Integer ordenId = (Integer) conn.createQuery("INSERT INTO orden (fecha_orden, total, estado ,id_cliente) VALUES (:fecha_orden, :total,:estado, :id_cliente)", true)
                        .addParameter("fecha_orden", timestamp)
                        .addParameter("total", orden.getTotal())
                        .addParameter("estado", orden.getEstado())
                        .addParameter("id_cliente", orden.getId_cliente())
                        .executeUpdate().getKey();
                orden.setId_orden(ordenId);
                orden.setFecha_orden(timestamp);

                return ResponseEntity.ok(orden);
            } catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.status(500).body(e.getMessage());
            }

        }
    }

    @Override
    public ResponseEntity<Object> update(OrdenEntity orden) {
        // Verifica el estado correctamente usando .equals()
        if (orden.getEstado().equals("pagada")) {
            try(Connection conn = sql2o.open()) {
                conn.createQuery("UPDATE orden SET fecha_orden = :fecha_orden, estado = :estado, total = :total, id_cliente = :id_cliente WHERE id_orden = :id_orden")
                        .addParameter("fecha_orden", orden.getFecha_orden())
                        .addParameter("total", orden.getTotal())
                        .addParameter("estado", orden.getEstado())
                        .addParameter("id_cliente", orden.getId_cliente())
                        .addParameter("id_orden", orden.getId_orden())
                        .executeUpdate();

                // Recupera los productos y cantidades
                List<Detalle_OrdenEntity> detalles = conn.createQuery("SELECT * FROM detalle_orden WHERE id_orden = :id_orden")
                        .addParameter("id_orden", orden.getId_orden())
                        .executeAndFetch(Detalle_OrdenEntity.class);

                for (Integer i = 0; i < detalles.size(); i++) {

                    // Recupera producto
                    ProductoEntity producto = conn.createQuery("SELECT * FROM producto WHERE id_producto = :id_producto")
                            .addParameter("id_producto", detalles.get(i).getId_producto())
                            .executeAndFetchFirst(ProductoEntity.class);
                    Integer newStock = producto.getStock() - detalles.get(i).getCantidad();

                    if (newStock == 0) {
                        producto.setEstado("agotado");
                        producto.setStock(newStock);

                        // Actualiza stock
                        conn.createQuery("UPDATE producto SET nombre = :nombre, precio = :precio,estado = :estado, stock = :stock, id_categoria = :id_categoria WHERE id_producto = :id_producto")
                                .addParameter("nombre", producto.getNombre())
                                .addParameter("precio", producto.getPrecio())
                                .addParameter("estado", producto.getEstado())
                                .addParameter("stock", producto.getStock())
                                .addParameter("id_categoria", producto.getId_categoria())
                                .addParameter("id_producto", producto.getId_producto())
                                .executeUpdate();
                    } else if (newStock < 0) {
                        OrdenRepositoryImp ordenRepositoryImp = new OrdenRepositoryImp();
                        ordenRepositoryImp.delete(orden.getId_orden());

                        for (Integer j = 0; j < i; j++) {
                            ProductoEntity auxproducto = conn.createQuery("SELECT * FROM producto WHERE id_producto = :id_producto")
                                    .addParameter("id_producto", detalles.get(j).getId_producto())
                                    .executeAndFetchFirst(ProductoEntity.class);
                            producto.setStock(producto.getStock() + detalles.get(i).getCantidad());

                            conn.createQuery("UPDATE producto SET nombre = :nombre, precio = :precio, stock = :stock, id_categoria = :id_categoria WHERE id_producto = :id_producto")
                                    .addParameter("nombre", auxproducto.getNombre())
                                    .addParameter("precio", auxproducto.getPrecio())
                                    .addParameter("stock", auxproducto.getStock())
                                    .addParameter("id_categoria", auxproducto.getId_categoria())
                                    .addParameter("id_producto", auxproducto.getId_producto())
                                    .executeUpdate();
                        }
                        orden.setEstado("fallida");
                        return ResponseEntity.ok(orden);
                    } else if (producto.getStock() < 0) {
                        OrdenRepositoryImp ordenRepositoryImp = new OrdenRepositoryImp();
                        ordenRepositoryImp.delete(orden.getId_orden());

                        for (Integer j = 0; j < i; j++) {
                            Detalle_OrdenRepositoryImp detalleRepositoryImp = new Detalle_OrdenRepositoryImp();
                            detalleRepositoryImp.delete(detalles.get(j).getId_orden());
                        }
                        orden.setEstado("fallida");
                        return ResponseEntity.ok(orden);
                    }
                }
                return ResponseEntity.ok(orden);
            }catch (Exception e) {
                e.printStackTrace();  // Para ayudar a depurar el error
                return ResponseEntity.status(500).body(e.getMessage());
            }
        } else {
            try(Connection conn = sql2o.open()){
                conn.createQuery("UPDATE orden SET fecha_orden = :fecha_orden, estado = :estado, total = :total, id_cliente = :id_cliente WHERE id_orden = :id_orden")
                        .addParameter("fecha_orden", orden.getFecha_orden())
                        .addParameter("total", orden.getTotal())
                        .addParameter("estado", orden.getEstado())
                        .addParameter("id_cliente", orden.getId_cliente())
                        .addParameter("id_orden", orden.getId_orden())
                        .executeUpdate();
                return ResponseEntity.ok(orden);
            } catch (Exception e) {
                e.printStackTrace();  // Para ayudar a depurar el error
                return ResponseEntity.status(500).body(e.getMessage());
            }
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
