package com.anymv.util;

public class Utils {
    public static int findOffset(int page, int size){
        if(page == 0) {
            page = 1;
        }

        if(size == 0) {
            size = 1;
        }

        return page * size;
    }
}
