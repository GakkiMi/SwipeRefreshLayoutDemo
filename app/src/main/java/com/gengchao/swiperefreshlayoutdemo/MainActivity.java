package com.gengchao.swiperefreshlayoutdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<String> datas = new ArrayList<>();
    private MyRecyclerviewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiDatas();
        initRecyclerView();
        initRefreshLayout();
    }

    private void initRecyclerView() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new MyRecyclerviewAdapter(datas);
        mRecyclerView.setAdapter(mAdapter);
        //listview
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //gridview
//        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        //瀑布流
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mAdapter.setOnItemClickListener(new MyRecyclerviewAdapter.OnItemClickListener() {

            @Override
            public void onClick(View v, int position, String number) {
                Toast.makeText(MainActivity.this, datas.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void intiDatas() {

        String[] cityName = new String[]{"Birmingham伯明翰",
                "Montgomery蒙哥马利",
                "Mobile莫比尔县",
                "Anniston安尼斯顿",
                "Gadsden加兹登",
                "Phoenix凤凰城",
                "Scottsdale斯科茨代尔",
                "Tempe坦佩",
                "Buckeye巴克艾",
                "Chandler钱德勒",
                "ElDorado埃尔拉多",
                "Jonesboro琼斯伯勒",
                "PaineBluff潘恩崖",
                "LittleRock小石城",
                "Fayetteville费耶特维尔",
                "FortSmith史密斯堡"};
        for (int i = 0; i < cityName.length; i++) {
            datas.add(cityName[i]);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_add:
                mAdapter.addData(0, "new city");
                break;
            case R.id.id_action_delete:
                mAdapter.removeData(0);
                break;
            case R.id.id_action_gridview:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
                break;
            case R.id.id_action_listview:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

                break;
            case R.id.id_action_recycleview:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                break;
        }
        return true;
    }

    public void initRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light
                , android.R.color.holo_green_light
                , android.R.color.holo_purple);
        swipeRefreshLayout.setDistanceToTriggerSync(100);
//        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.item_press));
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            mAdapter.addData(i, "new city:" + i);

                        }
                        mAdapter.notifyItemRangeChanged(0, 10);
                        mRecyclerView.scrollToPosition(0);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }
}
