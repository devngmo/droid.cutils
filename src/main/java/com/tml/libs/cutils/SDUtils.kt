package com.tml.libs.cutils

import android.content.Context
import android.os.storage.StorageVolume
import java.io.File

class SDUtils {
    companion object {
        fun getExternalCardDirectory(context: Context): File? {
            val storageManager = context.getSystemService(Context.STORAGE_SERVICE)
            try {
                val storageVolumeClazz = Class.forName("android.os.storage.StorageVolume")
                val getVolumeList = storageManager.javaClass.getMethod("getVolumeList")
                val getPath = storageVolumeClazz.getMethod("getPath")
                val isRemovable = storageVolumeClazz.getMethod("isRemovable")
                val result = getVolumeList.invoke(storageManager) as Array<*>
                result.forEach {
                    if (isRemovable.invoke(it) as Boolean) {
                        return File(getPath.invoke(it) as String)
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
            return null
        }
    }
}