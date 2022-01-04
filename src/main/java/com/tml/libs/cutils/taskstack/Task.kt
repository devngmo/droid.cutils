package com.tml.libs.cutils.taskstack

import android.app.Activity
import android.content.Context
import com.tml.libs.cutils.StaticLogger
import org.json.JSONObject

interface TaskListener {
    fun onTaskBeginExec(task: Task)
    fun onTaskFinished(task: Task)
    fun onTaskCanceled(task: Task)
    fun send(sender: Task, msg:String)
}

open class TaskExecResult(var pass:Boolean) {
    var args = JSONObject()
}

const val TASK_MSG_CANCEL = "TASK_MSG_CANCEL"

abstract class Task(val id:String, var breakStackOnFail:Boolean) {
    lateinit var execContext:Activity
    lateinit var mListener:TaskListener
    var result = TaskExecResult(false)
    lateinit var mStack:TaskStackInterface

    var canceled = false

    fun onAttach(stack:TaskStackInterface) {
        mStack = stack
    }

    fun exec(listener:TaskListener, context: Activity) {
        StaticLogger.I(this, "exec...")
        execContext = context
        mListener = listener
        mListener.onTaskBeginExec(this)
        if (!canceled)
            doExec(context)
        else {
            StaticLogger.W(this, "CANCELED!!!")
            result.pass = false
            onFinished()
        }
    }

    abstract fun doExec(context: Activity)

    fun onFinished() {
        StaticLogger.I(this, "onFinished passed ${result.pass}")
        mListener.onTaskFinished(this)
    }

    fun onCanceled() {
        StaticLogger.W(this, "onCanceled")
        mListener.onTaskCanceled(this)
    }

    open fun onMsg(msg:String, extra:JSONObject? = null) {
        StaticLogger.I(this, "onMsg $msg")
        if (msg == TASK_MSG_CANCEL)
            canceled = true
    }
}

const val MSG_RESET_STACK_TIME = "MSG_RESET_STACK_TIME"
class ResetStackTimerTask :Task("ResetStackTimerTask", false) {
    override fun onMsg(msg: String, extra: JSONObject?) {
        super.onMsg(msg, extra)
    }

    override fun doExec(context: Activity) {
        mListener.send(this, MSG_RESET_STACK_TIME)
        result.pass = true
        onFinished()
    }
}

class WaitForStackTimerTask(val waitTime:Long):Task("WaitForStackTimerTask", false) {
    override fun doExec(context: Activity) {
        val t = Thread(Runnable {
            val dt = System.currentTimeMillis()
            try {
                if (waitTime > dt)
                    Thread.sleep(waitTime - dt + 1)
            }
            catch (ex:Exception) {
                StaticLogger.E("WaitForStackTimerTask", "unhandled exception", ex)
            }

            result.pass = true

            context.runOnUiThread(object: Runnable {
                override fun run() {
                    onFinished()
                }
            })
        })
        t.start()
    }

    override fun onMsg(msg: String, extra: JSONObject?) {
    }

}