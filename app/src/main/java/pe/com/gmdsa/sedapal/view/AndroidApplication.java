package pe.com.gmdsa.sedapal.view;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.bumptech.glide.request.target.ViewTarget;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import pe.com.gmdsa.sedapal.R;
import pe.com.gmdsa.sedapal.domain.model.User;
import pe.com.gmdsa.sedapal.domain.ro.RetrofitService;
import pe.com.gmdsa.sedapal.util.Constants;
import pe.com.gmdsa.sedapal.util.SSLUtil;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("all")
public class AndroidApplication extends Application {
    private static AndroidApplication sApplication;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        sApplication = this;
        ViewTarget.setTagId(R.id.glide_tag);
    }

    public static AndroidApplication getInstance() {
        return sApplication;
    }

    public void setUser(User user) {
        guardarUsuario(user);
    }

    public User getUser() {
        return obtenerUsuario();
    }

    private void guardarUsuario(User user) {
        if (user != null) {
            SharedPreferences.Editor editorData = getSharedPreferences(Constants.SESSION.USER_DATA, MODE_PRIVATE).edit();
            editorData.putString(Constants.SESSION.FECHA, user.getFecha());
            editorData.putString(Constants.SESSION.NOMBRE, user.getNombre());
            editorData.putString(Constants.SESSION.SURTIDOR, user.getSurtidor());
            editorData.putString(Constants.SESSION.TOKEN, user.getToken());
            editorData.apply();
        } else {

        }
    }

    public void setLecturaFinal(String lf) {
        SharedPreferences.Editor editorData = getSharedPreferences(Constants.SESSION.LEC_FINAL, MODE_PRIVATE).edit();
        editorData.putString(Constants.SESSION.L_FINAL, lf);
        editorData.apply();
    }

    public String getLecturaFinal() {
        SharedPreferences prefs = getSharedPreferences(Constants.SESSION.LEC_FINAL, MODE_PRIVATE);
        return prefs.getString(Constants.SESSION.L_FINAL, "");
    }

    public void cerraSesion() {
        SharedPreferences prefs = getSharedPreferences(Constants.SESSION.USER_DATA, MODE_PRIVATE);
        SharedPreferences.Editor editor_1 = prefs.edit();
        editor_1.clear();
        editor_1.apply();
        editor_1.commit();
    }

    private User obtenerUsuario() {
        SharedPreferences prefs = getSharedPreferences(Constants.SESSION.USER_DATA, MODE_PRIVATE);

        return new User(
                prefs.getString(Constants.SESSION.FECHA, ""),
                prefs.getString(Constants.SESSION.NOMBRE, ""),
                prefs.getString(Constants.SESSION.SURTIDOR, ""),
                prefs.getString(Constants.SESSION.TOKEN, "")
        );
    }

    /**
     * Retrofit Global methods
     **/
    public RetrofitService getService() {
        // if (retrofit == null) {
        Gson gson = new GsonBuilder()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVICES.URL_BASE_USADA)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getRequestHeader())
                .build();
        //}
        return retrofit.create(RetrofitService.class);
    }

    public String getTokenUser() {
        String token = "VACIO";
        SharedPreferences prefs = getSharedPreferences(Constants.SESSION.USER_DATA, MODE_PRIVATE);
        token = prefs.getString(Constants.SESSION.TOKEN, "VACIO");
       // token = "5CE23D9F8E1B89A5.21062021030601.96D884DBEE92C556";

        token = "E8A4FB7DBCA108D0.25102018031003.24920EF85ED19542";

        Log.d("TOKEN_USER", token);
        return token;
    }

    public String getDeviceImei() {

        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return TODO;
        }
        String deviceid = mTelephonyManager.getDeviceId() != null ? mTelephonyManager.getDeviceId() : "0";
        Log.d("TOKEN_USER_Imei", deviceid);

        return deviceid;
    }

    private OkHttpClient getRequestHeader() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.readTimeout(Constants.APP_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpBuilder.connectTimeout(Constants.APP_TIMEOUT, TimeUnit.MILLISECONDS);

        HttpLoggingInterceptor loLogging = new HttpLoggingInterceptor();
        loLogging.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request loOriginal = chain.request();

                Request newRequest = loOriginal.newBuilder()
                        .header("Token", getTokenUser())
                        .header("Content", "application/json")
                        .header("imei", getDeviceImei())
                        .method(loOriginal.method(), loOriginal.body())
                        .build();

                return chain.proceed(newRequest);
            }
        };
        okHttpBuilder.addInterceptor(loLogging);
        okHttpBuilder.addInterceptor(interceptor);

        try {
            final SSLSocketFactory sslSocketFactory = SSLUtil.getSSLConfig(getApplicationContext()).getSocketFactory();
            okHttpBuilder.sslSocketFactory(sslSocketFactory);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        okHttpBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
        return okHttpBuilder.build();
    }
}