package com.example.myapplication.mark;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;

public class ToolsActivity extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_tools, container, false);

        CardView cardView_webmark=root.findViewById(R.id.BUTTON_WEBMARK);
        cardView_webmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), WebpageCollectionActivity.class);
                startActivity(intent);
            }
        });
        CardView cardView_marks=root.findViewById(R.id.BUTTON_MARKS);
        cardView_marks.setOnClickListener(v -> {
            Intent intent=new Intent(getContext(), MarksActivity.class);
            startActivity(intent);
        });
        return root;
    }

}