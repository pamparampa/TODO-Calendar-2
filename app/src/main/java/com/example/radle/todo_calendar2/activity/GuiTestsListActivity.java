package com.example.radle.todo_calendar2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.radle.todo_calendar2.R;

import java.util.Arrays;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class GuiTestsListActivity extends Activity {

    @BindView(R.id.guiTestsList)
    ListView guiTestsList;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tests_list_activity);
        ButterKnife.bind(this);

        final String[] guiTests = {"CalendarRowView"};
        this.guiTestsList.setAdapter(new ArrayAdapter<>(this, R.layout.text_list_row,
                Arrays.asList(guiTests)));
    }

    @OnItemClick(R.id.guiTestsList)
    public void onItemClick(final long id) {
        final Intent intent = new Intent(getApplicationContext(), CalendarRowViewActivity.class);
        startActivity(intent);
    }
}