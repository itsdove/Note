package com.example.myapplication;

import static java.security.AccessController.getContext;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ActivityWebpagecollectionBinding;
import com.google.android.material.tabs.TabLayout;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;


public class WebpageCollectionActivity extends AppCompatActivity {

    //private AppBarConfiguration appBarConfiguration;
    private ActivityWebpagecollectionBinding binding;
    private ArrayList<WebPageCollection> collectionList=new ArrayList<>();
    ItemAdapter itemAdapter = new ItemAdapter();
    RecyclerView recyclerView;
    private AlertDialog dlg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWebpagecollectionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
/*
        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_webmark);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
*/
        binding.FABADDCOLLECTION.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(0,0);
            }
        });
        binding.BUTTONEDITCOLLECTION.setVisibility(View.GONE);
        binding.BUTTONDELETECOLLECTION.setVisibility(View.GONE);
        binding.BUTTONCANCELCOLLECTION.setVisibility(View.GONE);
        binding.BUTTONEDITCOLLECTION.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1,itemAdapter.getPosition());
            }
        } );
        binding.BUTTONDELETECOLLECTION.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=itemAdapter.getPosition();
                LitePal.delete(WebPageCollection.class,pos+1);
                collectionList.remove(pos);
                itemAdapter.notifyDataSetChanged();

                binding.BUTTONEDITCOLLECTION.setVisibility(View.GONE);
                binding.BUTTONDELETECOLLECTION.setVisibility(View.GONE);
                binding.BUTTONCANCELCOLLECTION.setVisibility(View.GONE);
            }
        });
        binding.BUTTONCANCELCOLLECTION.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.BUTTONEDITCOLLECTION.setVisibility(View.GONE);
                binding.BUTTONDELETECOLLECTION.setVisibility(View.GONE);
                binding.BUTTONCANCELCOLLECTION.setVisibility(View.GONE);
            }
        });

        for(WebPageCollection e: LitePal.findAll(WebPageCollection.class)){
            collectionList.add(e);
        }

        recyclerView = findViewById(R.id.RV_COLLECTIONLIST);
        recyclerView.setAdapter(itemAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(WebpageCollectionActivity.this);
        recyclerView.setLayoutManager(layoutManager);
    }



    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

    }


    private void showDialog(int flag,int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(WebpageCollectionActivity.this);
        builder.setView(WebpageCollectionActivity.this.getLayoutInflater().inflate(R.layout.dialog_webpagecollection, null));

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText collection_name = (EditText) dlg.findViewById(R.id.ET_WEBPAGE_NAME);
                EditText collection_url = (EditText) dlg.findViewById(R.id.ET_WEBPAGE_URL);
                String name = collection_name.getText().toString();
                String url = collection_url.getText().toString();
                if (flag == 0) {
                    WebPageCollection collection = new WebPageCollection(name, url);
                    collectionList.add(collection);
                    collection.save();
                } else if (flag == 1) {
                    WebPageCollection collection= collectionList.get(pos);
                    collection.setName(name);
                    collection.setUrl(url);

                    LitePal.delete(WebPageCollection.class,pos+1);
                    collectionList.remove(pos);

                    collectionList.add(collection);
                    collection.save();
                }
                recyclerView.getAdapter().notifyDataSetChanged();


                binding.BUTTONDELETECOLLECTION.setVisibility(View.GONE);
                binding.BUTTONEDITCOLLECTION.setVisibility(View.GONE);
                binding.BUTTONCANCELCOLLECTION.setVisibility(View.GONE);
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dlg=builder.create();
        dlg.show();
        if(flag==1){
            EditText collection_name = (EditText) dlg.findViewById(R.id.ET_WEBPAGE_NAME);
            EditText collection_url = (EditText) dlg.findViewById(R.id.ET_WEBPAGE_URL);
            String name = collectionList.get(pos).getName();
            String url =collectionList.get(pos).getUrl();
            collection_name.setText(name);
            collection_url.setText(url);
        }
    }



    private class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.CollectionViewHolder> {

        private int mPosition;
        public int getPosition(){return mPosition;}
        @NonNull
        @Override
        public CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(WebpageCollectionActivity.this).inflate(R.layout.itemlist_collection, parent, false);
            CollectionViewHolder viewHoder = new CollectionViewHolder(view);
            return viewHoder;
        }


        @Override
        public void onBindViewHolder(@NonNull CollectionViewHolder holder, int position) {
            WebPageCollection collection = collectionList.get(position);
            holder.name.setText(collection.getName());
            holder.url.setText(collection.getUrl());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPosition = holder.getLayoutPosition();
                    String url = collectionList.get(mPosition).getUrl();
                    if (url.isEmpty()) return;
                    if (!url.matches("(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                            + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)")){
                        return;
                    }
                    if(!isConnect(url))return;
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mPosition=holder.getLayoutPosition();
                    binding.BUTTONEDITCOLLECTION.setVisibility(View.VISIBLE);
                    binding.BUTTONDELETECOLLECTION.setVisibility(View.VISIBLE);
                    binding.BUTTONCANCELCOLLECTION.setVisibility(View.VISIBLE);
                    return true;//false;
                }
            });
        }

        @Override
        public int getItemCount() {
            return collectionList.size();
        }

        private class CollectionViewHolder extends RecyclerView.ViewHolder{
            TextView name;
            TextView url;

            public CollectionViewHolder(@NonNull View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.TEXT_COLLECTION_NAME);
                url=itemView.findViewById(R.id.TEXT_COLLECTION_URL);
            }
        }
    }

    private boolean isConnect(String url_str) {
        HttpURLConnection con;
        URL url;
        int counts = 0;
        if (url_str == null || url_str.length() <= 0) {
            return false;
        }
        while (counts < 5) {
            try {
                url = new URL(url_str);
                con = (HttpURLConnection) url.openConnection();
                break;
            }catch (Exception ex) {
                counts++;
                continue;
            }
        }
        if(counts==5)return false;
        else return true;
    }


/*
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_webmark);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
*/
}