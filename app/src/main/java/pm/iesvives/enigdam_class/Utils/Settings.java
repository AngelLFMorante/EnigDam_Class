package pm.iesvives.enigdam_class.Utils;

import pm.iesvives.enigdam_class.Service.RetrofitClient;

public class Settings {
    //Enter the url of the end point
    public static final String URL = "http://192.168.1.132:8080/game/";
    //Response client of retrofit
    public static final RetrofitClient RESPONSE_CLIENT = new RetrofitClient();
    //Encrypt and Decrypt password
    public static final String ALGORITHM = "AES";
    // definition of the encryption mode to be used
    public static final  String ENCRYPT_KEY = "1Hbfh667adfDEJ78";
    //CountDownTimer config at 30 min
    public static final long TIME_LEFT_IN_MILLISECONDS = 600000 * 3;
}
