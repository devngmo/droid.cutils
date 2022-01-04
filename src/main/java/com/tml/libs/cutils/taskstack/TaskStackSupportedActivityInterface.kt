package com.tml.libs.cutils.taskstack

interface TaskStackSupportedActivityInterface {
    fun createTaskStackController(): TaskStackControllerInterface
    fun setupTSVM()
    fun populateTask()
    fun createTaskFactory(): TaskFactoryInterface
}