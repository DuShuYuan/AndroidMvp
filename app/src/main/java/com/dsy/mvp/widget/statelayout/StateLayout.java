package com.dsy.mvp.widget.statelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dsy.mvp.R;

/**
 * 状态布局layout
 */
public class StateLayout extends FrameLayout implements View.OnClickListener {

    private Context mContext;
    private StateType mState = StateType.SUCCESS;

    private View contentView;
    private View stateView;

    private ImageView stateImg;
    private TextView stateBtn;
    private TextView stateText;
    private ProgressBar progressBar;

    private OnReloadListener onReloadListener;

    public StateLayout(Context context) {
        this(context, null);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            throw new IllegalStateException(getClass().getSimpleName() + " can host only one direct child");
        }
        build();
    }

    private void build() {
        if (getChildCount() > 0) {
            contentView = getChildAt(0);
        }
        stateView = LayoutInflater.from(mContext).inflate(R.layout.state_view, null);

        stateImg = stateView.findViewById(R.id.state_img);
        stateText = stateView.findViewById(R.id.state_text);
        stateBtn = stateView.findViewById(R.id.state_btn);
        progressBar = stateView.findViewById(R.id.state_loading);
        stateBtn.setOnClickListener(this);

        stateView.setVisibility(GONE);

        addView(stateView);
    }

    @Override
    public void onClick(View view) {

        int i = view.getId();
        if (i == R.id.layout_state || i == R.id.state_btn) {

            if (onReloadListener != null) {
                onReloadListener.onReload(view, mState);
            }
        }
    }

    public void setState(StateType state) {
        this.mState = state;
        switch (state) {
            case SUCCESS:
                if (contentView != null) {
                    contentView.setVisibility(VISIBLE);
                }
                stateView.setVisibility(GONE);
                break;
            case LOADING:
                if (contentView != null) {
                    contentView.setVisibility(GONE);
                }
                stateView.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                stateImg.setVisibility(GONE);
                stateText.setVisibility(GONE);
                stateBtn.setVisibility(GONE);
                break;
            default:
                if (contentView != null) {
                    contentView.setVisibility(GONE);
                }
                stateView.setVisibility(VISIBLE);
                progressBar.setVisibility(GONE);

                if (state.imgId == 0) {
                    stateImg.setVisibility(GONE);
                } else {
                    stateImg.setVisibility(VISIBLE);
                    stateImg.setImageResource(state.imgId);
                }
                if (state.txtId == 0) {
                    stateText.setVisibility(GONE);
                } else {
                    stateText.setVisibility(VISIBLE);
                    stateText.setText(state.txtId);
                }
                if (state.btnTxtId != 0) {
                    stateBtn.setText(state.btnTxtId);
                    stateBtn.setVisibility(VISIBLE);
                } else {
                    stateBtn.setVisibility(GONE);
                }
        }
    }

    public void setOnReloadListener(OnReloadListener onReloadListener) {
        this.onReloadListener = onReloadListener;
    }

    public interface OnReloadListener {
        void onReload(View v, StateType status);
    }
}
