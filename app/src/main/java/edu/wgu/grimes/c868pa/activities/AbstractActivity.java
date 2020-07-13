//*********************************************************************************
//  File:             AbstractActivity.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import edu.wgu.grimes.c868pa.R;

/**
 * Handles a lot of the boiler plate code needed in most of the activities.  I added several hooks
 * as well to pull up as much of the logic as I could.
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public abstract class AbstractActivity extends AppCompatActivity {

    /**
     * Boolean flag to set the state of the activity to either true for new or false for editing
     */
    protected boolean mNew;

    /**
     * Contains the ID of the entity if there is one.
     */
    protected int mId;

    /**
     * Contains the ID of the parent entity if there is one.
     */
    protected int mParentId;

    /**
     * Base  view model provider factory to create ViewModels
     */
    protected ViewModelProvider.Factory factory;

    /**
     * Hook that runs each time the activity is created
     *
     * @param savedInstanceState Holds state from previous construction if populated
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(getCloseMenuItem());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initButterKnife();
        restoreState(savedInstanceState);
        initRecyclerView();

        factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());

        initViewModel();
    }

    /**
     * Hook that runs before the activity is closed if it is interrupted during an orientation
     * shift or put into the background.
     *
     * @param outState The state that will be passed when the activity is recreated.
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState(outState);
    }

    /**
     * Hook that inflates the menu, this adds items to the action bar if it is present.
     *
     * @param menu The menu to inflate
     * @return True if you want the menu to be displayed
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (getMenu() != 0) {
            inflater.inflate(getMenu(), menu);
        }
        return true;
    }

    /**
     * Hook to handle options menu prep.  I'm using this to set the delete menu icon
     * to invisible if this is a new entity vs and edit.
     *
     * @param menu The menu to interact with
     * @return True if you want the menu to be displayed
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem delete = menu.findItem(getDeleteMenuItem());
        if (delete != null) {
            delete.setVisible(!mNew);
        }
        return true;
    }

    /**
     * Hook to handle action bar item clicks.  The action bar will automatically handle clicks
     * on the Home/Up button, so long as you specify a parent activity in the AndroidManifest.xml
     *
     * @param item The item that was selected
     * @return True if you want the menu to be displayed
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == getSaveMenuItem()) {
            save();
            return true;
        } else if (itemId == getDeleteMenuItem()) {
            alertDelete(this::delete, this::onMenuDeleteCancel).show();
            return true;
        } else if (itemId == android.R.id.home) {
            closeActivity();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Alert Dialog to verify that the user intended to the entity.
     *
     * @param deleteRunner Strategy to run the delete if the user confirms.
     * @param cancelRunner Strategy to run if the user cancels.
     * @return The Delete Alert Dialog so that the caller can decide when to show it.
     */
    private AlertDialog alertDelete(Runnable deleteRunner, Runnable cancelRunner) {
        return new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete?")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("Delete", (dialog, which) -> {
                    deleteRunner.run();
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    cancelRunner.run();
                    dialog.dismiss();
                }).create();
    }

    /**
     * Default cancel strategy for the alertDelete method
     */
    protected void onMenuDeleteCancel() {
        // noop
    }

    /**
     * Swipe Delete initialization code. Abstracted here because both editor and list activities
     * currently have recycler views and need this init. It sets recycler view to detect the
     * swipe and calls the hooks so that the subclasses can handle the delete action
     */
    protected void initSwipeDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Runnable onCancel = () -> onSwipeDeleteCancel(viewHolder);
                alertDelete(() -> onSwipeDelete(viewHolder), onCancel).show();
            }
        }).attachToRecyclerView(getRecyclerView());
    }

    /**
     * Abstracted method to launch a new intent.  Probably don't need this here but I thought
     * I'd do it in case I wanted to do any special behavior to all the intent launches
     *
     * @param clazz Class to be attached to the intent
     */
    protected void openActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        openActivity(intent);
    }

    /**
     * I made this to make the call consistent.  Used with an intent that needed some special
     * configuration.
     *
     * @param intent Intent to launch
     */
    protected void openActivity(Intent intent) {
        startActivity(intent);
    }

    /**
     * Another call for consistency.  Used to close the current activity.
     */
    protected void closeActivity() {
        finish();
    }

    /**
     * Shows a validation error with a title and a message with an Okay button to close.
     * @param title Title of the validation error
     * @param message Message of the validation error
     */
    protected void showValidationError(String title, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(message).setTitle(title)
                .setCancelable(true)
                .setPositiveButton("Okay", (dialog, id) -> dialog.cancel());
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Hook to get subclass content view
     *
     * @return The Content View
     */
    protected abstract int getContentView();

    /**
     * Hook to init butter knife in the subclass
     */
    protected abstract void initButterKnife();

    /**
     * Hook to init the subclass recycler view
     */
    protected abstract void initRecyclerView();

    /**
     * Hook to init the subclass view model
     */
    protected abstract void initViewModel();

    /**
     * Hook to get the subclass menu
     *
     * @return The Menu
     */
    protected abstract int getMenu();

    /**
     * Hook to get the subclass Delete Menu Item
     *
     * @return Delete Menu Item
     */
    protected abstract int getDeleteMenuItem();

    /**
     * Hook to get the subclass Save Menu Item
     *
     * @return Save Menu Item
     */
    protected abstract int getSaveMenuItem();

    /**
     * Hook to get the subclass Close Menu Item
     *
     * @return Close Menu Item
     */
    protected abstract int getCloseMenuItem();

    /**
     * Hook to trigger the subclass to persist the entity to the database
     */
    protected abstract void save();

    /**
     * Hook to trigger the subclass to delete the entity from the database
     */
    protected abstract void delete();

    /**
     * Hook to get the subclass recycler view
     *
     * @return Recyclerview
     */
    protected abstract RecyclerView getRecyclerView();

    /**
     * Hook to trigger the subclass to handle the swipe delete action
     *
     * @param viewHolder RecyclerView.ViewHolder
     */
    protected abstract void onSwipeDelete(RecyclerView.ViewHolder viewHolder);

    /**
     * Hook to trigger the subclass to handle a canceled swipe delete
     *
     * @param viewHolder RecyclerView.ViewHolder
     */
    protected abstract void onSwipeDeleteCancel(RecyclerView.ViewHolder viewHolder);

    /**
     * Hook to restore the internal activity state from the savedInstanceState Bundle
     *
     * @param savedInstanceState Stores the internal activity state
     */
    protected abstract void restoreState(Bundle savedInstanceState);

    /**
     * Hook to save the internal activity state to the outState Bundle
     * @param outState Contains the internal activity state
     */
    protected abstract void saveState(Bundle outState);

    /**
     * Helper to show toast messages in subclasses
     *
     * @param toastMessage The message to show in a toast
     */
    protected void showToast(final String toastMessage) {
        showToast(toastMessage, Toast.LENGTH_SHORT);
    }

    /**
     * Helper to show toast messages in subclasses
     *
     * @param toastMessage The message to show in a toast
     * @param length The toast length, e.g. Toast.LENGTH_SHORT
     */
    protected void showToast(final String toastMessage, int length) {
        runOnUiThread(() -> Toast.makeText(this, toastMessage, length).show());
    }
}

