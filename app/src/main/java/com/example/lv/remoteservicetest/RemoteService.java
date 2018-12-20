package com.example.lv.remoteservicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

public class RemoteService extends Service {

    public RemoteService() {
    } //constructor

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder(); //返回MyBinder服务对象
    } //onBind()

    public class MyBinder extends Binder {
        public RemoteService getService() {
            return RemoteService.this; //返回当前的Service类
        } //getService()

    } //class MyBinder

    public BigDecimal add(BigDecimal a, BigDecimal b) { //实现加法功能
        return a.add(b);
    } //add()

    public BigDecimal subtract(BigDecimal a, BigDecimal b) { //实现减法功能
        return a.subtract(b);
    } //subtract()

    public BigDecimal multiply(BigDecimal a, BigDecimal b) { //实现乘法功能
        return a.multiply(b);
    } //multiply()

    public BigDecimal divide(BigDecimal a, BigDecimal b) { //实现除法功能
        if (String.valueOf(b).equals("0")) {
            Toast.makeText(getApplicationContext(), "除数不可为0", Toast.LENGTH_SHORT).show();
        } else {
            return a.divide(b, 20, ROUND_HALF_DOWN);
        } //if-else
        return  new BigDecimal(0);
    } //divide()

    @Override
    public void onDestroy() {
        super.onDestroy(); //销毁该Service
    } //onDestroy()
}
