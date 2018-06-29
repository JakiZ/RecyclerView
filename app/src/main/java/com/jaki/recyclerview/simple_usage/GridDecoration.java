package com.jaki.recyclerview.simple_usage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class GridDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };
    private Drawable divider;

    public GridDecoration(Context context) {
        TypedArray ta = context.obtainStyledAttributes(ATTRS);
        divider = ta.getDrawable(0);
        ta.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawDivider(c,parent);
    }

    private void drawDivider(Canvas c, RecyclerView parent){
        //此种情况下，四个边缘都不绘制分割线
        int childCount = parent.getChildCount();
        //获取当前列数
        int spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        //获取方向
        int orientation = ((GridLayoutManager) parent.getLayoutManager()).getOrientation();
        boolean isDrawHorizontalDivider = true;
        boolean isDrawVerticalDivider = true;
        int extra = childCount % spanCount;
        extra = extra == 0 ? spanCount : extra;
        for (int i = 0; i < childCount; i++) {
             isDrawHorizontalDivider = true;
             isDrawVerticalDivider = true;

             //方向是竖直，最右一列不绘制竖直分割线
            if (orientation == RecyclerView.VERTICAL && (i + 1) % spanCount == 0){
                isDrawVerticalDivider = false;
            }

            //方向是竖直，最下一列不绘制水平分割线
            if (orientation == RecyclerView.VERTICAL && ((i + 1) - childCount / spanCount * spanCount) > 0){
                isDrawHorizontalDivider = false;
            }

            //方向是水平，最右一列不绘制竖直分割线 ((i + 1) - childCount / spanCount * spanCount) >= 0
            if (orientation == RecyclerView.HORIZONTAL && ((i + 1) - childCount / spanCount * spanCount) > 0){
                isDrawVerticalDivider = false;
            }

            //方向是水平，最下一列不绘制水平分割线
            if (orientation == RecyclerView.HORIZONTAL && (i + 1) % spanCount == 0){
                isDrawHorizontalDivider = false;
            }

            if (isDrawHorizontalDivider){
                drawHorizontalDivider(c,parent,i);
            }
            if (isDrawVerticalDivider){
                drawVerticalDivider(c,parent,i);
            }
        }

    }

    private void drawHorizontalDivider(Canvas c, RecyclerView parent, int position) {
        View child = parent.getChildAt(position);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        int top = child.getBottom() + params.bottomMargin;
        int bottom = top + divider.getIntrinsicHeight();
        int left = child.getLeft() - params.leftMargin;
        int right = child.getRight() + params.rightMargin + divider.getIntrinsicWidth();
        divider.setBounds(left,top,right,bottom);
        divider.draw(c);

        Log.e("jaki","h = " +divider.getIntrinsicHeight());
        Log.e("jaki","w = " +divider.getIntrinsicWidth());
    }

    private void drawVerticalDivider(Canvas c, RecyclerView parent, int position) {
        View child = parent.getChildAt(position);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
        int top = child.getTop() - params.topMargin;
        int bottom =child.getBottom() + params.bottomMargin + divider.getIntrinsicHeight();
        int left = child.getRight() + params.rightMargin;
        int right = left + divider.getIntrinsicWidth();
        divider.setBounds(left,top,right,bottom);
        divider.draw(c);
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildLayoutPosition(view);
        //获取当前列数
        int spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        //获取方向
        int orientation = ((GridLayoutManager) parent.getLayoutManager()).getOrientation();
        if (orientation == RecyclerView.VERTICAL && (position + 1) % spanCount == 0){
            outRect.set(0,0,0,divider.getIntrinsicHeight());
            return;
        }
        if (orientation == RecyclerView.HORIZONTAL && (position + 1) % spanCount == 0){
            outRect.set(0,0,divider.getIntrinsicWidth(),0);
            return;
        }

        outRect.set(0,0,divider.getIntrinsicWidth(),divider.getIntrinsicHeight());


    }

}
