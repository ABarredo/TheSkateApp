package core.TheSkateAppV2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity implements WantDeviceDialogFragment.dialogListener{
    private Toolbar toolbar;
    private String TAG = "Abarredo.MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_appbar);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        navigationDrawerFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        WantDeviceDialogFragment WDDF = new WantDeviceDialogFragment();
        SharedPreferences shared = this.getSharedPreferences(getString(R.string.WantDeviceAsked),Context.MODE_PRIVATE);
        if(!shared.getBoolean(getString(R.string.WantDeviceAsked),false)){
            WDDF.show(getFragmentManager(),"wantDevice");
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if(id == R.id.navigate){
            startActivity(new Intent(this,SubActivity.class));
            Log.d(TAG, "Navigating");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogItemClicked(int x) {
        SharedPreferences shared = this.getSharedPreferences(getString(R.string.WantDevice),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        SharedPreferences sharedAsked = this.getSharedPreferences(getString(R.string.WantDeviceAsked),Context.MODE_PRIVATE);
        SharedPreferences.Editor editorAsked = sharedAsked.edit();
        editorAsked.putBoolean(getString(R.string.WantDeviceAsked),true);
        editorAsked.apply();
        switch(x){
            case -1:
                editor.putBoolean(getString(R.string.WantDevice),true);
                Log.d(TAG,"Entra en True");
                editor.apply();
                break;
            case -2:
                editor.putBoolean(getString(R.string.WantDevice),false);
                Log.d(TAG,"Entra en False");
                editor.apply();
                break;
        }
    }
}
