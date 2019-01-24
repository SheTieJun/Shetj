package com.shetj.diyalbume.ohter.view


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog
import com.shetj.diyalbume.R
import com.shetj.diyalbume.ohter.presenter.OtherPresenter
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_tip_dialog.*
import me.shetj.base.qmui.BaseQMUIFragment
import me.shetj.base.qmui.QMUIFragment
import timber.log.Timber
import java.util.concurrent.TimeUnit

class TipDialogFragment : BaseQMUIFragment<OtherPresenter>() {


    override fun onCreateView(): View {
        return  LayoutInflater.from(context).inflate(R.layout.fragment_tip_dialog, null)
    }

    override fun initEventAndData() {
        Timber.i("initEventAndData")
        val intent = Intent()
        intent.putExtra("BaseQMUIFragment", "TipDialogFragment")
        setFragmentResult(QMUIFragment.RESULT_OK, intent)

        RxView.clicks(bt_loading)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
           var tipDialog = QMUITipDialog.Builder(context)
                    .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                    .setTipWord("正在加载")
                    .create()
            tipDialog.show()
            Flowable.just(tipDialog)
                    .delay(1500,TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe( { it ->
                        it.dismiss()
                    },{ it1 -> Timber.i(it1.message)})
        }

    }

    override fun lazyLoadData() {
        Timber.i("lazyLoadData")
    }


}
