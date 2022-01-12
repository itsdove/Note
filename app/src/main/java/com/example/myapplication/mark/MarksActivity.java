package com.example.myapplication.mark;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMarksBinding;
import com.example.myapplication.noteall.ItemViewModel;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MarksActivity extends AppCompatActivity {

    //private AppBarConfiguration appBarConfiguration;
    private ActivityMarksBinding binding;
    FragmentManager fragmentManager;

    RecordFragment recordFragment;
    RecordlistFragment recordlistFragment;

    List<RecordItem> recordItemList=new ArrayList<>();
    ItemViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMarksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        for(RecordItem e: LitePal.findAll(RecordItem.class)){
            recordItemList.add(e);
        }

        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        //读取recordItemList
        viewModel.setRecordItemList(recordItemList);

        fragmentManager = getSupportFragmentManager();
        setFragment();


    }

    private void setFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        recordlistFragment = new RecordlistFragment();
        fragmentTransaction.add(R.id.FRAGMENT_M,recordlistFragment );
        if(recordFragment!=null)fragmentTransaction.remove(recordFragment);
        fragmentTransaction.show(recordlistFragment);

        fragmentTransaction.commit();
    }

}
