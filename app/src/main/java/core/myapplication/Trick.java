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
    private Uri skateBoardPhoto;
    private Uri skateBoardGripTape;
    private Uri graph;
    private List<String> dataRoll;
    private List<String> dataPitch;
    private List<String> dataYaw;
    private List<String> dataAltitude;

    public Trick(Uri videofile, Uri skateBoardPhoto, Uri graph) {
        this.videofile = videofile;
        this.skateBoardPhoto = skateBoardPhoto;
        this.skateBoardGripTape = skateBoardGripTape;
        this.graph = graph;
    }

    public Uri getGraph() {
        return graph;
    }

    public void setGraph(Uri graph) {
        this.graph = graph;
    }



    public Uri getSkateBoardPhoto() {
        return skateBoardPhoto;
    }

    public void setSkateBoardPhoto(Uri skateBoardPhoto) {
        this.skateBoardPhoto = skateBoardPhoto;
    }

    public Uri getSkateBoardGripTape() {
        return skateBoardGripTape;
    }

    public void setSkateBoardGripTape(Uri skateBoardGripTape) {
        this.skateBoardGripTape = skateBoardGripTape;
    }

    public List<String> getDataRoll() {
        return dataRoll;
    }

    public void setDataRoll(List<String> dataRoll) {
        this.dataRoll = dataRoll;
    }



    public List<String> getDataPitch() {
        return dataPitch;
    }

    public void setDataPitch(List<String> dataPitch) {
        this.dataPitch = dataPitch;
    }

    public List<String> getDataYaw() {
        return dataYaw;
    }

    public void setDataYaw(List<String> dataYaw) {
        this.dataYaw = dataYaw;
    }

    public List<String> getDataAltitude() {
        return dataAltitude;
    }

    public void setDataAltitude(List<String> dataAltitude) {
        this.dataAltitude = dataAltitude;
    }

    public Trick(Uri videofile, List<String> dataRoll) {
        this.videofile = videofile;
        this.dataRoll = dataRoll;
    }

    public void setVideofile(Uri videofile) {
        this.videofile = videofile;
    }

    public void setdataRoll(List<String> dataRoll) {
        this.dataRoll = dataRoll;
    }

    public Trick(Uri videofile, List<String> dataRoll, List<String> dataPitch, List<String> dataYaw, List<String> dataAltitude) {
        this.videofile = videofile;
        this.dataRoll = dataRoll;
        this.dataPitch = dataPitch;
        this.dataYaw = dataYaw;
        this.dataAltitude = dataAltitude;
    }

    public List<String> getdataRoll() {
        return dataRoll;
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
        dest.writeParcelable(this.skateBoardPhoto, 0);
        dest.writeParcelable(this.skateBoardGripTape, 0);
        dest.writeParcelable(this.graph, 0);
        dest.writeParcelable(this.videofile, 0);
        dest.writeStringList(this.dataRoll);
        dest.writeStringList(this.dataPitch);
        dest.writeStringList(this.dataYaw);
        dest.writeStringList(this.dataAltitude);
    }

    private Trick(Parcel in) {
        this.videofile = in.readParcelable(Uri.class.getClassLoader());
        this.graph = in.readParcelable(Uri.class.getClassLoader());
        this.skateBoardPhoto = in.readParcelable(Uri.class.getClassLoader());
        this.skateBoardGripTape = in.readParcelable(Uri.class.getClassLoader());
        this.dataRoll = new ArrayList<String>();
        in.readStringList(this.dataRoll);
        this.dataPitch= new ArrayList<String>();
        in.readStringList(this.dataPitch);
        this.dataYaw = new ArrayList<String>();
        in.readStringList(this.dataYaw);
        this.dataAltitude = new ArrayList<String>();
        in.readStringList(this.dataAltitude);
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
