package core.myapplication;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogFragment extends android.app.DialogFragment {
    private String TAG = "AbarredoDialogFragment";
    dialogListener mListener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        setCancelable(false);
        builder.setTitle("¿Nivel del truco realizado?");
        builder.setItems(R.array.levels, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, String.valueOf(which+1));
                mListener.onDialogItemClicked(which);
            }
        });
        return builder.create();
    }
    public interface dialogListener{
        public void onDialogItemClicked(int x);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            mListener = (dialogListener) activity;

        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString()+"must implement dialogListener");
        }
    }
}
