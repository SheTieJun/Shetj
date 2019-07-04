package com.shetj.diyalbume.pipiti.localMusic;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shetj.diyalbume.R;

import java.util.ArrayList;
import java.util.List;

public class MusicSelectAdapter extends BaseQuickAdapter<Music,BaseViewHolder> {

    private int select=-1;
    private ArrayList<Music> MusicList;
    private MediaPlayer player;

    public MusicSelectAdapter(@Nullable List<Music> data) {
        super(R.layout.item_select_music,data);
    }


    @Override
    protected void convert(BaseViewHolder helper, Music item) {
        helper.setText(R.id.tv_music_name,item.name)
        .setText(R.id.tv_music_size,byte2FitMemorySize(item.size))
        .setText(R.id.tv_music_time,formatTime(item.duration))
        .addOnClickListener(R.id.tv_play);

    }

    public void setSelect(int position){
        select=position;
    }

    public String getSelect(){
        if (select!=-1) {
            return MusicList.get(select).getUrl();
        }else{
            return null;
        }
    }

    /******************** 存储相关常量 ********************/
    /**
     * KB与Byte的倍数
     */
    public   final int KB = 1024;
    /**
     * MB与Byte的倍数
     */
    public   final int MB = 1048576;
    /**
     * GB与Byte的倍数
     */
    public   final int GB = 1073741824;
    @SuppressLint("DefaultLocale")
    public  String byte2FitMemorySize(long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum <  KB) {
            return String.format("%.2fB", byteNum + 0.005);
        } else if (byteNum <  MB) {
            return String.format("%.2fKB", byteNum /  KB + 0.005);
        } else if (byteNum <  GB) {
            return String.format("%.2fMB", byteNum /  MB + 0.005);
        } else {
            return String.format("%.2fGB", byteNum /  GB + 0.005);
        }
    }

    public static String formatTime(long ms) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day;
        //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;
        //小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        //分钟
        String strSecond = second < 10 ? "0" + second : "" + second;
        //秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
        //毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

        return strMinute + "：" + strSecond;
    }


}