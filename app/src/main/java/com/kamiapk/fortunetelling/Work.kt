package com.kamiapk.fortunetelling

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class Work(context: Context, params: WorkerParameters) : Worker(context, params){

    //通知チャンネルを作るためにNotificationManagerクラスのインスタンス取得
    //コンテクストを明示しないとだめみたい。applicationContextが今回はコンテクスト
    //シングルトンのように、その寿命がApplicationの寿命と同じである場合、ApplicationContextを渡すのが望ましいとされている。
    val Nmanager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    init {

        //api26以上にしか通知チャンネルがないがこれがないとapi26以上で上手く機能しなくなる
        // Channelの取得と生成
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //チャンネルインスタンス取得
            //id:一意なstring, name:チャンネル名, チャンネルの重要度
            val channel = NotificationChannel("new fortune", "Fortune", NotificationManager.IMPORTANCE_HIGH)
            //チャンネルの設定
            channel.apply {
                description = "占い結果を表示します"
                //バイブレーションを入れる
                enableVibration(true)
            }
            //通知チャンネルの登録
            Nmanager.createNotificationChannel(channel)
        }
    }






    override fun doWork(): Result {


        Log.d("Test","AAA")
        //普通のインテントを作成
        val intent = Intent(applicationContext,Results::class.java)
        //インテントをペンディングインテントにしてOSが発行できるインテントにする
        var pendingIntent = PendingIntent.getActivity(applicationContext,1,intent,PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(applicationContext,"new fortune" ).apply {
            setContentTitle("あなたの運勢がわかりました") //タイトル
            setContentText("占い結果は…?タップして確認しよう!") //内容
            setSmallIcon(R.drawable.ic_launcher_background) //アイコン
            setContentIntent(pendingIntent) //タップ時のインテント
            setAutoCancel(true) //通知タップで消える
        }

        Nmanager.notify(1, notification.build())



        return Result.success()
    }


}