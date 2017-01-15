package com.treelist.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.multilevel.treelist.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangke on 2017-1-15.
 */
public class BaseActivity extends AppCompatActivity{

    protected List<Node> mDatas = new ArrayList<Node>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();

    }

    private void initDatas()
    {
        // id , pid , label , 其他属性
        mDatas.add(new Node("1", "-1", "文件管理系统"));

        mDatas.add(new Node(2+"", 1+"", "游戏"));
        mDatas.add(new Node(3+"", 1+"", "文档"));
        mDatas.add(new Node(4+"", 1+"", "程序"));
        mDatas.add(new Node(5+"", 2+"", "war3"));
        mDatas.add(new Node(6+"", 2+"", "刀塔传奇"));

        mDatas.add(new Node(7+"", 4+"", "面向对象"));
        mDatas.add(new Node(8+"", 4+"", "非面向对象"));

        mDatas.add(new Node(9+"", 7+"", "C++"));
        mDatas.add(new Node(10+"", 7+"", "JAVA"));
        mDatas.add(new Node(11+"", 7+"", "Javascript"));
        mDatas.add(new Node(12+"", 8+"", "C"));
        mDatas.add(new Node(13+"", 12+"", "C"));
        mDatas.add(new Node(14+"", 13+"", "C"));
        mDatas.add(new Node(15+"", 14+"", "C"));
        mDatas.add(new Node(16+"", 15+"", "C"));
    }
}
