package com.elf.appstore.model;

/**
 * Created by antino on 18-3-30.
 */

public class BaseItem {
    public static final int TYPE_BANNER = 0;
    public static final int TYPE_CATEGORY = 1;
    public static final int TYPE_NET_APP = 2;
    public int itemType=0;
    public int getItemType() {
        return itemType;
    }
}
