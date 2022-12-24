package com.example.hw1;

import android.os.Bundle;

import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;


public class TopThreeFragment extends Fragment {
    private ExtendedFloatingActionButton[] topName;
    private TextView[] topThreeTV;
    private Record[] records;
    private TextInputEditText nameTI;
    private Button submitBtn;
    private MySP mySP;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_three, container, false);
        initParams(view); // findView is inside init
        Bundle score = getArguments();
        submitBtn.setOnClickListener(view2 -> {
            submitBtn.setEnabled(false);    //cancel button after saving data
            submitRecord(score.getInt("score"));
        });
        return view;
    }

    public void findView(View view) {
        topName = new ExtendedFloatingActionButton[]{
                view.findViewById(R.id.first_place),
                view.findViewById(R.id.second_place),
                view.findViewById(R.id.third_place)
        };
        topThreeTV = new TextView[] {
                view.findViewById(R.id.first_TV),
                view.findViewById(R.id.second_TV),
                view.findViewById(R.id.third_TV)
        };
        nameTI = view.findViewById(R.id.name_TI);
        submitBtn = view.findViewById(R.id.submit_btn);
    }

    public void initParams(View view) {
        topName = new ExtendedFloatingActionButton[3];
        topThreeTV = new TextView[3];
        findView(view);
        records = new Record[3];
        for (int i = 0; i < records.length; i++) {
            records[i] = new Record(Integer.valueOf(topThreeTV[i].getText().toString()),
                    topName[i].getText().toString());
        }
        mySP.init(getContext());
        mySP = mySP.getInstance();
        getDataFromSP();
        updateRecordsUI();
    }

    public String getName() {
        if (nameTI.getText().equals("")) { // no name
            return "John doe";
        }
        return nameTI.getText().toString();
    }

    public void updateRecordsUI() {
        for (int i = 0; i < records.length; i++) {
            topName[i].setText(records[i].getName());
            topThreeTV[i].setText(String.valueOf(records[i].getScore()));
        }
    }

    public void submitRecord(int score) {
        if (score < records[2].getScore()) {   // if score not high enough
            return;
        }
        if (score >= records[0].getScore()) {
            shiftRecordDown(2);
            records[0].setScore(score);
            records[0].setName(getName());
        } else if (score >= records[1].getScore()) {
            shiftRecordDown(1);
            records[1].setScore(score);
            records[1].setName(getName());
        } else {
            records[2].setScore(score);
            records[2].setName(getName());
        }
        updateRecordsUI();
        saveDataToSP();
    }

    public void shiftRecordDown(int howMany) {
        records[2].setScore(records[1].getScore());
        topThreeTV[2].setText(topThreeTV[2].getText());
        records[2].setName(topName[1].getText().toString());
        topName[2].setText(records[2].getName());
        if (howMany == 2) {
            records[1].setScore(records[0].getScore());
            topThreeTV[2].setText(topThreeTV[0].getText());
            records[1].setName(topName[0].getText().toString());
            topName[1].setText(records[1].getName());
        }
    }

    private void saveDataToSP() {
        String recordsJson = new Gson().toJson(records);
        mySP.putString("records", recordsJson);
    }

    private void getDataFromSP() {
        String recordsAsJson = mySP.getInstance().getString("records", "");
        if (!recordsAsJson.equals(""))
            records = new Gson().fromJson(recordsAsJson, Record[].class);
    }
}