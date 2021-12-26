package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.myapplication.databinding.FragmentRecordBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentRecordBinding binding;
    private RecordItem recordItem=new RecordItem();
    private ItemViewModel viewModel;
    private List<RecordItem> recordItemList;
    private int pos;

    public RecordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordFragment newInstance(String param1, String param2) {
        RecordFragment fragment = new RecordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentRecordBinding.inflate(inflater, container, false);

        return binding.getRoot();//inflater.inflate(R.layout.fragment_account, container, false);
        //return inflater.inflate(R.layout.fragment_record, container, false);
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        recordItemList=viewModel.getRecordItemList();

        pos=viewModel.getPos();
        if(pos!=-1){
            recordItem=recordItemList.get(pos);
            binding.ETRECORDTITLE.setText(recordItem.getTitle());
            binding.ETRECORDURL.setText(recordItem.getUrl());
            binding.ETRECORDDESCPTION.setText(recordItem.getDescription());
            switch (recordItem.getType()){
                case 0:binding.RBRECORDBOOK.setChecked(true);break;
                case 1:binding.RBRECORDVIDEO.setChecked(true);break;
                case 2:binding.RBRECORDMUSIC.setChecked(true);break;
                default:break;
            }
            switch (recordItem.getState()){
                case 0:binding.RBRECORD0.setChecked(true);break;
                case 1:binding.RBRECORD1.setChecked(true);break;
                case 2:binding.RBRECORD2.setChecked(true);break;
                default:break;
            }
            binding.RATINGBARRECORD.setRating(recordItem.getRating());
        }

        binding.SWITCHRECORD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    binding.ETRECORDTITLE.setEnabled(true);
                    binding.ETRECORDURL.setEnabled(true);
                    binding.ETRECORDDESCPTION.setEnabled(true);
                }
                else{
                    binding.ETRECORDTITLE.setEnabled(false);
                    binding.ETRECORDURL.setEnabled(false);
                    binding.ETRECORDDESCPTION.setEnabled(false);
                }
            }
        });
        binding.RGRECORDTYPE.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String str="";
                switch (checkedId){
                    case R.id.RB_RECORD_BOOK:str="阅读";recordItem.setType(0);break;
                    case R.id.RB_RECORD_VIDEO:str="观看";recordItem.setType(1);break;
                    case R.id.RB_RECORD_MUSIC:str="听";recordItem.setType(2);break;
                }
                binding.RBRECORD0.setText("未"+str);
                binding.RBRECORD1.setText("正在"+str);
                binding.RBRECORD2.setText("已"+str);
            }
        });
        binding.RGRECORDSTATE.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.RB_RECORD_0:recordItem.setState(0);break;
                    case R.id.RB_RECORD_1:recordItem.setState(1);break;
                    case R.id.RB_RECORD_2:recordItem.setState(2);break;

                }
            }
        });
        binding.BUTTONRECORDOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordItem.setTitle(binding.ETRECORDTITLE.getText().toString());
                recordItem.setUrl(binding.ETRECORDURL.getText().toString());
                recordItem.setDescription(binding.ETRECORDDESCPTION.getText().toString());
                recordItem.setRating(binding.RATINGBARRECORD.getRating());

                if(pos!=-1)recordItemList.remove(pos);

                recordItemList.add(recordItem);
                recordItem.save();
                setRecordItemListFragment();
            }
        });
        binding.BUTTONRECORDCANCEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecordItemListFragment();
            }
        });
    }

    private void setRecordItemListFragment() {

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.FRAGMENT_M, new RecordlistFragment(), null)
                .commit();
    }

}