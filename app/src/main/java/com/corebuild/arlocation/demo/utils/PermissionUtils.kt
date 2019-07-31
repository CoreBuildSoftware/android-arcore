package com.corebuild.arlocation.demo.utils

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat

object PermissionUtils {

    private const val CAMERA_PERMISSION_CODE = 0
    private const val MULTIPLE_PERMISSION_CODE = 1
    private const val CAMERA_PERMISSION = Manifest.permission.CAMERA
    private val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION)

    fun hasLocationAndCameraPermissions(activity: Activity): Boolean {
        permissions.forEach { permission ->
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    fun hasCameraPermission(activity: Activity): Boolean {
        return (ActivityCompat.checkSelfPermission(activity, CAMERA_PERMISSION) == PackageManager.PERMISSION_GRANTED)
    }

    fun requestCameraPermission(activity: Activity) {
        ActivityCompat.requestPermissions(
                activity, arrayOf(CAMERA_PERMISSION), CAMERA_PERMISSION_CODE)
    }

    fun requestCameraAndLocationPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(
                activity, permissions, MULTIPLE_PERMISSION_CODE)
    }

    fun shouldShowRequestPermissionRationale(activity: Activity): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, CAMERA_PERMISSION)
    }


    fun launchPermissionSettings(activity: Activity) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = Uri.fromParts("package", activity.packageName, null)
        activity.startActivity(intent)
    }
}