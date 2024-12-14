package proyecto.entities;

public class Almacen_ProductoEntity {
    /* Atributos
        id_almacen_producto SERIAL (PK) Identificador único del almacén_producto
        id_almacen INT (FK) Identificador único del almacén
        id_producto INT (FK) Identificador único del producto
        cantidad INT Cantidad de productos en el almacén
     */
    private int id_almacen_producto;
    private int id_almacen;
    private int id_producto;

    public Almacen_ProductoEntity(int id_almacen_producto, int id_almacen, int id_producto) {
        this.id_almacen_producto = id_almacen_producto;
        this.id_almacen = id_almacen;
        this.id_producto = id_producto;
    }

    public int getId_almacen_producto() {
        return id_almacen_producto;
    }

    public int getId_almacen() {
        return id_almacen;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_almacen_producto(int id_almacen_producto) {
        this.id_almacen_producto = id_almacen_producto;
    }

    public void setId_almacen(int id_almacen) {
        this.id_almacen = id_almacen;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }
}
