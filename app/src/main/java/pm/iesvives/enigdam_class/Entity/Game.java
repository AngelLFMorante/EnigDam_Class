package pm.iesvives.enigdam_class.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Game implements Serializable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("id_user")
    private int id_user;
    @SerializedName("time")
    private String time;
    @SerializedName("score")
    private String score;

    public Game() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
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
