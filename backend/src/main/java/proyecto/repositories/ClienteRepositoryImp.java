package proyecto.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import proyecto.entities.ClienteEntity;

import java.util.List;

@Repository
public class ClienteRepositoryImp implements ClienteRepository{

    @Autowired
    private Sql2o sql2o;



    @Override
    public List<ClienteEntity> findAll() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM cliente")
                    .executeAndFetch(ClienteEntity.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ClienteEntity findById(int id_cliente) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM cliente WHERE id_cliente = :id_cliente")
                    .addParameter("id_cliente", id_cliente)
                    .executeAndFetchFirst(ClienteEntity.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ClienteEntity findByEmail(String email) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM cliente WHERE email = :email")
                    .addParameter("email", email)
                    .executeAndFetchFirst(ClienteEntity.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ClienteEntity create(ClienteEntity cliente) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("INSERT INTO cliente (nombre, telefono, email, direccion) VALUES (:nombre, :apellido, :telefono, :email, :direccion)", true)
                    .addParameter("nombre", cliente.getNombre())
                    .addParameter("telefono", cliente.getTelefono())
                    .addParameter("email", cliente.getEmail())
                    .addParameter("direccion", cliente.getDireccion())
                    .executeUpdate();
            return cliente;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ClienteEntity update(ClienteEntity cliente) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("UPDATE cliente SET nombre = :nombre, telefono = :telefono, email = :email, direccion = :direccion WHERE id_cliente = :id_cliente")
                    .addParameter("nombre", cliente.getNombre())
                    .addParameter("telefono", cliente.getTelefono())
                    .addParameter("email", cliente.getEmail())
                    .addParameter("direccion", cliente.getDireccion())
                    .addParameter("id_cliente", cliente.getId_cliente())
                    .executeUpdate();
            return cliente;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean delete(int id_cliente) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("DELETE FROM cliente WHERE id_cliente = :id_cliente")
                    .addParameter("id_cliente", id_cliente)
                    .executeUpdate();
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }


}
