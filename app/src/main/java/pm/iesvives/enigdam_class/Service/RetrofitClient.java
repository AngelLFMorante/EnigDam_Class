package pm.iesvives.enigdam_class.Service;

import pm.iesvives.enigdam_class.Dao.IPlayerDao;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static IPlayerDao service;

    public RetrofitClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.138:8080/game/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(IPlayerDao.class);
    }

    public IPlayerDao getService(){
        return service;
    }


}
