package vn.tungdx.learningpathview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import vn.tungdx.learningpathview.entities.Item;
import vn.tungdx.learningpathview.utils.Utils;
import vn.tungdx.learningpathview.widgets.ItemView;
import vn.tungdx.learningpathview.widgets.LearningPathView;
import vn.tungdx.learningpathview.widgets.OnItemViewClickListener;

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
    }

    private void setUI(List<Item> items) {
        setContentView(R.layout.activity_main);
        LearningPathView learningPathView = (LearningPathView) findViewById(R.id.learning_path);

        learningPathView.display(items, new OnItemViewClickListener() {
            @Override
            public void onItemViewClicked(ItemView itemView, Item item) {
                Toast.makeText(MainActivity.this, item.text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
