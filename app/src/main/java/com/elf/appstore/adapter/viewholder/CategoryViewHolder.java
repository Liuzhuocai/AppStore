package com.elf.appstore.adapter.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.elf.appstore.R;
import com.elf.appstore.adapter.base.CommonViewHolder;
import com.elf.appstore.model.CategoryItem;

/**
 * Created by antino on 18-4-2.
 */

public class CategoryViewHolder extends CommonViewHolder<CategoryItem> {

    public CategoryViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void bindData(CategoryItem data) {
        TextView title = (TextView)getView(R.id.category_tittle);
        title.setText(data.tittle);
        TextView more = (TextView)getView(R.id.category_more);
        Log.i("xxxx","CategoryViewHolder setData = "+" , "+data.tittle);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonClickListener.onMoreClickListener(data);
            }
        });
    }
}
