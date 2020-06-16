//*********************************************************************************
//  File:             AbstractListActivity.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.activities;

import android.os.Bundle;

import edu.wgu.grimes.c196pa.R;

/**
 * Contains some empty implementations to keep the subclass activities as clean as possible
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public abstract class AbstractListActivity extends AbstractActivity {

    /**
     * List activities use a back icon.
     * @return Back icon ( <- )
     */
    @Override
    protected int getCloseMenuItem() {
        return R.drawable.ic_arrow_back;
    }

    @Override
    protected int getMenu() {
        return 0; // noop
    }

    @Override
    protected int getDeleteMenuItem() {
        return 0; // noop
    }

    @Override
    protected int getSaveMenuItem() {
        return 0; // noop
    }

    @Override
    protected void save() {
        // noop
    }

    @Override
    protected void delete() {
        // noop
    }

    @Override
    protected void saveState(Bundle outState) {
        // noop
    }

    @Override
    protected void restoreState(Bundle savedStateInstance) {
        // noop
    }

}
