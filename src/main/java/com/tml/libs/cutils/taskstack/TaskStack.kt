package com.tml.libs.cutils.taskstack

import android.app.Activity
import org.json.JSONObject

interface TaskStackListener {
    /**
     * Result = Cancel this task or not
     * TRUE: cancel this task
     * FALSE: run this task
     */
    fun onTaskBeginExec(stack: TaskStackInterface, task: Task)
    fun onTaskFailed(stack: TaskStackInterface, task:Task)
    fun onTaskFinished(stack: TaskStackInterface, task:Task)
    fun onTaskCanceled(stack: TaskStackInterface, task:Task)
    fun onFinished(stack: TaskStackInterface, passed:Boolean)
}

interface TaskStackInterface {
    fun moveFirst() : TaskStackInterface
    fun moveNext() : TaskStackInterface
    fun isExecuted():Boolean
    fun exec(context: Activity)
    fun push(task:Task)
    fun send(msg:String)
    fun hasNext(): Boolean
    fun execNext()
    fun popNextTaskByID(id: String, besideOnly:Boolean)
    fun getEnvArgs():JSONObject
    fun clear()
    fun register(listener: TaskStackListener)
    fun unregister(listener: TaskStackListener)
    fun taskCount():Int
}
open class TaskStack //: TaskStackInterface, TaskListener
{
//    var mEnvArgs = JSONObject()
//    override fun getEnvArgs(): JSONObject {
//        return mEnvArgs
//    }
//
//    override fun popNextTaskByID(id: String, besideOnly:Boolean) {
//
//    }
//
//    override fun execNext() {
//
//    }
//
//    override fun hasNext(): Boolean {
//        return false
//    }
//
//    override fun send(sender: Task, msg: String) {
//
//    }
//
//    override fun onTaskBeginExec(task: Task) {
//
//    }
//
//    var stackListener:TaskStackListener? = null
//    override fun onTaskFinished(task: Task) {
//        if (!task.result.pass) {
//            stackListener?.onTaskFailed(this, task)
//        }
//        moveNext()
//    }
//
//    var taskList = ArrayList<Task>()
//
//    var curTaskIndex :Int = -1
//
//    override fun push(task:Task) {
//        taskList.add(task)
//    }
//
//    override fun moveFirst():TaskStackInterface {
//        curTaskIndex = 0
//        return this
//    }
//
//    override fun moveNext():TaskStackInterface {
//        curTaskIndex++
//        return this
//    }
//
//    override fun exec(context: Activity) {
//        taskList[curTaskIndex].exec(this, context)
//    }
//
//    override fun send(msg: String) {
//        taskList[curTaskIndex].onMsg(msg)
//    }
}