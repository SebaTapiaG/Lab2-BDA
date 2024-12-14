package proyecto.entities;

public class RepartidorEntity {

    /* Atributos
    Campo Tipo de dato Descripción
    id_repartidor SERIAL (PK) Identificador único del repartidor
    nombre VARCHAR(255) Nombre completo del repartidor
    direccion VARCHAR(255) Dirección del repartidor
    email VARCHAR(100) Correo electrónico del repartidor
    telefono VARCHAR(20) Teléfono de contacto
     */

    private int id_repartidor;
    private String nombre;

    private String contrasena;
    private String email;
    private String telefono;
    private int id_almacen;

    public RepartidorEntity(int id_repartidor, String nombre, String contrasena, String email, String telefono, int id_almacen) {
        this.id_repartidor = id_repartidor;
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.email = email;
        this.telefono = telefono;
        this.id_almacen = id_almacen;
    }

    public RepartidorEntity() {
    }

    public int getId_repartidor() {
        return id_repartidor;
    }

    public void setId_repartidor(int id_repartidor) {
        this.id_repartidor = id_repartidor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getId_almacen() {
        return id_almacen;
    }
    public void setId_almacen(int id_almacen) {
        this.id_almacen = id_almacen;
    }
}
