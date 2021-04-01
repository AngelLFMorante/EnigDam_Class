package pm.iesvives.enigdam_class.Dao;

import java.util.List;
import pm.iesvives.enigdam_class.Entity.PlayerDto;
import retrofit2.Call;
import retrofit2.http.GET;


public interface IPlayerDao {

    /**
     * Creation of the service to be consumed with retrofit,
     * here you will find all the methods to be used for the web service call.
     */
    @GET("scores")
    Call<List<PlayerDto>> getAllScores();
}
