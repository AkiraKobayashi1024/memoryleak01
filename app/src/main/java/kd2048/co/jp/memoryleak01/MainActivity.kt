package kd2048.co.jp.memoryleak01

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import java.lang.Thread.sleep
import java.lang.ref.WeakReference
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    val mAsync = AsyncTaskAAA()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val mText = findViewById<TextView>(R.id.text_hello)
        mAsync.mRef = WeakReference(mText)
        mAsync.execute(60)

    }

    override fun onDestroy() {
        // キャンセルしなければメモリーリーク
        //mAsync.cancel(true)
        super.onDestroy()
    }

}

class AsyncTaskAAA : AsyncTask<Int, Int?, Int?>() {
    var mRef: WeakReference<TextView?> = WeakReference(null)
    var mCount: Int = 0

    override fun doInBackground(vararg p0: Int?): Int {
        //TODO("Not yet implemented")
        mCount = p0[0]!!
        for (i in 1..mCount) {
            Log.d("kd>", "doInBackground ${mCount}")
            publishProgress(mCount)
            mCount -= 1
            sleep(1000)
            if (mRef.get() == null) break
        }
        return 0
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        Log.d("kd>", "onProgressUpdate ${values[0]}")

        Log.d("kd>", "onProgressUpdate mText ${mRef.get()}")
        Log.d("kd>", "SetText.mText ${mRef.get()}")
        mRef.get()?.setText("${values[0]}")
    }


}