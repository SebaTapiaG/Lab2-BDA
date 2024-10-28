package proyecto.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import proyecto.entities.CategoriaEntity;

import java.util.List;

@Repository
public class CategoriaRepositoryImp implements CategoriaRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public List<CategoriaEntity> findAll() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM categoria")
                    .executeAndFetch(CategoriaEntity.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public CategoriaEntity findById(int id_categoria) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM categoria WHERE id_categoria = :id_categoria")
                    .addParameter("id_categoria", id_categoria)
                    .executeAndFetchFirst(CategoriaEntity.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public CategoriaEntity create(CategoriaEntity categoria) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("INSERT INTO categoria (nombre) VALUES (:nombre)", true)
                    .addParameter("nombre", categoria.getNombre())
                    .executeUpdate();
            return categoria;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public CategoriaEntity update(CategoriaEntity categoria) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("UPDATE categoria SET nombre = :nombre WHERE id_categoria = :id_categoria")
                    .addParameter("nombre", categoria.getNombre())
                    .addParameter("id_categoria", categoria.getId_categoria())
                    .executeUpdate();
            return categoria;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean delete(int id_categoria) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("DELETE FROM categoria WHERE id_categoria = :id_categoria")
                    .addParameter("id_categoria", id_categoria)
                    .executeUpdate();
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

}
