package pe.com.gmdsa.sedapal.domain.ro.request;

public class EventoDiarioRequest {
    //private String fecha;
    //private String surtidor;
    private String m3;
    private String foto;
    private int evento; //1 apertura , 2 cierre

    /*
    public EventoDiarioRequest(String fecha, String surtidor, float m3, String foto, int evento) {
        this.fecha = fecha;
        this.surtidor = surtidor;
        this.m3 = m3;
        this.foto = foto;
        this.evento = evento;
    }
    */

    public EventoDiarioRequest(String m3, String foto, int evento) {
        this.m3 = m3;
        this.foto = foto;
        this.evento = evento;
    }

    /*
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSurtidor() {
        return surtidor;
    }

    public void setSurtidor(String surtidor) {
        this.surtidor = surtidor;
    }
    */

    public String getM3() {
        return m3;
    }

    public void setM3(String m3) {
        this.m3 = m3;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getEvento() {
        return evento;
    }

    public void setEvento(int evento) {
        this.evento = evento;
    }
}
