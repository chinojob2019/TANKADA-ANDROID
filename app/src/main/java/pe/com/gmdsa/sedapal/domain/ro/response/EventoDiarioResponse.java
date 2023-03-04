package pe.com.gmdsa.sedapal.domain.ro.response;


import pe.com.gmdsa.sedapal.domain.model.Despacho;

public class EventoDiarioResponse extends BaseResponse {

    Despacho data;

    public Despacho getData() {
        return data;
    }

    public void setData(Despacho data) {
        this.data = data;
    }
}
