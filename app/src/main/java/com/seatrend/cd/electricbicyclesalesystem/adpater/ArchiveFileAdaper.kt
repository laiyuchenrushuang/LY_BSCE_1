package com.seatrend.cd.electricbicyclesalesystem.adpater

import android.content.ClipData
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.seatrend.cd.electricbicyclesalesystem.R



class ArchiveFileAdaper(private var mContext: Context? = null, private var mData: ArrayList<HashMap<String, String>>? = null) : RecyclerView.Adapter<ArchiveFileAdaper.MyHolder>(){


    private var svListener: ArchiveFileAdaper.SearchViewState ? = null
    private var cb_check = false
    private var positionIsCheckZCBMList =  ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolder {
        var view: View = LayoutInflater.from(mContext).inflate(R.layout.activity_archive_recycle__item, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    override fun onBindViewHolder(holder: MyHolder?, position: Int) {
        holder!!.setItemView(position)
    }

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {

        var zcbm: TextView? = null
        var cphm: TextView? = null
        var shzt: TextView? = null
        var item_cb: CheckBox? = null

        init {
            zcbm = view.findViewById(R.id.zcbm)
            cphm = view.findViewById(R.id.cphm)
            shzt = view.findViewById(R.id.shzt)
            item_cb = view.findViewById(R.id.item_cb)
            initEvent()
        }

        private fun initEvent() {
            item_cb!!.setOnCheckedChangeListener(object :CompoundButton.OnCheckedChangeListener{
                override fun onCheckedChanged(p0: CompoundButton?, check: Boolean) {
                   if (check){
                       positionIsCheckZCBMList.add(mData!!.get(adapterPosition)["zcbm"].toString())
                       setListSortAndRepeat(positionIsCheckZCBMList)
                   }else{
                       positionIsCheckZCBMList.remove(mData!!.get(adapterPosition)["zcbm"].toString())
                   }

                }

            })
        }

        fun setItemView(position: Int) {
            var zcbms: String? = mData!!.get(position)["zcbm"]
            var cphms: String? = mData!!.get(position)["clhm"]
            var shzts: String? = mData!!.get(position)["shzt"]
            if (null != shzts && shzts.equals("1"))//1代表成功
            {
                shzts = "通过"
                shzt!!.setTextColor(Color.GREEN)
                shzt!!.setText(shzts)
            }else{
                shzts = "不通过"
                shzt!!.setTextColor(Color.RED)
                shzt!!.setText(shzts)
            }

            zcbm!!.setText(zcbms)
            cphm!!.setText(cphms)

            //全选的时候取消和添加
            if (!cb_check) {
                item_cb!!.isChecked = false
            } else {
                item_cb!!.isChecked = true
            }
        }
    }

    /**
     * 是否全选 check = true 全选
     */
    fun setAllselectListener(check: Boolean) {
        this.cb_check = check
        notifyDataSetChanged()
        //被点击，position重置或者全选
        if(check){
            for(index in 0 until mData!!.size){
                positionIsCheckZCBMList.add(mData!!.get(index)["zcbm"].toString())
            }
            setListSortAndRepeat(positionIsCheckZCBMList)
        }else{
            positionIsCheckZCBMList.clear()
        }
    }

    /**
     * 去重和排序，已选中的CheckBox
     */

    private fun setListSortAndRepeat(positionList: ArrayList<String>) {
        val set = HashSet(positionList)
        positionList.clear()
        positionList.addAll(set)
        positionList.sort()
    }

    /**
     * 获取被勾选的位置,获取的整车编码
     */
    fun getPositionList(): ArrayList<String> {
        return  positionIsCheckZCBMList
    }

    /**
     * 注册监听
     */
    fun registerSetSearchViewListener(listener:SearchViewState){
        this.svListener = listener
    }

    interface SearchViewState{
        fun searchViewChangeListener(change:Boolean)
    }
}
