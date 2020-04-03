package com.hugh.kit.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.hugh.kit.App
import com.hugh.kit.R
import com.hugh.kit.utils.JumpUtil
import com.hugh.kit.utils.ResultCallBack
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var url: String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var orientation = resources.configuration.orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main)
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_1)
        }

        if (savedInstanceState != null) {
            url = savedInstanceState?.getString("url")!!
            if (url != null && url.isNotEmpty()) {
                tv_go.text = url;
            }
        }

        var jumpUtil = JumpUtil.from(this@MainActivity)

        tv_go.setOnClickListener() { _: View? ->
            run {
                jumpUtil.startForResult(SecondActivity::class.java, object : ResultCallBack {
                    override fun onActivityResult(resultCode: Int, data: Intent?) {
                        url = data?.getStringExtra("url")!!;
                        tv_go.text = url;
                    }
                })
            }
        }

        var app = application as App
        var second = app.getActivity();
        if (second != null) {
            val intent = Intent(this, second::class.java)
            startActivity(intent)
            app.removeActivity(second)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (url != null) {
            outState?.putString("url", url);
        }
    }
}
