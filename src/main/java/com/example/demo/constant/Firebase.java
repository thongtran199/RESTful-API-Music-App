package com.example.demo.constant;

import org.hibernate.validator.internal.engine.messageinterpolation.parser.Token;

public class Firebase {
    private static String TOKEN = "";
    public static String getToken()
    {
        return TOKEN;
    }
    public static void setToken(String token)
    {
        TOKEN = token;
    }
}
