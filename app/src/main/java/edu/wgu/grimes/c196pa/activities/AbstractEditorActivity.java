package edu.wgu.grimes.c196pa.activities;

import androidx.recyclerview.widget.RecyclerView;

import edu.wgu.grimes.c196pa.R;

public abstract class AbstractEditorActivity extends AbstractActivity {

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


