package proyecto.entities;

public class ZonaEntity {
    private int id_zona; // Clave primaria
    private String nombre; // Nombre de la zona
    private String estado; // Estado de la zona (activo, inactivo, etc.)
    private String area; // Representación WKT (Well-Known Text) del POLYGON

    // Constructor por defecto
    public ZonaEntity() {}

    // Constructor con parámetros
    public ZonaEntity(int id_zona, String nombre, String estado, String area) {
        this.id_zona = id_zona;
        this.nombre = nombre;
        this.estado = estado;
        this.area = area;
    }

    // Getters y Setters
    public int getId() {
        return id_zona;
    }

    public void setId(int id_zona) {
        this.id_zona = id_zona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getArea() {
        return area;
    }

    public void setAreaWkt(String area) {
        this.area = area;
    }

    // Método toString para imprimir el objeto de forma legible
    @Override
    public String toString() {
        return "ZonaEntity{" +
                "id=" + id_zona +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}

