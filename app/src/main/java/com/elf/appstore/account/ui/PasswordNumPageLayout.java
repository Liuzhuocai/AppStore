package com.elf.appstore.account.ui;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.elf.appstore.R;

/**
 * Created by liuzuocai on 18-3-24.
 */

public class PasswordNumPageLayout extends BasePageLayout {
    public PasswordNumPageLayout(Context c) {
        super(c, false);
    }

    protected void onCreateContent(LinearLayout parent) {
        View view = mLayoutInflater.inflate(R.layout.login_pwd_layout, null);
        parent.addView(view);
    }
}
