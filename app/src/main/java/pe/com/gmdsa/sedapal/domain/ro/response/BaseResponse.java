package pe.com.gmdsa.sedapal.domain.ro.response;


/**
 * Created by glarab on 03/06/2016.
 */

public class BaseResponse {

    private String codResult;
    private String msg;
    private String msgError;

    public String getCodResult() {
        return codResult;
    }

    public void setCodResult(String codResult) {
        this.codResult = codResult;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgError() {
        return msgError;
    }

    public void setMsgError(String msgError) {
        this.msgError = msgError;
    }
}
