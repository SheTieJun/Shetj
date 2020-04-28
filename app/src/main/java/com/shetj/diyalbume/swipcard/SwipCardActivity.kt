package com.shetj.diyalbume.swipcard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.PagerSnapHelper
import com.shetj.diyalbume.R
import kotlinx.android.synthetic.main.activity_swip_card.*

class SwipCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swip_card)

        val datas = ArrayList<String>()
        for(i in 1..19){
            datas.add("$i")
        }

        val mAdapter = ImageAdapter(datas)
        iRecyclerView.adapter = mAdapter
        iRecyclerView.layoutManager = TierLayoutManager()
        btn_remove?.setOnClickListener {
            val removeAt = mAdapter.data.removeAt(0)
            mAdapter.addData(removeAt)
            mAdapter.notifyDataSetChanged()
        }
        PagerSnapHelper().attachToRecyclerView(iRecyclerView)
    }
}
