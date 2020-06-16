//*********************************************************************************
//  File:             AbstractEditorActivity.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.activities;

import androidx.recyclerview.widget.RecyclerView;

import edu.wgu.grimes.c196pa.R;

/**
 * Contains some empty implementations to keep the subclass activities as clean as possible
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public abstract class AbstractEditorActivity extends AbstractActivity {

    /**
     * Editor activities use a close icon.
     * @return Close Icon ( X )
     */
    @Override
    protected int getCloseMenuItem() {
        return R.drawable.ic_close;
    }

    @Override
    protected void initRecyclerView() {
        // noop
    }

    @Override
    protected void handleSwipeDelete(RecyclerView.ViewHolder viewHolder) {
        // noop
    }

    @Override
    protected void onSwipeCancel(RecyclerView.ViewHolder viewHolder) {
        // noop
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return null; // noop
    }

}


