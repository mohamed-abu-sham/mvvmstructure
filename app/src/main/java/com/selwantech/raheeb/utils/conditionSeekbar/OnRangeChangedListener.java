package com.selwantech.raheeb.utils.conditionSeekbar;

import com.selwantech.raheeb.model.Condition;

public interface OnRangeChangedListener {
    void onRangeChanged(ConditionSeekBar view, float leftValue, float rightValue, boolean isFromUser);

    void onStartTrackingTouch(ConditionSeekBar view, boolean isLeft);

    void onStopTrackingTouch(ConditionSeekBar view, boolean isLeft, Condition condition);
}
