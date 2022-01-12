package com.example.myapplication.clock;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;

public class Fqclock extends Fragment {
    TomatoView clockView;
    TextView textViewStart;
    TextView textViewStop;
    private int interval=0;
    static  View root ;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.activity_main2, container, false);
        }
        clockView = root.findViewById(R.id.clockView);
        textViewStart = root.findViewById(R.id.tv_start);
        textViewStop=root.findViewById(R.id.tv_stop);
        textViewStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textViewStart.getText().toString().equals("开始")) {
                    if(clockView.start()) {
                        clockView.start();
                        textViewStart.setText("取消");
                    }
                }else{
                    AlertDialog.Builder alertDB = new AlertDialog.Builder(getContext());
                    alertDB.setPositiveButton(Fqclock.this.getResources().getString(R.string.string_confirmation), (dialogInterface, i) -> {
                        if(clockView.cancel()) {
                            clockView.cancel();
                            textViewStart.setText("开始");
                        }
                    });
                    alertDB.setNegativeButton(Fqclock.this.getResources().getString(R.string.string_cancel), (dialogInterface, i) -> {

                    });
                    alertDB.setMessage(Fqclock.this.getResources().getString(R.string.string_confirm_delete));
                    alertDB.setTitle(Fqclock.this.getResources().getString(R.string.hint)).show();
                }

            }
        });

        textViewStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textViewStop.getText().toString().equals("暂停")) {
                    if(clockView.purse()) {
                        clockView.purse();
                        textViewStop.setText("开始");
                    }
                }else {
                    if (clockView.start()) {
                        clockView.start();
                        textViewStop.setText("暂停");
                    }
                }
            }
        });
        return  root;
    }

}