package core.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

/**
 * Created by A.Barredo on 18/12/2015.
 * Student of Deusto University
 */
public class SubActivityFragment extends Fragment {
    private ImageView skateImage;
    private ImageView graphImage;
    private ImageView nameImage;
    private VideoView ollieVideo;
    private ImageButton imageButton;
    private Uri uri;
    private File file;
    private View view;
    public static final int IMAGE1 = 1;
    public static final int IMAGE2 = 2;
    public static final int IMAGE3 = 3;
    public static final int VIDEO = 4;

    private static String TAG = "Abarredo.SAFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.sub_activity_fragment, container, false);
        view = layout;
        return layout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        skateImage = (ImageView) view.findViewById(R.id.card_skate_image);
        if (getImagePath(IMAGE1) != null) {
            String path = getImagePath(IMAGE1);
            //skateImage.setImageBitmap(BitmapFactory.decodeFile(path));
            skateImage.setImageBitmap(decodeSampledBitmapFromPath(path, skateImage.getMeasuredWidth(), skateImage.getMeasuredHeight()));
        }
        graphImage = (ImageView) view.findViewById(R.id.card_graph_image);
        if (getImagePath(IMAGE2) != null) {
            String path = getImagePath(IMAGE2);
            //skateImage.setImageBitmap(BitmapFactory.decodeFile(path));
            graphImage.setImageBitmap(BitmapFactory.decodeFile(path));
            graphImage.setAdjustViewBounds(true);
            graphImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        nameImage = (ImageView) view.findViewById(R.id.card_name_image);
        if (getImagePath(IMAGE3) != null) {
            String path = getImagePath(IMAGE3);
            //skateImage.setImageBitmap(BitmapFactory.decodeFile(path));
            nameImage.setImageBitmap(decodeSampledBitmapFromPath(path, nameImage.getMeasuredWidth(), nameImage.getMeasuredHeight()));
        }
        MediaController mediaController = new MediaController(getActivity());
        ollieVideo = (VideoView) view.findViewById(R.id.card_skate_video);
        ollieVideo.setMediaController(mediaController);
        mediaController.setAnchorView(ollieVideo);
        mediaController.hide();
        if (getImagePath(VIDEO) != null) {
            String path = getImagePath(VIDEO);
            ollieVideo.setVideoPath(path);
            ollieVideo.seekTo(10);
        }


    }

    public static int calculateSample(int realWidth, int realHeight, int desiredWidth, int desiredHeight) {
        Log.d(TAG, "" + realWidth);
        Log.d(TAG, "" + realHeight);
        Log.d(TAG, "" + desiredWidth);
        Log.d(TAG, "" + desiredHeight);
        return 3;
    }

    public static Bitmap decodeSampledBitmapFromPath(String path, int realWidth, int realHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateSample(realWidth, realHeight, options.outWidth, options.outHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public String getImagePath(int type) {
        switch (type) {
            case IMAGE1:
            file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "THE SKATE APP/11111111_000000/skate.jpg");
            if (file.exists()) {
                Log.d(TAG, "image exists");
                return file.getAbsolutePath();
            } else {
                Log.d(TAG, "no image");
                return null;
            }
            case IMAGE2:
                file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS), "THE SKATE APP/11111111_000000/graph.JPG");
                if (file.exists()) {
                    Log.d(TAG, "image exists");
                    return file.getAbsolutePath();
                } else {
                    Log.d(TAG, "no image");
                    return null;
                }
            case IMAGE3:
                file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS), "THE SKATE APP/11111111_000000/ollie.jpg");
                if (file.exists()) {
                    Log.d(TAG, "image exists");
                    return file.getAbsolutePath();
                } else {
                    Log.d(TAG, "no image");
                    return null;
                }
            case VIDEO:
                file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS), "THE SKATE APP/11111111_000000/OllieVideo.mp4");
                if (file.exists()) {
                    Log.d(TAG, "video exists");
                    return file.getAbsolutePath();
                } else {
                    Log.d(TAG, "no video");
                    return null;
                }
        }
        return null;
    }
}
