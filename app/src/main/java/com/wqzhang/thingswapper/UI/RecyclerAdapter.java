package com.wqzhang.thingswapper.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wqzhang.thingswapper.R;

/**
 * Created by wqzhang on 16-10-21.
 * 自定义 recycleAdapter 类
 */

public class RecyclerAdapter extends RecyclerView.Adapter {

    private final String TAG = "RecyclerAdapterTest";

    private LayoutInflater inflater = null;
    private Context mContext = null;
    private String[] data = new String[20];

    public RecyclerAdapter(Context context) {
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
        SlideContentView slideContentView = viewHolder.slide_content_view;
        TextView textView = (TextView) slideContentView.findViewById(R.id.tittle);
        textView.setText(data[position]);
        slideContentView.getmFinshImgBtn().setOnClickListener(new View.OnClickListener() {
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
        public SlideContentView slide_content_view;


        public ViewHolder(View itemView) {
            super(itemView);
            slide_content_view = (SlideContentView) itemView;
        }
    }

}
