package pe.com.gmdsa.sedapal.domain.ro.response;

public class CerrarSesionResponse extends BaseResponse {

    String token;

    public CerrarSesionResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
