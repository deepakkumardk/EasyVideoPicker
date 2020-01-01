package com.deepakkumardk.videopickerlib.util

import android.app.Activity
import android.net.Uri
import android.provider.MediaStore
import com.deepakkumardk.videopickerlib.model.VideoModel
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.onComplete

private val VIDEO_URI: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

/**
 * @author Deepak Kumar
 * @since 12/10/19
 */
class VideoEx {

    fun getAllVideos(activity: Activity?, onCompleted: (MutableList<VideoModel>) -> Unit) {
        val startTime = System.currentTimeMillis()

        val projection =
            arrayOf(VIDEO_ID, VIDEO_DISPLAY_NAME, VIDEO_DATA, VIDEO_DURATION, VIDEO_SIZE)

        val videoMap = mutableMapOf<Long, VideoModel>()
        val cr = activity?.contentResolver
        doAsyncResult {
            cr?.query(
                VIDEO_URI, projection,
                null, null, ORDER_BY
            )?.use {
                val idIndex = it.getColumnIndex(VIDEO_ID)
                val nameIndex = it.getColumnIndex(VIDEO_DISPLAY_NAME)
                val dataIndex = it.getColumnIndex(VIDEO_DATA)
                val durationIndex = it.getColumnIndex(VIDEO_DURATION)
                val sizeIndex = it.getColumnIndex(VIDEO_SIZE)

                while (it.moveToNext()) {
                    val model = VideoModel()
                    val id = it.getLong(idIndex)
                    val name = it.getString(nameIndex)
                    val path = it.getString(dataIndex)
                    val duration = it.getLong(durationIndex)
                    val size = it.getLong(sizeIndex)

                    model.id = id.toString()
                    model.folderName = name
                    model.videoPath = path
                    model.videoDuration = duration

                    val timeLimit = VideoPickerUI.getPickerItem().timeLimit
                    val sizeLimit = VideoPickerUI.getPickerItem().sizeLimit
                    timeLimit?.let {
                        if (duration <= timeLimit)
                            videoMap[id] = model
                    }
                    sizeLimit?.let {
                        if (size <= sizeLimit)
                            videoMap[id] = model
                        else
                            videoMap.remove(id)
                    }
                    if (timeLimit == null && sizeLimit == null)
                        videoMap[id] = model
                }
                it.close()
            }
            onComplete {
                val fetchingTime = System.currentTimeMillis() - startTime
                log("Fetching Completed in $fetchingTime ms")
                onCompleted.invoke(filterVideosMap(videoMap))
            }
            return@doAsyncResult
        }
    }

    private fun filterVideosMap(videoMap: MutableMap<Long, VideoModel>): MutableList<VideoModel> {
        val myVideosList: MutableList<VideoModel> = arrayListOf()
        videoMap.entries.forEach {
            val model = it.value

            val newModel =
                VideoModel(model.id, model.folderName, model.videoPath, model.videoDuration)
            myVideosList.add(newModel)
        }

        return myVideosList
    }

}