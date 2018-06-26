package com.sun.zh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;

import com.sun.zh.study.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewStub vs_network = findViewById(R.id.vs_network);
        vs_network.inflate();


//        File file = new File("/data/phone/img.png");
//        try {
//            FileInputStream fileInputStream = new FileInputStream(file);
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            byte[] b = new byte[1024];
//            int len = 0;
//            while (len == -1) {
//                len = fileInputStream.read(b);
//                byteArrayOutputStream.write(b, 0, len);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
