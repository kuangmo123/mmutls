package com.example.mylibrary;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SweetAlertDialogMulti extends SweetAlertDialog {
    public SweetAlertDialogMulti(Context context, int alertType) {
        super(context, alertType);

        this.setOnShowListener(dialog -> {
            SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
            TextView text = alertDialog.findViewById(R.id.content_text);
            text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            text.setLines(4);
        });
    }

    public SweetAlertDialogMulti(Context context) {
        super(context);
        this.setOnShowListener(dialog -> {
            SweetAlertDialog alertDialog = (SweetAlertDialog) dialog;
            TextView text = alertDialog.findViewById(R.id.content_text);
            text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            text.setLines(3);
        });
    }
}
