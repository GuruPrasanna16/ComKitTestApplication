package com.example.ivi.example.comkittestapplication

import android.content.Context
import android.util.Log
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

/*
 * Copyright (C) 1992 TomTom NV. All rights reserved.
 *
 * This software is the proprietary copyright of TomTom NV and its subsidiaries and may be
 * used for internal evaluation purposes or commercial use strictly subject to separate
 * license agreement between you and TomTom NV. If you are the licensee, you are only permitted
 * to use this software in accordance with the terms of your license agreement. If you are
 * not the licensee, you are not authorized to use this software in any manner and should
 * immediately return or destroy it.
 */

//import com.tomtom.navui.util.FileUtil;
//import com.tomtom.navui.util.Log;


class ConfigReader @JvmOverloads constructor(
      private val context: Context?
) {

    val comkitSetting: String
        get() {
            var rawSettings = ""
            rawSettings = readAssetsFile(COMKIT_SETTINGS_FILE).toString()
            Log.i(TAG, "Json Data is " + rawSettings)
            return rawSettings
        }

    private fun readAssetsFile(name: String): String? {
        var json: String? = null
        json = try {
            val `is`: InputStream = (context?.getAssets()?.open(name) ?: "") as InputStream
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charset.forName("utf-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }


    companion object {
        private const val TAG = "ConfigReader"
        private const val COMKIT_SETTINGS_FILE = "comkit.json"
    }
}