package proyecto.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import proyecto.entities.ProductoEntity;
import proyecto.entities.ZonaEntity;

import java.util.List;

@Repository
public class ZonaRepositoryImp implements ZonaRepository {
    @Autowired
    private Sql2o sql2o;

    @Override
    public ResponseEntity<List<Object>> findAll() {
        try (Connection conn = sql2o.open()) {
            List<ZonaEntity> zonas = conn.createQuery("SELECT id_zona, nombre, estado, ST_AsText(area) AS area FROM zonas")
                    .executeAndFetch(ZonaEntity.class);
            List<Object> result = (List) zonas;
            if (zonas.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 No Content si no hay resultados
            }
            return ResponseEntity.ok(result); // 200 OK con los datos
    }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(null); // 500 Internal Server Error
        }
    }

    @Override
    public ResponseEntity<Object> findById(int id_zona) {
        // Validar el token si es necesario
        try (Connection conn = sql2o.open()) {
            // Consultar la base de datos para obtener el producto por su ID
            ZonaEntity zona = conn.createQuery("SELECT id_zona, nombre, estado, ST_AsText(area) AS area FROM zonas WHERE id_zona = :id_zona")
                    .addParameter("id_zona", id_zona)
                    .executeAndFetchFirst(ZonaEntity.class);

            // Si el producto existe, devolverlo con un código 200 OK
            if (zona != null) {
                return ResponseEntity.ok(zona);
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
    public ResponseEntity<List<Object>> findByEstado(String estado) {
        try (Connection conn = sql2o.open()) {
            List<ZonaEntity> zonas = conn.createQuery("SELECT id_zona, nombre, estado, ST_AsText(area) AS area FROM zonas WHERE estado = :estado")
                    .executeAndFetch(ZonaEntity.class);
            List<Object> result = (List) zonas;
            if (zonas.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 No Content si no hay resultados
            }
            return ResponseEntity.ok(result); // 200 OK con los datos
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(500).body(null); // 500 Internal Server Error
        }
    }

    @Override
    public ResponseEntity<Object> create(ZonaEntity zona) {
        try (Connection conn = sql2o.open()) {
            int id_zona = (int) conn.createQuery(
                            "INSERT INTO zonas (nombre, estado, area) " +
                                    "VALUES (:nombre, :estado, ST_GeogFromText('SRID=4326;' || :area))", true
                    )
                    .addParameter("nombre", zona.getNombre())
                    .addParameter("estado", zona.getEstado())
                    .addParameter("area", zona.getArea()) // El área se pasa en formato WKT
                    .executeUpdate()
                    .getKey();
            zona.setId(id_zona); // Actualizar el ID de la zona creada
            return ResponseEntity.ok(zona);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    /**
     * **UPDATE**: Actualiza una Zona existente en la base de datos.
     * @param zona El objeto ZonaEntity con los datos actualizados.
     * @return ResponseEntity con el objeto actualizado o un mensaje de error.
     */
    @Override
    public ResponseEntity<Object> update(ZonaEntity zona) {
        try (Connection conn = sql2o.open()) {
            int rowsAffected = conn.createQuery(
                            "UPDATE zonas SET " +
                                    "nombre = :nombre, " +
                                    "estado = :estado, " +
                                    "area = ST_GeogFromText('SRID=4326;' || :area) " +
                                    "WHERE id_zona = :id_zona"
                    )
                    .addParameter("nombre", zona.getNombre())
                    .addParameter("estado", zona.getEstado())
                    .addParameter("area", zona.getArea()) // El área se pasa en formato WKT
                    .addParameter("id_zona", zona.getId())
                    .executeUpdate()
                    .getResult();

            if (rowsAffected == 0) {
                return ResponseEntity.status(404).body("No se encontró la zona con id_zona: " + zona.getId());
            }
            return ResponseEntity.ok(zona);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    /**
     * **DELETE**: Elimina una Zona de la base de datos.
     * @param id_zona El ID de la zona que se desea eliminar.
     * @return ResponseEntity indicando si se eliminó correctamente o no.
     */
    @Override
    public ResponseEntity<Object> delete(int id_zona) {
        try (Connection conn = sql2o.open()) {
            int rowsAffected = conn.createQuery(
                            "DELETE FROM zonas WHERE id_zona = :id_zona"
                    )
                    .addParameter("id_zona", id_zona)
                    .executeUpdate()
                    .getResult();

            if (rowsAffected == 0) {
                return ResponseEntity.status(404).body("No se encontró la zona con id_zona: " + id_zona);
            }
            return ResponseEntity.ok("Zona eliminada con éxito");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

}
