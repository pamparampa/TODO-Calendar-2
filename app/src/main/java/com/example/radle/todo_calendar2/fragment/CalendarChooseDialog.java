package com.example.radle.todo_calendar2.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CalendarChooseDialog extends DialogFragment implements
        DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnClickListener {
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose calendars you want to use");
        builder.setMultiChoiceItems(new String[]{"1", "2", "3"},
                new boolean[]{false, false, false}, this);
        builder.setPositiveButton("Ok", this);
        return builder.create();
    }

    @Override
    public void onClick(final DialogInterface dialogInterface, final int i, final boolean b) {

    }

    @Override
    public void onClick(final DialogInterface dialogInterface, final int i) {

    }
}
