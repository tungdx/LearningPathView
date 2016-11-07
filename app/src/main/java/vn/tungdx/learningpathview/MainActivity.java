package vn.tungdx.learningpathview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import vn.tungdx.learningpathview.entities.Item;
import vn.tungdx.learningpathview.utils.Utils;
import vn.tungdx.learningpathview.widgets.LearningPathView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json = Utils.readAssetFile(getApplicationContext(), "learning_path.json");
                Type listType = new TypeToken<ArrayList<Item>>() {
                }.getType();

                final List<Item> items = new Gson().fromJson(json, listType);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setUI(items);
                    }
                });
            }
        }).start();

        //http://stackoverflow.com/questions/14888650/android-canvas-drawing-arc-between-to-points-and-removing-arc-from-path
        //https://www.google.com/search?q=android+draw+curve+between+two+view&client=safari&rls=en&biw=1440&bih=736&tbm=nws&source=lnms&sa=X&ved=0ahUKEwidkJCy75XQAhXITrwKHSjlAfAQ_AUICCgB&dpr=2#q=android+draw+curve+between+two+view
    }

    private void setUI(List<Item> items) {
        setContentView(R.layout.activity_main);
        LearningPathView learningPathView = (LearningPathView) findViewById(R.id.learning_path);

        learningPathView.display(items);
    }
}
