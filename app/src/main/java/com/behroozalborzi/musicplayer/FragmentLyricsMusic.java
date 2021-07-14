package com.behroozalborzi.musicplayer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by Behrooz on 7/10/2021.
 * https://behroozalborzi.ir
 * Android Developer
 * Thank you ... :)
 */
public class FragmentLyricsMusic extends Fragment {

//    private ClickItemListener itemEventListener;
    private Music music;
    private  TextView artistName;
    private  TextView musicName;
    private  ImageView imageViewArtist;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        itemEventListener = (ClickItemListener) context;
        if (getArguments() != null){
            music = getArguments().getParcelable("artist");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_lyrics_music,container,false);
        artistName = view.findViewById(R.id.tv_framentLyricsMusicArtist);
        musicName = view.findViewById(R.id.tv_fragmentLyricsMusicName);
        imageViewArtist = view.findViewById(R.id.iv_fragmentLyricsArtistCover);

        artistName.setText(music.getArtist());
        musicName.setText(music.getMusicName());
        imageViewArtist.setImageResource(music.getArtistResId());


        view.findViewById(R.id.iv_fragmentLyricsBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}
