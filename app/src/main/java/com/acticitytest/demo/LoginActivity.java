package com.acticitytest.demo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.MemoryCookieStore;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private static final int DEFAULT_TIMEOUT = 5;
    private OkHttpClient okHttpClient;

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.check)
    EditText check;
    @BindView(R.id.codeImage)
    ImageView codeImage;
    @BindView(R.id.refresh)
    Button refresh;
    @BindView(R.id.login)
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        // bind the view using butterknife
        ButterKnife.bind(this);
        initClient();
        getCodeImage(okHttpClient);
    }

    @OnClick({R.id.refresh, R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                loginWeb(username.getText().toString(),
                        password.getText().toString(),
                        check.getText().toString());
                break;
            case R.id.refresh:
                getCodeImage(okHttpClient);
                break;
        }
    }

    public void initClient(){
        CookieJarImpl cookieJar = new CookieJarImpl(new MemoryCookieStore());
        okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .connectTimeout(DEFAULT_TIMEOUT , TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT , TimeUnit.SECONDS)
                .build();
    }

    public void getCodeImage(OkHttpClient client){
        OkHttpUtils.initClient(client);
      //  if (NetworkUtils.isNetworkAvailable(this))
        {

            OkHttpUtils.get().url("https://zhjw.neu.edu.cn/ACTIONVALIDATERANDOMPICTURE.APPPROCESS")
                    .build().execute(new BitmapCallback() {
                @Override
                public void onError(Call call, Exception e) {
                    Toast.makeText(LoginActivity.this, "Error occurs when download the bitmap",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Bitmap response) {

                    codeImage.setImageBitmap(response);
                }
            });
        }
        /*else {
            Toast.makeText(LoginActivity.this, "Network is not available.",
                    Toast.LENGTH_SHORT).show();
        }*/
    }

    public void loginWeb(String userNo, String password, String check){
        RequestBody requestBody = new FormBody.Builder()
                .add("WebUserNO", userNo)
                .add("Password", password)
                .add("Agnomen", check)
                .build();
        final Request request = new Request.Builder()
                .url("https://zhjw.neu.edu.cn/ACTIONLOGON.APPPROCESS")
                .post(requestBody)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Response response = okHttpClient.newCall(request).execute();
                    String responseData = response.body().string();
                    new LoginTask(responseData, LoginActivity.this).execute();
                }catch (Exception e){
                    Log.e("Post", e.toString());
                }
            }
        }).start();
    }

    public static class LoginTask extends AsyncTask<Void, Integer, Boolean>{

        private String loginResult;
        private Context context;

        public LoginTask(String loginResult, Context context){
            this.loginResult = loginResult;
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... params){
            Document doc = Jsoup.parse(loginResult);
            Elements success = doc.select("title");
            for (Element link : success) {
                if (link.text().equals("网络综合平台")) {
                    return true;
                }
            }
            return false;
        }
        @Override
        protected void onPostExecute(Boolean result){
            if(result){
                Toast.makeText(context, "登录成功！",
                        Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "登录失败！请检查账号密码或验证码输入是否正确",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
