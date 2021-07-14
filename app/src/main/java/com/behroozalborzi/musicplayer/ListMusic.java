package com.behroozalborzi.musicplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Behrooz on 7/8/2021.
 * https://behroozalborzi.ir
 * Android Developer
 * Thank you ... :)
 */
public class ListMusic {

    public static List<Music> getListMusic(){
        List<Music> music = new ArrayList<>();
        Music music1 = new Music();
        music1.setArtist("Evan Band");
        music1.setMusicName("Chehel Gis");
        music1.setCoverResId(R.drawable.music_1_cover);
        music1.setArtistResId(R.drawable.music_1_artist);
        music1.setMusicFileResId(R.raw.music_1);

        Music music2 = new Music();
        music2.setArtist("Reza Sadeghi");
        music2.setMusicName("Tanha Tarin");
        music2.setCoverResId(R.drawable.music_2_cover);
        music2.setArtistResId(R.drawable.music_2_artist);
        music2.setMusicFileResId(R.raw.music_2);

        Music music3 = new Music();
        music3.setArtist("Reza Bahram");
        music3.setMusicName("Hich");
        music3.setCoverResId(R.drawable.music_3_cover);
        music3.setArtistResId(R.drawable.music_3_artist);
        music3.setMusicFileResId(R.raw.music_3);

        Music music4 = new Music();
        music4.setArtist("Hamid Hiraad");
        music4.setMusicName("Dir Kardi");
        music4.setCoverResId(R.drawable.music_4_cover);
        music4.setArtistResId(R.drawable.music_4_artist);
        music4.setMusicFileResId(R.raw.music_4);

        Music music5 = new Music();
        music5.setArtist("Reza Sadeghi");
        music5.setMusicName("Tanha Tarin");
        music5.setCoverResId(R.drawable.music_2_cover);
        music5.setArtistResId(R.drawable.music_2_artist);
        music5.setMusicFileResId(R.raw.music_2);

        music.add(music1);
        music.add(music2);
        music.add(music3);
        music.add(music4);
        music.add(music5);

        return music;
    }
}
