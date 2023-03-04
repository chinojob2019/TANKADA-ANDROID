package pe.com.gmdsa.sedapal.domain.ro.request;

public class RecuperarContrasenaRequest {
    String usuario;

    public RecuperarContrasenaRequest(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
