package proyecto.entities;

public class CategoriaEntity {
    /* Atributos
    Campo Tipo de dato Descripción
    id_categoria SERIAL (PK) Identificador único de la categoría
    nombre VARCHAR(100) Nombre de la categoría
     */

    private int id_categoria;
    private String nombre;

    public CategoriaEntity(int id_categoria, String nombre) {
        this.id_categoria = id_categoria;
        this.nombre = nombre;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
