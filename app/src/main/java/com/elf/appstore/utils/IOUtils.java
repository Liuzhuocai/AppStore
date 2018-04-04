package com.elf.appstore.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by antino on 18-3-19.
 */

public class IOUtils {
    public static void closeAll(Closeable... closeables){
        if(closeables == null){
            return;
        }
        for (Closeable closeable : closeables) {
            if(closeable!=null){
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
