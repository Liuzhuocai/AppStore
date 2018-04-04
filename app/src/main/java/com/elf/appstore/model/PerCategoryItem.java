package com.elf.appstore.model;

import com.elf.appstore.model.entities.AppItemInfo;

import java.util.ArrayList;

/**
 * Created by antino on 18-3-26.
 */

public class PerCategoryItem {
    //TODO:antino 首页应用分类数据
    private String type;
    private ArrayList<AppItemInfo> list;

    public ArrayList<AppItemInfo> getList() {
        return list;
    }

    public void setList(ArrayList<AppItemInfo> list) {
        this.list = list;
    }
}
