package com.elf.appstore.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by antino on 18-3-30.
 */

public class CategoryItem extends BaseItem implements Parcelable{
    private int typeId;
    public String tittle;
    public String url;
    public CategoryItem(){
        itemType=TYPE_CATEGORY;
    }

    protected CategoryItem(Parcel in) {
        typeId = in.readInt();
        tittle = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(typeId);
        dest.writeString(tittle);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CategoryItem> CREATOR = new Creator<CategoryItem>() {
        @Override
        public CategoryItem createFromParcel(Parcel in) {
            return new CategoryItem(in);
        }

        @Override
        public CategoryItem[] newArray(int size) {
            return new CategoryItem[size];
        }
    };
}
