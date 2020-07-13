//*********************************************************************************
//  File:             AbstractListActivity.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.activities;

import android.os.Bundle;

import edu.wgu.grimes.c868pa.R;

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

    /**
     * Generally not used by list activities
     */
    @Override
    protected int getMenu() {
        return 0; // noop
    }

    /**
     * Generally not used by list activities
     */
    @Override
    protected int getDeleteMenuItem() {
        return 0; // noop
    }

    /**
     * Generally not used by list activities
     */
    @Override
    protected int getSaveMenuItem() {
        return 0; // noop
    }

    /**
     * Generally not used by list activities
     */
    @Override
    protected void save() {
        // noop
    }

    /**
     * Generally not used by list activities
     */
    @Override
    protected void delete() {
        // noop
    }

    /**
     * Generally not used by list activities
     */
    @Override
    protected void saveState(Bundle outState) {
        // noop
    }

    /**
     * Generally not used by list activities
     */
    @Override
    protected void restoreState(Bundle savedStateInstance) {
        // noop
    }

}
