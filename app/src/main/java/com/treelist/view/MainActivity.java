package com.treelist.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.treelist.R;

/**
 * Created by zhangke on 2017-1-15.
 */
public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void goListView(View v){
        startActivity(new Intent(this,ListViewActivity.class));
    }
    public void goRecyclerView(View v){
        startActivity(new Intent(this,RecyclerViewActivity.class));
    }
}
