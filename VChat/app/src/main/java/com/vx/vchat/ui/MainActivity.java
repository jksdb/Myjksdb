package com.vx.vchat.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.vx.vchat.R;
import com.vx.vchat.fragment.BaseFragment;
import com.vx.vchat.fragment.ChatListFragment;
import com.vx.vchat.fragment.ContactFragment;
import com.vx.vchat.fragment.Three;
import com.vx.vchat.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv;
    private ViewPager viewPager;
    private Button but1, but2, but3;
    private List<BaseFragment> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
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

    private void setItem(int index) {
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


        tv = (TextView) findViewById(R.id.tv);
        viewPager = (ViewPager) findViewById(R.id.main_viewPager);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMClient.getInstance().logout(true);
            }
        });

        initDate();

        FragmentPagerAdapter fpa = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return list.get(position);
            }

            @Override
            public int getCount() {

                return list.size();
            }
        };
        viewPager.setAdapter(fpa);
        setItem(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        });
    }

    private void initDate() {
        ChatListFragment chat = new ChatListFragment();
        ContactFragment contact = new ContactFragment();
        Three three = new Three();

        list.add(chat);
        list.add(contact);
        list.add(three);
    }
}
