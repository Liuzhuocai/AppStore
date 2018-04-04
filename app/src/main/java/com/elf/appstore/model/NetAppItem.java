package com.elf.appstore.model;

import com.elf.appstore.model.entities.AppItemInfo;

/**
 * Created by antino on 18-3-30.
 */

public class NetAppItem extends BaseItem {
    public AppItemInfo appItemInfo;
    public NetAppItem(){
        appItemInfo = new AppItemInfo();
        itemType=TYPE_NET_APP;
    }
}
