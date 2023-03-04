package pe.com.gmdsa.sedapal.domain.ro.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import pe.com.gmdsa.sedapal.domain.model.Despacho;
import pe.com.gmdsa.sedapal.domain.model.Evento;


public class DespachoAnfConsultaResponse extends BaseResponse {


    DespachoAnfConsultaResponseData data;

    public DespachoAnfConsultaResponseData getData() {
        return data;
    }

    public void setData(DespachoAnfConsultaResponseData data) {
        this.data = data;
    }

    public static class DespachoAnfConsultaResponseData implements Parcelable {
        String placa;
        int idCamion;
        Despacho despacho;
        ArrayList<Evento> eventosANF;

        public DespachoAnfConsultaResponseData(String placa, int idCamion, Despacho despacho, ArrayList<Evento> eventosANF) {
            this.placa = placa;
            this.idCamion = idCamion;
            this.despacho = despacho;
            this.eventosANF = eventosANF;
        }

        public int getIdCamion() {
            return idCamion;
        }

        public void setIdCamion(int idCamion) {
            this.idCamion = idCamion;
        }

        public String getPlaca() {
            return placa;
        }

        public void setPlaca(String placa) {
            this.placa = placa;
        }

        public Despacho getDespacho() {
            return despacho;
        }

        public void setDespacho(Despacho despacho) {
            this.despacho = despacho;
        }

        public ArrayList<Evento> getEventosANF() {
            return eventosANF;
        }

        public void setEventosANF(ArrayList<Evento> eventosANF) {
            this.eventosANF = eventosANF;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.placa);
            dest.writeInt(this.idCamion);
            dest.writeParcelable(this.despacho, flags);
            dest.writeTypedList(this.eventosANF);
        }

        protected DespachoAnfConsultaResponseData(Parcel in) {
            this.placa = in.readString();
            this.idCamion = in.readInt();
            this.despacho = in.readParcelable(Despacho.class.getClassLoader());
            this.eventosANF = in.createTypedArrayList(Evento.CREATOR);
        }

        public static final Parcelable.Creator<DespachoAnfConsultaResponseData> CREATOR = new Parcelable.Creator<DespachoAnfConsultaResponseData>() {
            @Override
            public DespachoAnfConsultaResponseData createFromParcel(Parcel source) {
                return new DespachoAnfConsultaResponseData(source);
            }

            @Override
            public DespachoAnfConsultaResponseData[] newArray(int size) {
                return new DespachoAnfConsultaResponseData[size];
            }
        };
    }
}
