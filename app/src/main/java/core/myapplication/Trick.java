package core.myapplication;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A.Barredo on 14/12/2015.
 * Student of Deusto University
 */
public class Trick implements Parcelable {
    private Uri videofile;
    private List<String> data;
    public Trick(Uri videofile, List<String> data) {
        this.videofile = videofile;
        this.data = data;
    }

    public void setVideofile(Uri videofile) {
        this.videofile = videofile;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    public Uri getVideofile() {
        return videofile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.videofile, 0);
        dest.writeStringList(this.data);
    }

    private Trick(Parcel in) {
        this.videofile = in.readParcelable(Uri.class.getClassLoader());
        this.data = new ArrayList<String>();
        in.readStringList(this.data);
    }

    public static final Parcelable.Creator<Trick> CREATOR = new Parcelable.Creator<Trick>() {
        public Trick createFromParcel(Parcel source) {
            return new Trick(source);
        }

        public Trick[] newArray(int size) {
            return new Trick[size];
        }
    };

}
