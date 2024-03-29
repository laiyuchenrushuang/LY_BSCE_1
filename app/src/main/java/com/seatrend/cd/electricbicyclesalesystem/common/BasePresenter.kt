package com.seatrend.cd.electricbicyclesalesystem.common

import com.seatrend.cd.electricbicyclesalesystem.entity.CommonResponse

/**
 * Created by seatrend on 2018/8/20.
 */

abstract class BasePresenter(var mView: BaseView) {
    abstract fun doNetworkTask(map: Map<String, String>, url: String)
    abstract fun requestResults(commonResponse: CommonResponse, isSuccess: Boolean)
}
