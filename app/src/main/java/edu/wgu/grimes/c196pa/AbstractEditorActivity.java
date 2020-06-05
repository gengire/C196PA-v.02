package edu.wgu.grimes.c196pa;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public abstract class AbstractEditorActivity extends AppCompatActivity {

    protected boolean mNew;
    protected boolean mEditing;

    protected int mId;
    protected int mParentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initButterKnife();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initRecyclerView();
        initViewModel();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(getMenu(), menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem delete = menu.findItem(getDeleteMenuItem());
        delete.setVisible(!mNew);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == getSaveMenuItem()) {
            save();
            return true;
        } else if (itemId == getDeleteMenuItem()) {
            delete();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    protected abstract int getContentView();

    protected abstract void initButterKnife();

    protected abstract void initRecyclerView();

    protected abstract void initViewModel();

    protected abstract int getMenu();

    protected abstract int getDeleteMenuItem();

    protected abstract int getSaveMenuItem();

    protected abstract void save();

    protected abstract void delete();
}

