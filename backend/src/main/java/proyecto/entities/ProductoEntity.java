package proyecto.entities;

public class ProductoEntity {
    /* Atributos
    Campo Tipo de dato Descripción
    id_producto SERIAL (PK) Identificador único del producto
    nombre VARCHAR(255) Nombre del producto
    descripcion TEXT Descripción del producto
    precio DECIMAL(10, 2) Precio del producto
    stock INT Cantidad disponible del producto
    estado VARCHAR(50) Estado del producto (&quot;disponible&quot;, &quot;agotado&quot;)
    id_categoria INTEGER (FK) Clave foránea que referencia a la categoría
     */

    private int id_producto;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private String estado;
    private int id_categoria;

    public ProductoEntity(int id_producto,String nombre, String descripcion, double precio, int stock, String estado, int id_categoria) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
        this.id_categoria = id_categoria;

    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }
}
