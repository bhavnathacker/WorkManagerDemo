package com.example.workmanagerdemo

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.work.*
import com.example.workmanagerdemo.workers.FirstWorker
import com.example.workmanagerdemo.workers.SecondWorker

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->

            val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

            val workManager = WorkManager.getInstance()

            val data = Data.Builder()
            data.putString("KEY_WORK", "DOING FIRST WORK")

            var firstWorkRequest = OneTimeWorkRequest.Builder(FirstWorker::class.java).setInputData(data.build()).build()
            var secondWorkRequest = OneTimeWorkRequest.Builder(SecondWorker::class.java).addTag("SECOND_WORK").setConstraints(constraints).build()
            //workManager.enqueue(firstWorkRequest)
            //workManager.beginUniqueWork("My WORK", ExistingWorkPolicy.REPLACE, firstWorkRequest).enqueue()
            workManager.beginWith(firstWorkRequest).then(secondWorkRequest).enqueue()

            workManager.getWorkInfoByIdLiveData(firstWorkRequest.id).observe(this, Observer {workInfo ->
                var fwStatus = workInfo?.state
                Toast.makeText(this, fwStatus?.name, Toast.LENGTH_SHORT).show()

            })

            workManager.getWorkInfosByTagLiveData("SECOND_WORK").observe(this, Observer { workInfoList ->
                var swStatus = workInfoList?.getOrNull(0)?.state
                Toast.makeText(this, swStatus?.name, Toast.LENGTH_SHORT).show()

            })
            

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
