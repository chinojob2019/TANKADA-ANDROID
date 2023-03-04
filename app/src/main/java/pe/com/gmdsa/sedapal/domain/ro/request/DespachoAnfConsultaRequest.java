package pe.com.gmdsa.sedapal.domain.ro.request;

public class DespachoAnfConsultaRequest {

    String placa;

    public DespachoAnfConsultaRequest(String placa) {
        this.placa = placa;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
