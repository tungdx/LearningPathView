package vn.tungdx.learningpathview.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by TUNGDX on 11/3/2016.
 */

public class Utils {
    public static String readAssetFile(Context context, String fileName) {
        BufferedReader input = null;
        File file = null;
        try {
            input = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
