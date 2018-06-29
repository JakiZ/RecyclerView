package com.jaki.recyclerview.simple_usage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class SimpleUsageDecoration extends RecyclerView.ItemDecoration {


    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };
    //绘制divider的drawable
    private Drawable divider;

    //布局的方向
    private int orientation;

    public SimpleUsageDecoration(Context context, int orientation) {
        TypedArray ta = context.obtainStyledAttributes(ATTRS);
        divider = ta.getDrawable(0);
        ta.recycle();
//        divider = new ColorDrawable(Color.RED);

        if (orientation == RecyclerView.HORIZONTAL || orientation == RecyclerView.VERTICAL){
            this.orientation = orientation;
        }else {
            throw new IllegalStateException("illegal orientation");
        }
    }

    /**
     * 在RecyclerView上绘制分割线装饰 在item绘制之前调用
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (orientation == RecyclerView.HORIZONTAL){
            drawHorizontal(c,parent);
        }else if (orientation == RecyclerView.VERTICAL){
            drawVertical(c,parent);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent){
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin + Math.round(child.getRotationY());
            int bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(left,top,right,bottom);
//            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setColor(Color.RED);
//            c.drawPaint(paint);

//            LightingColorFilter colorFilter = new LightingColorFilter(0x000000,0x00ff00);
//            divider.setColorFilter(colorFilter);
            divider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent){
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin + Math.round(child.getTranslationX());
            int right = left + divider.getIntrinsicHeight();
            divider.setBounds(left,top,right,bottom);
            divider.draw(c);
        }
    }

    /**
     * 在RecyclerView上绘制分割线装饰 在item绘制之后调用
     * @param c
     * @param parent
     * @param state
     */


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

    }

    /**
     * 为item设置偏移量  padding
     * @param outRect
     * @param view    子item
     * @param parent  该RecyclerView
     * @param state RecyclerView 当前状态
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (orientation == RecyclerView.HORIZONTAL){
            outRect.set(0,0,divider.getIntrinsicWidth(),0);
        }else {
            outRect.set(0,0,0,divider.getIntrinsicHeight());
        }
    }
}
