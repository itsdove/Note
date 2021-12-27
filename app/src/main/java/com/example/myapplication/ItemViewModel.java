package com.example.myapplication;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ItemViewModel extends ViewModel  {
    private RecordItem recordItem=new RecordItem();
    private List<RecordItem> recordItemList=new ArrayList<>();
    private int pos=-1;

    public void setRecordItem(RecordItem recordItem) {
        this.recordItem = recordItem;
    }

    public void setRecordItemList(List<RecordItem> recordItemList) {
        this.recordItemList = recordItemList;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public RecordItem getRecordItem() {
        return recordItem;
    }

    public List<RecordItem> getRecordItemList() {
        return recordItemList;
    }

    public int getPos() {
        return pos;
    }
}
