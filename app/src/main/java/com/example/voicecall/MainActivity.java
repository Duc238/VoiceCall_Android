package com.example.voicecall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CALL_PHONE = 1;
    EditText editTextTelNum;
    Button btnGoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ứng dụng gọi điện thoại");
        editTextTelNum=findViewById(R.id.edtTelNumber);
        btnGoi=findViewById(R.id.btnCall);
        // Kiểm tra xem quyền CALL_PHONE đã được cấp hay chưa
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // Quyền chưa được cấp, yêu cầu quyền từ người dùng
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    PERMISSION_REQUEST_CALL_PHONE);
        } else {
            // Quyền đã được cấp, thực hiện các hành động liên quan đến cuộc gọi điện thoại
            btnGoi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    makePhoneCall();
                }
            });
        }
    }
    private void makePhoneCall() {
        String phoneNumber=editTextTelNum.getText().toString();
        if(phoneNumber.trim().length()>0) {
            Toast.makeText(this, "Thực hiện cuộc gọi điện thoại", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp, thực hiện các hành động liên quan đến cuộc gọi điện thoại
                makePhoneCall();
            } else {
                // Quyền không được cấp, hiển thị thông báo cho người dùng
                Toast.makeText(this, "Ứng dụng cần quyền gọi điện thoại để hoạt động.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}