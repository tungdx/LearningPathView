package vn.tungdx.learningpathview.entities;

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
}
