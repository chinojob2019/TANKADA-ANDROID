package pe.com.gmdsa.sedapal.domain.ro.request;

import java.util.List;

public class DespachoVentasRequest {
    int tipo;
    String codigo;

    public DespachoVentasRequest(int tipo, String codigo) {
        this.tipo = tipo;
        this.codigo = codigo;
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
}
