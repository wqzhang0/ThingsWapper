package com.wqzhang.thingswapper.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wqzhang.thingswapper.R;

/**
 * Created by wqzhang on 16-10-21.
 * 自定义 recycleAdapter 类
 * 添加点击事件的 回调  函数
 */

public class RecyclerAdapterTest extends RecyclerView.Adapter {

    private final String TAG = "RecyclerAdapterTest";

    private LayoutInflater inflater = null;
    private Context mContext = null;
    private String[] data = new String[20];

    public RecyclerAdapterTest(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < 20; i++) {
            data[i] = "item" + (i + 1);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");
        parent = new SlideContentView(mContext);
        View view = inflater.inflate(R.layout.list_view_item, parent, false);
        SlideContentView slideContentView = new SlideContentView(mContext);
        slideContentView.setContentView(view);

        ViewHolder viewHolder = new ViewHolder(slideContentView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder");
        ViewHolder viewHolder = (ViewHolder) holder;
        TextView t = (TextView) viewHolder.contentView.findViewById(R.id.tittle);
        t.setText(data[position]);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, data[position] + " Click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount");
        return data == null ? 0 : data.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout contentView;
        public RelativeLayout bottom_right_layout;
        public RelativeLayout bottom_left_layout;
        public ImageButton bottom_left_trashImgBtn;
        public ImageButton bottom_right_finshImgBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            contentView = (LinearLayout) itemView;
            bottom_left_layout = (RelativeLayout) itemView.findViewById(R.id.bottom_left);
            bottom_right_layout = (RelativeLayout) itemView.findViewById(R.id.bottom_right);
            bottom_right_finshImgBtn = (ImageButton) itemView.findViewById(R.id.finsh);
            bottom_left_trashImgBtn = (ImageButton) itemView.findViewById(R.id.trash);
        }
    }

}
