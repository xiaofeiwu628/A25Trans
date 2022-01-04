package com.example.timemangertrans;

public class GlobalVariable {

    static public String useraccount;

    public GlobalVariable(){

    }
    public static String getUseraccount() {
        return useraccount;
    }

    public static void setUseraccount(String useraccount) {
        GlobalVariable.useraccount = useraccount;
    }


    public GlobalVariable(String account){
        useraccount = account;
    }
}
