package com.example.ggjg_andorid.utils

import android.os.Environment.*

object FilePaths {
    val ROOT_DIR = getExternalStorageDirectory().path
    val PICTURES = "$ROOT_DIR/Pictures"
    val CAMERA = "$ROOT_DIR/DCIM"
    val DOWNLOAD = "$ROOT_DIR/Download"
}

fun getImageDirectory(): List<String> {
    var imgList = listOf<String>()
    FilePaths.PICTURES.getDirectoryPaths().forEach {
        imgList = imgList.plus(it.getFilePaths())
    }
    FilePaths.CAMERA.getDirectoryPaths().forEach {
        imgList = imgList.plus(it.getFilePaths())
    }
    imgList = imgList.plus(FilePaths.DOWNLOAD.getFilePaths())
    imgList = imgList.filter { !it.contains(".mp4") }
    return imgList
}