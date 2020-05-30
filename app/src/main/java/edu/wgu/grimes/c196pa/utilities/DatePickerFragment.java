package edu.wgu.grimes.c196pa.utilities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private final Date date;
    private final TextView textView;

    public DatePickerFragment(TextView textView, Date date) {
        this.date = date;
        this.textView = textView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar cDate = Calendar.getInstance();
        if (date != null) {
            cDate.setTime(date);
        }
        int year = cDate.get(Calendar.YEAR);
        int month = cDate.get(Calendar.MONTH);
        int day = cDate.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String strDate = StringUtils.getFormattedDate(month, dayOfMonth, year);
        textView.setText(strDate);
    }

}
