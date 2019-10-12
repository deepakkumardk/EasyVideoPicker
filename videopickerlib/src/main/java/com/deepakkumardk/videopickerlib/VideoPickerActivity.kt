package com.deepakkumardk.videopickerlib

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.deepakkumardk.videopickerlib.model.VideoModel
import com.deepakkumardk.videopickerlib.util.*
import kotlinx.android.synthetic.main.activity_video_picker.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton

class VideoPickerActivity : AppCompatActivity() {
    private lateinit var videoAdapter: VideoPickerAdapter
    private var itemList: MutableList<VideoModel> = mutableListOf()
    private var selectedVideos: MutableList<VideoModel> = ArrayList()

    private var debugMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_picker)

        videoAdapter = VideoPickerAdapter(itemList) { model, position, view ->
            onImageClick(model, position, view)
        }

        recycler_view.apply {
            init(this@VideoPickerActivity)
            adapter = videoAdapter
        }

        checkPermission()
        fab_done.setOnClickListener {
            val result = Intent()
            val list = getSelectedVideos()
            result.putExtra(EXTRA_SELECTED_VIDEOS, list)
            setResult(Activity.RESULT_OK, result)
            finish()
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onImageClick(model: VideoModel, position: Int, view: View) {
        toast("hello onClick")
    }

    private fun checkPermission() {
        val storageReadPermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        when {
            storageReadPermission -> {
                loadVideos()
            }
            else -> when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    requestPermissions(
                        arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ), RC_READ_STORAGE
                    )
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RC_READ_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the contacts-related task you need to do.
                    loadVideos()
                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                    alert(
                        title = "Permission Request",
                        message = "Please allow us to show Videos."
                    ) {
                        yesButton { checkPermission() }
                    }.show()
                }
                return

            }
        }
    }

    private fun getSelectedVideos(): ArrayList<VideoModel> {
        val list = arrayListOf<VideoModel>()
        for (model in this.selectedVideos) {
            if (model.isSelected)
                list.add(model)
        }
        return list
    }

    private fun loadVideos() {
        itemList.clear()
        progress_bar.show()
        val startTime = System.currentTimeMillis()
        VideoEx().getAllVideos(this) {
            itemList.addAll(it)
            val fetchingTime = System.currentTimeMillis() - startTime
            if (debugMode) {
                longToast("Fetching Completed in $fetchingTime ms")
                log("Fetching Completed in $fetchingTime ms")
            }
            progress_bar.hide()
//            setSubtitle()
            videoAdapter.notifyDataSetChanged()
        }
    }
}
