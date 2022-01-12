package com.example.myapplication.noteall;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.litepal.LitePal;
import java.util.LinkedList;
import java.util.List;

public class Notelist extends Fragment {
    Adapter adapter;
    RecyclerView recyclerView;
    List<Note> notes= new LinkedList<>();
    int mSelectPosition;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_main, container, false);
        LitePal.getDatabase();
        notes=LitePal.findAll(Note.class);
        adapter = new Adapter(notes);
        recyclerView=root.findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SpacesItemDecoration(8));
        FloatingActionButton floatingActionButton = root.findViewById(R.id.fbutton);
        floatingActionButton.setOnClickListener(view -> {
            Note note=new Note();
            note.Title="我的笔记"+notes.size();
            note.setId(notes.size());
            notes.add(note);
            note.save();
            adapter.notifyItemInserted(notes.size());
            Intent intent=new Intent(getContext(), DocActivity.class);
            intent.putExtra("name",notes.get(notes.size()-1).getId());
            intent.putExtra("n",notes.get(notes.size()-1).getTitle());
            startActivity(intent);

        });
        return root;
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