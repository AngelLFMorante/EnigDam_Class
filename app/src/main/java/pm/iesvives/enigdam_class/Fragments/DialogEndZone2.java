package pm.iesvives.enigdam_class.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.View;
import android.widget.TextView;
import pm.iesvives.enigdam_class.R;


public class DialogEndZone2 extends DialogFragment {

    private String hintTest;
    private String ok;
    private TextView textCoordinate;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        hintTest = getResources().getString(R.string.hintTestZone2);
        ok = getResources().getString(R.string.btnOk);
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_end_zone2, null);
        textCoordinate = view.findViewById(R.id.textCoordinateZone2);
        textCoordinate.setText(hintTest);

        alertBuilder
                .setView(view)
                .setPositiveButton(ok, (dialog, which) -> dismiss());

        return alertBuilder.create();
    }
}