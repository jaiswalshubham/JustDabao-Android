package com.assignment.justdabao.main.models;

import java.util.List;

public class AllItem {
    List<AddItem> addItemList;

    public AllItem(List<AddItem> addItemList) {
        this.addItemList = addItemList;
    }

    public List<AddItem> getAddItemList() {
        return addItemList;
    }

    public void setAddItemList(List<AddItem> addItemList) {
        this.addItemList = addItemList;
    }
}
