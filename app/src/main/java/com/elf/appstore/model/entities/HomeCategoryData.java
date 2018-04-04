package com.elf.appstore.model.entities;
import com.elf.appstore.model.BaseItem;
import com.elf.appstore.model.PerCategoryItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by antino on 18-4-2.
 */

public class HomeCategoryData {
    public ArrayList<PerCategoryItem> data;
    public boolean isEmpty(){
        return false;
    }

    public List<BaseItem> toBaseItem(){
        return null;
    }
}
