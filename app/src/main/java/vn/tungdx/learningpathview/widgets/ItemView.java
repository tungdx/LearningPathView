package vn.tungdx.learningpathview.widgets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.tungdx.learningpathview.R;
import vn.tungdx.learningpathview.entities.Item;

/**
 * Created by TUNGDX on 11/3/2016.
 */

public class ItemView extends LinearLayout {
    private TextView textView;
    private CircleImageView imageViewBackground;
    private ImageView imageViewIcon;
    private View imageContainer;

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
        imageViewBackground = (CircleImageView) findViewById(R.id.imageBackground);
        imageViewIcon = (ImageView) findViewById(R.id.imageIcon);
        imageContainer = findViewById(R.id.imageContainer);
    }

    public void display(Item item) {
        //text
        textView.setText(item.text);

        //bg color
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(item.bgColor));
        imageViewBackground.setImageDrawable(colorDrawable);

        //icon
        int resourceId = getResources().getIdentifier(item.icon, "drawable", getContext().getPackageName());
        imageViewIcon.setImageResource(resourceId);
    }

    public float getImageMarginLeft() {
        return ViewCompat.getX(imageContainer);
    }

    public float getImageMarginRight() {
        return getWidth() - imageContainer.getWidth() - getImageMarginLeft();
    }
}
