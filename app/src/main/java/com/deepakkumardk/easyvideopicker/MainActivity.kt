package com.deepakkumardk.easyvideopicker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.deepakkumardk.videopickerlib.EasyVideoPicker
import com.deepakkumardk.videopickerlib.model.VideoPickerItem
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_open_picker.setOnClickListener {
            openVideoPicker()
        }
    }

    private fun openVideoPicker() {
        val item = VideoPickerItem().apply {
            showIcon = true
            debugMode = true
            themeResId = R.style.CustomTheme
            timeLimit = TimeUnit.MINUTES.toMillis(2)
        }
        EasyVideoPicker().startPickerForResult(this, item, 2001)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2001 && resultCode == Activity.RESULT_OK && data != null) {
            val list = EasyVideoPicker.getSelectedVideos(data)
            var text = ""
            list?.forEach {
                text += "${it.videoPath} \n"
            }
            text_video_path.text = text
        }
    }
}
