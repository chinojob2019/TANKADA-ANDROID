package pe.com.gmdsa.sedapal.domain.ro.response;


import java.util.List;

import pe.com.gmdsa.sedapal.domain.model.Despacho;

public class ConsultaDiariaResponse extends BaseResponse {

    ConsultaDiariaResponseData data;

    public ConsultaDiariaResponseData getData() {
        return data;
    }

    public void setData(ConsultaDiariaResponseData data) {
        this.data = data;
    }

    public class ConsultaDiariaResponseData {
        private String totalDespacho;
        private String diferenciaAperturada;
        private List<Despacho> resultado;

        public ConsultaDiariaResponseData(String totalDespacho, String diferenciaAperturada, List<Despacho> resultado) {
            this.totalDespacho = totalDespacho;
            this.diferenciaAperturada = diferenciaAperturada;
            this.resultado = resultado;
        }

        public String getTotalDespacho() {
            return totalDespacho;
        }

        public void setTotalDespacho(String totalDespacho) {
            this.totalDespacho = totalDespacho;
        }

        public String getDiferenciaAperturada() {
            return diferenciaAperturada;
        }

        public void setDiferenciaAperturada(String diferenciaAperturada) {
            this.diferenciaAperturada = diferenciaAperturada;
        }

        public List<Despacho> getResultado() {
            return resultado;
        }

        public void setResultado(List<Despacho> resultado) {
            this.resultado = resultado;
        }


    }

}
