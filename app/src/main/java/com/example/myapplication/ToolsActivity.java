package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ToolsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);

        CardView cardView_webmark=findViewById(R.id.BUTTON_WEBMARK);
        cardView_webmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ToolsActivity.this, WebpageCollectionActivity.class);
                startActivity(intent);
            }
        });
        CardView cardView_marks=findViewById(R.id.BUTTON_MARKS);
        cardView_marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ToolsActivity.this,MarksActivity.class);
                startActivity(intent);
            }
        });
    }
}