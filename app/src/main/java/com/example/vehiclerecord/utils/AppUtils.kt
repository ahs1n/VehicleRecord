package com.example.vehiclerecord.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.vehiclerecord.R
import com.example.vehiclerecord.core.MainApp.Companion.PROJECT_NAME
import edu.aku.hassannaqvi.naunehal.databinding.EndSectionDialogBinding
import edu.aku.hassannaqvi.naunehal.ui.EndingActivity
import edu.aku.hassannaqvi.naunehal.utils.CreateTable.DATABASE_COPY
import edu.aku.hassannaqvi.naunehal.utils.CreateTable.DATABASE_NAME
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

fun dbBackup(context: Context) {
    val sharedPref = context.getSharedPreferences("listingHHSMK", Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    val dt: String = sharedPref.getString("dt", "").toString()
    if (dt != SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())) {
        editor.putString("dt", SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date()))
        editor.apply()
    }

    var folder: File
    folder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        File(context.getExternalFilesDir("")?.absolutePath + File.separator + PROJECT_NAME)
    else
        File(Environment.getExternalStorageDirectory().toString() + File.separator + PROJECT_NAME)

    var success = true
    if (!folder.exists()) {
        success = folder.mkdirs()
    }
    if (success) {
        val directoryName = folder.path + File.separator + sharedPref.getString("dt", "")
        folder = File(directoryName)
        if (!folder.exists()) {
            success = folder.mkdirs()
        }
        if (success) {
            val any = try {
                val dbFile = File(context.getDatabasePath(DATABASE_NAME).path)
                val fis = FileInputStream(dbFile)
                val outFileName: String = directoryName + File.separator + DATABASE_COPY
                // Open the empty db as the output stream
                val output: OutputStream = FileOutputStream(outFileName)

                // Transfer bytes from the inputfile to the outputfile
                val buffer = ByteArray(1024)
                var length: Int
                while (fis.read(buffer).also { length = it } > 0) {
                    output.write(buffer, 0, length)
                }
                // Close the streams
                output.flush()
                output.close()
                fis.close()
            } catch (e: IOException) {
                e.message?.let { Log.e("dbBackup:", it) }
            }
        }
    } else {
        Toast.makeText(context, "Not create folder", Toast.LENGTH_SHORT).show()
    }

}

fun AppCompatActivity.openSectionEndingActivity() {
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.item_dialog_2)
    dialog.setCancelable(false)
    val params = WindowManager.LayoutParams()
    params.copyFrom(dialog.window!!.attributes)
    params.width = WindowManager.LayoutParams.WRAP_CONTENT
    params.height = WindowManager.LayoutParams.WRAP_CONTENT
    dialog.show()
    dialog.window!!.attributes = params
    dialog.findViewById<View>(R.id.btnOk).setOnClickListener { view: View? ->
        this.finish()
        val intent = Intent(this, EndingActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this.startActivity(intent)
    }
    dialog.findViewById<View>(R.id.btnNo).setOnClickListener { view: View? -> dialog.dismiss() }
}

fun contextEndActivity(activity: Activity) {
    val dialog = Dialog(activity)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.item_dialog_2)
    dialog.setCancelable(false)
    val params = WindowManager.LayoutParams()
    params.copyFrom(dialog.window!!.attributes)
    params.width = WindowManager.LayoutParams.WRAP_CONTENT
    params.height = WindowManager.LayoutParams.WRAP_CONTENT
    dialog.show()
    dialog.window!!.attributes = params
    val endSecAActivity = activity as EndSectionActivity
    dialog.findViewById<View>(R.id.btnOk)
        .setOnClickListener { view: View? -> endSecAActivity.endSecActivity(true) }
    dialog.findViewById<View>(R.id.btnNo).setOnClickListener { view: View? -> dialog.dismiss() }
}

@JvmOverloads
fun AppCompatActivity.openWarningActivity(
    id: Int,
    item: Any? = null,
    title: String = "WARNING!",
    message: String = "Are you sure, you want to exit without saving?",
    btnYesTxt: String = "YES",
    btnNoTxt: String = "NO"
) {
    val dialog = Dialog(this)

    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    val bi: EndSectionDialogBinding =
        DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.end_section_dialog, null, false)
    dialog.setContentView(bi.root)
    bi.alertTitle.text = title
    bi.alertTitle.setTextColor(ContextCompat.getColor(this, R.color.redDark))
    bi.content.text = message
    bi.btnOk.text = btnYesTxt
    bi.btnOk.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
    bi.btnNo.text = btnNoTxt
    bi.btnNo.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
    dialog.setCancelable(false)
    val params = WindowManager.LayoutParams()
    params.copyFrom(dialog.window!!.attributes)
    params.width = WindowManager.LayoutParams.WRAP_CONTENT
    params.height = WindowManager.LayoutParams.WRAP_CONTENT
    dialog.window!!.attributes = params
    dialog.show()
    bi.btnOk.setOnClickListener {
        dialog.dismiss()
        val warningActivity = this as WarningActivityInterface
        warningActivity.callWarningActivity(id, item)
    }
    bi.btnNo.setOnClickListener {
        dialog.dismiss()
    }
}

@JvmOverloads
fun AppCompatActivity.openWarningDialog(title: String, message: String, btnYesTxt: String = "OK") {
    val dialog = Dialog(this)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    val bi: EndSectionDialogBinding =
        DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.end_section_dialog, null, false)
    dialog.setContentView(bi.root)
    bi.alertTitle.text = title
    bi.alertTitle.setTextColor(ContextCompat.getColor(this, R.color.green))
    bi.content.text = message
    bi.btnOk.text = btnYesTxt
    bi.btnOk.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
    bi.btnNo.visibility = View.GONE
    val params = WindowManager.LayoutParams()
    params.copyFrom(dialog.window!!.attributes)
    params.width = WindowManager.LayoutParams.WRAP_CONTENT
    params.height = WindowManager.LayoutParams.WRAP_CONTENT
    dialog.window!!.attributes = params
    dialog.show()
    bi.btnOk.setOnClickListener {
        dialog.dismiss()
    }
}

interface EndSectionActivity {
    fun endSecActivity(flag: Boolean)
}

interface WarningActivityInterface {
    fun callWarningActivity(id: Int, item: Any? = null)
}

fun showGPSAlert(context: Context) {
    val alertDialogBuilder = AlertDialog.Builder(context)
    alertDialogBuilder
        .setMessage("GPS is disabled in your device. Enable it?")
        .setCancelable(false)
        .setPositiveButton(
            "Enable GPS"
        ) { dialog: DialogInterface?, id: Int ->
            val callGPSSettingIntent = Intent(
                Settings.ACTION_LOCATION_SOURCE_SETTINGS
            )
            context.startActivity(callGPSSettingIntent)
        }
    alertDialogBuilder.setNegativeButton(
        "Cancel"
    ) { dialog: DialogInterface, id: Int -> dialog.cancel() }
    val alert = alertDialogBuilder.create()
    alert.show()
}

fun String.convertStringToUpperCase(): String {
    /*
     * Program that first convert all uper case into lower case then
     * convert fist letter into uppercase
     */
    val calStr = this.split(" ").map { it.toLowerCase(Locale.ENGLISH).capitalize(Locale.ENGLISH) }
    return calStr.joinToString(separator = " ")
}

fun String.shortStringLength(): String {
    /*
     * Program that first convert all uper case into lower case then
     * convert fist letter into uppercase
     */
    var calStr = this
    if (this.length > 15)
        calStr = this.substring(15).plus("...")
    return calStr
}


