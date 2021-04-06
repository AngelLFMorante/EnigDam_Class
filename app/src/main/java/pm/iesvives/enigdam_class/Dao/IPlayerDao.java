package pm.iesvives.enigdam_class.Dao;

import java.util.List;
import java.util.Map;

import pm.iesvives.enigdam_class.Entity.PlayerDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Creation of the service to be consumed with retrofit,
 * here you will find all the methods to be used for the web service call.
 */
public interface IPlayerDao {

    @GET("scores")
    Call<List<PlayerDto>> getAllScores();

    @GET("players")
    Call<List<PlayerDto>> allPlayers();

    @POST("add")
    Call<Map<String, String>> addPlayer(@Body PlayerDto player);



}
