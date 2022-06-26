package com.anymv.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

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

    public static String hashSha256(String input) {
        return Hashing.sha256().hashString(input, StandardCharsets.UTF_8).toString();
    }
}
