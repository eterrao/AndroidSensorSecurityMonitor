package com.raomengyang.androidsensormonitor.utils

import android.hardware.Camera

/**
 * Created by Raomengyang on 18-8-27.
 * Email    : ericrao@welove-inc.com
 * Desc     :
 * Version  : 1.0
 */
class CameraChecker {

    fun isCameraUsebyApp(): Boolean {
        var camera: Camera? = null
        try {
            camera = Camera.open()
        } catch (e: RuntimeException) {
            return true
        } finally {
            if (camera != null) camera!!.release()
        }
        return false
    }
}