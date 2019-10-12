package com.deepakkumardk.videopickerlib

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import com.deepakkumardk.videopickerlib.model.VideoModel
import com.deepakkumardk.videopickerlib.model.VideoPickerItem
import com.deepakkumardk.videopickerlib.util.EXTRA_SELECTED_VIDEOS
import com.deepakkumardk.videopickerlib.util.VideoPickerUI

/**
 * @author Deepak Kumar
 * @since 12/10/19
 */
class EasyVideoPicker {

    fun startPickerForResult(activity: Activity?, item: VideoPickerItem, requestCode: Int) {
        VideoPickerUI.setPickerUI(item)
        val intent = Intent(activity, VideoPickerActivity::class.java)
        activity.let {
            it?.startActivityForResult(intent, requestCode)
        }
    }

    fun startPickerForResult(fragment: Fragment?, item: VideoPickerItem, requestCode: Int) {
        VideoPickerUI.setPickerUI(item)
        val intent = Intent(fragment?.context, VideoPickerActivity::class.java)
        fragment.let {
            it?.startActivityForResult(intent, requestCode)
        }
    }


    companion object {
        /**
         * returns selected list of VideoModel
         */
        fun getSelectedVideos(data: Intent?): ArrayList<VideoModel>? {
            return data?.getParcelableArrayListExtra(EXTRA_SELECTED_VIDEOS)
        }

    }

}