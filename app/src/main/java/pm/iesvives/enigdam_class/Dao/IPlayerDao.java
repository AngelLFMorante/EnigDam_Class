package pm.iesvives.enigdam_class.Dao;

import java.util.List;
import pm.iesvives.enigdam_class.Entity.Game;
import pm.iesvives.enigdam_class.Entity.PlayerDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Creation of the service to be consumed with retrofit,
 * here you will find all the methods to be used for the web service call.
 */
public interface IPlayerDao {

    @GET("scores")
    Call<List<PlayerDto>> getAllScores();

    @POST("addGame")
    Call<Void> addGame(@Body Game game);

    @GET("players")
    Call<List<PlayerDto>> allPlayers();

    @GET("player")
    Call<PlayerDto> getPlayer(@Query(value="id", encoded=true) int id);

    @POST("add")
    Call<Void> addPlayer(@Body PlayerDto player);

    @PUT("editPlayer")
    Call<PlayerDto> editPlayer(@Query(value="id", encoded=true) int id, @Body PlayerDto player);

    @DELETE("deletePlayer")
    Call<Void> deletePlayer(@Query(value="id", encoded=true) int id);

    @GET("forgetPassword")
    Call<Void> forgetPassword(@Query(value="email", encoded=true) String email);

}
