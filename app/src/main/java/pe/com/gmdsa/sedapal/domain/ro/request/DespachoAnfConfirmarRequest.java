package pe.com.gmdsa.sedapal.domain.ro.request;

public class DespachoAnfConfirmarRequest {
    private int idCamion;
    private int codigoEvento;
    private int flag;
    private int volumen;
    private int lectura_final;
    private int lectura_inicial;

    public DespachoAnfConfirmarRequest(int idCamion, int codigoEvento) {
        this.idCamion = idCamion;
        this.codigoEvento = codigoEvento;
    }

    public DespachoAnfConfirmarRequest(int idCamion, int codigoEvento, int flag, int volumen, int lectura_final, int lectura_inicial) {
        this.idCamion = idCamion;
        this.codigoEvento = codigoEvento;
        this.flag = flag;
        this.volumen = volumen;
        this.lectura_final = lectura_final;
        this.lectura_inicial = lectura_inicial;
    }

    public int getIdCamion() {
        return idCamion;
    }

    public void setIdCamion(int idCamion) {
        this.idCamion = idCamion;
    }

    public int getCodigoEvento() {
        return codigoEvento;
    }

    public void setCodigoEvento(int codigoEvento) {
        this.codigoEvento = codigoEvento;
    }

    public double getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public double getVolumen() {
        return volumen;
    }

    public void setVolumen(int volumen) {
        this.volumen = volumen;
    }

    public double getLectura_final() {
        return lectura_final;
    }

    public void setLectura_final(int lectura_final) {
        this.lectura_final = lectura_final;
    }

    public double getLectura_inicial() {
        return lectura_inicial;
    }

    public void setLectura_inicial(int lectura_inicial) {
        this.lectura_inicial = lectura_inicial;
    }
}
