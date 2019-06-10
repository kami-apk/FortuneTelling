package com.kamiapk.fortunetelling


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView.setOnClickListener {
            it.animate().apply{
                duration = 10000L
                rotation( 360.0f * 100.0f)
            }
            //一度きりの処理を10秒後に実行
            val request = OneTimeWorkRequestBuilder<Work>()
                .setInitialDelay(10L, TimeUnit.SECONDS)

                .build()

            //requestをキューにいれる
            //多重通知防止のためidを設定し、キューが積まれていたら何も操作をしないようにする。
            WorkManager.getInstance().beginUniqueWork("unique_work_id",ExistingWorkPolicy.KEEP,request).enqueue()
        }

    }
}
