package com.shetj.diyalbume.encrypt

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import com.alibaba.android.arouter.facade.annotation.Route
import com.shetj.diyalbume.R

import me.shetj.base.base.BaseActivity
import me.shetj.base.base.BasePresenter
import me.shetj.base.tools.json.DES

@Route(path = "/shetj/EncryptActivity")
class EncryptActivity : BaseActivity<BasePresenter<*>>(), View.OnClickListener {

    /**
     * shetiejun
     */
    private var mEditQuery: EditText? = null
    /**   */
    private var mText: TextView? = null
    /**
     * 加密
     */
    private var mBtnJiami: Button? = null
    /**
     * 解密
     */
    private var mBtnJiemi: Button? = null
    /**
     * shetiejun
     */
    private var mEditJiemi: EditText? = null
    /**   */
    private var mText2: TextView? = null

    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encrypt)
        initView()
    }

    override fun initView() {

        mEditQuery = findViewById(R.id.edit_query)
        mText = findViewById(R.id.text)
        mBtnJiami = findViewById(R.id.btn_jiami)
        mBtnJiami!!.setOnClickListener(this)
        mBtnJiemi = findViewById(R.id.btn_jiemi)
        mBtnJiemi!!.setOnClickListener(this)
        mEditJiemi = findViewById(R.id.edit_jiemi)
        mText2 = findViewById(R.id.text2)
    }

    override fun initData() {

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_jiami -> {
                val jiamiwen = mEditQuery!!.text.toString()
                val encrypt = DES().encrypt(jiamiwen)
                mText!!.text = " DES() = $encrypt\n"
                mEditJiemi!!.setText(encrypt)
            }
            R.id.btn_jiemi -> {
                val jiamiwen1 = mEditJiemi!!.text.toString()
                val decrypt = DES().decrypt(jiamiwen1)
                mText2!!.text = " DES() = $decrypt\n"
            }
            else -> {
            }
        }
    }

    companion object {

        /** 加密KEY  */
        private val KEY = "6;9Ku7;:84VG*B68".toByteArray()
    }
}
