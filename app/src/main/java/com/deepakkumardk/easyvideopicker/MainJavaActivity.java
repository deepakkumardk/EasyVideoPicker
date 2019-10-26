package com.deepakkumardk.easyvideopicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.deepakkumardk.videopickerlib.EasyVideoPicker;
import com.deepakkumardk.videopickerlib.model.SelectionMode;
import com.deepakkumardk.videopickerlib.model.SelectionStyle;
import com.deepakkumardk.videopickerlib.model.VideoModel;
import com.deepakkumardk.videopickerlib.model.VideoPickerItem;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Deepak Kumar
 * @since 15/10/19
 */
public class MainJavaActivity extends AppCompatActivity {

    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = findViewById(R.id.text_video_path);

        findViewById(R.id.btn_open_picker).setOnClickListener((view) -> openVideoPicker());
        findViewById(R.id.btn_java_example).setVisibility(View.INVISIBLE);

        ActionBar toolbar = getSupportActionBar();
        if (toolbar != null) {
            toolbar.setTitle("Java Example");
            toolbar.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void openVideoPicker() {
        VideoPickerItem item = new VideoPickerItem();
        item.setShowIcon(true);
        item.setDebugMode(true);
        item.setShowDuration(true);
        item.setLimitMessage("Please select less than %s pictures");
        item.setTimeLimit(TimeUnit.MINUTES.toMillis(100));
        item.setSelectionMode(new SelectionMode.Custom(5));
        item.setSelectionStyle(SelectionStyle.Small.INSTANCE);
        new EasyVideoPicker().startPickerForResult(this, item, 2001);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2001 && resultCode == Activity.RESULT_OK && data != null) {
            ArrayList<VideoModel> list = EasyVideoPicker.Companion.getSelectedVideos(data);
            StringBuilder text = new StringBuilder();

            if (list != null) {
                for (VideoModel path : list)
                    text.append(path).append(" \n");
            }

            resultText.setText(text.toString());
        }
    }
}
