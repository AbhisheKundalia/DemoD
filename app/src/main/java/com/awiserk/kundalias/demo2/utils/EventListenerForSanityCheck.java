package com.awiserk.kundalias.demo2.utils;

/**
 * Created by akundalia on 5/4/2017.
 */

public class EventListenerForSanityCheck {
    private boolean[] boo;
    private ChangeListener listener;

    public EventListenerForSanityCheck(boolean[] boo) {
        this.boo = boo;
    }


    public void setBoo(boolean[] boo) {
        this.boo = boo;
        boolean isClear = areAllFalse(boo);
        if (listener != null &&  !isClear) listener.onError();
        else if (listener != null && isClear) listener.onNoError();
        //send is clean for on checkbox error
        listener.onCheckboxError(!boo[3]);

    }

    public ChangeListener getListener() {
        return listener;
    }

    public void setListener(ChangeListener listener) {
        this.listener = listener;
    }

    public interface ChangeListener {
        void onError();
        void onCheckboxError(boolean a);
        void onNoError();
    }


    public static boolean areAllFalse(boolean[] array) {
        for (boolean b : array) if(b) return false;
        return true;
    }
}