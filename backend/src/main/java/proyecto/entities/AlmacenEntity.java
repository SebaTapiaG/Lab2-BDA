package proyecto.entities;

public class AlmacenEntity {
    /* Atributos
        id_almacen SERIAL (PK) Identificador único del almacén
        nombre_almacen VARCHAR(255) Nombre del almacén

     */
    private int id_almacen;
    private String nombre_almacen;
    //Agregamos latitud y longitud
    private double latitud;
    private double longitud;

    public AlmacenEntity(int id_almacen, String nombre_almacen, double latitud, double longitud) {
        this.id_almacen = id_almacen;
        this.nombre_almacen = nombre_almacen;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getId_almacen() {
        return id_almacen;
    }

    public String getNombre_almacen() {
        return nombre_almacen;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setId_almacen(int id_almacen) {
        this.id_almacen = id_almacen;
    }

    public void setNombre_almacen(String nombre_almacen) {
        this.nombre_almacen = nombre_almacen;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
