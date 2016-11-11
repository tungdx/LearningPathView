package vn.tungdx.learningpathview.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    @SerializedName("out_delta_dp")
    public int outDelta = 0;
    @SerializedName("in_delta_dp")
    public int inDelta = 0;
    @SerializedName("small_curve")
    public List<SmallCurve> smallCurve;
}