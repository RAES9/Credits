package com.raes.ezz.helpers;

public final class Singleton {
    private static final Singleton INSTANCE = new Singleton();
    private Singleton() {}
    public DatabaseConnection db = new DatabaseConnection("rds-mysql.cc8kiiamwgsp.us-east-1.rds.amazonaws.com"
            ,"3306"
            , "kiosk"
            , "admin"
            ,"masterUser");

    public static Singleton getInstance() {
        return INSTANCE;
    }
}
