package com.selwantech.raheeb.seekbar;

import com.selwantech.raheeb.model.Distance;

public interface OnRangeChangedListener {
    void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser);

    void onStartTrackingTouch(RangeSeekBar view, boolean isLeft);

    void onStopTrackingTouch(RangeSeekBar view, boolean isLeft, Distance distance);
}
