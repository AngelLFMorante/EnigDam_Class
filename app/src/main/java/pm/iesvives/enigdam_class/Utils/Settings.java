package pm.iesvives.enigdam_class.Utils;

import pm.iesvives.enigdam_class.Service.RetrofitClient;

public class Settings {
    //Enter the url of the end point
    public static final String URL = "http://172.22.112.1:8080/game/";
    /*192.168.1.138 ip noemi*/
    /*192.168.1.134 ip angel*/
    /*172.22.112.1 ip Luis*/
    //Response client of retrofit
    public static final RetrofitClient RESPONSE_CLIENT = new RetrofitClient();
    //Encrypt and Decrypt password
    public final static String ALGORITHM = "AES";
    // definition of the encryption mode to be used
    public final static String ENCRYPT_KEY = "1Hbfh667adfDEJ78";

}
