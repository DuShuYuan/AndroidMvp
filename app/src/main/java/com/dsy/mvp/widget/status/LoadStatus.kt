package com.dsy.mvp.widget.status

import com.dsy.mvp.R

enum class LoadStatus {
    SUCCESS,
    LOADING,
    ERROR(R.string.state_load_fail, R.mipmap.state_load_fail, R.string.retry),
    NO_NETWORK(R.string.state_net_error, R.mipmap.state_network_error, R.string.retry),
    EMPTY(R.string.state_empty, R.mipmap.state_empty, 0);

    var emptyTxt = 0
    var emptyImg = 0
    var emptyBtn = 0
    constructor()
    constructor(emptyTxt: Int, emptyImg: Int, emptyBtn: Int) {
        this.emptyTxt = emptyTxt
        this.emptyImg = emptyImg
        this.emptyBtn = emptyBtn
    }

}