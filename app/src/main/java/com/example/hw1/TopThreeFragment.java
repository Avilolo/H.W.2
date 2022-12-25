package com.example.hw1;

import android.location.Location;
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


public class TopThreeFragment extends Fragment implements View.OnClickListener{
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
        topName[0].setOnClickListener(view3 ->
        {

        });
        topName[1].setOnClickListener(view3 ->
        {

        });
        topName[2].setOnClickListener(view3 ->
        {

        });
        return view;
    }

    public void findView(View view) {
        topName = new ExtendedFloatingActionButton[]{
                view.findViewById(R.id.first_place),
                view.findViewById(R.id.second_place),
                view.findViewById(R.id.third_place)
        };
        topThreeTV = new TextView[]{
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
        //TODO set for test delete after
        {
            Location loc1 = new Location("a");
            loc1.setLatitude(32.07305292200886);
            loc1.setLongitude(34.81572865754036);
            Location loc2 = new Location("b");
            loc1.setLatitude(32.08018539444466);
            loc1.setLongitude(34.90582291178376);
            Location loc3 = new Location("c");
            loc1.setLatitude(32.12334080559518);
            loc1.setLongitude(34.80645009997441);
            records[0].setLocation(new Location(loc1));
            records[1].setLocation(loc2);
            records[2].setLocation(loc3);
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
        for (int i = 0, j = 2; i < records.length; i++, j--) {
            if (score >= records[i].getScore()) {
                shiftRecordDown(j);
                records[i].setScore(score);
                records[i].setName(getName());
//                records[i].setLocation(mapFragment.requestCurrentLocation());
                break;
            }
        }
//        if (score >= records[0].getScore()) {
//            shiftRecordDown(2);
//            records[0].setScore(score);
//            records[0].setName(getName());
//        } else if (score >= records[1].getScore()) {
//            shiftRecordDown(1);
//            records[1].setScore(score);
//            records[1].setName(getName());
//        } else {
//            records[2].setScore(score);
//            records[2].setName(getName());
//        }
        updateRecordsUI();
        saveDataToSP();
    }

    public void shiftRecordDown(int howMany) {
        if (howMany == 0)
            return;
        records[2].setScore(records[1].getScore());
        topThreeTV[2].setText(topThreeTV[1].getText());
        records[2].setName(topName[1].getText().toString());
        topName[2].setText(records[2].getName());
//        records[2].setLocation(records[1].getLocation());
        if (howMany == 2) {
            records[1].setScore(records[0].getScore());
            records[1].setName(topName[0].getText().toString());
//            records[1].setLocation(records[0].getLocation());
            topThreeTV[1].setText(topThreeTV[0].getText());
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

    @Override
    public void onClick(View view) {
        for (ExtendedFloatingActionButton btn : topName) {
            btn.setOnClickListener(this);
        }
    }
}