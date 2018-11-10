package com.example.SpringBoot.Services;

import com.example.SpringBoot.Services.Error.MessageKey;

/**
 * @Author: Andy Su
 * @Date: 2018-11-10
 */
public class UtilServiceMessageKey extends MessageKey {
    public static String INCOMPATIBLE_INPUT = "Input has incompatible types";
    public static String WRONG_TYPE_INPUT = "Input has wrong types";
    public UtilServiceMessageKey(int status, String tags, String message){
        super(status, tags, message);
    }
}
