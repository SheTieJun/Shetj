package me.shetj.constraintlayout.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import me.shetj.constraintlayout.R

/**
 * A simple [Fragment] subclass.
 */
class BlankFragment : Fragment() {

    companion object {
        fun newInstance() = BlankFragment()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank_2, container, false)
    }

}
