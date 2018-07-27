package com.tml.libs.cutils;

public interface SimpleTaskListener {
    void onTaskFinished();
    void onTaskFailed();
    void onTaskStarted();
}
