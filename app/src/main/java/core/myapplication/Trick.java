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
    private Uri videoFile;
    private Uri skateBoardPhoto;
    private Uri skateBoardGripTape;
    private Uri graph;
    private Uri dataRollFile;
    private Uri dataPitchFile;
    private Uri dataYawFile;
    private Uri dataAltitudeFile;



    public void setDataAltitudeFile(Uri dataAltitudeFile) {
        this.dataAltitudeFile = dataAltitudeFile;
    }

    public void setVideofile(Uri videoFile) {

        this.videoFile = videoFile;
    }

    public void setDataRollFile(Uri dataRollFile) {
        this.dataRollFile = dataRollFile;
    }

    public void setDataPitchFile(Uri dataPitchFile) {
        this.dataPitchFile = dataPitchFile;
    }

    public void setDataYawFile(Uri dataYawFile) {
        this.dataYawFile = dataYawFile;
    }

    public Uri getDataRollFile() {

        return dataRollFile;
    }

    public Uri getDataPitchFile() {
        return dataPitchFile;
    }

    public Uri getDataYawFile() {
        return dataYawFile;
    }

    public Uri getDataAltitudeFile() {
        return dataAltitudeFile;
    }

    public Trick(Uri videoFile,Uri dataRollFile,  Uri dataPitchFile, Uri dataYawFile, Uri dataAltitudeFile) {
        this.dataRollFile = dataRollFile;
        this.videoFile = videoFile;
        this.dataPitchFile = dataPitchFile;
        this.dataYawFile = dataYawFile;
        this.dataAltitudeFile = dataAltitudeFile;
    }

    public Trick(Uri videoFile, Uri skateBoardPhoto, Uri graph) {
        this.videoFile = videoFile;
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
    public String getVideoFilePath(){
        return videoFile.getPath();
    }
    public String getDataRollFilePath(){
        return dataRollFile.getPath();
    }
    public String getDataPitchFilePath(){
        return dataPitchFile.getPath();
    }
    public String getDataYawFilePath(){
        return dataYawFile.getPath();
    }
    public String getDataAltitudeFilePath(){
        return dataAltitudeFile.getPath();
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
    public Uri getVideofile() {
        return videoFile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeParcelable(this.skateBoardPhoto, 0);
        //dest.writeParcelable(this.skateBoardGripTape, 0);
        //dest.writeParcelable(this.graph, 0);
        dest.writeParcelable(this.videoFile, 0);
        dest.writeParcelable(this.dataRollFile, 0);
        dest.writeParcelable(this.dataPitchFile, 0);
        dest.writeParcelable(this.dataYawFile, 0);
        dest.writeParcelable(this.dataAltitudeFile, 0);
    }

    private Trick(Parcel in) {
        this.videoFile = in.readParcelable(Uri.class.getClassLoader());
        //this.graph = in.readParcelable(Uri.class.getClassLoader());
        //this.skateBoardPhoto = in.readParcelable(Uri.class.getClassLoader());
        //this.skateBoardGripTape = in.readParcelable(Uri.class.getClassLoader());
        this.dataRollFile = in.readParcelable(Uri.class.getClassLoader());
        this.dataPitchFile = in.readParcelable(Uri.class.getClassLoader());
        this.dataYawFile = in.readParcelable(Uri.class.getClassLoader());
        this.dataAltitudeFile = in.readParcelable(Uri.class.getClassLoader());
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
