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


public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ViewHolderGame> {

    private List<PlayerDto> players;

    public ScoresAdapter(List<PlayerDto> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public ViewHolderGame onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list,null,false);
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

    public class ViewHolderGame extends RecyclerView.ViewHolder {

        TextView txtUserPlayerScore;

        public ViewHolderGame(@NonNull View itemView) {
            super(itemView);
            txtUserPlayerScore = (TextView) itemView.findViewById(R.id.userPlayerScore);
        }


        public void assignData(PlayerDto player) {
            String username = player.getUsername();
            if(username.length() > 8){
                username = username.substring(0,8);
            }
            txtUserPlayerScore.setText(String.format("%-8s Time: %s--Score: %s", username, player.getTime(), player.getScore()));
        }
    }

}
