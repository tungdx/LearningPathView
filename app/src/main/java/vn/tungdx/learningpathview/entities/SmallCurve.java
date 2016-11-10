package vn.tungdx.learningpathview.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tungdx on 11/10/16.
 */

public class SmallCurve {
    @SerializedName("curve_type")
    public String curveType;
    @SerializedName("control_y")
    public float controlY = 1;
    @SerializedName("control_x")
    public float controlX = 1;
}
