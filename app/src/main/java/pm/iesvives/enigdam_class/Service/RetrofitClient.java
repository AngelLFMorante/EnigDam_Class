package pm.iesvives.enigdam_class.Service;

import pm.iesvives.enigdam_class.Dao.IPlayerDao;
import pm.iesvives.enigdam_class.Utils.Settings;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static IPlayerDao service;

    public RetrofitClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Settings.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(IPlayerDao.class);
    }

    public IPlayerDao getService(){
        return service;
    }


}
