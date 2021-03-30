package pm.iesvives.enigdam_class.Service;

import pm.iesvives.enigdam_class.Dao.IGameDao;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static IGameDao service;

    public RetrofitClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.138:8080/game/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(IGameDao.class);
    }

    public IGameDao getService(){
        return service;
    }


}
