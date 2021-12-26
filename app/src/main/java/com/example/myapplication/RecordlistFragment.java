package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.FragmentRecordlistBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecordlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordlistFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentRecordlistBinding binding;
    private List<RecordItem> recordItemList;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter=new ItemAdapter();
    private ItemViewModel viewModel;

    public RecordlistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecordlistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordlistFragment newInstance(String param1, String param2) {
        RecordlistFragment fragment = new RecordlistFragment();
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
        binding = FragmentRecordlistBinding.inflate(inflater, container, false);
        return binding.getRoot();//return inflater.inflate(R.layout.fragment_recordlist, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        recordItemList=viewModel.getRecordItemList();

        recyclerView= binding.RVRECORDLIST;
        recyclerView.setAdapter(itemAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        itemAdapter.notifyDataSetChanged();

        binding.FABADDMARKS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setPos(-1);
                setRecordFragment();
            }
        });
    }

    private void setRecordFragment() {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.FRAGMENT_M, new RecordFragment(), null)
                .addToBackStack(null)
                .commit();
    }

    private class ItemAdapter extends RecyclerView.Adapter<RecordlistFragment.ItemAdapter.RecordViewHolder> {
        private int mPosition;

        public int getmPosition() {
            return mPosition;
        }

        @NonNull
        @Override
        public ItemAdapter.RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.itemlist_record, parent, false);
            RecordViewHolder viewHoder = new RecordViewHolder(view);
            return viewHoder;
        }

        @Override
        public void onBindViewHolder(@NonNull ItemAdapter.RecordViewHolder holder, int position) {
            RecordItem recordItem=recordItemList.get(position);
            holder.title.setText(recordItem.getTitle());
            String type="未选择";
            switch (recordItem.getType()){
                case 0:type="书籍";break;
                case 1:type="影视";break;
                case 2:type="音乐";break;
                default:break;
            }
            holder.type.setText(type);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPosition=holder.getLayoutPosition();
                    viewModel.setPos(mPosition);
                    setRecordFragment();
                }
            });
        }

        @Override
        public int getItemCount() {
            return recordItemList.size();
        }

        public class RecordViewHolder extends RecyclerView.ViewHolder{
            TextView title;
            TextView type;

            public RecordViewHolder(@NonNull View itemView) {
                super(itemView);
                title=itemView.findViewById(R.id.TEXT_RECORDLIST_TITLE);
                type=itemView.findViewById(R.id.TEXT_RECORDLIST_TYPE);
            }
        }
    }
}