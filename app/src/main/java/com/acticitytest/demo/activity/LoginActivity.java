package com.acticitytest.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.acticitytest.demo.R;
import com.acticitytest.demo.entity.HttpResult;
import com.acticitytest.demo.entity.User;
import com.acticitytest.demo.http.HttpMethods;
import com.acticitytest.demo.http.presenter.UserPresenter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.MemoryCookieStore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
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
import rx.Subscriber;

import static android.app.ActionBar.DISPLAY_SHOW_CUSTOM;

public class LoginActivity extends AppCompatActivity {

    private static final int DEFAULT_TIMEOUT = 5;
    private OkHttpClient okHttpClient;
    public static String stuNum;
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
        ButterKnife.bind(this);
        initClient();
        getCodeImage(okHttpClient);
    }

    @Override
    protected void onStop(){
        super.onStop();
        finish();
    }

    @OnClick({R.id.refresh, R.id.login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                stuNum = username.getText().toString();
                loginWeb(username.getText().toString(),
                        password.getText().toString(),
                        check.getText().toString());
                /*Intent intent=new Intent("android.intent.action.FLOATING_BAR");
                startActivity(intent);*/
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
                    Toast.makeText(LoginActivity.this,
                            "Error occurs when download the bitmap",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Bitmap response) {

                    codeImage.setImageBitmap(response);
                }
            });
        }
        /*else {
            Toast.makeText(LoginActivity.this, "NetworkUtils is not available.",
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
                    new LoginTask(responseData,
                            LoginActivity.this,
                            username.getText().toString())
                            .execute();
                }catch (Exception e){
                    Log.e("Post", e.toString());
                }
            }
        }).start();
    }

    public static class LoginTask extends AsyncTask<Void, Integer, Boolean>{

        private String loginResult;
        private Context context;
        private String stuNum;

        LoginTask(String loginResult, Context context, String stuNum){
            this.loginResult = loginResult;
            this.context = context;
            this.stuNum = stuNum;
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
                HttpMethods.getInstance();
                UserPresenter.addUser(new Subscriber<HttpResult<User>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof SocketTimeoutException) {
                            Toast.makeText(context, "网络中断，请检查您的网络状态",
                                    Toast.LENGTH_SHORT).show();
                        } else if (e instanceof ConnectException) {
                            Toast.makeText(context, "网络中断，请检查您的网络状态",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "error:" + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            Log.e("ProgressSubscriber", e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(HttpResult<User> userHttpResult) {
                        if(userHttpResult.getStatus() == 0){
                            Toast.makeText(context, "用户数据发送成功",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }, stuNum);
                Intent intent=new Intent("android.intent.action.FLOATING_BAR");
                context.startActivity(intent);
            }else{
                Toast.makeText(context, "登录失败！请检查账号密码或验证码输入是否正确",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
