package core.myapplication;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by A.Barredo on 17/12/2015.
 * Student of Deusto University
 */

public class DataSaver {
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int MEDIA_TYPE_DATA = 3;
    private static String TAG = "Abarredo.DataSaver";
    private static FileOutputStream out = null;
    private static BufferedWriter buf = null;
    private static File mediaFile;

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static File getAlbumStorageDir(String albumName, String timeStamp, Context context) {
        // Get the directory for the user's public pictures directory.
        File file;
        if (timeStamp == null) {
             file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), albumName);
            if (!file.mkdirs()) {
                Log.d(TAG, "Directory not created");
            }
            MediaScannerConnection.scanFile(context, new String[]{file.getPath().toString()}, null, null);
        } else {
            file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), albumName + "/" + timeStamp+"/");
            if (!file.mkdirs()) {
                Log.d(TAG, "Directory not created");
            }
            MediaScannerConnection.scanFile(context, new String[]{file.getPath().toString()}, null, null);
        }

        return file;

    }

    public static File getPrivateAlbumStorageDir(Context context, String albumName) {
        // Get the directory for the app's private pictures directory.
        File file = new File(context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(TAG, "Directory not created");
        }

        return file;
    }

    public static void deleteFile(File file) {
        file.delete();
    }

    public static File getOutputMediaFile(int type, String albumName, String FileName, String timeStamp, Context context) {

        File mediaStorageDir = getAlbumStorageDir(albumName, timeStamp, context);

        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
            MediaScannerConnection.scanFile(context, new String[]{mediaFile.getPath().toString()}, null, null);
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
            MediaScannerConnection.scanFile(context, new String[]{mediaFile.getPath().toString()}, null, null);
        } else if (type == MEDIA_TYPE_DATA) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "DAT_" + FileName + ".txt");
            MediaScannerConnection.scanFile(context, new String[]{mediaFile.getPath().toString()}, null, null);
        } else {
            return null;
        }

        return mediaFile;
    }

    public static Uri getOutputMediaFileUri(File file) {
        return Uri.fromFile(file);
    }

    public static boolean saveData(File file, List<String> data) {
        try {
            out = new FileOutputStream(file);
            for (int i = 0; i < data.size(); i++) {
                out.write(data.get(i).getBytes());
                out.write("\n".getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;


    }
}
