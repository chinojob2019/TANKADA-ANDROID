package pe.com.gmdsa.sedapal.domain.ro.response;

import pe.com.gmdsa.sedapal.domain.model.User;

/**
 * Created by jmauriciog on 30/12/2016.
 */

public class LoginResponse extends BaseResponse {

    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        data = data;
    }

}
