package com.example.user.workshop;

import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

public class NewsList extends AppCompatActivity {
    private ListView lvNewslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        create_listView();
    }

    private void create_listView() {
        lvNewslist = (ListView) findViewById(R.id.lvNewsList);
        lvNewslist.setAdapter(new CustomAdapter(getApplicationContext()));



    }


}
