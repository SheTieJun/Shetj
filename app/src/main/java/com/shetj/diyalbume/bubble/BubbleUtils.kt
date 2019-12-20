package com.shetj.diyalbume.bubble

import android.app.Notification
import android.app.PendingIntent
import android.app.Person
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import com.shetj.diyalbume.R
@RequiresApi(Build.VERSION_CODES.Q)
object BubbleUtils {

    fun createBubble(context:Context){
        // Create bubble intent
        val bubbleIntent = createBubbleIntent(context)
        // Create bubble metadata
        val bubbleData =
                    Notification.BubbleMetadata.Builder()
                            .setDesiredHeight(600)
                            .setIcon(Icon.createWithResource(context, R.mipmap.shetj_logo))
                            .setIntent(bubbleIntent)
                            .build()


        // Create notification
        val chatBot = Person.Builder()
                .setBot(true)
                .setName("BubbleBot")
                .setImportant(true)
                .build()

        val builder = Notification.Builder(context, "BubbleBot")
                .setContentIntent(bubbleIntent)
                .setSmallIcon(Icon.createWithResource(context, R.mipmap.shetj_logo))
                .setBubbleMetadata(bubbleData)
                .addPerson(chatBot)

        builder.build()
    }


    fun  createBunbble2(context:Context){
        val bubbleIntent = createBubbleIntent(context)
        Notification.BubbleMetadata.Builder()
                .setDesiredHeight(600)
                .setIcon(Icon.createWithResource(context, R.mipmap.shetj_logo))
                .setIntent(bubbleIntent)
                .setAutoExpandBubble(true)
                .setSuppressNotification(true)
                .build()
    }

    private fun createBubbleIntent(context: Context): PendingIntent{
        val target = Intent(context, BubbleActivity::class.java)
        return PendingIntent.getActivity(context, 0, target, 0 /* flags */)
    }
}

