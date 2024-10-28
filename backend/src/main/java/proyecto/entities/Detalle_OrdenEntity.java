package proyecto.entities;

public class Detalle_OrdenEntity {
    /* Atributos
    Campo Tipo de dato Descripción
    id_detalle SERIAL (PK) Identificador único del detalle de la orden
    id_orden INTEGER (FK) Clave foránea que referencia a la orden
    id_producto INTEGER (FK) Clave foránea que referencia al producto
    cantidad INT Cantidad del producto en la orden
    precio_unitario DECIMAL(10, 2) Precio unitario del producto
     */

    private int id_detalle;
    private int id_orden;
    private int id_producto;
    private int cantidad;
    private double precio_unitario;

    public Detalle_OrdenEntity(int id_detalle, int id_orden, int id_producto, int cantidad, double precio_unitario) {
        this.id_detalle = id_detalle;
        this.id_orden = id_orden;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
    }

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }
}
