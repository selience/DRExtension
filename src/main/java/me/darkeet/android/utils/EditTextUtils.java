
package me.darkeet.android.utils;

import static android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE;
import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * Utilities for working with an {@link EditText}
 */
public class EditTextUtils {

    /**
     * Bind given runnable to be invoked when the
     * 
     * @param editText
     * @param runnable
     * @return edit text
     */
    public static EditText onDone(final EditText editText, final Runnable runnable) {
        if ((editText.getInputType() & TYPE_TEXT_FLAG_MULTI_LINE) == 0)
            editText.setOnKeyListener(new OnKeyListener() {

                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode != KEYCODE_ENTER)
                        return false;
                    if (event == null)
                        return false;
                    if (event.getAction() != ACTION_DOWN)
                        return false;

                    runnable.run();
                    return true;
                }
            });

        editText.setOnEditorActionListener(new OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == IME_ACTION_DONE) {
                    runnable.run();
                    return true;
                }
                return false;
            }
        });

        return editText;
    }
    
    /**
     * Bind View focus changed
     * @param editText
     * @return
     */
    public static EditText onFocusChanged(final EditText editText) {
        final Context context=editText.getContext();
        final InputMethodManager imm=(InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    imm.showSoftInput(v, 0);
                } else {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
        return editText;
    }
}
