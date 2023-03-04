package pe.com.gmdsa.sedapal.domain.model;

/**
 * Created by jmauricio on 01/06/2016.
 */
public class User {

    private String fecha;
    private String nombre;
    private String surtidor;
    private String token;
    private int estado;
    private String mensaje;

    public User(String fecha, String nombre, String surtidor, String token, int estado, String mensaje) {
        this.fecha = fecha;
        this.nombre = nombre;
        this.surtidor = surtidor;
        this.token = token;
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public User(String fecha, String nombre, String surtidor, String token) {
        this.fecha = fecha;
        this.nombre = nombre;
        this.surtidor = surtidor;
        this.token = token;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSurtidor() {
        return surtidor;
    }

    public void setSurtidor(String surtidor) {
        this.surtidor = surtidor;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
