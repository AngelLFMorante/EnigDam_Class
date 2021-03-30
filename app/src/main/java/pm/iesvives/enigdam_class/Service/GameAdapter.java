package pm.iesvives.enigdam_class.Service;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import pm.iesvives.enigdam_class.Entity.Game;
import pm.iesvives.enigdam_class.R;


public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolderGame> {

    private List<Game> games;

    public GameAdapter(List<Game> games) {
        this.games = games;
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
            holder.assignData(games.get(position));
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class ViewHolderGame extends RecyclerView.ViewHolder {

        TextView txtUserPlayerScore;

        public ViewHolderGame(@NonNull View itemView) {
            super(itemView);
            txtUserPlayerScore = (TextView) itemView.findViewById(R.id.userPlayerScore);
        }


        public void assignData(Game game) {
            //TODO aquí tendré que gestionar bien el tema del usuario que lo recoga bien de la base de datos.
            txtUserPlayerScore.setText(String.format("User: %s  | Time: %s  | Score: %s", game.getId_user(), game.getTime(), game.getScore()));
        }
    }

}
