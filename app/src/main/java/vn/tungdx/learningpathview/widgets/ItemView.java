package vn.tungdx.learningpathview.widgets;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.tungdx.learningpathview.R;
import vn.tungdx.learningpathview.entities.Item;

/**
 * Created by TUNGDX on 11/3/2016.
 */

public class ItemView extends LinearLayout {
    private TextView textView;
    private ImageView imageView;

    public ItemView(Context context) {
        super(context);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        textView = (TextView) findViewById(R.id.text);
        imageView = (ImageView) findViewById(R.id.image);
    }

    public void display(Item item) {
        textView.setText(item.text);
        imageView.setImageResource(R.mipmap.ic_launcher);
    }

    public float getImageViewX() {
        return ViewCompat.getX(imageView);
    }
}
