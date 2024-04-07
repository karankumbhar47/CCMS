package com.example.androidapp;


import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

public class EditTextWithDrawableClick extends androidx.appcompat.widget.AppCompatEditText {
    private Drawable drawableEnd;
    private OnDrawableClickListener onDrawableClickListener;

    public EditTextWithDrawableClick(Context context) {
        super(context);
        init();
    }

    public EditTextWithDrawableClick(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithDrawableClick(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Drawable[] drawables = getCompoundDrawablesRelative();
        drawableEnd = drawables[2]; // Assuming the drawable is at the end

        setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (getRight() - drawableEnd.getBounds().width())) {
                    if (onDrawableClickListener != null) {
                        onDrawableClickListener.onClick(this);
                        return true;
                    }
                }
            }
            return false;
        });
    }

    public interface OnDrawableClickListener {
        void onClick(EditText editText);
    }

    public void setOnDrawableClickListener(OnDrawableClickListener listener) {
        this.onDrawableClickListener = listener;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        showHideDrawable();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        showHideDrawable();
    }

    private void showHideDrawable() {
        Drawable[] drawables = getCompoundDrawables();
        if (getText().length() > 0 && isFocused()) {
            drawables[0].setAlpha(255); // Fully visible
        } else {
        }
    }
}
