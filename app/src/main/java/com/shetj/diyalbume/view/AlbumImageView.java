package com.shetj.diyalbume.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.shetj.diyalbume.R;

/**
 * <b>@packageName：</b> com.shetj.diyalbume.view<br>
 * <b>@author：</b> shetj<br>
 * <b>@createTime：</b> 2017/12/19<br>
 * <b>@company：</b><br>
 * <b>@email：</b> 375105540@qq.com<br>
 * <b>@describe</b><br>
 */

public class AlbumImageView extends AppCompatImageView{

	public static final int DEFAULT_FRAME_COLOR = Color.RED;


	private Drawable controlDrawable;//移动图片
	private Drawable deleteDrawable;//删除图片


	private Paint mPaint;

	private Path mPath = new Path();//外框

	private int mFrameColor = DEFAULT_FRAME_COLOR ;
	private boolean isDrag = false;


	public AlbumImageView(Context context) {
		super(context);
		init();
	}

	public AlbumImageView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public AlbumImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		obtainStyledAttributes(attrs);
	}

	private void obtainStyledAttributes(AttributeSet attrs) {
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.AlbumImageView);

	}

	private void init() {
		mPaint = new Paint();
		mPaint.setColor(mFrameColor);//设置画笔颜色
		mPaint.setAntiAlias(true);//设置抗锯齿
		mPaint.setStyle(Paint.Style.STROKE);
		PathEffect pathEffect = new DashPathEffect(new float[] { 15, 10 }, 5);
		mPaint.setPathEffect(pathEffect);



	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPath.reset();
		mPath.moveTo(0,0);
		mPath.lineTo(getWidth(),0);
		mPath.lineTo(getWidth(),getHeight());
		mPath.lineTo(0,getHeight());
		mPath.lineTo(0,0);
		canvas.drawPath(mPath,mPaint);
	}

	private float lastX;
	private float lastY;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getRawX();
		float y = event.getRawY();
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:
				isDrag = false;
				lastX = x;
				lastY = y;
				break;

			case MotionEvent.ACTION_MOVE:
				isDrag = true;
				float dx=x-lastX;
				float dy=y-lastY;

				float x1 = getX() + dx;
				float y1 = getY() + dy;

				setX(x1);
				setY(y1);

				lastX=x;
				lastY=y;
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		return  !isNotDrag() || super.onTouchEvent(event);
	}

	private boolean isNotDrag() {
		return  !isDrag;
	}

	public String getJson() {
		return "{"+getX()+","+getY()+"}";
	}
}
