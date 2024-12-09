package proyecto.dto;

public class ProductoMasCompradoDTO {
    private int id_producto;
    private String nombre;
    private double total_comprado;

    public ProductoMasCompradoDTO(int id_producto, String nombre, double total_comprado) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.total_comprado = total_comprado;
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

    public double getTotal_comprado() {
        return total_comprado;
    }

    public void setTotal_comprado(double total_comprado) {
        this.total_comprado = total_comprado;
    }
}
