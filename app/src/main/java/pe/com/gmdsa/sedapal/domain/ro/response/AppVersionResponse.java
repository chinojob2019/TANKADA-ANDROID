package pe.com.gmdsa.sedapal.domain.ro.response;

public class AppVersionResponse extends BaseResponse {
    String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public class Version {
        int versionCode;
        boolean estado;

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public boolean isEstado() {
            return estado;
        }

        public void setEstado(boolean estado) {
            this.estado = estado;
        }

        @Override
        public String toString() {
            return "Version{" +
                    "versionCode=" + versionCode +
                    ", estado=" + estado +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AppVersionResponse{" +
                "data=" + data +
                '}';
    }
}

