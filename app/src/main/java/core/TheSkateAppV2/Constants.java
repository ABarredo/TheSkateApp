package core.TheSkateAppV2;

/**
 * Created by A.Barredo on 16/12/2015.
 * Student of Deusto University
 */
public interface Constants {

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final int UPDATE = 6;
    public static final int DONE_READING = 7;


    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    public static final String TRICK_PASSED = "trick passed";
    public static final String NO_BLUETOOTH = "bo bluetooth device";

}
