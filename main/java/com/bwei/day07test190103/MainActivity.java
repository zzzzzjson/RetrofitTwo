package com.bwei.day07test190103;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import bean.NewsBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import contactss.Contacts;
import presenter.MainPresenter;
import view.MyView;

public class MainActivity extends AppCompatActivity implements MyView{

    @BindView(R.id.main_recyclerview)
    RecyclerView mMainRecyclerview;

    MainPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainPresenter(this);
        Map<String,String> map=new HashMap<>();
        map.put("pscid","1");
        presenter.getRequest(Contacts.USER_LOGIN,map, NewsBean.class);
        presenter.getRequest(Contacts.USER_LOGIN,map, NewsBean.class);
    }

    @Override
    public void Success(Object success) {
        if (success instanceof NewsBean){
            NewsBean newsBean = (NewsBean) success;
            Log.e("------","------");
            Toast.makeText(this, newsBean.getMsg()+"memeda", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void Error(String error) {
        Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
    }
}
