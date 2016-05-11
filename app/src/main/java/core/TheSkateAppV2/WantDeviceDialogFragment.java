package core.TheSkateAppV2;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by A.Barredo on 10/05/2016.
 */
public class WantDeviceDialogFragment extends android.app.DialogFragment {
    private String TAG = "AbarredoWantDeviceDialogFragment";
    private String titulo;
    dialogListener mListener;
    SharedPreferences shared;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        setCancelable(false);
        builder.setTitle("Skater");
        builder.setMessage("¿Desea utilizar el dispositivo Skater?");
        builder.setPositiveButton(R.string.YES,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Log.d(TAG,"positivo = "+i);
                mListener.onDialogItemClicked(i);
            }
        });
        builder.setNegativeButton(R.string.NO, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG,"falso = "+i);
                mListener.onDialogItemClicked(i);
            }
        });

        Dialog dialog = builder.create();
        return dialog;
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
