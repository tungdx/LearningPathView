package vn.tungdx.learningpathview.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TUNGDX on 11/3/2016.
 */

public class Item {
    public static class Pos {
        public static final String LEFT = "left";
        public static final String RIGHT = "right";
        public static final String TOP = "top";
        public static final String BOTTOM = "bottom";
    }

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
    @SerializedName("in_pos")
    public String inPos;
    @SerializedName("out_pos")
    public String outPos;

}
