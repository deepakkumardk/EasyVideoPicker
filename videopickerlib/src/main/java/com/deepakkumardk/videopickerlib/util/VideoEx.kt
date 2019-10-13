package com.deepakkumardk.videopickerlib.util

import android.app.Activity
import android.net.Uri
import android.provider.MediaStore
import com.deepakkumardk.videopickerlib.model.VideoModel
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.onComplete

private val VIDEO_URI: Uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
private val THUMB_ID: String = MediaStore.Video.Media.MINI_THUMB_MAGIC
private val THUMB_MINI: Int = MediaStore.Video.Thumbnails.MINI_KIND

class VideoEx {

    fun getAllVideos(activity: Activity?, onCompleted: (MutableList<VideoModel>) -> Unit) {
        val startTime = System.currentTimeMillis()

        val projection = arrayOf(VIDEO_DISPLAY_NAME, VIDEO_DATA, VIDEO_DURATION)

        var count = 0
        val videoMap = mutableMapOf<String, VideoModel>()
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
                val uriIndex = it.getColumnIndex(MediaStore.Video.Thumbnails.DATA)

                var id: String
                var name: String
                var path: String
                var duration: Long
                while (it.moveToNext()) {
                    val model = VideoModel()
//                    id = it.getString(idIndex)
                    name = it.getString(nameIndex)
                    path = it.getString(dataIndex)
                    duration = it.getLong(durationIndex)

//                    model.id = id
                    model.folderName = name
                    model.videoPath = path
                    model.uriStr = it.getString(uriIndex)

                    val timeLimit = VideoPickerUI.getPickerItem().timeLimit
                    if (duration <= timeLimit)
                        videoMap[count++.toString()] = model
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

    private fun filterVideosMap(videoMap: MutableMap<String, VideoModel>): MutableList<VideoModel> {
        val myVideosList: MutableList<VideoModel> = arrayListOf()
        videoMap.entries.forEach {
            val model = it.value

            val newModel = VideoModel(
                model.id, model.folderName, model.videoPath, false, model.uriStr, null
            )
            myVideosList.add(newModel)
        }

        return myVideosList
    }

}