package me.shetj.update


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * A simple [Fragment] subclass.
 */
class BlankFragment : Fragment() , LifecycleObserver {

    private var cout: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        lifecycle.addObserver(this)
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cout =  arguments!!.getInt(ARG_ITEM_COUNT)
    }

    companion object {

        // TODO: Customize parameters
        fun newInstance(itemCount: Int): BlankFragment =
                BlankFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_ITEM_COUNT, itemCount)
                    }
                }

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun initLazy(){
        //start 只会执行一次
        Log.i("Fragment$cout","initLazy = ON_START")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun initLazy2(){
        //不可见，
        Log.i("Fragment$cout","initLazy2 = ON_PAUSE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun initLazy3(){
        Log.i("Fragment$cout","initLazy3 = ON_PAUSE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun initLazy4(){
        Log.i("Fragment$cout","initLazy4 = ON_DESTROY")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun initLazy5(){
        //结束
        Log.i("Fragment$cout","initLazy5 = ON_STOP")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun initLazy6(){
        //可见的时候
        Log.i("Fragment$cout","initLazy6 = ON_RESUME")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun initLazy7(){
        //每次切换状态后，会置成这个歌
        Log.i("Fragment$cout","initLazy7 = ON_ANY")
    }

//    开始 ON_PAUSE->ON_START->ON_RESUME
//    结束前现会ON_PAUSE->ON_STOP->ON_DESTROY
//    ON_RESUME->ON_PAUSE
//    ON_PAUSE->ON_RESUME
}
