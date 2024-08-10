package com.example.finalapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.finalapp.R;

import java.io.File;
import java.io.FileWriter;

// fragment that governs the behaviour of feedback submission
public class Feedback extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_feedback, container, false);

        Button submitButton = view.findViewById(R.id.submitButton);
        EditText text = view.findViewById(R.id.text);

        // write feedback into a feedback file
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeFileOnInternalStorage(getActivity(), "feedback.txt", text.getText().toString() + "\n");
                makeToast("Feedback is submitted");
                text.setText("");
            }
        });
        return view;
    }

    // write a sting in certain file that will be stored in feedback folder
    public void writeFileOnInternalStorage(Context mcoContext, String sFileName, String sBody){
        File dir = new File(mcoContext.getFilesDir(), "feedback");
        if(!dir.exists()){
            dir.mkdir();
        }
        try {
            File gpxfile = new File(dir, sFileName);
            FileWriter writer = new FileWriter(gpxfile, true);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // show a string in an enlarged toast message
    public void makeToast(String text)
    {
        SpannableStringBuilder biggerText = new SpannableStringBuilder(text);
        biggerText.setSpan(new RelativeSizeSpan(3f), 0, text.length(), 0);
        Toast.makeText(getActivity(), biggerText, Toast.LENGTH_SHORT).show();
    }
}
