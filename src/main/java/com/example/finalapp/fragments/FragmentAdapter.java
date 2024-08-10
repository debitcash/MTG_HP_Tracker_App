package com.example.finalapp.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:{
                SoundEffects soundEffects = new SoundEffects();
                return soundEffects;
            }
            case 1:{
                AboutUs aboutUs = new AboutUs();
                return aboutUs;
            }
            case 2:{
                Feedback feedback = new Feedback();
                return feedback;
            }
            default:{
                return null;
            }
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
