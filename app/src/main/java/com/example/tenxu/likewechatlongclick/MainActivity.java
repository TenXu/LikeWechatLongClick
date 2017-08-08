package com.example.tenxu.likewechatlongclick;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lqr.recyclerview.LQRRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LikeAdapter.ItemRootCallBack {

    @BindView(R.id.id_lqrrv)
    LQRRecyclerView mIdLqrRv;

    private LikeAdapter mAdapter;
    private List<Contact> mContactsList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initAdapter();
        getData();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initAdapter() {
        mAdapter = new LikeAdapter(this, mContactsList, R.layout.like_item, this);
        mIdLqrRv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
//        mIdLqrRv.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });
//        mIdLqrRv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
//
//            }
//        });
    }

    private void getData() {
        String header = "http://pic1.16pic.com/00/10/80/16pic_1080912_b.jpg";
        for (int i = 0; i < 50; i++) {
            Contact contact = new Contact();
            contact.setHeader(header);
            contact.setName("测试名称" + i);
            contact.setLastMsg("测试消息来的，巴拉巴拉");
            contact.setLastTime(System.currentTimeMillis() / 1000);
            mContactsList.add(contact);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void rootLongClick(View view, int position, float downX, float downY) {
        Contact contact = mContactsList.get(position);
        showReason(view, contact, view.getHeight(), position, (int) downX, (int) downY);
    }

    @Override
    public void rootClick(View view) {
        Toast.makeText(this, "单击", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("ObsoleteSdkInt")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showReason(View anchor, final Contact item, int parHeight, final int position, int curViewX, int curViewY) {
        final PopupView popup = new PopupView(this);
        View view = View.inflate(this, R.layout.custom_dialog_item, null);
        LinearLayout linearLayout=ButterKnife.findById(view,R.id.dialog_root);
        linearLayout.measure(0,0);
        TextView del = ButterKnife.findById(view, R.id.id_del);
        TextView toTop = ButterKnife.findById(view, R.id.id_to_top);
        TextView noRead = ButterKnife.findById(view, R.id.id_no_read);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            del.setBackgroundResource(R.drawable.bg_key_value_item);
            toTop.setBackgroundResource(R.drawable.bg_key_value_item);
            noRead.setBackgroundResource(R.drawable.bg_key_value_item);
        } else {
            del.setBackgroundResource(R.drawable.bg_keyvalue_item_ripple_orange);
            toTop.setBackgroundResource(R.drawable.bg_keyvalue_item_ripple_orange);
            noRead.setBackgroundResource(R.drawable.bg_keyvalue_item_ripple_orange);
        }
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContactsList.remove(item);
                mAdapter.notifyDataSetChanged();
                popup.hideView();
            }
        });
        toTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转页面
                Contact top = mContactsList.remove(position);
                mContactsList.add(0, top);
                mAdapter.notifyDataSetChanged();
                popup.hideView();
            }
        });
        noRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转页面
                Toast.makeText(MainActivity.this, "标记为未读", Toast.LENGTH_SHORT).show();
                popup.hideView();
            }
        });
        popup.initView(view, UIUtils.dip2Px(150), linearLayout.getMeasuredHeight());

        RecyclerView.LayoutManager layoutManager = mIdLqrRv.getLayoutManager();
        int lastItemPosition = 0;
        int firstItemPosition = 0;
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
            //获取最后一个可见view的位置
            lastItemPosition = linearManager.findLastVisibleItemPosition();
            //获取第一个可见view的位置
            firstItemPosition = linearManager.findFirstVisibleItemPosition();
        }
        int total=lastItemPosition-firstItemPosition;
        int currentPos=position-firstItemPosition;

        popup.showView(anchor,total,currentPos, parHeight, curViewX, curViewY);
    }
}
