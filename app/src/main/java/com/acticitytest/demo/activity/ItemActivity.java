package com.acticitytest.demo.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.acticitytest.demo.R;

public class ItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Bundle bundle=getIntent().getExtras();
        int picId=bundle.getInt("picId");
        String countryName=bundle.getString("countryName");
        ImageView imageView=(ImageView)findViewById(R.id.itemPic);
        imageView.setImageResource(picId);
        TextView textView=(TextView)findViewById(R.id.itemName);
        textView.setText(countryName);

        Button button=(Button)findViewById(R.id.help);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(ItemActivity.this);
                dialog.setMessage("确定帮助？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });
    }
}
