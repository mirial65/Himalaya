package com.example.himalaya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.himalaya.R;
import com.example.himalaya.utils.DateFormatUtil;
import com.example.himalaya.utils.LogUtil;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Time : 2020/7/25 11:07
 * Author : zengkun
 */
public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.InnerHolder> {


    private static final String TAG = "DetailListAdapter";
    private List<Track> mDetailData = new ArrayList<>();
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat mSimpleDateFormat2 = new SimpleDateFormat("mm:ss");
    private ItemClickListener mItemClickListener = null;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_detail, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, final int position) {
        //找到控件,这个itemView就是上面创建的view
        View itemView = holder.itemView;
        //顺序
        TextView orderTv = itemView.findViewById(R.id.order_text);
        //标题
        TextView titleTv = itemView.findViewById(R.id.detail_item_title);
        //播放次数
        TextView playCountTv = itemView.findViewById(R.id.detail_item_play_count);
        //时长
        TextView durationTv = itemView.findViewById(R.id.detail_item_duration);
        //更新日期
        TextView updateDateTv = itemView.findViewById(R.id.detail_item_update_time);
        //设置数据
        Track track = mDetailData.get(position);
        orderTv.setText(track.getOrderNum() + "");
        titleTv.setText(track.getTrackTitle());
        playCountTv.setText(track.getPlayCount() + "");
        LogUtil.d(TAG, "duration" + track.getDuration());
        if (track.getDuration() > 3600) {
            String formatHour = DateFormatUtil.formatHour(track.getDuration() * 1000);
            durationTv.setText(formatHour);
        }else {
            String formatMin = DateFormatUtil.formatMin(track.getDuration() * 1000);
            durationTv.setText(formatMin);
        }
        String format = mSimpleDateFormat.format(track.getUpdatedAt());
        updateDateTv.setText(format);
        //设置item的点击事件
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(), "you click " + position + " item", Toast.LENGTH_SHORT).show();
                if (mItemClickListener != null) {
                    //参数需要有列表和位置
                    mItemClickListener.onItemClick(mDetailData, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDetailData.size();
    }

    public void setData(List<Track> tracks) {
        //清除原来的数据
        mDetailData.clear();
        //添加新的数据
        mDetailData.addAll(tracks);
        //更新ui
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.mItemClickListener = listener;
    }
    public interface ItemClickListener {
        void onItemClick(List<Track> detailData, int position);
    }
}