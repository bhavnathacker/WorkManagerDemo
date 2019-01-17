package com.example.workmanagerdemo.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.workmanagerdemo.makeNotification
import com.example.workmanagerdemo.sleep
import java.lang.Thread.sleep

class FirstWorker(ctx: Context, params: WorkerParameters): Worker(ctx, params) {

    private val TAG by lazy {
        FirstWorker::class.java.simpleName
    }

    override fun doWork(): Result {
        val data = inputData.getString("KEY_WORK")?:""
       makeNotification(data, applicationContext)

        return try {
            //Actual Work here
            sleep()
            makeNotification("completed first work", applicationContext)
            Result.success()
        } catch (throwable: Throwable) {
            Log.e(TAG, "Error doing first work", throwable)
            Result.failure()
        }
    }
}