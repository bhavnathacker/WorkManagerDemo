package com.example.workmanagerdemo.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.workmanagerdemo.makeNotification
import com.example.workmanagerdemo.sleep
import java.lang.Thread.sleep

class SecondWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val TAG by lazy {
        SecondWorker::class.java.simpleName
    }

    override fun doWork(): Result {
        sleep()
        makeNotification("Doing second work", applicationContext)

        return try {
            //Actual Work here
            sleep()
            makeNotification("completed second work", applicationContext)
            Result.success()
        } catch (throwable: Throwable) {
            Log.e(TAG, "Error doing second work", throwable)
            Result.failure()
        }
    }
}