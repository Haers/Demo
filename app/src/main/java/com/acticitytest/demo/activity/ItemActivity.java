package com.acticitytest.demo.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.acticitytest.demo.R;
import com.acticitytest.demo.entity.HttpResult;
import com.acticitytest.demo.entity.Message;
import com.acticitytest.demo.entity.User;
import com.acticitytest.demo.http.HttpMethods;
import com.acticitytest.demo.http.ProgressSubscriber;
import com.acticitytest.demo.http.SubscriberOnNextListener;
import com.acticitytest.demo.http.presenter.MessagePresenter;
import com.acticitytest.demo.http.presenter.UserPresenter;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

import static android.view.View.GONE;

public class ItemActivity extends AppCompatActivity {

    public static final int MAKE_CALL = 1;
    private final int BUTTON_HELP = 1;
    private final int BUTTON_CALL = 2;
    private final int BUTTON_MODIFY = 3;
    private final int BUTTON_DELETE = 4;
    private final int BUTTON_DONE = 6;
    private final int BUTTON_PAY = 7;
    private final int BUTTON_SUBMIT_MODIFY = 8;
    private final String yes = "是";
    private final String no = "否";
    private SubscriberOnNextListener getMessageOnNext;
    private SubscriberOnNextListener getResultOnNext;
    private SubscriberOnNextListener getUserOnNext;
    private static int listener;
    private int activity;
    private int id;
    private String senderId;
    private String msg;
    private String sendLocation;
    private String fetchLocation;
    private Boolean isCaught;
    private Boolean isDone;
    @BindView(R.id.item_help)
    Button help;
    @BindView(R.id.item_call)
    Button call;
    @BindView(R.id.item_modify)
    Button modify;
    @BindView(R.id.item_delete)
    Button delete;
    @BindView(R.id.item_done)
    Button done;
    @BindView(R.id.item_pay)
    Button pay;
    @BindView(R.id.item_submit)
    Button submit;
    @BindView(R.id.itemPic)
    ImageView itemPic;
    @BindView(R.id.item_sender_id)
    TextView sender_id;
    @BindView(R.id.item_send_time)
    TextView send_time;
    @BindView(R.id.item_send_message)
    TextView send_message;
    @BindView(R.id.item_fetch_location)
    TextView fetch_location;
    @BindView(R.id.item_send_location)
    TextView send_location;
    @BindView(R.id.item_is_caught)
    TextView is_caught;
    @BindView(R.id.item_is_done)
    TextView is_done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        HttpMethods.getInstance();
        initData();
        initView();
    }

    private void initData(){
        Bundle bundle=getIntent().getExtras();
        activity = bundle.getInt("activity");
        id = bundle.getInt("id");
        senderId = bundle.getString("senderId");
        String sendDate = bundle.getString("sendDate");
        String sendTime = bundle.getString("sendTime");
        String Time = sendDate + "-" + sendTime;
        msg = bundle.getString("msg");
        fetchLocation = bundle.getString("fetchLocation");
        sendLocation = bundle.getString("sendLocation");
        isCaught = bundle.getBoolean("isCaught");
        isDone = bundle.getBoolean("isDone");
        sender_id.setText(senderId);
        send_time.setText(Time);
        send_message.setText(msg);
        fetch_location.setText(fetchLocation);
        send_location.setText(sendLocation);
        if(isCaught)
            is_caught.setText(yes);
        else
            is_caught.setText(no);
        if(isDone)
            is_done.setText(yes);
        else
            is_done.setText(no);
    }

    private void initView(){
        switch (activity){
            //主界面获取所有消息
            case 1:
                modify.setVisibility(GONE);
                delete.setVisibility(GONE);
                done.setVisibility(GONE);
                pay.setVisibility(GONE);
                submit.setVisibility(GONE);
                if(isCaught){
                    help.setVisibility(GONE);
                    call.setVisibility(GONE);
                }
                break;
            //我发布的消息
            case 2:
                help.setVisibility(GONE);
                call.setVisibility(GONE);
                done.setVisibility(GONE);
                modify.setVisibility(GONE);
                submit.setVisibility(GONE);
                if(isCaught)
                    delete.setVisibility(GONE);
                if(!isDone)
                    pay.setVisibility(GONE);
                break;
            //我帮助的人
            case 3:
                help.setVisibility(GONE);
                modify.setVisibility(GONE);
                delete.setVisibility(GONE);
                pay.setVisibility(GONE);
                submit.setVisibility(GONE);
                if(isDone)
                    done.setVisibility(GONE);
                break;
        }
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

    @Override
    public void onResume(){
        super.onResume();
        getMessageOnNext = new SubscriberOnNextListener<List<Message>>() {
            @Override
            public void onNext(List<Message> messages) {

            }
        };
        getResultOnNext = new SubscriberOnNextListener<HttpResult<Message>>() {
            @Override
            public void onNext(HttpResult<Message> httpResult) {
                switch (listener){
                    case BUTTON_HELP:
                        Toast.makeText(ItemActivity.this,
                                "已确认帮助", Toast.LENGTH_SHORT).show();
                        is_caught.setText(yes);
                        break;
                    case BUTTON_DELETE:
                        Toast.makeText(ItemActivity.this,
                                "消息删除成功", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case BUTTON_DONE:
                        Toast.makeText(ItemActivity.this,
                                "任务已完成", Toast.LENGTH_SHORT).show();
                        is_done.setText(yes);
                        done.setVisibility(GONE);
                        break;
                    case BUTTON_SUBMIT_MODIFY:
                        Toast.makeText(ItemActivity.this,
                                "消息修改成功", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        getUserOnNext = new SubscriberOnNextListener<List<User>>() {
            @Override
            public void onNext(List<User> user) {
                switch (listener){
                    case BUTTON_PAY:
                        //openInWeb(user.get(0).getPay());
                        Intent intentPay = new Intent("android.intent.action.PAY");
                        intentPay.putExtra("payUrl", user.get(0).getPay());
                        startActivity(intentPay);
                        break;
                    case BUTTON_CALL:
                        try{
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:"+user.get(0).getTelephone()));
                            startActivity(intent);
                        }catch (SecurityException e){
                            Toast.makeText(ItemActivity.this,
                                    "无法申请拨号权限", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

            }
        };
    }

    @OnClick({R.id.item_call, R.id.item_help, R.id.item_modify,
            R.id.item_delete, R.id.item_done, R.id.item_pay, R.id.item_submit})
    public void onClick(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ItemActivity.this);
        dialog.setCancelable(false);
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        switch (view.getId()){
            case R.id.item_call:
                listener = BUTTON_CALL;
                dialog.setMessage("将拨打求助者电话？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(ContextCompat.checkSelfPermission(ItemActivity.this,
                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(ItemActivity.this,
                                    new String[]{Manifest.permission.CALL_PHONE},1);
                        }else {
                            makeCall();
                        }
                    }
                });
                dialog.show();
                break;
            case R.id.item_help:
                listener = BUTTON_HELP;
                dialog.setMessage("确定帮助？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        caughtMessage();
                    }
                });
                dialog.show();
                break;
            case R.id.item_modify:
                listener = BUTTON_MODIFY;
                enableModify();
                break;
            case R.id.item_delete:
                listener = BUTTON_DELETE;
                dialog.setMessage("确定删除消息？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMessage();
                    }
                });
                dialog.show();
                break;
            case R.id.item_done:
                listener = BUTTON_DONE;
                dialog.setMessage("确定完成帮助？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doneMessage();
                    }
                });
                dialog.show();
                break;
            case R.id.item_pay:
                listener = BUTTON_PAY;
                dialog.setMessage("确定支付，将跳转到支付界面？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        payMessage();
                    }
                });
                dialog.show();
                break;
            case R.id.item_submit:
                listener = BUTTON_SUBMIT_MODIFY;
                dialog.setMessage("确定提交修改？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    modifyMessage();
                    }
                });
                dialog.show();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults){
        switch (requestCode){
            case MAKE_CALL:
                if(grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED){
                    makeCall();
                }else{
                    Toast.makeText(this, "Make call failed",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private void makeCall(){
        UserPresenter.getUser(
                new ProgressSubscriber<List<User>>(getUserOnNext, this), senderId);
    }

    private void caughtMessage(){
        MessagePresenter.caughtMessage(
                new ProgressSubscriber<HttpResult<Message>>(getResultOnNext, this),
                id, LoginActivity.stuNum);
    }

    private void enableModify(){

    }

    private void modifyMessage(){
        MessagePresenter.modifyMessage(
                new ProgressSubscriber<HttpResult<Message>>(getResultOnNext, this),
                id, msg, sendLocation, fetchLocation);
    }

    private void deleteMessage(){
        MessagePresenter.deleteMessage(new ProgressSubscriber<HttpResult<Message>>
                (getResultOnNext, this), id);
    }

    private void doneMessage(){
        MessagePresenter.doneMessage(
                new ProgressSubscriber<HttpResult<Message>>(getResultOnNext, this), id);
    }

    private void payMessage(){
        UserPresenter.getUser(
                new ProgressSubscriber<List<User>>(getUserOnNext, this), senderId);
    }

    private void openInWeb(String uri){
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));
        intent.setClassName("com.android.browser",
                "com.android.browser.BrowserActivity");
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            final ComponentName componentName = intent.resolveActivity(this.getPackageManager());
            Log.e("Scan", "componentName = " + componentName.getClassName());
            startActivity(Intent.createChooser(intent, "请选择浏览器"));
        }else {
            Toast.makeText(this, "没有匹配的程序", Toast.LENGTH_SHORT).show();
        }
    }
}
