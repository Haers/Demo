package com.acticitytest.demo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class userInfoActivity extends AppCompatActivity {
    ImageView payCode ;
    Button choosePayCode ;
    Button change_ok;
private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.update_userinfo);
         payCode = (ImageView)findViewById(R.id.pay);
        choosePayCode = (Button)findViewById(R.id.choosePayCode);
        change_ok = (Button)findViewById(R.id.change_ok) ;
        choosePayCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(userInfoActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(userInfoActivity.this,new String[]
                            { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
                }else {
                      openAlbum();
                }
            }
        });
        change_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void openAlbum(){
        try{
            //Intent intent = new Intent("android.intent.action.GET_CONTENT");
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 2);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }
                else {

                }
                break;
                default:
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 2:
                /*if(resultCode == RESULT_OK){
                    if(Build.VERSION.SDK_INT >= 19){
                        handleImageOnKitKat(data);
                    }else{
                        handleImageBeforeKitKat(data);
                    }
                }*/
                if(resultCode == -1){
                    try{

                        Uri uri = data.getData();
                        Bitmap bit = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri));
                        payCode.setImageBitmap(bit);

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                break;


        }
    }



}
