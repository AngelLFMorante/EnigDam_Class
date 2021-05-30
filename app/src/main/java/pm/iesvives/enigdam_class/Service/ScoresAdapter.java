package pm.iesvives.enigdam_class.Service;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pm.iesvives.enigdam_class.Entity.PlayerDto;
import pm.iesvives.enigdam_class.R;

/**
 * It is an adapter to receive players' scores in a listView.
 */
public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ViewHolderGame> {

    private List<PlayerDto> players;

    public ScoresAdapter(List<PlayerDto> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public ViewHolderGame onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, null, false);
        return new ViewHolderGame(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGame holder, int position) {
        //pasamos los parametros al viewHolder
        holder.assignData(players.get(position));
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public static class ViewHolderGame extends RecyclerView.ViewHolder {

        TextView txtUserPlayerNameScore;
        TextView txtUserPlayerTimeScore;
        TextView txtUserPlayerScoreScore;


        public ViewHolderGame(@NonNull View itemView) {
            super(itemView);
            txtUserPlayerNameScore = itemView.findViewById(R.id.userPlayerNameScore);
            txtUserPlayerTimeScore = itemView.findViewById(R.id.userPlayerTimeScore);
            txtUserPlayerScoreScore = itemView.findViewById(R.id.userPlayerScoreScore);
        }


        public void assignData(PlayerDto player) {
            String username = player.getUsername();

            if (username.length() > 8) {
                username = username.substring(0, 8);
            }
            txtUserPlayerNameScore.setText(String.format("%-8s", username));
            txtUserPlayerTimeScore.setText(String.format("%s", player.getTime()));
            txtUserPlayerScoreScore.setText(String.format("%s", player.getScore()));

        }
    }

}
