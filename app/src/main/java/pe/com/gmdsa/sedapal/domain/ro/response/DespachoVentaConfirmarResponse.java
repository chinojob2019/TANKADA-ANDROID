package pe.com.gmdsa.sedapal.domain.ro.response;


import pe.com.gmdsa.sedapal.domain.model.Despacho;

public class DespachoVentaConfirmarResponse extends BaseResponse {

    DepachoData data;

    public DepachoData getData() {
        return data;
    }

    public void setData(DepachoData data) {
        this.data = data;
    }

    public class DepachoData{
        Despacho despacho;

        public Despacho getDespacho() {
            return despacho;
        }

        public void setDespacho(Despacho despacho) {
            this.despacho = despacho;
        }
    }
}
