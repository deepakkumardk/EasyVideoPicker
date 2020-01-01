package com.deepakkumardk.videopickerlib

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.deepakkumardk.videopickerlib.model.SelectionMode
import com.deepakkumardk.videopickerlib.model.VideoModel
import com.deepakkumardk.videopickerlib.util.EXTRA_SELECTED_VIDEOS
import com.deepakkumardk.videopickerlib.util.RC_READ_STORAGE
import com.deepakkumardk.videopickerlib.util.VideoEx
import com.deepakkumardk.videopickerlib.util.VideoPickerUI
import com.deepakkumardk.videopickerlib.util.applyCustomTheme
import com.deepakkumardk.videopickerlib.util.hide
import com.deepakkumardk.videopickerlib.util.init
import com.deepakkumardk.videopickerlib.util.log
import com.deepakkumardk.videopickerlib.util.show
import kotlinx.android.synthetic.main.activity_video_picker.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

/**
 * @author Deepak Kumar
 * @since 12/10/19
 */
class VideoPickerActivity : AppCompatActivity() {
    private lateinit var videoAdapter: VideoPickerAdapter
    private var itemList: MutableList<VideoModel> = mutableListOf()
    private var selectedVideos: MutableList<VideoModel> = ArrayList()

    private var debugMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyCustomTheme(VideoPickerUI.getTheme())
        setContentView(R.layout.activity_video_picker)

        initToolbar()
        debugMode = VideoPickerUI.getDebugMode()
        videoAdapter = VideoPickerAdapter(itemList) { model, position, view, opacityView ->
            onItemClick(model, position, view, opacityView)
        }

        recycler_view.apply {
            init(this@VideoPickerActivity)
            adapter = videoAdapter
        }

        checkPermission()
        fab_done.setOnClickListener { sendResultIntent() }
        setUIForSingleMode()
    }

    private fun initToolbar() {
        supportActionBar?.title = getToolbarTitle()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
    }

    private fun setSubtitle() {
        supportActionBar?.title = getToolbarTitle()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getToolbarTitle(): String {
        return if (VideoPickerUI.getSelectionMode() == SelectionMode.Single)
            "Select a Video"
        else
            "${getSelectedVideos().size} Selected"
    }

    private fun setUIForSingleMode() {
        if (VideoPickerUI.getSelectionMode() == SelectionMode.Single) {
            supportActionBar?.title = getToolbarTitle()
            fab_done.hide()
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onItemClick(model: VideoModel, position: Int, view: View, opacityView: View) {
        when (VideoPickerUI.getPickerItem().selectionMode) {
            is SelectionMode.Single -> {
                model.isSelected = !model.isSelected
                selectedVideos.add(model)
                sendResultIntent()
            }
            is SelectionMode.Multiple -> {
                addSelectedItem(model, view, opacityView)
            }
            is SelectionMode.Custom -> {
                val custom = VideoPickerUI.getPickerItem().selectionMode as SelectionMode.Custom
                if (selectedVideos.size >= custom.limit && !model.isSelected) {
                    val limitMessage =
                        VideoPickerUI.getPickerItem().limitMessage.format(custom.limit)
                    toast(limitMessage)
                    return
                } else {
                    addSelectedItem(model, view, opacityView)
                }
            }
        }
        setSubtitle()
    }

    private fun addSelectedItem(model: VideoModel, view: View, opacityView: View) {
        model.isSelected = !model.isSelected
        view.show()
        view.isSelected = model.isSelected
        opacityView.isSelected = model.isSelected
        when (model.isSelected) {
            true -> selectedVideos.add(model)
            false -> selectedVideos.remove(model)
        }
    }

    private fun checkPermission() {
        val readPermission = isGranted(Manifest.permission.READ_EXTERNAL_STORAGE)
        val writePermission = isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (readPermission && writePermission) {
            loadVideos()
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), RC_READ_STORAGE
            )
        }
    }

    private fun isGranted(permission: String): Boolean {
        val perm = ContextCompat.checkSelfPermission(this, permission)
        return perm == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RC_READ_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadVideos()
            }
        }
    }

    private fun sendResultIntent() {
        val result = Intent()
        val list = getSelectedVideos()
        result.putExtra(EXTRA_SELECTED_VIDEOS, list)
        setResult(Activity.RESULT_OK, result)
        finish()
    }

    private fun getSelectedVideos(): ArrayList<VideoModel> {
        val list = selectedVideos.filter { it.isSelected }.map { it }
        return ArrayList(list)
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
            setSubtitle()
            videoAdapter.notifyDataSetChanged()
        }
    }
}
