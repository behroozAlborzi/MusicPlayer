package com.behroozalborzi.musicplayer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Behrooz on 7/8/2021.
 * https://behroozalborzi.ir
 * Android Developer
 * Thank you ... :)
 */
public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {
    private List<Music> musicList;
    private int musicPlayPosition=-1;
    private ClickItemListener clickItemListener;

    public MusicAdapter(List<Music> musicList,ClickItemListener clickItemListener) {
        this.clickItemListener = clickItemListener;
        this.musicList = musicList;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MusicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_music_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        holder.bind(musicList.get(position));
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder{
        private SimpleDraweeView simpleViewArtist;
        private TextView artistTv;
        private TextView musicNameTv;
        private LottieAnimationView animationView;

        public MusicViewHolder(@Nullable View itemView) {
            super(itemView);
            simpleViewArtist = itemView.findViewById(R.id.musicPlayer_rv_artistIv);
            artistTv = itemView.findViewById(R.id.musicPlayer_rv_artistNameTv);
            musicNameTv = itemView.findViewById(R.id.musicPlayer_rv_musicNameTv);
            animationView = itemView.findViewById(R.id.animationViewMusic);

        }

        public void bind(Music music){
            simpleViewArtist.setActualImageResource(music.getCoverResId());
            artistTv.setText(music.getArtist());
            musicNameTv.setText(music.getMusicName());
//            imageViewLyrics.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    itemEventListener.onLyricsClicked(music);
//                }
//            });

            if (getAdapterPosition() == musicPlayPosition){
                animationView.setVisibility(View.VISIBLE);
            }else {
                animationView.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickItemListener.onClicked(music,getAdapterPosition());
                }
            });

        }

    }

    public void notifyOnMusicChange(Music music){
        int index = musicList.indexOf(music);
        if (index != -1){
            if (index != musicPlayPosition){
                notifyItemChanged(musicPlayPosition);
                musicPlayPosition = index;
                notifyItemChanged(musicPlayPosition);
            }
        }
    }
}
