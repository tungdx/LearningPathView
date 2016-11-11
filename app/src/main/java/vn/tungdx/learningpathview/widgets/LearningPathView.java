package vn.tungdx.learningpathview.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.tungdx.learningpathview.R;
import vn.tungdx.learningpathview.entities.Curve;
import vn.tungdx.learningpathview.entities.Item;
import vn.tungdx.learningpathview.entities.SmallCurve;

/**
 * Created by TUNGDX on 11/3/2016.
 */

public class LearningPathView extends ViewGroup {
    private List<Item> items;
    private DashPathEffect dashPathEffect = new DashPathEffect(new float[]{1.0f, 25.0f}, 0);
    private Paint paint = new Paint();


    public LearningPathView(Context context) {
        super(context);
        init();
    }

    public LearningPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LearningPathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setPaintColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setPathEffect(dashPathEffect);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void setPaintColor(int color) {
        paint.setColor(color);
        paint.setShadowLayer(1, 1, 1, color);
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
            int childLeft = layoutParams.marginLeft;
            int childTop = layoutParams.yTop;
            int childRight = layoutParams.marginLeft + child.getMeasuredWidth();
            int childBottom = layoutParams.yTop + child.getMeasuredHeight();
            child.layout(childLeft, childTop, childRight, childBottom);

            Item item = items.get(i);
            String inPos = item.curve.inPos != null ? item.curve.inPos : "";
            String outPos = item.curve.outPos != null ? item.curve.outPos : "";
            calculateInOutPoint(child, inPos, layoutParams.inPoint, item.curve.inDelta);
            calculateInOutPoint(child, outPos, layoutParams.outPoint, item.curve.outDelta);
        }
    }

    private void calculateInOutPoint(View child, String pos, PointF inOutPoint, int delta) {
        ItemView itemView = (ItemView) child;
        LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
        int childLeft = layoutParams.marginLeft;
        int childTop = layoutParams.yTop;
        int childRight = layoutParams.marginLeft + child.getMeasuredWidth();
        int childBottom = layoutParams.yTop + child.getMeasuredHeight();
        switch (pos) {
            case Curve.GatePos.LEFT:
                float y = childBottom - ((float) child.getMeasuredHeight() / 2);
                y += convertDeltaToPixels(delta);
                inOutPoint.set(childLeft + itemView.getImageViewX(), y);
                break;
            case Curve.GatePos.RIGHT:
                y = childBottom - ((float) child.getMeasuredHeight() / 2);
                y += convertDeltaToPixels(delta);
                inOutPoint.set(childRight - itemView.getImageViewX(), y);
                break;
            case Curve.GatePos.TOP:
                float x = childRight - ((float) child.getMeasuredWidth() / 2);
                x += convertDeltaToPixels(delta);
                inOutPoint.set(x, childTop);
                break;
            case Curve.GatePos.BOTTOM:
                x = childRight - ((float) child.getMeasuredWidth() / 2);
                x += convertDeltaToPixels(delta);
                inOutPoint.set(x, childBottom);
                break;
        }
    }

    private int convertDeltaToPixels(int delta) {
        if (delta == 0) return 0;
        int sign = delta > 0 ? 1 : -1;
        return sign * dipToPixels(getContext(), Math.abs(delta));
    }

    private int dipToPixels(Context context, int dipValue) {
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

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            View nextChild = i < count - 1 ? getChildAt(i + 1) : null;
            if (nextChild == null) {
                return;
            }
            PointF outPoint = ((LayoutParams) child.getLayoutParams()).outPoint;
            PointF inPoint = ((LayoutParams) nextChild.getLayoutParams()).inPoint;

            Path path = new Path();
            path.moveTo(outPoint.x, outPoint.y);

            Item item = items.get(i);

            List<SmallCurve> smallCurves = item.curve.smallCurve;
            if (smallCurves == null || smallCurves.size() == 0) {
                continue;
            }
            if (smallCurves.size() == 1) {
                SmallCurve small = smallCurves.get(0);

                float x1 = Curve.CurveType.RIGHT.equals(small.curveType) ? outPoint.x + inPoint.x : Math.abs(outPoint.x - inPoint.x);
                x1 = x1 / small.controlX;
                float y1 = (outPoint.y + inPoint.y) / small.controlY;
                path.quadTo(x1, y1, inPoint.x, inPoint.y);
            } else {
                SmallCurve small1 = smallCurves.get(0);
                SmallCurve small2 = smallCurves.get(1);

                float x1 = Curve.CurveType.RIGHT.equals(small1.curveType) ? outPoint.x + inPoint.x : Math.abs(outPoint.x - inPoint.x);
                x1 = x1 / small1.controlX;
                float y1 = (outPoint.y + inPoint.y) / small1.controlY;

                float x2 = Curve.CurveType.RIGHT.equals(small2.curveType) ? outPoint.x + inPoint.x : Math.abs(outPoint.x - inPoint.x);
                x2 = x2 / small2.controlX;
                float y2 = (outPoint.y + inPoint.y) / small2.controlY;

                path.cubicTo(x1, y1, x2, y2, inPoint.x, inPoint.y);
            }
            paint.setColor(item.getCurveColor());
            canvas.drawPath(path, paint);
        }
    }


    public static class LayoutParams extends ViewGroup.LayoutParams {
        public int marginLeft;
        public int yTop;
        public PointF inPoint = new PointF();
        public PointF outPoint = new PointF();

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams() {
            super(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

    }
}
