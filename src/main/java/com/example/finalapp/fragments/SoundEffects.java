package com.example.finalapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.finalapp.services.MusicService;
import com.example.finalapp.R;

// fragment that governs the behaviour of music player
public class SoundEffects extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_sound_effects, container, false);

        Button onButton = view.findViewById(R.id.onButton);
        Button offButton = view.findViewById(R.id.offButton);

        // play music on click
        onButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MusicService.class);
            getActivity().startForegroundService(intent);
        });

        // stop music on click
        offButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MusicService.class);
            getActivity().stopService(intent);
        });
        return view;
    }
}
