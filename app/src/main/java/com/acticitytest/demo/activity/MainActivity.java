package com.acticitytest.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.acticitytest.demo.R;
import com.acticitytest.demo.fragment.ListViewFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.commit();
        fragmentTransaction.replace(android.R.id.content,new ListViewFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_user_info:
                Intent intent_info = new Intent("android.intent.action.USER_INFO");
                startActivity(intent_info);
                break;
            case R.id.menu_my_message:
                Intent intent_message = new Intent("android.intent.action.USER_MESSAGE");
                startActivity(intent_message);
                break;
            case R.id.menu_my_order:
                Intent intent_order = new Intent("android.intent.action.USER_ORDER");
                startActivity(intent_order);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

