package com.example.myapplication.noteall;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.example.myapplication.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DocActivity extends AppCompatActivity implements EventListener {
    EditText editText;
    Intent intent;
    private EventManager asr;//语音识别核心库

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent=getIntent();
        setContentView(R.layout.doclayout);
        editText = findViewById(R.id.edittext);

        readf();
        setTitle(intent.getStringExtra("n"));

        initPermission();

        //初始化EventManager对象
        asr = EventManagerFactory.create(this, "asr");
        //注册自己的输出事件类
        asr.registerListener(this); //  EventListener 中 onEvent方法

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
    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }




    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {

        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
            // 识别相关的结果都在这里
            if (params == null || params.isEmpty()) {
                return;
            }
            if (params.contains("\"final_result\"")) {
                // 一句话的最终识别结果
                String regrex = "\\[(.*?),";  //使用正则表达式抽取我们需要的内容
                Pattern pattern = Pattern.compile(regrex);
                Matcher matcher = pattern.matcher(params);
                if (matcher.find()) {
                    int a  = matcher.group(0).indexOf("[");
                    int b  = matcher.group(0).indexOf(",");
                    editText.setText(editText.getText()+matcher.group(0).substring(a+2,b-3));
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item=  menu.add(0,1,0,"");
        item.setIcon(R.drawable.yy);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                asr.send(SpeechConstant.ASR_START, null, null, 0, 0);
                return true;
    }});
        return true;
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

