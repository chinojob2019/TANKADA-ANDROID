package pe.com.gmdsa.sedapal.util;

import java.util.ArrayList;
import java.util.List;


import pe.com.gmdsa.sedapal.R;
import retrofit2.http.PUT;

/**
 * Created by jmauriciog on 02/06/2016.
 */
public class Constants {

    public static final int DEFAULT_SOCKET_TIMEOUT = 1;
    public static int SPLASH_DELAY = 4500;

    public static int APP_TIMEOUT = 1000000;
    public static int PLAY_SERVICES_TIMEOUT = 100000;

    public static int ASYNC_REQUEST = 1;
    public static int SYNC_REQUEST = 0;

    public static int SUCCESS_REQUEST = 1;
    public static int ERROR_REQUEST = 0;

    public static int IDENTIF_LECTOR_QR = 70;

    public static class SERVICES {
        public static String SUCCESSFUL_CODE = "0000";

        //public static String URL_BASE_DUMMIES = "http://10.240.147.38:8080/";
        //public static String URL_BASE_DUMMIES = "http://10.240.147.27:8180/WSOpenSGC/";
       // public static String URL_BASE_DUMMIES = "http://10.100.177.151:8080/WSOpenSGC-0.1/";//CANVIA DESA

      //  public static String URL_BASE_DUMMIES = "http://localhost:8080/"; //LOCAL PC
      public static String URL_BASE_DUMMIES = "http://192.168.1.12:8080/"; //LOCAL PC

        public static String URL_BASE_USADA = URL_BASE_DUMMIES;
        public static String URL_PRUEBA = "http://sedapal.getsandbox.com/";

        public static final String LOGIN_URL = "login";
        public static final String POST_LOGIN = "opLogin";
        public static final String POST_CERRAR_SESION = "opCerrarSesion";
        public static final String POST_CONSULTA_DIARIA = "opConsultaDiaria";
        public static final String POST_DESPACHO_ANF_CONFIRMAR = "opDespachoANFConfirmar";
        public static final String POST_DESPACHO_ANF_CONSULTA = "opDespachoANFConsulta";
        public static final String POST_DESPACHO_VENTA_CONFIRMAR = "opDespachoVentaConfirmar";
        public static final String POST_DESPACHO_VENTAS = "opDespachoVentas";
        public static final String POST_RECUPERAR_CONTRASENA = "opRecuperarContrasena";
        public static final String POST_EVENTO_DIARIO = "opRegistrarEventoDiario";
        public static final String POST_SESION_TOKEN = "opSessionToken";

        public static final String ERROR_CONECTION="Error de conexión";
        public static final String ERROR_SERVIDOR="Error de servidor";
        public static final String ERROR_JSON="Error de Json";

    }

    public static class SESSION {
        public static final String USER_DATA = "user_data";
        public static final String FECHA = "key_fecha";
        public static final String NOMBRE = "key_nombre";
        public static final String SURTIDOR = "key_surtidor";
        public static final String TOKEN = "key_token";
        public static final String ESTADO = "key_estado";
        public static final String MENSAJE = "key_mensaje";
        public static final String LEC_FINAL = "lectura_final";
        public static final String L_FINAL = "key_lectura_final";
    }

    public static class INTENT_KEY {
        public static final String MESSAGE = "message";
        public static final String DATE = "date";
        public static final String TIME = "time";
        public static final String PUMP = "pump";
        public static final String M3 = "m3";
        public static final String PHOTO = "foto";
        public static final String EVENT = "event";
        public static final String NUMBER = "0";
        public static final String TYPE_VIEW = "type_view";
        public static final String STATE = "estado";

        public static final String FLAG="flag";
        public static final String LEC_INICIAL="lectura_inicial";
        public static final String LEC_FINAL="lectura_final";
        public static final String ID_CAMION="idCamion";
        public static final String CODE_QR = "codigo_qr";
        public static final String LICENCE = "placa";
        public static final String TICKET = "ticket";
        public static final String DOCUMENT = "documento";
        public static final String CAPACITY = "capacidad";
        public static final String OBSERVATION = "observaciones";
        public static final String ID_DESPACHO = "id_despacho";
        public static final String TYPE_DESPATCH = "type_despatch";
        public static final String CODE = "code";
        public static final String USER = "user";
        public static final String OPCION_CIERRE_DIARIO = "opcion_cierre_diario";
        public static final String EVENT_LIST = "lista_eventos";
    }

    public static class VALUES {
        public static final int IMG_GIF = R.drawable.splash_gif;//IMAGEN A MOSTAR
        public static float ZOOM_MAPA_DEFAULT = 14;
        public static double LATITUDE_POSITION_DEFAULT = -12.096701;
        public static double LONGITUDE_POSITION_DEFAULT = -77.058767;
        public static String EMPTY = "";
        public static String ONE_SPACE = " ";
        public static String COLON = ":";
        public static String SLASH = " / ";
        public static final int EVENTO_APERTURAR = 1;
        public static final int EVENTO_CERRAR = 2;
        public static final int RADIO_BUTTON = -1;


        public static class SALES {
            public static int OPENING = 0;
            public static int NORMAL = 1;
            public static int ANF = 2;
            public static int CLOSURE = 3;
        }


        public static final int CURRENT_STATUS_SIN_APERTURA = 0;
        public static final int CURRENT_STATUS_APERTURADO = 1;
        public static final int CURRENT_STATUS_CERRADO = 2;
        public static final int CURRENT_STATUS_SIN_CIERRE_ANTERIOR = 3;

    }

    // deben tener final ya que se ejecutará en runtime
    public static class PERMISSIONS {
        public static final int ACCESS_FINE_LOCATION = 1;
    }

    public static class PREFERENCES {

    }


}
