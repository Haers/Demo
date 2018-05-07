package com.acticitytest.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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
    }
}
