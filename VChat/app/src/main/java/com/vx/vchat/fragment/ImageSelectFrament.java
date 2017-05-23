package com.vx.vchat.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vx.vchat.R;
import com.vx.vchat.adapter.SelectImageAdapter;
import com.vx.vchat.ui.MessageActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import static android.R.id.list;
import static android.R.id.paste;


/**
 * Created by asus on 2017/5/23.
 */

public class ImageSelectFrament extends BaseFragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private Button send;
    private ArrayList<String> list = new ArrayList();
    SelectImageAdapter selectImageAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frament_image_selent, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.message_bottom_fragmnt_select_RecyclerView);
        send = (Button) view.findViewById(R.id.btn_send);
        send.setOnClickListener(this);
        getImagePath();

        selectImageAdapter = new SelectImageAdapter(getActivity(), list);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);//横向
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(selectImageAdapter);
    }

    private void getImagePath() {
        list.clear();
        Cursor datas = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (datas != null) {
            while (datas.moveToNext()) {
                String path = datas.getString(datas.getColumnIndex(MediaStore.Images.Media.DATA));
                int w = datas.getInt(datas.getColumnIndex(MediaStore.Images.Media.WIDTH));
                int h = datas.getInt(datas.getColumnIndex(MediaStore.Images.Media.HEIGHT));
                list.add(path);

            }
            datas.close();
        }
    }

    @Override
    public void onClick(View v) {
        MessageActivity ma= (MessageActivity) getActivity();
        HashSet<String> paths = selectImageAdapter.getPaths();
        Iterator<String> iterator = paths.iterator();
        while (iterator.hasNext()){
            String str = iterator.next();
            ma.createImage(str);
        }
    }
}
