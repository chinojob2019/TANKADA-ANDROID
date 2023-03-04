package pe.com.gmdsa.sedapal.domain.ro.request;

public class DespachoVentaConfirmarRequest {

    int IdDespacho;

    public DespachoVentaConfirmarRequest(int idDespacho) {
        IdDespacho = idDespacho;
    }

    public int getIdDespacho() {
        return IdDespacho;
    }

    public void setIdDespacho(int idDespacho) {
        IdDespacho = idDespacho;
    }
}
