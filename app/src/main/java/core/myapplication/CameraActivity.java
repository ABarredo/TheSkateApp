package core.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


public class CameraActivity extends ActionBarActivity {
    private boolean isRecording = false;
    private Camera camera;
    private CameraPreview preview;
    private BluetoothAdapter mBluetoothAdapter;
    private Button btnCapture;
    private Button btnData;
    private StringBuffer mOutStringBuffer;
    private MediaRecorder mediaRecorder;
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private Bluetooth mBluetooth = null;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private String readMessage;
    private String mConnectedDeviceName = null;
    private List<String> mData;
    private Trick mTrick;
    private String TAG = "Abarredo.CameraActivity";
    private Uri uri;
    private static DataSaver dataSaver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.d(TAG, "onCreate");
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        camera = getCameraInstance();
        preview = new CameraPreview(this, camera);
        final FrameLayout fLayout = (FrameLayout) findViewById(R.id.camera_preview);
        fLayout.addView(preview);
        btnCapture = (Button) findViewById(R.id.button_capture);
        btnCapture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (isRecording) {

                    } else {
                        if (mBluetooth.getState() == Bluetooth.STATE_CONNECTED) {
                            Log.d(TAG, "started recording");
                            Toast.makeText(getApplicationContext(), "Started recording ", Toast.LENGTH_SHORT).show();
                            isRecording = true;
                            mediaRecorder = new MediaRecorder();
                            camera.unlock();
                            mediaRecorder.setCamera(camera);
                            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                            mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
                            File file = DataSaver.getOutputMediaFile(MEDIA_TYPE_VIDEO,"THE SKATE APP");
                            uri = DataSaver.getOutputMediaFileUri(file);
                            mediaRecorder.setOutputFile(file.toString());
                            mediaRecorder.setPreviewDisplay(null);
                            try {
                                mediaRecorder.prepare();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            sendMessage("S");
                            mediaRecorder.start();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    mediaRecorder.stop();
                                    releaseMediaRecorder();
                                    isRecording = false;
                                    Toast.makeText(getApplicationContext(), "Finished recording ", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "finished recording");
                                    Log.d(TAG, mData.size()+"");
                                    mTrick = new Trick(uri,mData);
                                    Intent i = new Intent();
                                    Bundle b = new Bundle();
                                    b.putParcelable(Constants.TRICK_PASSED, mTrick);
                                    i.putExtras(b);
                                    i.setClass(getApplicationContext(), SubActivity.class);
                                    startActivity(i);
                                }
                            }, 15000);

                        }
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //STOPing

                }
                return false;
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            Log.d(TAG, "Try to connect with insecure");
        } else if (mBluetooth == null) {
            setup();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (mBluetooth != null) {
            mBluetooth.stop();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (mBluetooth != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mBluetooth.getState() == Bluetooth.STATE_NONE) {
                // Start the Bluetooth chat services
                connectDevice(true);
                Toast.makeText(getApplicationContext(), " Bluetooth.STATE_NONE", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Bluetooth.STATE_NONE");
            }
            if (mBluetooth.getState() == Bluetooth.STATE_CONNECTED) {
                Toast.makeText(getApplicationContext(), " Connected onResume", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Connected onResume");
            }
        }else{
            setup();
        }
        if (camera == null) {
            camera = getCameraInstance();
            preview = new CameraPreview(this, camera);
            final FrameLayout fLayout = (FrameLayout) findViewById(R.id.camera_preview);
            fLayout.addView(preview);
        }

    }

    public void setup() {
        Log.d(TAG, "setup");
        // Initialize the BluetoothChatService to perform bluetooth connections
        mBluetooth = new Bluetooth((Activity) this, mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
        mData = new ArrayList<String>();
    }


    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mBluetooth.getState() != Bluetooth.STATE_CONNECTED) {
            Toast.makeText((Activity) this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(getApplicationContext(), " Sending data", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Sending data");
        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mBluetooth.write(send);

            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
        }
    }

    private void setStatus(int resId) {
        Activity activity = (Activity) this;
        if (null == activity) {
            return;
        }
        final ActionBar actionBar = activity.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(resId);
    }

    private void setStatus(CharSequence subTitle) {
        Activity activity = (Activity) this;
        if (null == activity) {
            return;
        }
        final ActionBar actionBar = activity.getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(subTitle);
    }

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case Bluetooth.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            break;
                        case Bluetooth.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            break;
                        case Bluetooth.STATE_LISTEN:
                        case Bluetooth.STATE_NONE:
                            setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    break;
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    readMessage = new String(readBuf, 0, msg.arg1);
                    mData.add(readMessage);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);

                    break;
                case Constants.MESSAGE_TOAST:

                    break;
            }
        }
    };

    private void connectDevice(boolean secure) {
        Toast.makeText(getApplicationContext(), " mBluetooth.connect", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "mBluetooth.connect");
        // Get the device MAC address
        String address = getAddress();
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mBluetooth.connect(device, secure);


    }

    public String getAddress() {
        Log.d(TAG, "getAddress");
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
// If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals("HC-06")) {
                    return device.getAddress();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), " onPause", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onPause");
        releaseMediaRecorder();       // if you are using MediaRecorder, release it first
        releaseCamera();              // release the camera immediately on pause event
    }

    private void releaseMediaRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder.reset();   // clear recorder configuration
            mediaRecorder.release(); // release the recorder object
            mediaRecorder = null;
            camera.lock();           // lock camera for later use
        }
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.release();        // release the camera for other applications
            camera = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_camera, menu);
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

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;

        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }


    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("ABARREDO.gOutMediaFile", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

}
