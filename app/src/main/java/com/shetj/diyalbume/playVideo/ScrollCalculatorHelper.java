package com.shetj.diyalbume.playVideo;

import android.os.Handler;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

/**
 * 计算滑动，自动播放的帮助类
 */

public class ScrollCalculatorHelper {

    private int firstVisible = 0;
    private int lastVisible = 0;
    private int visibleCount = 1;
    private int playId;
    private int rangeTop;
    private int rangeBottom;
    private int center ;
    private PlayRunnable runnable;
    private AutoRecycleView autoAdapter;
    private Handler playHandler = new Handler();

    public ScrollCalculatorHelper(int playId,  int center,int rangeTop, int rangeBottom,AutoRecycleView autoAdapter) {
        this.playId = playId;
        this.rangeTop = rangeTop;
        this.rangeBottom = rangeBottom;
        this.autoAdapter = autoAdapter;
        this.center = center;
    }

    public void onScrollStateChanged(RecyclerView view, int scrollState, LinearLayoutManager linearLayoutManager) {
        switch (scrollState) {
            case RecyclerView.SCROLL_STATE_IDLE:
                playVideo(view);
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                isStop(view,linearLayoutManager);
                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
                isStop(view,linearLayoutManager);
                break;
        }
    }

    private void isStop(RecyclerView view, LinearLayoutManager linearLayoutManager) {
        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
        autoAdapter.isStop(firstVisibleItemPosition,lastVisibleItemPosition);
    }


    public void onScroll(RecyclerView view, int firstVisibleItem, int lastVisibleItem, int visibleItemCount) {
        if (firstVisible == firstVisibleItem) {
            return;
        }
        firstVisible = firstVisibleItem;
        lastVisible = lastVisibleItem;
        visibleCount = visibleItemCount+1;
    }


    void playVideo(RecyclerView view) {

        if (view == null) {
            return;
        }

        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();

        TextView BaseTextView = null;
        boolean needPlay = false;
        for (int i = 0; i < visibleCount; i++) {

            if (layoutManager.getChildAt(i) != null && layoutManager.getChildAt(i).findViewById(playId) != null) {
                TextView textView = layoutManager.getChildAt(i).findViewById(playId);
                int[] screenPosition = new int[2];
                textView.getLocationOnScreen(screenPosition);
                int halfHeight = textView.getHeight() / 2;
                int rangePosition = screenPosition[1] + halfHeight;
                //中心点在播放区域内
                if ( rangePosition >= rangeTop && rangePosition <= rangeBottom ) {
                    if (runnable !=null &&  runnable.textView == textView){
                        textView.setText("同一个"+firstVisible+i);
                        return;
                    }
                    BaseTextView = textView;
                    BaseTextView.setTag(firstVisible+i);
                    needPlay = true;
                    break;
                }

            }
        }


        if ( BaseTextView!=null && needPlay) {
            if (runnable != null) {
                TextView tmpPlayer = runnable.textView;
                playHandler.removeCallbacks(runnable);
                runnable = null;
                if (tmpPlayer == BaseTextView) {
                    return;
                }
            }
            runnable = new PlayRunnable(BaseTextView);
            //降低频率
            playHandler.postDelayed(runnable, 100);
        }


    }

    private class PlayRunnable implements Runnable {

        TextView textView;

        public PlayRunnable(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void run() {
            autoAdapter.setPlay((Integer) textView.getTag());
        }
    }


}
