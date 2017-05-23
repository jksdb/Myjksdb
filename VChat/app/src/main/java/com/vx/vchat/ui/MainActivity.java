package com.vx.vchat.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.vx.vchat.R;
import com.vx.vchat.fragment.BaseFragment;
import com.vx.vchat.fragment.ChatListFragment;
import com.vx.vchat.fragment.ContactFragment;
import com.vx.vchat.fragment.Three;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, EMMessageListener, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private Button but1, but2, but3;
    private List<BaseFragment> list = new ArrayList<>();
    private ChatListFragment chat;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//添加 消息监听
        EMClient.getInstance().chatManager().addMessageListener(this);

        initView();
        //menu  菜单的 退出键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_btn1:
                setItem(0);
                break;
            case R.id.chat_btn2:
                viewPager.setCurrentItem(1, true);
                setItem(1);
                break;
            case R.id.chat_btn3:
                setItem(2);
                break;

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mainactiviay, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.menu_item_1:
                Toast.makeText(this, "menu_item_1", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setItem(int index) {
        //设置当前页
        viewPager.setCurrentItem(index, true);


        but2.setBackgroundColor(getResources().getColor(R.color.abc_search_url_text_pressed));
        but1.setBackgroundColor(getResources().getColor(R.color.abc_search_url_text_pressed));
        but3.setBackgroundColor(getResources().getColor(R.color.abc_search_url_text_pressed));
        switch (index) {
            case 0:
                but1.setBackgroundColor(getResources().getColor(R.color.abc_secondary_text_material_dark));
                break;
            case 1:
                but2.setBackgroundColor(getResources().getColor(R.color.abc_secondary_text_material_dark));
                break;
            case 2:
                but3.setBackgroundColor(getResources().getColor(R.color.abc_secondary_text_material_dark));
                break;
        }


    }

    private void initView() {


        but1 = (Button) findViewById(R.id.chat_btn1);
        but2 = (Button) findViewById(R.id.chat_btn2);
        but3 = (Button) findViewById(R.id.chat_btn3);

        but1.setOnClickListener(this);
        but2.setOnClickListener(this);
        but3.setOnClickListener(this);

        tabLayout = (TabLayout) findViewById(R.id.chat_tableLayout);

        viewPager = (ViewPager) findViewById(R.id.main_viewPager);
        //Fragment适配器
        getFragment();

        FragmentPagerAdapter fpa = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return list.get(position);
            }

            @Override
            public int getCount() {

                return list.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return super.getPageTitle(position);
            }
        };

        viewPager.setAdapter(fpa);

        //吧tabLayout和viewPager绑定
        tabLayout.setupWithViewPager(viewPager);
        //==========================
        tabLayout.getTabAt(0).setIcon(R.mipmap.xx);
        tabLayout.getTabAt(1).setIcon(R.mipmap.lxr);
        tabLayout.getTabAt(2).setIcon(R.mipmap.zy);
        //===========================
        //添加  标签选择监听
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab == tabLayout.getTabAt(0)) {
                    tab.setIcon(R.mipmap.xx1);
                } else if (tab == tabLayout.getTabAt(1)) {
                    tab.setIcon(R.mipmap.lxr1);
                } else {
                    tab.setIcon(R.mipmap.zy1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                if (tab == tabLayout.getTabAt(0)) {
                    tab.setIcon(R.mipmap.xx);
                } else if (tab == tabLayout.getTabAt(1)) {
                    tab.setIcon(R.mipmap.lxr);
                } else {
                    tab.setIcon(R.mipmap.zy);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        //预加载个数
        viewPager.setOffscreenPageLimit(2);

        setItem(0);

        viewPager.addOnPageChangeListener(this);
    }

    private void getFragment() {
        chat = new ChatListFragment();
        ContactFragment contact = new ContactFragment();
        Three three = new Three();

        list.add(chat);
        list.add(contact);
        list.add(three);
    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chat.resetList();
            }
        });

    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageReadAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageDeliveryAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }

    @Override
    protected void onDestroy() {
        EMClient.getInstance().chatManager().removeMessageListener(this);

        super.onDestroy();
    }
    //============================================

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
