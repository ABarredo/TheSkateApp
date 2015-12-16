package core.myapplication;

import android.net.Uri;

import java.util.List;

/**
 * Created by A.Barredo on 14/12/2015.
 * Student of Deusto University
 */
public class Trick {
    Uri videofile;
    List<String> data;

    public Trick(Uri videofile, List<String> data) {
        this.videofile = videofile;
        this.data = data;
    }
    public Trick( List<String> data) {
        this.data = data;
    }
}
