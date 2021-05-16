package com.example.radle.todo_calendar2.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.radle.todo_calendar2.dto.Calendar;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CalendarChooseDialog extends DialogFragment implements
        DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnClickListener {
    private final List<Calendar> calendars;
    private Listener listener;
    private final List<String> chosenCalendars = new LinkedList<>();

    public CalendarChooseDialog(List<Calendar> calendars) {
        this.calendars = calendars;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Listener) context;
    }

    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose calendars you want to use");
        builder.setMultiChoiceItems(getCalendarTitles(),
                getFalsesArray(), this);
        builder.setPositiveButton("Ok", this);
        return builder.create();
    }

    @Override
    public void onClick(final DialogInterface dialogInterface, final int which, final boolean isChecked) {
        if (isChecked)
            chosenCalendars.add(calendars.get(which).getCalendarId());
        else
            chosenCalendars.remove(calendars.get(which).getCalendarId());
    }

    @Override
    public void onClick(final DialogInterface dialogInterface, final int i) {
        this.listener.onChosenCalendarsClick(this.chosenCalendars);
    }

    private boolean[] getFalsesArray() {
        boolean[] falses = new boolean[calendars.size()];
        Arrays.fill(falses, false);
        return falses;
    }

    private CharSequence[] getCalendarTitles() {
        return (CharSequence[]) calendars.stream()
                .map(calendar -> (CharSequence) calendar.getTitle()).toArray(CharSequence[]::new);
    }

    public interface Listener {
        void onChosenCalendarsClick(List<String> chosenCalendars);
    }
}
