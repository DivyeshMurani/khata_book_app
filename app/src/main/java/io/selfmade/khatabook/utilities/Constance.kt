package io.selfmade.khatabook.utilities

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Environment
import io.selfmade.khatabook.model.CreateItem
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object Constance {


    fun showDialog(
        activity: Activity,
        title: String,
        msg: String,
        listener: DialogInterface.OnClickListener
    ) {

        AlertDialog.Builder(activity)
            .setMessage(msg)
            .setNegativeButton(
                "cancel"
            ) { p0, p1 -> p0!!.dismiss() }
            .setPositiveButton("ok", listener)
            .setTitle(title)
            .create().show()
    }

    fun showDialog(
        activity: Activity,
        title: String,
        msg: String,
    ) {

        AlertDialog.Builder(activity)
            .setMessage(msg)
            .setPositiveButton(
                "OK"
            ) { p0, p1 -> p0!!.dismiss() }
            .setTitle(title)
            .create().show()
    }



    fun getImageDirectoryPath(): String{
        return Environment.DIRECTORY_PICTURES + File.separator + "KhataBook"
    }

    fun getNewFileName() : String {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        return "JPEG_${timeStamp}_.jpg"
    }




}