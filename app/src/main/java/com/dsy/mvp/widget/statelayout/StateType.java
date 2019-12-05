package com.dsy.mvp.widget.statelayout;

import com.dsy.mvp.R;

public enum StateType {
    SUCCESS,
    LOADING,
    ERROR(R.string.state_load_fail,R.mipmap.state_load_fail,0),
    NO_NETWORK(R.string.state_net_error,R.mipmap.state_network_error,0),
    EMPTY(R.string.state_empty,R.mipmap.state_empty,0);

    public int txtId;//提示语
    public int imgId;//提示图片
    public int btnTxtId;//按钮文本

    StateType(int txtId, int imgId, int btnTxtId) {
        this.txtId = txtId;
        this.imgId = imgId;
        this.btnTxtId = btnTxtId;
    }

    StateType() {
    }

}