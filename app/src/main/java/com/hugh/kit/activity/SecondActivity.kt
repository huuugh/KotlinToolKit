package com.hugh.kit.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hugh.kit.App
import com.hugh.kit.R
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    lateinit var app:App;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        send_data.setOnClickListener() { _: View? ->
            run{
                var intent = Intent()
                intent.putExtra("url", "success")
                setResult(1, intent)
                finish()
            }
        }

        app = application as App
        app.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        app.removeActivity(this)
    }
}
