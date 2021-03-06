package com.shetj.diyalbume

import android.content.ContentValues
import android.net.Uri
import androidx.core.content.contentValuesOf
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import io.reactivex.Observable
import io.reactivex.functions.Function
import me.shetj.base.tools.file.SDCardUtils
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.net.URI
import java.util.concurrent.TimeUnit

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.shetj.diyalbume", appContext.packageName)
    }

    @Test
    fun test(){
        var i = 0
        Observable.just(0L).doOnComplete {
            doWork()
        }.repeatWhen (Function { ob ->

            return@Function ob.flatMap{
                if ( i <=  4) {
                    return@flatMap Observable.timer((3000 + i * 1000).toLong(), TimeUnit.MILLISECONDS)
                }
                return@flatMap Observable.error<String>(Throwable("Polling work finished"))
            }

        })

    }

    private fun doWork() {
        var workTime = Math.random()*500 +500
        try {
            print("xx"+"doWork start,  threadId=" + Thread.currentThread().id);
            Thread.sleep(workTime.toLong())
            print("xx"+ "doWork finished");
        } catch (e:InterruptedException ) {
            e.printStackTrace()
        }

    }


    @Test
    fun testInfo(){
        val initialValues = ContentValues()
        initialValues.put("key", true)

        val contentValues = contentValuesOf(Pair("key", true), Pair("key1", false))
        contentValues.put("key3",false)

    }

    @Test
    fun testFile(){
        val file = File(SDCardUtils.getPath("test")+"/test.text")
        if (!file.exists()){
            file.createNewFile()
        }
        val uri: URI = file.toURI()
        print( Uri.parse(uri.path))
    }
}
