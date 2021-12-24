package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class DocActivity extends AppCompatActivity {
    EditText editText;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent=getIntent();
        setContentView(R.layout.doclayout);
        editText = findViewById(R.id.edittext);
        readf();
        setTitle(intent.getStringExtra("n"));
    }
    public void readf(){
    BufferedReader br = null;
    StringBuffer stringBuffer=new StringBuffer();
    String line;
    try {
        br = new BufferedReader(new InputStreamReader(openFileInput(intent.getIntExtra("name",-1)+".txt")));
       while((line=br.readLine())!=null){
           stringBuffer.append(line);
       }
       editText.setText(stringBuffer.toString());
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        //关闭资源
        if(br != null){

            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
    @Override
    public void onBackPressed() {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(intent.getIntExtra("name",-1)+".txt", Context.MODE_PRIVATE)));
                bw.write(editText.getText().toString());//data中不包含换行符
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if(bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        super.onBackPressed();
    }
}

