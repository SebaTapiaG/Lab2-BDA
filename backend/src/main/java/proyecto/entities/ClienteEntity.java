package proyecto.entities;

public class ClienteEntity {

    /* Atributos
    Campo Tipo de dato Descripción
    id_cliente SERIAL (PK) Identificador único del cliente
    nombre VARCHAR(255) Nombre completo del cliente
    direccion VARCHAR(255) Dirección del cliente
    email VARCHAR(100) Correo electrónico del cliente
    telefono VARCHAR(20) Teléfono de contacto
     */

    private int id_cliente;
    private String nombre;
    private String direccion;
    private String email;
    private String telefono;

    public ClienteEntity(int id_cliente, String nombre, String direccion, String email, String telefono) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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


}
