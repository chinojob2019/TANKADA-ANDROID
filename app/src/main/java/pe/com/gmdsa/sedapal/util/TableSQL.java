package pe.com.gmdsa.sedapal.util;

/**
 * Created by jmauriciog on 14/12/2015.
 */
public class TableSQL {

    public static class TABLE {
        public static String USER = "TB_USER";
        public static String PARAMETRIC = "TB_PARAMETRIC";
        public static String DEVICE = "TB_DEVICE";
    }

    public static class CREATE {
        public static String USER_TABLE = "CREATE TABLE " + TABLE.USER +
                " (userCode text, firstName text, lastName text, email text, photo text)";

        public static String PARAMETRIC_TABLE = "CREATE TABLE " + TABLE.PARAMETRIC +
                " (key text, value text)";

        public static String DEVICE_TABLE = "CREATE TABLE " + TABLE.DEVICE +
                " (gcm_id text, state integer)";

    }

    public static class DELETE {
        public static String USER_TABLE = "DELETE FROM " + TABLE.USER;
        public static String DEVICE_TABLE = "DELETE FROM " + TABLE.DEVICE;
        public static String PARAMETRIC_TABLE = "DELETE FROM " + TABLE.PARAMETRIC;
    }

    public static class DROP {
        public static String USER_TABLE = "DROP TABLE IF EXISTS " + TABLE.USER;

    }

    public static class SELECT {
        public static String USER_TABLE = "SELECT * FROM " + TABLE.USER;
        public static String PARAMETRIC_TABLE = "SELECT * FROM " + TABLE.PARAMETRIC;
        public static String DEVICE_TABLE = "SELECT * FROM " + TABLE.DEVICE;
    }

}
