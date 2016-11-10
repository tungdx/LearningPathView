package vn.tungdx.learningpathview.entities;

import com.google.gson.annotations.SerializedName;

public class Curve {

    public static class GatePos {
        public static final String LEFT = "left";
        public static final String RIGHT = "right";
        public static final String TOP = "top";
        public static final String BOTTOM = "bottom";
    }

    public static class CurveType {
        public static final String RIGHT = "right";
        public static final String LEFT = "left";
    }

    @SerializedName("in_pos")
    public String inPos;
    @SerializedName("out_pos")
    public String outPos;
    @SerializedName("curve_type")
    public String curveType;
    @SerializedName("control_y")
    public float controlY = 1;
    @SerializedName("control_x")
    public float controlX = 1;
}