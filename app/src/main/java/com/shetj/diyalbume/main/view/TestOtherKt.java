package com.shetj.diyalbume.main.view;

import android.widget.TextView;

import androidx.core.text.StringKt;
import androidx.core.view.MenuKt;
import androidx.core.view.ViewGroupKt;
import androidx.core.view.ViewKt;
import androidx.core.widget.TextViewKt;

import java.util.ArrayList;
import java.util.TimerTask;

import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.concurrent.LocksKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.concurrent.TimersKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.text.StringsKt;

public class TestOtherKt {

    void testStringKt(){
        StringsKt.all("xxx", new Function1<Character, Boolean>() {
            @Override
            public Boolean invoke(Character character) {
                return null;
            }
        });

    }

    void testArrayKt(){
        ArraysKt.toSet(new String[]{});
    }

    void testViewKt(){
//        ViewKt.getMarginEnd()
//        TextViewKt.doOnTextChanged()
    }

    void testViewGroupKt(){
//        MenuKt.forEach();
//        ViewGroupKt.isEmpty()
    }

    void testTreadKt(){
//        ThreadsKt.thread(true, true,  null, "test", 1, new Function0<Unit>() {
//            @Override
//            public Unit invoke() {
//                System.out.println("xxx");
//                return null;
//            }
//        });
    }


    void testTimerKt(){
    }

    void testFileKt(){
//        FilesKt.copyTo()
    }
}
