package com.seabig.moduledemo.widget.ui.widget.recyclermgr;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

/**
 * @author YJZ
 *         date 2018/6/3
 *         d escription 自定义L
 */

public class EchelonLayoutManager extends RecyclerView.LayoutManager {

    private int mItemViewWidth;
    private int mItemViewHeight;
    private int mItemCount;
    private int mScrollOffset = Integer.MAX_VALUE;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        if (state.getItemCount() == 0 || state.isPreLayout()) {
            return;
        }
        removeAndRecycleAllViews(recycler);
        mItemViewWidth = (int) (getHorizontalSpace() * 0.87f);
        mItemViewHeight = (int) (mItemViewWidth * 1.46f);
        mItemCount = getItemCount();
        //获取到所有Item的高度和
        mScrollOffset = Math.min(Math.max(mItemViewHeight, mScrollOffset), mItemCount * mItemViewHeight);
        layoutChild(recycler);
    }

    private void layoutChild(RecyclerView.Recycler recycler) {
        if (getItemCount() == 0) {
            return;
        }
        //获取到最后一个Item的位置
        int bottomItemPosition = (int) Math.floor(mScrollOffset / mItemViewHeight);
        //获取到出去一个完整的Item的高度，还剩余多少空间
        int remainSpace = getVerticalSpace() - mItemViewHeight;
        //滑动的时候可以获取到最后一个Item在屏幕上还显示的高度
        int bottomItemVisibleHeight = mScrollOffset % mItemViewHeight;
        //最后一个Item显示高度相对于本身的比例
        final float offsetPercentRelativeToItemView = bottomItemVisibleHeight * 1.0f / mItemViewHeight;
        //把我们需要的Item添加到这个集合
        ArrayList<ItemViewInfo> layoutInfos = new ArrayList<>();
        for (int i = bottomItemPosition - 1, j = 1; i >= 0; i--, j++) {
            //计算偏移量
            double maxOffset = (getVerticalSpace() - mItemViewHeight) / 2 * Math.pow(0.8, j);
            //这个Item的top值
            int start = (int) (remainSpace - offsetPercentRelativeToItemView * maxOffset);
            //这个Item需要缩放的比例
            float mScale = 0.9f;
            float scaleXY = (float) (Math.pow(mScale, j - 1) * (1 - offsetPercentRelativeToItemView * (1 - mScale)));
            float positionOffset = offsetPercentRelativeToItemView;
            //Item上面的距离占RecyclerView可用高度的比例
            float layoutPercent = start * 1.0f / getVerticalSpace();
            ItemViewInfo info = new ItemViewInfo(start, scaleXY, positionOffset, layoutPercent);
            layoutInfos.add(0, info);
            remainSpace = (int) (remainSpace - maxOffset);
            //在添加Item的同时，计算剩余空间是否可以容下下一个Item，如果不能的话，就不再添加了
            if (remainSpace <= 0) {
                info.setTop((int) (remainSpace + maxOffset));
                info.setPositionOffset(0);
                info.setLayoutPercent(info.getTop() / getVerticalSpace());
                info.setScaleXY((float) Math.pow(mScale, j - 1));
                break;
            }
        }
        if (bottomItemPosition < mItemCount) {
            final int start = getVerticalSpace() - bottomItemVisibleHeight;
            layoutInfos.add(new ItemViewInfo(start, 1.0f, bottomItemVisibleHeight * 1.0f / mItemViewHeight, start * 1.0f / getVerticalSpace())
                    .setIsBottom());
        } else {
            bottomItemPosition = bottomItemPosition - 1;
        }
        //这里做的是回收处理
        int layoutCount = layoutInfos.size();
        final int startPos = bottomItemPosition - (layoutCount - 1);
        final int endPos = bottomItemPosition;
        final int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View childView = getChildAt(i);
            int pos = getPosition(childView);
            if (pos > endPos || pos < startPos) {
                removeAndRecycleView(childView, recycler);
            }
        }
        detachAndScrapAttachedViews(recycler);
        //这里主要是对需要显示的Item进行排列以及缩放
        for (int i = 0; i < layoutCount; i++) {
            View view = recycler.getViewForPosition(startPos + i);
            ItemViewInfo layoutInfo = layoutInfos.get(i);
            addView(view);
            measureChildWithExactlySize(view);
            int left = (getHorizontalSpace() - mItemViewWidth) / 2;
            layoutDecoratedWithMargins(view, left, layoutInfo.getTop(), left + mItemViewWidth, layoutInfo.getTop() + mItemViewHeight);
            view.setPivotX(view.getWidth() / 2);
            view.setPivotY(0);
            view.setScaleX(layoutInfo.getScaleXY());
            view.setScaleY(layoutInfo.getScaleXY());
        }
    }

    /**
     * 测量itemView的确切大小
     */
    private void measureChildWithExactlySize(View child) {
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(mItemViewWidth, View.MeasureSpec.EXACTLY);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(mItemViewHeight, View.MeasureSpec.EXACTLY);
        child.measure(widthSpec, heightSpec);
    }

    /**
     * 获取RecyclerView的显示高度
     */
    private int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    /**
     * 获取RecyclerView的显示宽度
     */
    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int pendingScrollOffset = mScrollOffset + dy;
        mScrollOffset = Math.min(Math.max(mItemViewHeight, mScrollOffset + dy), mItemCount * mItemViewHeight);
        layoutChild(recycler);
        return mScrollOffset - pendingScrollOffset + dy;
    }

    /**
     * 滑动的处理，如果想要RecyclerView滑动的话，就要打开这个开关
     */
    @Override
    public boolean canScrollVertically() {
        return true;
    }

}
