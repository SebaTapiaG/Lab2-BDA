package proyecto.repositories;

import proyecto.entities.ProductoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;


@Repository
public class ProductoRepositoryImp implements ProductoRepository {

    @Autowired
    private Sql2o sql2o;

    @Autowired
    CategoriaRepository categoriaRepository;
    @Override
    public List<ProductoEntity> findAll() {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM producto")
                    .executeAndFetch(ProductoEntity.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ProductoEntity findById(int id_producto) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM producto WHERE id_producto = :id_producto")
                    .addParameter("id_producto", id_producto)
                    .executeAndFetchFirst(ProductoEntity.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ProductoEntity findByNombre(String nombre) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM producto WHERE nombre = :nombre")
                    .addParameter("nombre", nombre)
                    .executeAndFetchFirst(ProductoEntity.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
  //Retorna la lista de productos de una categoria
    public List<ProductoEntity> findByCategoria(int id_categoria) {
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM producto WHERE id_categoria = :id_categoria")
                    .addParameter("id_categoria", id_categoria)
                    .executeAndFetch(ProductoEntity.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ProductoEntity create(ProductoEntity producto) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("INSERT INTO producto (nombre, precio, stock, id_categoria) VALUES (:nombre, :precio, :stock, :id_categoria)", true)
                    .addParameter("nombre", producto.getNombre())
                    .addParameter("precio", producto.getPrecio())
                    .addParameter("stock", producto.getStock())
                    .addParameter("id_categoria", producto.getId_categoria())
                    .executeUpdate();
            return producto;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public ProductoEntity update(ProductoEntity producto) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("UPDATE producto SET nombre = :nombre, precio = :precio, stock = :stock, id_categoria = :id_categoria WHERE id_producto = :id_producto")
                    .addParameter("nombre", producto.getNombre())
                    .addParameter("precio", producto.getPrecio())
                    .addParameter("stock", producto.getStock())
                    .addParameter("id_categoria", producto.getId_categoria())
                    .addParameter("id_producto", producto.getId_producto())
                    .executeUpdate();
            return producto;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean delete(int id_producto) {
        try(Connection conn = sql2o.open()){
            conn.createQuery("DELETE FROM producto WHERE id_producto = :id_producto")
                    .addParameter("id_producto", id_producto)
                    .executeUpdate();
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }


}
