package pe.com.gmdsa.sedapal.domain.ro.response;


import pe.com.gmdsa.sedapal.domain.model.Despacho;

public class DespachoAnfConfirmarResponse extends BaseResponse {

    DespachoAnfCofirmarData data;

    public DespachoAnfCofirmarData getData() {
        return data;
    }

    public void setData(DespachoAnfCofirmarData data) {
        this.data = data;
    }

    public class DespachoAnfCofirmarData {
        int tipo;
        Despacho despacho;
        String codigo;

        public int getTipo() {
            return tipo;
        }

        public void setTipo(int tipo) {
            this.tipo = tipo;
        }

        public Despacho getDespacho() {
            return despacho;
        }

        public void setDespacho(Despacho despacho) {
            this.despacho = despacho;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }
    }


}
