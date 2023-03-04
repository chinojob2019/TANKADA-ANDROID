package pe.com.gmdsa.sedapal.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Evento implements Parcelable {
    private int codigo;
    private String descripcion;

    public Evento(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.codigo);
        dest.writeString(this.descripcion);
    }

    protected Evento(Parcel in) {
        this.codigo = in.readInt();
        this.descripcion = in.readString();
    }

    public static final Creator<Evento> CREATOR = new Creator<Evento>() {
        @Override
        public Evento createFromParcel(Parcel source) {
            return new Evento(source);
        }

        @Override
        public Evento[] newArray(int size) {
            return new Evento[size];
        }
    };
}
