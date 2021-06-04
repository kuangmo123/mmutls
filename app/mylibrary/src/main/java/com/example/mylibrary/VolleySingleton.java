package com.example.mylibrary;

import android.annotation.SuppressLint;
import android.content.Context;



public class VolleySingleton {
    Context context;

    public VolleySingleton(Context context) {
        this.context = context;
    }

    @SuppressLint("StaticFieldLeak")
    static VolleySingleton INSTANCE;

    public static synchronized VolleySingleton getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new VolleySingleton(context);
        }
        return INSTANCE;
    }

    RequestQueue requestQueue;

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
}
