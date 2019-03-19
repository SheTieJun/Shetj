package com.shetj.diyalbume.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;


/**
 * 1.初始化 画笔
 * 2.如果有布局限制
 * 3.onDraw() 中在画布上进行绘制
 */
public class CircleView extends View {

    // 设置画笔变量
    Paint mPaint1;
    private Path mPath;

    // 自定义View有四个构造函数
    // 如果View是在Java代码里面new的，则调用第一个构造函数
    public CircleView(Context context){
        super(context);

        // 在构造函数里初始化画笔的操作
        init();
    }


// 如果View是在.xml里声明的，则调用第二个构造函数
// 自定义属性是从AttributeSet参数传进来的
    public CircleView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();

    }

// 不会自动调用
// 一般是在第二个构造函数里主动调用
// 如View有style属性时
    public CircleView(Context context,AttributeSet attrs,int defStyleAttr ){
        super(context, attrs,defStyleAttr);
        init();
    }


    //API21之后才使用
    // 不会自动调用
    // 一般是在第二个构造函数里主动调用
    // 如View有style属性时
    public  CircleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    // 画笔初始化
    private void init() {

        // 创建画笔
        mPaint1 = new Paint ();
        // 设置画笔颜色为蓝色
        mPaint1.setColor(Color.TRANSPARENT);
        // 设置画笔宽度为10px
        mPaint1.setStrokeWidth(5f);
        //设置画笔模式为填充
        mPaint1.setStyle(Paint.Style.FILL);
        mPath = new Path();
    }


    // 复写onDraw()进行绘制  
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        //DIFFERENCE是第一次不同于第二次的部分显示出来A-B-------
        //REPLACE是显示第二次的B******
        //REVERSE_DIFFERENCE 是第二次不同于第一次的部分显示--------
        //INTERSECT交集显示A-(A-B)*******
        //UNION全部显示A+B******
        //XOR补集 就是全集的减去交集生育部分显示--------
        canvas.save();
        int width = getWidth();
        int height = getHeight();
        int r = Math.min(width, height)/2;
        canvas.clipRect(0,0,width,height);
        mPath.addCircle(width/2, height/2, r, Path.Direction.CCW);

        canvas.clipPath(mPath, Region.Op.XOR);
        canvas.drawColor(Color.BLUE);
//        canvas.restore();

//        mPaint1.setColor(Color.BLUE);
//        mPaint1.setStyle(Paint.Style.STROKE);
//        canvas.translate(10, 10);
//        canvas.drawRect(0, 0, 300, 300, mPaint1);
//        mPaint1.setColor(Color.RED);
//        canvas.drawRect(200, 200, 400, 400,mPaint1);
        invalidate();

    }

}
