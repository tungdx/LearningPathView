package vn.tungdx.learningpathview.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.tungdx.learningpathview.R;
import vn.tungdx.learningpathview.entities.Item;

/**
 * Created by TUNGDX on 11/3/2016.
 */

public class LearningPathView extends ViewGroup {
    private List<Item> items;

    public LearningPathView(Context context) {
        super(context);
    }

    public LearningPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LearningPathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int totalWidth = 0;
        int size = getChildCount();


        for (int i = 0; i < size; i++) {

            View child = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();

            Item item = items.get(i);
            layoutParams.yTop = height * item.yTop / 100;
            if (i == 0) {
                layoutParams.marginLeft = dipToPixels(getContext(), item.marginLeft);
            } else {
                View previousView = getChildAt(i - 1);
                int previousMarginLeft = ((LayoutParams) previousView.getLayoutParams()).marginLeft;

                if (item.marginLeft >= 0) {
                    layoutParams.marginLeft = previousMarginLeft + getChildAt(i - 1).getMeasuredWidth() + dipToPixels(getContext(), item.marginLeft);
                } else {
                    layoutParams.marginLeft = previousMarginLeft - dipToPixels(getContext(), Math.abs(item.marginLeft));
                }

            }

            measureChild(child, MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            totalWidth += child.getMeasuredWidth();
        }
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(totalWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            child.layout(layoutParams.marginLeft, layoutParams.yTop, layoutParams.marginLeft + child.getMeasuredWidth(), layoutParams.yTop + child.getMeasuredHeight());
        }
    }

    public int dipToPixels(Context context, int dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    public void display(List<Item> items) {
        this.items = items;
        removeAllViews();
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        for (Item item : items) {
            ItemView view = (ItemView) layoutInflater.inflate(R.layout.itemview, this, false);
            addView(view, new LayoutParams());
            view.display(item);
        }
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {
        public int marginLeft;
        public int yTop;

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams() {
            super(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

    }
}
