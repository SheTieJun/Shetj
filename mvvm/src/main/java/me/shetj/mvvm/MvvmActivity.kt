package me.shetj.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import me.shetj.mvvm.model.User
import me.shetj.mvvm.obseverable.ObserverableUser


class MvvmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //初始化
        val activityBinding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_mvvm)
        activityBinding.lifecycleOwner = this


        val user = User("shetj",27)

        activityBinding.setVariable(BR.userInfo,user)

       val user2=  ObserverableUser()

        activityBinding.setVariable(BR.userInfo2,user2)

    }


}
