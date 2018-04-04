package com.elf.appstore.download;

import com.elf.appstore.model.DownloadTaskRecord;

/**
 * Created by antino on 18-3-22.
 */

public interface DownloadListener {
    void onStateChanged(DownloadTaskRecord info);
}
