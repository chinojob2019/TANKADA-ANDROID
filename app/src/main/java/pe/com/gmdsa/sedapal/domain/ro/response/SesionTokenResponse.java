package pe.com.gmdsa.sedapal.domain.ro.response;


import pe.com.gmdsa.sedapal.domain.model.User;

public class SesionTokenResponse extends BaseResponse {
    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        data = data;
    }
}
