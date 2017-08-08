package com.example.tenxu.likewechatlongclick;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRViewHolderForRecyclerView;

import java.util.List;

/**
 * 适配器
 * Created by TenXu on 2017/8/1.
 */

@SuppressWarnings("WeakerAccess")
public class LikeAdapter extends LQRAdapterForRecyclerView<Contact> {

    private Activity mContext;
    private ItemRootCallBack mCallBack;
    private float xDown;
    private float yDown;

    LikeAdapter(Activity context, List<Contact> data, int defaultLayoutId,ItemRootCallBack callBack) {
        super(context, data, defaultLayoutId);
        this.mCallBack=callBack;
        this.mContext=context;
    }

    @Override
    public void convert(LQRViewHolderForRecyclerView helper, Contact item, final int position) {
        helper.setText(R.id.id_name,item.getName());
        helper.setText(R.id.id_lastmsg,item.getLastMsg());
        helper.setText(R.id.id_last_time,TimeUtil.getTimeStr(item.getLastTime()));
        ImageView header=helper.getView(R.id.id_header);
        Glide.with(mContext).load(item.getHeader()).into(header);
        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.rootClick(v);
            }
        });
        helper.getConvertView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    xDown=event.getX();
                    yDown=event.getY();
                }
                return false;
            }
        });

        helper.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mCallBack.rootLongClick(view,position,xDown,yDown);
                return false;
            }
        });
    }

    public interface ItemRootCallBack {
        void rootLongClick(View view,int position, float downX, float downY);

        void rootClick(View view);
    }
}
