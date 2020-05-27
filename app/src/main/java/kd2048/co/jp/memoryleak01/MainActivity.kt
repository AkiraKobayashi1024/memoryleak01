package kd2048.co.jp.memoryleak01

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import java.lang.Thread.sleep
import java.lang.ref.WeakReference
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    val mAsync = AsyncTaskAAA()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mText = findViewById<TextView>(R.id.text_hello)

        mAsync.mText = mText
        mAsync.execute(60)

    }

    override fun onDestroy() {
        // キャンセルしなければメモリーリーク
        //mAsync.cancel(true)
        super.onDestroy()
    }

}

class AsyncTaskAAA : AsyncTask<Int, Int?, Int?>() {
    var mText: TextView? = null
    var mCount: Int = 0

    override fun doInBackground(vararg p0: Int?): Int {
        //TODO("Not yet implemented")
        mCount = p0[0]!!
        for (i in 1..mCount) {
            Log.d("kd>", "doInBackground ${mCount}")
            publishProgress(mCount)
            mCount -= 1
            sleep(1000)
        }
        return 0
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        Log.d("kd>", "onProgressUpdate ${values[0]}")

        Log.d("kd>", "onProgressUpdate mText ${mText}")
        Log.d("kd>", "SetText.mText ${mText}")
        mText?.setText("${values[0]}")
    }


}