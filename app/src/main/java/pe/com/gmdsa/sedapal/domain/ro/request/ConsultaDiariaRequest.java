package pe.com.gmdsa.sedapal.domain.ro.request;

public class ConsultaDiariaRequest {

    String fecha;

    public ConsultaDiariaRequest(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
