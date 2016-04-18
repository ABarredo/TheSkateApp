package core.myapplication;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.util.EntityUtils;

import java.io.File;

public class SubActivity extends ActionBarActivity implements DialogFragment.dialogListener{



    private Trick trick = null;
    private String TAG = "AbarredoSubActivity";
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            if (this.getIntent().hasExtra(Constants.NO_BLUETOOTH)) {
                //Toast..makeText(getApplicationContext(), "No Bluetooth device", Toast.LENGTH_SHORT).show();
                SubActivityFragment firstFragment = new SubActivityFragment();
                firstFragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, firstFragment).commit();

            } else {
                Bundle b = this.getIntent().getExtras();
                if (b != null) {
                    trick = b.getParcelable(Constants.TRICK_PASSED);
                    //Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Object Passed");
                    SubActivityFragment firstFragment = new SubActivityFragment();
                    firstFragment.setArguments(getIntent().getExtras());
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragment_container, firstFragment).commit();
                    DialogFragment digi = new DialogFragment();
                    digi.show(getFragmentManager(), "nivel");
                    Log.d(TAG, String.valueOf(trick.getLevel()));

                }
            }
        }
    }
    @Override
    public void onDialogItemClicked(int level) {
        trick.setLevel(level + 1);
        UpLoader up = new UpLoader(getApplicationContext());
        Log.d(TAG, "Trick level "+trick.getLevel());
        up.execute(trick);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

}
