package com.tml.libs.cutils.taskstack

//const val TASKID_DownloadBasicDataTask = "DownloadBasicDataTask"
interface TaskFactoryInterface {
    fun createTask(id:String, breakOnFailed:Boolean) :Task?
}