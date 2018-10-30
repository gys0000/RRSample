package com.example.rr.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

public class LoadingDialogFragment extends DialogFragment {
    private static final String ARG_CONTENT = "content";

    public static LoadingDialogFragment newInstance(String content) {
        Bundle bundle = new Bundle();
        if (content != null) {
            bundle.putString(ARG_CONTENT, content);
        }
        LoadingDialogFragment fragment = new LoadingDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LoadingDialog dialog;
        Bundle args = getArguments();
        Context context = getContext();
        if (args.containsKey(ARG_CONTENT)) {
            String content = args.getString(ARG_CONTENT);
            dialog = new LoadingDialog(context, content);
        } else {
            dialog = new LoadingDialog(context);
        }
        return dialog;
    }
}
