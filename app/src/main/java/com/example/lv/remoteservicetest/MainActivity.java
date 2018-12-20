package com.example.lv.remoteservicetest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    private RemoteService remoteService;
    private Button clearButton;
    private TextView firstNumber;
    private TextView secondNumber;
    private TextView answer;
    private Button Button_add;
    private Button Button_sub;
    private Button Button_mul;
    private Button Button_div;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clearButton = findViewById(R.id.clear);
        firstNumber = findViewById(R.id.first_edit);
        secondNumber = findViewById(R.id.second_edit);
        answer = findViewById(R.id.answer);
        Button_add = findViewById(R.id.bt_add);
        Button_sub = findViewById(R.id.bt_sub);
        Button_mul = findViewById(R.id.bt_mul);
        Button_div = findViewById(R.id.bt_div);

        //Clear按钮清除所有内容
        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstNumber.setText("");
                secondNumber.setText("");
                answer.setText("Answer is here!");
            }
        });

        //加法运算
        findViewById(R.id.bt_add).setOnClickListener(new ButtonListener());
        //减法运算
        findViewById(R.id.bt_sub).setOnClickListener(new ButtonListener());
        //乘法运算
        findViewById(R.id.bt_mul).setOnClickListener(new ButtonListener());
        //除法运算
        findViewById(R.id.bt_div).setOnClickListener(new ButtonListener());

    }
    //Activity启动时与后台Service进行绑定
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, RemoteService.class);
        bindService(intent, conn, BIND_AUTO_CREATE); //绑定指定Service
    } //onStart()

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn); //解除绑定Service
    } //onDestroy

    class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            //关掉手机软键盘，防止其遮挡结果显示框
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘

            BigDecimal result = new BigDecimal("0"); //存储结果
            if (firstNumber.getText().toString().equals("") || secondNumber.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "请合法输入待处理的两个数", Toast.LENGTH_SHORT).show();
            } else {
                BigDecimal num1 = new BigDecimal(firstNumber.getText().toString());
                BigDecimal num2 = new BigDecimal(secondNumber.getText().toString());
                switch (view.getId()) {
                    case R.id.bt_add:
                        result = remoteService.add(num1, num2);
                        break;
                    case R.id.bt_sub:
                        result = remoteService.subtract(num1, num2);
                        break;
                    case R.id.bt_mul:
                        result = remoteService.multiply(num1, num2);
                        break;
                    case R.id.bt_div:
                        result = remoteService.divide(num1, num2);
                        break;
                } //switch
                answer.setText(String.valueOf(result));
            } //if-else
        } //onClick()
    } //class ButtonListener

    //设置与后台Service进行通讯
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            remoteService = ((RemoteService.MyBinder) iBinder).getService();
        } //onServiceConnected()

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        } //onServiceDisconnected()
    }; //new ServiceConnection()
}