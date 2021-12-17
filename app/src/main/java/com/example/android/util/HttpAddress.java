package com.example.android.util;

import java.io.Serializable;

public class HttpAddress {
    private static String[] args;
    final private static String adminAddress="admin";
    public static String admin(){
        return adminAddress;
    }
    /**
     *
     * @param address 首地址 例如：“user”
     * @param method 地址中的方法  例如： “insert”
     * @return
     */
    public static String[] get(String address,String method){
        switch (method){
            case "login":args=getLoginAddress(address);
                break;
            case "save":args=getInsertAddress(address);
                break;
            case "update":args=getUpdateAddress(address);
                break;
            case "getAll":args=getListAddress(address);
                break;
            case "start":args=getStartAddress(address);
                break;
            case "ifStart":args=getIfStartAddress(address);
                break;
            case "byDate":args=getByDateAddress(address);
                break;
            case "getStu":args=getStuAddress(address);
                break;
            case "saveStu":args=getSaveStuAddress(address);
                break;
        }
        return args;
    }
    /**
     *采用方法重载，分别处理两种情况，带id和不带id
     * @param address 首地址 例如：“user”
     * @param method 地址中的方法  例如： “delete”
     * @param id id则为相应参数  delete后的id参数
     * @return
     */
    public static String[] get(String address, String method, Serializable id){
        switch (method){
            case "delete":args=getDeleteAddress(address,id);
                break;
            case "get":args=getLineAddress(address,id);
                break;
            case "attendance":args=getAttendanceAddress(address,id);
                break;
            case "detail":args=getDetailAddress(address,id);
                break;
        }
        return args;
    }
    public static String[] getStuStatus(String address, Serializable id, Serializable date){
        args = getStuStatusAddress(address,id,date);
        return args;
    }

    private static String[] getLoginAddress(String address){
        args=new String[]{address,"login"};
        return args;
    }
    private static String[] getInsertAddress(String address){
        args=new String[]{address,"save"};
        return args;
    }
    private static String[] getDeleteAddress(String address, Serializable id){
        args=new String[]{address,"delete", String.valueOf(id)};
        return args;
    }
    private static String[] getUpdateAddress(String address){
        args=new String[]{address,"update"};
        return args;
    }
    private static String[] getLineAddress(String address, Serializable id){
        args=new String[]{address,"get", String.valueOf(id)};
        return args;
    }
    private static String[] getListAddress(String address){
        args=new String[]{address,"getAll"};
        return args;
    }
    private static String[] getStartAddress(String address){
        args=new String[]{address,"start"};
        return args;
    }
    private static String[] getIfStartAddress(String address){
        args=new String[]{address,"ifStart"};
        return args;
    }
    private static String[] getByDateAddress(String address){
        args=new String[]{address,"byDate"};
        return args;
    }
    private static String[] getStuAddress(String address){
        args=new String[]{address,"getStu"};
        return args;
    }
    private static String[] getSaveStuAddress(String address){
        args=new String[]{address,"saveStu"};
        return args;
    }
    private static String[] getAttendanceAddress(String address,Serializable id){
        args=new String[]{address,"attendance",String.valueOf(id)};
        return args;
    }

    private static String[] getDetailAddress(String address,Serializable id){
        args=new String[]{address,"detail",String.valueOf(id)};
        return args;
    }

    private static String[] getStuStatusAddress(String address,Serializable id,Serializable date){
        args=new String[]{address,"get",String.valueOf(id),String.valueOf(date)};
        return args;
    }
}
