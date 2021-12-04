package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Adapter adapter;
    RecyclerView recyclerView;
    List<Note> notes= new LinkedList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Note note=new Note();
        note.Title="asd";
        notes.add(note);
        adapter = new Adapter(notes);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView= findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(8));
    }
}

 class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0)
            outRect.top = space;
    }
}