package me.shetj.dlna.listener

interface DLNAStateCallback {
    fun onConnected()
    fun onDisconnected()
}