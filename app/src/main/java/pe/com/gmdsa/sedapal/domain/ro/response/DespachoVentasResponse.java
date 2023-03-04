package pe.com.gmdsa.sedapal.domain.ro.response;

import java.util.List;

import pe.com.gmdsa.sedapal.domain.model.ItemDespatchSale;

public class DespachoVentasResponse extends BaseResponse {

    DespachosData data;

    public class DespachosData {
        private int tipo;
        private String codigo;
        private List<ItemDespatchSale> despachos;

        public DespachosData(int tipo, String codigo, List<ItemDespatchSale> despachos) {
            this.tipo = tipo;
            this.codigo = codigo;
            this.despachos = despachos;
        }

        public int getTipo() {
            return tipo;
        }

        public void setTipo(int tipo) {
            this.tipo = tipo;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public List<ItemDespatchSale> getDespachos() {
            return despachos;
        }

        public void setDespachos(List<ItemDespatchSale> despachos) {
            this.despachos = despachos;
        }
    }


    public DespachosData getData() {
        return data;
    }

    public void setData(DespachosData data) {
        this.data = data;
    }
}
