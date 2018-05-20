package com.acticitytest.demo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.acticitytest.demo.R;
import com.acticitytest.demo.entity.HttpResult;
import com.acticitytest.demo.entity.User;
import com.acticitytest.demo.http.HttpMethods;
import com.acticitytest.demo.http.presenter.UserPresenter;
import com.acticitytest.demo.utils.RGBLuminanceSource;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.FormatException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Hashtable;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class UserInfoActivity extends AppCompatActivity {
    @BindView(R.id.info_pay)
    ImageView payCode ;
    @BindView(R.id.info_choosePayCode)
    Button choosePayCode ;
    @BindView(R.id.info_change_ok)
    Button change_ok;
    @BindView(R.id.info_name)
    TextView name;
    @BindView(R.id.info_sex)
    RadioGroup sex;
    @BindView(R.id.info_defaultLocation)
    TextView defaultLocation;
    @BindView(R.id.info_telephone)
    TextView telephone;

    private String pay;//支付二维码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_userinfo);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.info_change_ok, R.id.info_choosePayCode})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.info_choosePayCode:
                if(ContextCompat.checkSelfPermission(UserInfoActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(UserInfoActivity.this,new String[]
                            { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
                }else {
                    openAlbum();
                }
                break;
            case R.id.info_change_ok:
                boolean gender;
                if(sex.getCheckedRadioButtonId() == R.id.info_male)
                    gender = true;
                else
                    gender = false;
                HttpMethods.getInstance();
                UserPresenter.modifyUser(new Subscriber<HttpResult<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException) {
                            Toast.makeText(UserInfoActivity.this,
                                    "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
                        } else if (e instanceof ConnectException) {
                            Toast.makeText(UserInfoActivity.this,
                                    "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UserInfoActivity.this,
                                    "error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("ProgressSubscriber", e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(HttpResult<User> userHttpResult) {
                        if(userHttpResult.getStatus() == 0)
                            Toast.makeText(UserInfoActivity.this,
                                    "用户信息已保存", Toast.LENGTH_SHORT).show();
                        else{
                            Log.e("info", userHttpResult.getInfo());
                        }
                    }
                }, LoginActivity.stuNum, name.getText().toString(), gender,
                        defaultLocation.getText().toString(),
                        telephone.getText().toString(), pay);
                break;
        }
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
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }
                break;
                default:
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case 2:
                if(resultCode == -1){
                    try{

                        Uri uri = data.getData();
                        Bitmap bit = BitmapFactory.decodeStream(
                                this.getContentResolver().openInputStream(uri));
                        payCode.setImageBitmap(bit);
                        pay = scanningImage(bit);
                        Log.e("pay url", pay);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private String scanningImage(Bitmap bitmap) {

        Map<DecodeHintType, String> hints1 = new Hashtable<>();
        hints1.put(DecodeHintType.CHARACTER_SET, "utf-8");
        RGBLuminanceSource source = new RGBLuminanceSource(bitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        Result result;
        try {
            result = reader.decode(bitmap1, hints1);
            return result.getText();
        } catch (NotFoundException | ChecksumException | FormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
