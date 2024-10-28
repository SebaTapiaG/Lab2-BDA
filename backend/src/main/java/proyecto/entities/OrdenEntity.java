package proyecto.entities;
import java.sql.Timestamp;

public class OrdenEntity {
    /* Atributos
    Campo Tipo de dato Descripción
    id_orden SERIAL (PK) Identificador único de la orden
    fecha_orden TIMESTAMP Fecha en que se realizó la orden
    estado VARCHAR(50) Estado de la orden (&quot;pendiente&quot;, &quot;pagada&quot;, &quot;enviada&quot;)
    id_cliente INTEGER (FK) Clave foránea que referencia al cliente
    total DECIMAL(10, 2) Total a pagar por la orden
     */

    private int id_orden;
    private Timestamp fecha_orden;
    private String estado;
    private int id_cliente;
    private double total;

    public OrdenEntity(int id_orden, Timestamp fecha_orden, String estado, int id_cliente, double total) {
        this.id_orden = id_orden;
        this.fecha_orden = fecha_orden;
        this.estado = estado;
        this.id_cliente = id_cliente;
        this.total = total;
    }

    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public Timestamp getFecha_orden() {
        return fecha_orden;
    }

    public void setFecha_orden(Timestamp fecha_orden) {
        this.fecha_orden = fecha_orden;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


}
