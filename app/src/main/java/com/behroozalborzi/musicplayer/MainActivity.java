 package com.behroozalborzi.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.behroozalborzi.musicplayer.databinding.ActivityMainBinding;
import com.google.android.material.slider.Slider;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements ClickItemListener{
    private ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private MusicAdapter musicAdapter;
    private List<Music> musicList = ListMusic.getListMusic();
    private MediaPlayer mediaPlayer;
    private MusicState musicState = MusicState.STOPPING;
    private Timer timer;
    private int artistId;
    private boolean isDragging ;
    private int currentMusic;
    private boolean isShuffle;
    private boolean isRepeat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Fresco.initialize(this);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpViews();
        musicAdapter = new MusicAdapter(musicList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(musicAdapter);

        onMusicChange(musicList.get(currentMusic));

        binding.musicPlayerPlayIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (musicState){
                    case PLAYING:
                        mediaPlayer.pause();
                        binding.musicPlayerPlayIv.setImageResource(R.drawable.ic_play_32dp);
                        musicState= MusicState.PAUSED;
                        break;
                    case PAUSED:
                    case STOPPING:
                        mediaPlayer.start();
                        binding.musicPlayerPlayIv.setImageResource(R.drawable.ic_pause_24dp);
                        musicState= MusicState.PLAYING;
                        break;
                }
            }
        });

        binding.musicPlayerBackIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.musicPlayerLyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Music music = musicList.get(artistId);
                FragmentLyricsMusic fragmentLyrics = new FragmentLyricsMusic();
                Bundle bundle = new Bundle();
                bundle.putParcelable("artist",music);
                fragmentLyrics.setArguments(bundle);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.layout_container,fragmentLyrics);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        binding.musicSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                binding.musicPlayerCurrentlyTimeTv.setText(Music.onConvertTimeToStringCurrently((int) value));
            }
        });

        binding.musicSlider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
                isDragging = true;
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                isDragging = false;
                mediaPlayer.seekTo((int) slider.getValue());
            }
        });

        binding.musicPlayerForwardIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNextMusic();
            }
        });

        binding.musicPlayerPreviousIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPreviousMusic();
            }
        });

        binding.musicPlayerShuffleIv.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if (isShuffle){
                    isShuffle = false;
                    binding.musicPlayerShuffleIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_random_mode_white));

                }else {
                    isShuffle = true;
                    isRepeat = false;
                    binding.musicPlayerShuffleIv.setImageDrawable(getResources().getDrawable(R.drawable.ic_random_mode_blue));
                    binding.musicPlayerRepeatIv.setImageResource(R.drawable.ic_repeat_mode_white);
                }

            }
        });

        binding.musicPlayerRepeatIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRepeat){
                    isRepeat = false;
                    binding.musicPlayerRepeatIv.setImageResource(R.drawable.ic_repeat_mode_white);

                }else {
                    isRepeat = true;
                    isShuffle = false;
                    binding.musicPlayerRepeatIv.setImageResource(R.drawable.ic_repeat_mode_blue);
                    binding.musicPlayerShuffleIv.setImageResource(R.drawable.ic_random_mode_white);
                }
            }
        });

        binding.musicPlayerForwardIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNextMusic();
            }
        });
    }

    private void setUpViews(){
        recyclerView = findViewById(R.id.rv_musicPlayer);
    }

    private void onMusicChange(Music music) throws IllegalStateException{
        musicAdapter.notifyOnMusicChange(music);
        binding.musicSlider.setValue(0.0f);
        artistId = musicList.indexOf(music);
        mediaPlayer = MediaPlayer.create(this,music.getMusicFileResId());
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!isDragging){

                                    binding.musicSlider.setValue(mediaPlayer.getCurrentPosition());
                                }

                            }
                        });
                    }
                },1000,1000);
                binding.musicPlayerPlayIv.setImageResource(R.drawable.ic_pause_24dp);
                binding.musicSlider.setValueFrom(0.0f);
                binding.musicSlider.setValueTo(mediaPlayer.getDuration());
                musicState = MusicState.PLAYING;
                binding.musicPlayerDuration.setText(Music.onConvertTimeToStringCurrently(mediaPlayer.getDuration()));

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (isRepeat){
                            onPlayRepeat();
                        }else if (isShuffle){
                            onPlayShuffle();
                        }else {
                            getNextMusic();
                        }

                    }
                });

            }
        });

        binding.artistNameTv.setText(music.getArtist());
        binding.musicPlayerArtistIv.setImageResource(music.getArtistResId());
        binding.musicPlayerCoverArtistIv.setImageResource(music.getCoverResId());
        binding.musicNameTv.setText(music.getMusicName());

    }

    private void getPreviousMusic(){

        onBaseMediaPlayer();
        if (currentMusic == 0){
            currentMusic = musicList.size()-1;
        }else {
            currentMusic--;
        }

        onMusicChange(musicList.get(currentMusic));

    }
    private void getNextMusic() {
        onBaseMediaPlayer();
        if (currentMusic<musicList.size()-1)
            currentMusic ++;
        else
            currentMusic = 0;

        onMusicChange(musicList.get(currentMusic));
    }



    private void onPlayRepeat(){
        onBaseMediaPlayer();
        onMusicChange(musicList.get(currentMusic));

    }

    private void onPlayShuffle(){
        onBaseMediaPlayer();
        Random random = new Random();
        currentMusic = random.nextInt((musicList.size() - 1) -0+ 1)+0;
        onMusicChange(musicList.get(currentMusic));

    }

    private void onBaseMediaPlayer(){
        timer.cancel();
        timer.purge();
        mediaPlayer.release();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        mediaPlayer.release();
        mediaPlayer= null;

    }


    @Override
    public void onClicked(Music music, int position) {
        onBaseMediaPlayer();
      currentMusic =position;
      onMusicChange(musicList.get(currentMusic));
    }
}