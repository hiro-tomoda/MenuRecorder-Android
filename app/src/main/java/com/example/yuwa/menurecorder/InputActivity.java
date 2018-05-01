package com.example.yuwa.menurecorder;

import android.app.Application;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class InputActivity extends AppCompatActivity {

    private Button mRegistButton;


    private EditText mDateEdit;
    private Button mDateButton;

    private RadioGroup mRadioGroup;


    private Date mDate;
    private String mTimezone;




    private Realm mRealm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);



        mDateEdit = findViewById(R.id.date_edit);

        mDateButton = findViewById(R.id.date_button);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });





        mRadioGroup = findViewById(R.id.timezone_group);
        mRadioGroup.check(R.id.timezone_rb3);



        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radio = (RadioButton)group.findViewById(checkedId);


                Log.d("test", String.valueOf(radio.getId()));



                mTimezone = (String)radio.getText();
                Log.d("test", mTimezone);
            }
        });







        mRegistButton = findViewById(R.id.regist_button);
        mRegistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();


            }
        });


        // Realmの設定
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        mRealm = Realm.getInstance(config);






    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mRealm.close();
    }

    //////////////////////////////////////////////////////
    //メソッド名：showAlertDialog
    //概要：確認ダイアログを表示
    //////////////////////////////////////////////////////
    private void showAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("確認");
        alertDialogBuilder.setMessage("登録してよろしいですか？");

        alertDialogBuilder.setPositiveButton("はい", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 登録画面表示を終了する
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // ダイアログを閉じる
            }
        });


        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    //////////////////////////////////////////////////////
    //メソッド名：showDatePickerDialog
    //概要：日付入力ダイアログを表示
    //////////////////////////////////////////////////////
    private void showDatePickerDialog() {
        Log.d("test", "setOnClickListener");
        DatePickerDialog datePickerDialog = new DatePickerDialog(InputActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        try {
                            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
                            mDate = sdFormat.parse(String.format(Locale.JAPAN, "%02d/%02d/%02d", year, month, dayOfMonth));

                            Log.d("test", sdFormat.format(mDate));


                            mDateEdit.setText(sdFormat.format(mDate));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                2018, 4, 1);
        datePickerDialog.show();
    }


    private void addMenu() {
        Menu menu = new Menu();





        // Realmにレコードを登録
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(menu);
        mRealm.commitTransaction();
    }



}
