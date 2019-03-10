package com.example.radle.todo_calendar2.calendarView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.calendarView.dto.IdWithDataTime;
import com.example.radle.todo_calendar2.calendarView.tools.ParamsBuilder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarRowAdapter extends ArrayAdapter<IdWithDataTime> {
    private final List<IdWithDataTime> rowIdsWithFirstDateTimes;
    private final BoardListView.BoardParams boardParams;
    private final int layoutId;

    public CalendarRowAdapter(final Context context, final int layoutId,
                              final BoardListView.BoardParams params,
                              final List<IdWithDataTime> rowIdsWithFirstDateTimes) {
        super(context, layoutId, rowIdsWithFirstDateTimes);
        this.layoutId = layoutId;
        this.rowIdsWithFirstDateTimes = rowIdsWithFirstDateTimes;
        this.boardParams = params;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView,
                        @NonNull final ViewGroup parent) {
        final RecyclerView.ViewHolder viewHolder;
        if (convertView == null) {
            final LayoutInflater inflater =
                    (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(this.layoutId, parent, false);
            convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, this.boardParams.getRowHeight()));
            viewHolder = initRowView(position, convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RecyclerView.ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private RecyclerView.ViewHolder initRowView(final int position, final View convertView) {
        final ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.rowView.setParams(new ParamsBuilder().getRowParamsByBoardParams(this.boardParams, position,
                this.rowIdsWithFirstDateTimes));

        return viewHolder;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rowView)
        CalendarRowView rowView;

        ViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
