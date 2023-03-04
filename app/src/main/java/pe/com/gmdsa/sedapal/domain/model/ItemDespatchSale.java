package pe.com.gmdsa.sedapal.domain.model;

public class ItemDespatchSale {

    String surtidor;
    String placa;
    int ticket;
    String documento;
    String capacidad;
    String observaciones;
    int IdDespacho;


    public ItemDespatchSale(String surtidor, String placa, int ticket, String documento, String capacidad, String observaciones, int IdDespacho) {
        this.surtidor = surtidor;
        this.placa = placa;
        this.ticket = ticket;
        this.documento = documento;
        this.capacidad = capacidad;
        this.observaciones = observaciones;
        this.IdDespacho = IdDespacho;
    }

    public String getSurtidor() {
        return surtidor;
    }

    public void setSurtidor(String surtidor) {
        this.surtidor = surtidor;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(String capacidad) {
        this.capacidad = capacidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getIdDespacho() {
        return IdDespacho;
    }

    public void setIdDespacho(int idDespacho) {
        IdDespacho = idDespacho;
    }
}
