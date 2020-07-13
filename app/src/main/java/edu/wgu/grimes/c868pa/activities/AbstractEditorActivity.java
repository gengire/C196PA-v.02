//*********************************************************************************
//  File:             AbstractEditorActivity.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.activities;

import androidx.recyclerview.widget.RecyclerView;

import edu.wgu.grimes.c868pa.R;

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

    /**
     * Generally not used by editor activities
     */
    @Override
    protected void initRecyclerView() {
        // noop
    }

    /**
     * Generally not used by editor activities
     */
    @Override
    protected void onSwipeDelete(RecyclerView.ViewHolder viewHolder) {
        // noop
    }

    /**
     * Generally not used by editor activities
     */
    @Override
    protected void onSwipeDeleteCancel(RecyclerView.ViewHolder viewHolder) {
        // noop
    }

    /**
     * Generally not used by editor activities
     */
    @Override
    protected RecyclerView getRecyclerView() {
        return null; // noop
    }

}


