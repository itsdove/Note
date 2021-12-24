package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Adapter adapter;
    RecyclerView recyclerView;
    List<Note> notes= new LinkedList<>();
    int mSelectPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LitePal.initialize(this);
        LitePal.getDatabase();
        for(Note n:LitePal.findAll(Note.class)){
            notes.add(n);
        }
        adapter = new Adapter(notes);
        recyclerView= findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(8));
        FloatingActionButton floatingActionButton = findViewById(R.id.fbutton);
        floatingActionButton.setOnClickListener(view -> {
            Note note=new Note();
            note.Title="我的笔记"+notes.size();
            note.setId(notes.size());
            notes.add(note);
            note.save();
            adapter.notifyItemInserted(notes.size());
            Intent intent=new Intent(this,DocActivity.class);
            intent.putExtra("name",notes.get(notes.size()-1).getId());
            intent.putExtra("n",notes.get(notes.size()-1).getTitle());
            startActivity(intent);

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item=  menu.add(0,1,0,"");
        item.setIcon(R.drawable.button_tools);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent=new Intent(MainActivity.this,ToolsActivity.class);
                startActivity(intent);
                return true;
            }});

        return true;
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