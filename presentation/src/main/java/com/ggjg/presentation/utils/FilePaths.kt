package com.ggjg.presentation.utils

import android.os.Environment.*
import com.ggjg.presentation.utils.extension.getFilePaths

object FilePaths {
    val ROOT_DIR = getExternalStorageDirectory().path
    val CAMERA = "$ROOT_DIR/DCIM/Camera"
}

fun getImageDirectory(): List<String> {
    var imgList = listOf<String>()
    imgList = imgList.plus(FilePaths.CAMERA.getFilePaths())
    imgList = imgList.filter { !it.contains(".mp4") }
    return imgList.reversed()
}