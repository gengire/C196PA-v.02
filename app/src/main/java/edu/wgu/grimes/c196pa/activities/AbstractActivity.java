//*********************************************************************************
//  File:             AbstractActivity.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import edu.wgu.grimes.c196pa.R;

/**
 *
 */
public abstract class AbstractActivity extends AppCompatActivity {

    protected boolean mNew;
    protected int mId;
    protected int mParentId;
    protected ViewModelProvider.Factory factory;

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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        if (getMenu() != 0) {
            inflater.inflate(getMenu(), menu);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem delete = menu.findItem(getDeleteMenuItem());
        if (delete != null) {
            delete.setVisible(!mNew);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int itemId = item.getItemId();
        if (itemId == getSaveMenuItem()) {
            save();
            return true;
        } else if (itemId == getDeleteMenuItem()) {
            alertDelete(this::delete, this::onCancel).show();
            return true;
        } else if (itemId == android.R.id.home) {
            closeActivity();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

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

    private void onCancel() {
        // noop
    }

    public void initSwipeDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Runnable onCancel = () -> onSwipeCancel(viewHolder);
                alertDelete(() -> handleSwipeDelete(viewHolder), onCancel).show();
            }
        }).attachToRecyclerView(getRecyclerView());
    }

    protected void openActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        openActivity(intent);
    }

    protected void openActivity(Intent intent) {
        startActivity(intent);
    }

    protected void closeActivity() {
        finish();
    }

    protected void showValidationError(String title, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(message).setTitle(title)
                .setCancelable(true)
                .setPositiveButton("Okay", (dialog, id) -> dialog.cancel());
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    protected abstract int getContentView();

    protected abstract void initButterKnife();

    protected abstract void initRecyclerView();

    protected abstract void initViewModel();

    protected abstract int getMenu();

    protected abstract int getDeleteMenuItem();

    protected abstract int getSaveMenuItem();

    protected abstract int getCloseMenuItem();

    protected abstract void save();

    protected abstract void delete();

    protected abstract RecyclerView getRecyclerView();

    protected abstract void handleSwipeDelete(RecyclerView.ViewHolder viewHolder);

    protected abstract void onSwipeCancel(RecyclerView.ViewHolder viewHolder);

    protected abstract void restoreState(Bundle savedInstanceState);

    protected abstract void saveState(Bundle outState);

}

