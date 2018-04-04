package com.elf.appstore.model;

import com.elf.appstore.model.entities.AppItemInfo;

import java.util.List;

/**
 * Created by antino on 18-3-26.
 */

public class ApplistData {
    List<AppItemInfo> list;

    public List<AppItemInfo> getList() {
        return list;
    }

    public void setList(List<AppItemInfo> list) {
        this.list = list;
    }
}
