package com.acticitytest.demo.activity;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.acticitytest.demo.R;
import com.acticitytest.demo.entity.HttpResult;
import com.acticitytest.demo.entity.Message;
import com.acticitytest.demo.http.presenter.MessagePresenter;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class EditActivity extends AppCompatActivity {

    @BindView(R.id.edit_submit)
    Button sendMessage;
    @BindView(R.id.edit_fetch_location)
    EditText fetchLocation;
    @BindView(R.id.edit_send_location)
    EditText sendLocation;
    @BindView(R.id.edit_msg)
    EditText msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
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

    @OnClick(R.id.edit_submit)
    public void onClick(View view){
        AlertDialog.Builder dialog = new AlertDialog.Builder(EditActivity.this);
        dialog.setCancelable(false);
        dialog.setMessage("确定发布消息？");
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                submitMessage();
            }
        });
        dialog.show();
    }

    private void submitMessage(){
        MessagePresenter.sendMessage(new Subscriber<HttpResult<Message>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof SocketTimeoutException) {
                    Toast.makeText(EditActivity.this,
                            "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
                } else if (e instanceof ConnectException) {
                    Toast.makeText(EditActivity.this,
                            "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditActivity.this,
                            "error:" + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("ProgressSubscriber", e.getMessage());
                }
            }

            @Override
            public void onNext(HttpResult<Message> httpResult) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditActivity.this);
                dialog.setCancelable(false);
                dialog.setMessage("消息发布成功，是否编辑新的消息？");
                dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fetchLocation.setText("");
                        sendLocation.setText("");
                        msg.setText("");
                    }
                });
                dialog.show();
            }
        }, LoginActivity.stuNum, msg.getText().toString(),
                sendLocation.getText().toString(), fetchLocation.getText().toString());
    }
}
