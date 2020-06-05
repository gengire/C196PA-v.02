package edu.wgu.grimes.c196pa;

import androidx.recyclerview.widget.RecyclerView;

public abstract class AbstractListActivity extends AbstractActivity {

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

}
