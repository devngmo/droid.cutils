package com.tml.libs.cutils.taskstack

import android.annotation.SuppressLint
import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tml.libs.cutils.StaticLogger
import org.json.JSONObject
import kotlin.collections.ArrayList

open class TaskStackViewModel : ViewModel(), TaskStackInterface, TaskListener  {
    override fun taskCount(): Int {
        if (taskList.value == null)return 0
        return taskList.value!!.size
    }

    override fun clear() {
        curTaskIndex = -1
        taskList.value!!.clear()
    }

    var mEnvArgs = JSONObject()
    override fun getEnvArgs(): JSONObject {
        return mEnvArgs
    }
    override fun popNextTaskByID(id: String, besideOnly:Boolean) {
        var idx = curTaskIndex + 1

        while (idx < taskList.value!!.size) {
            if (taskList.value!![idx].id == id)
            {
                if (besideOnly) {
                    if (idx == curTaskIndex + 1) {
                        taskList.value!!.removeAt(idx)
                        return
                    }
                }
                else {
                    taskList.value!!.removeAt(idx)
                    return
                }
            }
            else {
                if (besideOnly)
                    return

                idx++
            }
        }
    }

    override fun hasNext(): Boolean {
        return curTaskIndex + 1 < taskList.value!!.size
    }

    override fun execNext() {
        curTaskIndex++
        StaticLogger.I(this, "execNext idx $curTaskIndex / ${taskCount()}")
        taskList.value!![curTaskIndex].exec(this, execContext)
    }

    var stackListeners = ArrayList<TaskStackListener>()
    override fun register(listener: TaskStackListener) {
        stackListeners.add(listener)
    }
    override fun unregister(listener: TaskStackListener) {
        stackListeners.remove(listener)
    }

    var stackStartTime :Long = 0
    @SuppressLint("StaticFieldLeak")
    lateinit var execContext:Activity

    override fun send(sender: Task, msg: String) {
        if (msg == MSG_RESET_STACK_TIME)
            stackStartTime = System.currentTimeMillis()
    }

    override fun onTaskBeginExec(task: Task) {
        StaticLogger.W(this, "onTaskBeginExec ${task.id}")
        for (x in stackListeners)
            x.onTaskBeginExec(this, task)
    }
    override fun onTaskCanceled(task: Task) {
        StaticLogger.W(this, "onTaskCanceled ${task.id}")
        for (x in stackListeners)
            x.onTaskCanceled(this, task)
    }

    override fun onTaskFinished(task: Task) {
        StaticLogger.W(this, "onTaskFinished ${task.id} pass ${task.result.pass}")
        if (!task.result.pass) {
            for (x in stackListeners)
                x.onTaskFailed(this, task)
            if (task.breakStackOnFail)
            {
                for (x in stackListeners)
                    x.onFinished(this, false)
                return
            }
        }
        else
        {
            for (x in stackListeners)
                x.onTaskFinished(this, task)
        }

        if (hasNext())
            execNext()
        else
        {
            for (x in stackListeners)
                x.onFinished(this, false)
        }
    }

    protected var taskList = MutableLiveData <ArrayList<Task>>()

    fun setList(ls : MutableLiveData<ArrayList<Task>>) {
        taskList = ls
        for (t in taskList.value!!)
            t.onAttach(this)
    }

    fun getTaskList(): LiveData<ArrayList<Task>> {
        return taskList
    }


    var curTaskIndex :Int = -1

    override fun push(task:Task) {
        if (taskList.value == null) {
            taskList.value = ArrayList<Task>()
        }
        taskList.value!!.add(task)
        task.onAttach(this)
    }

    override fun moveFirst(): TaskStackInterface {
        curTaskIndex = 0
        return this
    }

    override fun moveNext(): TaskStackInterface {
        curTaskIndex++
        return this
    }

    var mIsExecuted = false
    override fun isExecuted(): Boolean {
        return mIsExecuted
    }

    override fun exec(context: Activity) {
        mIsExecuted = true
        execContext = context
        stackStartTime = System.currentTimeMillis()
        moveFirst()
        taskList.value!![curTaskIndex].exec(this, execContext)
    }

    override fun send(msg: String) {
        try {
            taskList.value!![curTaskIndex].onMsg(msg)
        }
        catch (ex:Exception) {
            StaticLogger.E(this, "send $msg on $curTaskIndex")
        }
    }
}