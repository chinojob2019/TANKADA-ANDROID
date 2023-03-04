package pe.com.gmdsa.sedapal.domain.ro.request;

/**
 * Created by jmauriciog on 07/06/2016.
 */
public class LoginRequest {

    private String usuario;
    private String clave;

    public LoginRequest(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return clave;
    }

    public void setContrasena(String clave) {
        this.clave = clave;
    }
}
