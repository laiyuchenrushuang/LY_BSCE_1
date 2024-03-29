package com.seatrend.cd.electricbicyclesalesystem.common


import android.app.Dialog
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


import com.seatrend.cd.electricbicyclesalesystem.R

import butterknife.ButterKnife
import butterknife.Unbinder
import kotlinx.android.synthetic.main.fragment_dw_xxsj.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by seatrend on 2018/10/8.
 */

abstract class BaseFragment : Fragment(), BaseView {


    private var llNoData: LinearLayout? = null
    private var tvNoDataMsg: TextView? = null
    private var rootView: View? = null
    val ID_CARD_READ_CODE=10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (null != rootView) {
            var parent: ViewGroup? = container
            if (null != parent) {
                parent!!.removeView(parent)
            }
        }else{
            rootView = getLayoutView(inflater, container)
        }

        llNoData = rootView!!.findViewById(R.id.ll_no_data)
        tvNoDataMsg = rootView!!.findViewById(R.id.tv_msg)
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    abstract fun getLayoutView(inflater: LayoutInflater?, container: ViewGroup?): View
    abstract fun initView()

    override fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    }

    override fun showToast(msgId: Int) {
        Toast.makeText(context, getString(msgId), Toast.LENGTH_SHORT).show()
    }

    override fun showErrorDialog(msg: String) {
        try {
            val mDialog = Dialog(activity)
            mDialog.setContentView(R.layout.dialog_error)
            mDialog.setCanceledOnTouchOutside(true)
            val tvMsg = mDialog.findViewById<TextView>(R.id.tv_msg)
            val btnOk = mDialog.findViewById<Button>(R.id.btn_ok)
            tvMsg.text = msg
            btnOk.setOnClickListener { mDialog.dismiss() }
            mDialog.show()
        } catch (e: Exception) {

        }

    }

    override fun onDestroy() {
        super.onDestroy()

    }

    fun showNoDataView(msg: String) {
        llNoData!!.visibility = View.VISIBLE
        tvNoDataMsg!!.text = msg
    }

    fun hideNoDataView() {
        llNoData!!.visibility = View.GONE

    }
     fun showTimeDialog(view: TextView) {
        val dialog = Dialog(context)
        // MAlertDialog dialog=new MAlertDialog(this);
        dialog.setContentView(R.layout.dialog_date_picker)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel)
        val btnOk = dialog.findViewById<Button>(R.id.btn_ok)
        val mDatePicker = dialog.findViewById<DatePicker>(R.id.m_date_picker)

        btnCancel.setOnClickListener { dialog.dismiss() }

        btnOk.setOnClickListener {
            val year = mDatePicker.year
            val month = mDatePicker.month + 1
            val dayOfMonth = mDatePicker.dayOfMonth
            val time = year.toString() + "-" + month + "-" + dayOfMonth
            val dateToStamp = dateToStamp(time)
            if (dateToStamp - System.currentTimeMillis() > 0 ) {
                Toast.makeText(context, "日期不能超过当前日期", Toast.LENGTH_SHORT).show()
            } else {
                view.text = time
                dialog.dismiss()
            }
        }
    }
    open fun dateToStamp(s: String): Long {

        try {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
            val date = simpleDateFormat.parse(s)
            return date.time
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0
    }

    fun goNfcReadPlugin() {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.component = ComponentName("com.seatrend.cd.nfcread", "com.seatrend.cd.nfcread.IdCardReadActivity")
            startActivityForResult(intent, ID_CARD_READ_CODE)
        } catch (e: java.lang.Exception) {
            showToast("未找到NFC身份证读取插件，请先安装插件")

        }

    }

    protected fun showLog(s: String) {
        Log.d("lylog", s)
    }

    open fun getSYRXXCommitData(){
    }
}
