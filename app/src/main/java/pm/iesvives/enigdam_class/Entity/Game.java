package pm.iesvives.enigdam_class.Entity;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Game implements Serializable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("time")
    private String time;
    @SerializedName("score")
    private String score;

    @SerializedName("player")
    private PlayerDto player;

    public Game() {
    }

    public PlayerDto getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDto player) {
        this.player = player;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
