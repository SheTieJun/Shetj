package me.shetj.mvvm.obseverable

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class ObserverableUser : BaseObservable() {

    private var userName :String ? =null

    @Bindable
    var userAge: Int = 0

    @Bindable
    fun  getUserName():String{
        return userName?:"未命名"
    }

    fun setUserName(userName:String){
        this.userName = userName
        notifyChange()
    }

}