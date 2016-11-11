package vn.tungdx.learningpathview.entities;

import android.graphics.Color;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TUNGDX on 11/3/2016.
 */

public class Item {

    @SerializedName("id")
    public int id;
    @SerializedName("icon")
    public String icon;
    @SerializedName("bg_color")
    public String bgColor;
    @SerializedName("text")
    public String text;
    @SerializedName("y_top")
    public int yTop;
    @SerializedName("margin_left")
    public int marginLeft;
    @SerializedName("curve")
    public Curve curve;
    @SerializedName("curve_color")
    private String curveColor;

    public int getCurveColor() {
        try {
            return Color.parseColor(curveColor);
        } catch (Exception ex) {
            return Color.GRAY;
        }
    }
}
