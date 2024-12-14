package proyecto.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import proyecto.entities.ClienteEntity;
import proyecto.entities.RepartidorEntity;
import proyecto.utils.InputVerificationService;
import proyecto.utils.JwtMiddlewareService;

import java.util.Collections;
import java.util.List;

@Repository
public class RepartidorRepositoryImp implements RepartidorRepository {
    @Autowired
    private Sql2o sql2o;

    @Override
    public ResponseEntity<Object> findByNombre(String name) {
        try (Connection conn = sql2o.open()) {
            RepartidorEntity repartidor = conn.createQuery("SELECT id_repartidor, nombre, contrasena, email, telefono, id_almacen" +
                            "FROM Repartidor repartidor WHERE nombre = :nombre")
                    .addParameter("nombre", name)
                    .executeAndFetchFirst(RepartidorEntity.class);
            if (repartidor == null) {
                return ResponseEntity.status(404).body(null);
            }
            return ResponseEntity.ok(repartidor);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<Object> findByEmail(String email) {
        try (Connection conn = sql2o.open()) {
            RepartidorEntity repartidor = conn.createQuery("SELECT id_repartidor, nombre, contrasena, email, telefono, id_almacen" +
                            "FROM Repartidor WHERE email = :email")
                    .addParameter("email", email)
                    .executeAndFetchFirst(RepartidorEntity.class);
            if (repartidor == null) {
                return ResponseEntity.status(404).body(null);
            }
            return ResponseEntity.ok(repartidor);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<Object> findById(int id_repartidor) {
        try (Connection conn = sql2o.open()) {
            RepartidorEntity repartidor = conn.createQuery("SELECT id_repartidor, nombre, contrasena, email, telefono, id_almacen" +
                            "FROM Repartidor WHERE id_repartidor = :id_repartidor")
                    .addParameter("id_repartidor", id_repartidor)
                    .executeAndFetchFirst(RepartidorEntity.class);
            if (repartidor == null) {
                return ResponseEntity.status(404).body(null);
            }
            return ResponseEntity.ok(repartidor);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<List<Object>> findAll() {
        try (Connection conn = sql2o.open()) {
            List<RepartidorEntity> repartidores = conn.createQuery("SELECT id_repartidor, nombre, contrasena, email, telefono, id_almacen" +
                            "FROM Repartidor")
                    .executeAndFetch(RepartidorEntity.class);
            if (repartidores.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok((List) repartidores);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<List<RepartidorEntity>> findByAlmacen(int idAlmacen) {
        try (Connection conn = sql2o.open()) {
            // Cambiar executeAndFetchFirst por executeAndFetch para obtener todos los repartidores
            List<RepartidorEntity> repartidores = conn.createQuery("SELECT id_repartidor, nombre, contrasena, email, telefono, id_almacen" +
                            " FROM Repartidor WHERE id_almacen = :idAlmacen")
                    .addParameter("id_almacen", idAlmacen)
                    .executeAndFetch(RepartidorEntity.class);

            if (repartidores.isEmpty()) {
                return ResponseEntity.status(404).body(null); // Si no hay repartidores, devolver 404
            }

            return ResponseEntity.ok(repartidores); // Devolver lista de repartidores
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // En caso de error, devolver 500
        }
    }

    @Override
    public ResponseEntity<Object> update(RepartidorEntity repartidor) {
        try (Connection conn = sql2o.open()) {
            int rowsAffected = conn.createQuery(
                            "UPDATE repartidor " +
                                    "SET nombre = :nombre, " +
                                    "telefono = :telefono, " +
                                    "email = :email, " +
                                    "id_almacen = :id_almacen " +
                                    "WHERE id_repartidor = :id_repartidor"
                    )
                    .addParameter("nombre", repartidor.getNombre())
                    .addParameter("telefono", repartidor.getTelefono())
                    .addParameter("email", repartidor.getEmail())
                    .addParameter("id_almacen", repartidor.getId_almacen()) // Si se necesita actualizar el almacén
                    .addParameter("id_repartidor", repartidor.getId_repartidor())
                    .executeUpdate().getResult();

            if (rowsAffected == 0) {
                return ResponseEntity.status(404).body(null); // 404 Not Found si no se actualizó nada
            }

            return ResponseEntity.ok(repartidor); // 200 OK con el repartidor actualizado
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null); // 500 Internal Server Error
        }
    }

    @Override
    public ResponseEntity<Object> delete(int idRepartidor) {
        try (Connection conn = sql2o.open()) {
            int rowsAffected = conn.createQuery("DELETE FROM repartidor WHERE id_repartidor = :idRepartidor")
                    .addParameter("id_repartidor", idRepartidor)
                    .executeUpdate().getResult();

            if (rowsAffected == 0) {
                return ResponseEntity.status(404).body(false); // 404 Not Found si no se encontró el repartidor
            }

            return ResponseEntity.ok(true); // 200 OK si se eliminó correctamente
        } catch (Exception e) {
            return ResponseEntity.status(500).body(false); // 500 Internal Server Error
        }
    }

    @Override
    public ResponseEntity<Object> register(RepartidorEntity repartidor) {
        try (Connection connection = sql2o.open()) {
            // Validación de entradas
            if (!InputVerificationService.validateInput(repartidor.getNombre()) ||
                    !InputVerificationService.validateInput(repartidor.getEmail()) ||
                    !InputVerificationService.validateInput(repartidor.getContrasena())) {
                return ResponseEntity.badRequest().body("Error al crear el repartidor: caracteres no permitidos.");
            }

            // Verificación de si ya existe un repartidor con el mismo nombre o correo
            Integer count = connection.createQuery("SELECT COUNT(*) FROM repartidor WHERE email = :email OR nombre = :nombre")
                    .addParameter("email", repartidor.getEmail())
                    .addParameter("nombre", repartidor.getNombre())
                    .executeScalar(Integer.class);

            if (count != null && count > 0) {
                return ResponseEntity.status(409).body("Ya existe un repartidor con el mismo nombre o email.");
            }

            // Inserción del nuevo repartidor
            Integer repartidorId = (Integer) connection.createQuery(
                            "INSERT INTO repartidor (nombre, email, contrasena, telefono, id_almacen) " +
                                    "VALUES (:nombre, :email, :contrasena, :telefono, :id_almacen)",
                            true)
                    .addParameter("nombre", repartidor.getNombre())
                    .addParameter("email", repartidor.getEmail())
                    .addParameter("contrasena", repartidor.getContrasena())
                    .addParameter("telefono", repartidor.getTelefono())
                    .addParameter("id_almacen", repartidor.getId_almacen()) // Asociando el repartidor con un almacén
                    .executeUpdate()
                    .getKey();

            // Estableciendo el ID del repartidor recién insertado
            repartidor.setId_repartidor(repartidorId);

            // Devolviendo la respuesta con el repartidor registrado
            return ResponseEntity.ok(repartidor);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> login(String email, String password) {
        if (!InputVerificationService.validateInput(email) || !InputVerificationService.validateInput(password)) {
            return ResponseEntity.badRequest().body("Error al iniciar sesión: caracteres no permitidos.");
        }

        try {
            RepartidorEntity repartidor = (RepartidorEntity) findByEmail(email).getBody();

            if (repartidor == null) {
                return ResponseEntity.status(401).body("Repartidor no encontrado."); // 401 si no se encuentra el repartidor
            }

            if (repartidor.getContrasena().equals(password)) {
                // Eliminar la parte del token si no es necesario
                return ResponseEntity.ok(repartidor); // Devuelve el repartidor si la contraseña es correcta
            } else {
                return ResponseEntity.status(401).body("Contraseña incorrecta."); // 401 si la contraseña es incorrecta
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage()); // 500 si hay algún error
        }
    }
}
