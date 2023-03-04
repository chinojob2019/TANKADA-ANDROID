package pe.com.gmdsa.sedapal.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Despacho implements Parcelable {
    private String fecha;
    private String hora;
    private String surtidor;
    private String tipoDespacho;
    private String placa;
    private int ticket;
    private String documento;
    private String capacidad;
    private String m3;
    private int estado;
    private String tipo;
    private String observaciones;
    private String evento;

    private String flag;
    private String volumen;
    private String lectura_final;
    private String lectura_inicial;



    public Despacho() {
    }


    public Despacho(String fecha, String hora, String surtidor, String tipoDespacho, String placa, int ticket, String documento, String capacidad, String m3, int estado, String tipo, String observaciones, String evento) {
        this.fecha = fecha;
        this.hora = hora;
        this.surtidor = surtidor;
        this.tipoDespacho = tipoDespacho;
        this.placa = placa;
        this.ticket = ticket;
        this.documento = documento;
        this.capacidad = capacidad;
        this.m3 = m3;
        this.estado = estado;
        this.tipo = tipo;
        this.observaciones = observaciones;
        this.evento = evento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getSurtidor() {
        return surtidor;
    }

    public void setSurtidor(String surtidor) {
        this.surtidor = surtidor;
    }

    public String getTipoDespacho() {
        return tipoDespacho;
    }

    public void setTipoDespacho(String tipoDespacho) {
        this.tipoDespacho = tipoDespacho;
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

    public String getM3() {
        return m3;
    }

    public void setM3(String m3) {
        this.m3 = m3;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getVolumen() {
        return volumen;
    }

    public void setVolumen(String volumen) {
        this.volumen = volumen;
    }

    public String getLectura_final() {
        return lectura_final;
    }

    public void setLectura_final(String lectura_final) {
        this.lectura_final = lectura_final;
    }

    public String getLectura_inicial() {
        return lectura_inicial;
    }

    public void setLectura_inicial(String lectura_inicial) {
        this.lectura_inicial = lectura_inicial;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fecha);
        dest.writeString(this.hora);
        dest.writeString(this.surtidor);
        dest.writeString(this.tipoDespacho);
        dest.writeString(this.placa);
        dest.writeInt(this.ticket);
        dest.writeString(this.documento);
        dest.writeString(this.capacidad);
        dest.writeString(this.m3);
        dest.writeInt(this.estado);
        dest.writeString(this.tipo);
        dest.writeString(this.observaciones);
        dest.writeString(this.evento);
    }

    protected Despacho(Parcel in) {
        this.fecha = in.readString();
        this.hora = in.readString();
        this.surtidor = in.readString();
        this.tipoDespacho = in.readString();
        this.placa = in.readString();
        this.ticket = in.readInt();
        this.documento = in.readString();
        this.capacidad = in.readString();
        this.m3 = in.readString();
        this.estado = in.readInt();
        this.tipo = in.readString();
        this.observaciones = in.readString();
        this.evento = in.readString();
    }

    public static final Parcelable.Creator<Despacho> CREATOR = new Parcelable.Creator<Despacho>() {
        @Override
        public Despacho createFromParcel(Parcel source) {
            return new Despacho(source);
        }

        @Override
        public Despacho[] newArray(int size) {
            return new Despacho[size];
        }
    };
}
