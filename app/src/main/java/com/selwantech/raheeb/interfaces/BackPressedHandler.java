package com.selwantech.raheeb.interfaces;

import androidx.activity.OnBackPressedCallback;

public class BackPressedHandler extends OnBackPressedCallback {

    int position;
    BackPressed backPressed;

    /**
     * Create a {@link OnBackPressedCallback}.
     *
     * @param enabled The default enabled state for this callback.
     * @see #setEnabled(boolean)
     */
    public BackPressedHandler(boolean enabled, int position, BackPressed backPressed) {
        super(enabled);
        this.position = position;
        this.backPressed = backPressed;
    }

    @Override
    public void handleOnBackPressed() {
        backPressed.onBackPressed(position);
        setEnabled(false);
    }
}
