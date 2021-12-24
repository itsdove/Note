package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    List<Note> notes;
    private Context mcontext;
    public Adapter(List<Note> noteList) {
        this.notes = noteList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mcontext=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.textView.setText(note.getTitle());
        holder.imageView.setImageResource(R.drawable.doc);
    }
    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements MenuItem.OnMenuItemClickListener, View.OnCreateContextMenuListener {

        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(v -> {
                Intent intent=new Intent(mcontext,DocActivity.class);
                intent.putExtra("name",notes.get(getAdapterPosition()).getId());
                intent.putExtra("n",notes.get(getAdapterPosition()).getTitle());
                mcontext.startActivity(intent);
            });
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int position = getAdapterPosition();
            if(item.getItemId()==2){
            Note note = notes.get(position);
            notes.remove(position);
            Adapter.this.notifyItemRemoved(position);
            File file=new File("/data/data/com.example.myapplication/files/"+note.getId()+".txt");
            file.delete();
            note.delete();}
            else{
                View dialagueView= LayoutInflater.from(mcontext).inflate(R.layout.dialogview,null);
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(mcontext);
                alertDialog.setView(dialagueView);
                EditText editName=dialagueView.findViewById(R.id.de1);
                editName.setText(notes.get(position).getTitle());
                alertDialog.setPositiveButton("确定", (dialogInterface, i) -> {
                    Note note = notes.get(position);
                    note.setTitle(editName.getText().toString());
                    notes.set(position,note);
                    note.save();
                    Adapter.this.notifyItemChanged(position);
                });
                alertDialog.setCancelable(false).setNegativeButton ("取消", (dialogInterface, i) -> {
                });
                alertDialog.create().show();

            }
            return true;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem menuItem1 = menu.add(Menu.NONE, 1, 1, "重命名");
            MenuItem menuItem2 = menu.add(Menu.NONE, 2, 2, "删除");
            menuItem2.setOnMenuItemClickListener(this);
            menuItem1.setOnMenuItemClickListener(this);
        }
    }
}
