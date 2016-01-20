package core.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;

/**
 * Created by Sakur on 05/01/2016.
 */
public class UpLoader extends AsyncTask<Trick,Integer,Integer> {
    private Context context;
    private String TAG = "ABarredo.UpLoader";
    public UpLoader(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Trick ... trick) {
        try {
            String videoPath = trick[0].getVideoFilePath();
            String rollPath = trick[0].getDataRollFilePath();
            String pitchPath = trick[0].getDataPitchFilePath();
            String yawPath = trick[0].getDataYawFilePath();
            String altitudePath = trick[0].getDataAltitudeFilePath();
            HttpClient client = new DefaultHttpClient();
            File videoFile = new File(videoPath);
            File rollFile = new File(rollPath);
            File pitchFile = new File(pitchPath);
            File yawFile = new File(yawPath);
            File altitudeFile = new File(altitudePath);
            HttpPost post = new HttpPost("http://192.168.1.50");

            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            entityBuilder.addTextBody("user", "demoUser");
            entityBuilder.addBinaryBody("video", videoFile);
            entityBuilder.addBinaryBody("roll", rollFile);
            entityBuilder.addBinaryBody("pitch", pitchFile);
            entityBuilder.addBinaryBody("yaw", yawFile);
            entityBuilder.addBinaryBody("altitude", altitudeFile);
            // add more key/value pairs here as needed

            HttpEntity entity = entityBuilder.build();
            post.setEntity(entity);

            HttpResponse response = client.execute(post);
                HttpEntity httpEntity = response.getEntity();
            }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"trick uploaded" );
    }
}
