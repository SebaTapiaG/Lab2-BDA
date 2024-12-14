package proyecto.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import proyecto.entities.ClienteEntity;
import proyecto.utils.InputVerificationService;
import proyecto.utils.JwtMiddlewareService;



import java.util.List;

@Repository
public class ClienteRepositoryImp implements ClienteRepository{

    @Autowired
    private Sql2o sql2o;

    @Autowired
    private JwtMiddlewareService jwtMiddlewareService;

    @Autowired
    private InputVerificationService inputVerificationService;



    @Override
    public ResponseEntity<List<Object>> findAll() {
        try (Connection conn = sql2o.open()) {
            List<ClienteEntity> clientes = conn.createQuery("SELECT id_cliente, nombre, contrasena, direccion, email, telefono, " +
                            "ST_Y(ubicacion::geometry) AS latitud, ST_X(ubicacion::geometry) AS longitud " +
                            "FROM Cliente")
                    .executeAndFetch(ClienteEntity.class);
            List<Object> result = (List) clientes;
            if (clientes.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<Object> findById(int id_cliente) {
        try (Connection conn = sql2o.open()) {
            ClienteEntity cliente = conn.createQuery("SELECT id_cliente, nombre, contrasena, direccion, email, telefono, " +
                    "ST_Y(ubicacion::geometry) AS latitud, ST_X(ubicacion::geometry) AS longitud " +
                    "FROM Cliente WHERE id_cliente = :id_cliente")
                    .addParameter("id_cliente", id_cliente)
                    .executeAndFetchFirst(ClienteEntity.class);
            if (cliente == null) {
                return ResponseEntity.status(404).body(null);
            }
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<Object> findByEmail(String email) {
        try (Connection conn = sql2o.open()) {
            ClienteEntity cliente = conn.createQuery("SELECT id_cliente, nombre, contrasena, direccion, email, telefono, " +
                    "ST_Y(ubicacion::geometry) AS latitud, ST_X(ubicacion::geometry) AS longitud " +
                    "FROM Cliente WHERE email = :email")
                    .addParameter("email", email)
                    .executeAndFetchFirst(ClienteEntity.class);
            if (cliente == null) {
                return ResponseEntity.status(404).body(null);
            }
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Override
    public ResponseEntity<Object> findByName(String name) {
        try (Connection conn = sql2o.open()) {
            ClienteEntity cliente = conn.createQuery("SELECT id_cliente, nombre, contrasena, direccion, email, telefono, " +
                    "ST_Y(ubicacion::geometry) AS latitud, ST_X(ubicacion::geometry) AS longitud " +
                    "FROM Cliente cliente WHERE nombre = :nombre")
                    .addParameter("nombre", name)
                    .executeAndFetchFirst(ClienteEntity.class);
            if (cliente == null) {
                return ResponseEntity.status(404).body(null);
            }
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    @Override
    public ResponseEntity<Object> createUser(ClienteEntity user) {
        try (Connection connection = sql2o.open()) {
            if (!InputVerificationService.validateInput(user.getNombre()) ||
                    !InputVerificationService.validateInput(user.getEmail()) ||
                    !InputVerificationService.validateInput(user.getContrasena())) {
                return ResponseEntity.badRequest().body("Error al crear el usuario: caracteres no permitidos.");
            }

            Integer count = connection.createQuery("SELECT COUNT(*) FROM cliente WHERE email = :email OR nombre = :nombre")
                    .addParameter("email", user.getEmail())
                    .addParameter("nombre", user.getNombre())
                    .executeScalar(Integer.class);

            if (count != null && count > 0) {
                return ResponseEntity.status(409).body("Ya existe un usuario con el mismo nombre o email.");
            }

<<<<<<< HEAD
            Integer userId = (Integer) connection.createQuery(
                            "INSERT INTO cliente (nombre, email, contrasena, direccion, telefono, ubicacion) " +
                                    "VALUES (:nombre, :email, :contrasena, :direccion, :telefono, ST_SetSRID(ST_MakePoint(:longitud, :latitud), 4326)::geography)",
                            true)
=======
            Integer userId = (Integer) connection.createQuery("INSERT INTO cliente (nombre, email, contrasena, direccion, telefono,ubicacion) VALUES (:nombre, :email, :contrasena, :direccion, :telefono, ST_SetSRID(ST_MakePoint(:longitud, :latitud), 4326))", true)
>>>>>>> 5ba0fa632b710fdd9137c0069a89861b08459b92
                    .addParameter("nombre", user.getNombre())
                    .addParameter("email", user.getEmail())
                    .addParameter("contrasena", user.getContrasena())
                    .addParameter("direccion", user.getDireccion())
                    .addParameter("telefono", user.getTelefono())
<<<<<<< HEAD
                    .addParameter("latitud", user.getLatitud()) // Agregar la latitud
                    .addParameter("longitud", user.getLongitud()) // Agregar la longitud
                    .executeUpdate()
                    .getKey();

=======
                    .addParameter("latitud", user.getLatitud())
                    .addParameter("longitud", user.getLongitud())
                    .executeUpdate().getKey();
>>>>>>> 5ba0fa632b710fdd9137c0069a89861b08459b92

            user.setId_cliente(userId);
            String token = jwtMiddlewareService.generateToken(user);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> update(ClienteEntity cliente) {
        try (Connection conn = sql2o.open()) {
<<<<<<< HEAD
            int rowsAffected = conn.createQuery(
                            "UPDATE cliente " +
                                    "SET nombre = :nombre, " +
                                    "telefono = :telefono, " +
                                    "email = :email, " +
                                    "direccion = :direccion, " +
                                    "ubicacion = ST_SetSRID(ST_MakePoint(:longitud, :latitud), 4326)::geography " +
                                    "WHERE id_cliente = :id_cliente"
                    )
=======
            int rowsAffected = conn.createQuery("UPDATE cliente SET nombre = :nombre, telefono = :telefono, email = :email, direccion = :direccion, ubicacion = ST_SetSRID(ST_MakePoint(:longitud, :latitud), 4326) WHERE id_cliente = :id_cliente")
>>>>>>> 5ba0fa632b710fdd9137c0069a89861b08459b92
                    .addParameter("nombre", cliente.getNombre())
                    .addParameter("telefono", cliente.getTelefono())
                    .addParameter("email", cliente.getEmail())
                    .addParameter("direccion", cliente.getDireccion())
                    .addParameter("latitud", cliente.getLatitud()) // Nuevo parámetro de latitud
                    .addParameter("longitud", cliente.getLongitud()) // Nuevo parámetro de longitud
                    .addParameter("id_cliente", cliente.getId_cliente())
                    .addParameter("latitud", cliente.getLatitud())
                    .addParameter("longitud", cliente.getLongitud())
                    .executeUpdate().getResult();

            if (rowsAffected == 0) {
                return ResponseEntity.status(404).body(null); // 404 Not Found
            }

            return ResponseEntity.ok(cliente); // 200 OK con el cliente actualizado
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null); // 500 Internal Server Error
        }
    }

    @Override
    public ResponseEntity<Object> delete(int id_cliente) {
        try (Connection conn = sql2o.open()) {
            int rowsAffected = conn.createQuery("DELETE FROM cliente WHERE id_cliente = :id_cliente")
                    .addParameter("id_cliente", id_cliente)
                    .executeUpdate().getResult();
            if (rowsAffected == 0) {
                return ResponseEntity.status(404).body(false);
            }
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(false);
        }
    }

    @Override
    public ResponseEntity<Object> loginUser(String email, String password) {
        if (!InputVerificationService.validateInput(email) || !InputVerificationService.validateInput(password)) {
            return ResponseEntity.badRequest().body("Error al iniciar sesión: caracteres no permitidos.");
        }
        try {
            ClienteEntity user = (ClienteEntity) findByEmail(email).getBody();
            if (user == null) {
                return ResponseEntity.status(401).body("Usuario no encontrado.");
            }
            if (user.getContrasena().equals(password)) {
                String token = jwtMiddlewareService.generateToken(user);
                return ResponseEntity.ok(token);
            } else {
                return ResponseEntity.status(401).body("Contraseña incorrecta.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
