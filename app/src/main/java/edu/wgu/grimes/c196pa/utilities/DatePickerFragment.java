//*********************************************************************************
//  File:             DatePickerFragment.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.utilities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Date inputDate;
    private TextView outputTextView;
    private HasDate outputDate;

    public DatePickerFragment() {
        // no arg
    }

    public DatePickerFragment(TextView outputTextView, Date inputDate) {
        this.inputDate = inputDate;
        this.outputTextView = outputTextView;
    }

    public DatePickerFragment(HasDate outputDate, Date inputDate) {
        this.outputDate = outputDate;
        this.inputDate = inputDate;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar cDate = Calendar.getInstance();
        if (inputDate != null) {
            cDate.setTime(inputDate);
        }
        int year = cDate.get(Calendar.YEAR);
        int month = cDate.get(Calendar.MONTH);
        int day = cDate.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if( outputTextView != null) {
            String strDate = StringUtils.getFormattedDate(month, dayOfMonth, year);
            outputTextView.setText(strDate);
        } else if (outputDate != null) {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, dayOfMonth);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            outputDate.setDate(cal.getTime());
        }
    }

}
