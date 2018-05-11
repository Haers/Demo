package com.acticitytest.demo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.acticitytest.demo.R;
import com.acticitytest.demo.entity.HttpResult;
import com.acticitytest.demo.entity.Message;
import com.acticitytest.demo.http.HttpMethods;
import com.acticitytest.demo.http.presenter.MessagePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class EditActivity extends AppCompatActivity {

    @BindView(R.id.sendMessage)
    Button sendMessage;
    @BindView(R.id.edit_activity_result)
    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.sendMessage)
    public void onClick(View view){
        MessagePresenter.sendMessage(new Subscriber<HttpResult<Message>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(HttpResult<Message> httpResult) {
                int i = httpResult.getStatus();
                String str = i + "";
                result.setText(str);
            }
        }, "20152717", "A区快递", "一舍", "食堂");
    }
}
