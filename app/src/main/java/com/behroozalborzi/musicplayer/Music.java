package com.behroozalborzi.musicplayer;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

/**
 * Created by Behrooz on 7/8/2021.
 * https://behroozalborzi.ir
 * Android Developer
 * Thank you ... :)
 */
public class Music implements Parcelable {

    private int id;
    private String musicName;
    private String artist;
    private int coverResId;
    private int artistResId;
    private int musicFileResId;

    public int getMusicFileResId() {
        return musicFileResId;
    }

    public void setMusicFileResId(int musicFileResId) {
        this.musicFileResId = musicFileResId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getCoverResId() {
        return coverResId;
    }

    public void setCoverResId(int coverResId) {
        this.coverResId = coverResId;
    }

    public int getArtistResId() {
        return artistResId;
    }

    public void setArtistResId(int artistResId) {
        this.artistResId = artistResId;
    }


    public static String onConvertTimeToStringCurrently(int time){
        int sec = (time/1000) % 60;
        int min = (time/(1000*60))%60 ;
        int hour = min % 60;

        return String.format(Locale.US,"%02d:%02d",min,sec);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.musicName);
        dest.writeString(this.artist);
        dest.writeInt(this.coverResId);
        dest.writeInt(this.artistResId);
        dest.writeInt(this.musicFileResId);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.musicName = source.readString();
        this.artist = source.readString();
        this.coverResId = source.readInt();
        this.artistResId = source.readInt();
        this.musicFileResId = source.readInt();
    }

    public Music() {
    }

    protected Music(Parcel in) {
        this.id = in.readInt();
        this.musicName = in.readString();
        this.artist = in.readString();
        this.coverResId = in.readInt();
        this.artistResId = in.readInt();
        this.musicFileResId = in.readInt();
    }

    public static final Parcelable.Creator<Music> CREATOR = new Parcelable.Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel source) {
            return new Music(source);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };
}
