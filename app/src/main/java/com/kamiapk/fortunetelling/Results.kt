package com.kamiapk.fortunetelling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_results.*
import kotlin.random.Random

class Results : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val randomInt = Random.nextInt(100) + 1

        textView4.text = randomInt.toString() + "点!"


    }
}
