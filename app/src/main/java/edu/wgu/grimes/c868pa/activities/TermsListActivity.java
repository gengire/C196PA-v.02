//*********************************************************************************
//  File:             TermsListActivity.java
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
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c868pa.R;
import edu.wgu.grimes.c868pa.adapters.TermAdapter;
import edu.wgu.grimes.c868pa.database.entities.TermEntity;
import edu.wgu.grimes.c868pa.utilities.Constants;
import edu.wgu.grimes.c868pa.viewmodels.TermsListViewModel;

/**
 * Terms List Activity, responsible for controlling the terms list
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class TermsListActivity extends AbstractListActivity {

    /**
     * Local View Model for the terms list
     */
    private TermsListViewModel mViewModel;
    /**
     * Adapter for the terms in the recycler view
     */
    private TermAdapter mAdapter;

    @BindView(R.id.recycler_view_terms_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.fab_add_term)
    FloatingActionButton fabAddTerm;

    @Override
    protected int getContentView() {
        return R.layout.activity_terms_list;
    }

    @Override
    protected void initButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    protected int getMenu() {
        return R.menu.menu_term_list;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_terms:
                mViewModel.deleteAll();
                showToast("All terms deleted");
                return true;
            case R.id.add_sample_terms:
                mViewModel.addSampleData();
                showToast("Sample terms added");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new TermAdapter();

        mAdapter.setOnItemClickListener(term -> {
            Intent intent = new Intent(TermsListActivity.this, TermEditorActivity.class);
            intent.putExtra(Constants.TERM_ID_KEY, term.getId());
            openActivity(intent);
        });
        mRecyclerView.setAdapter(mAdapter);
        initSwipeDelete();
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this, factory).get(TermsListViewModel.class);
        mViewModel.getAllTerms().observe(TermsListActivity.this, terms -> mAdapter.submitList(terms));
        mViewModel.getAllTermCus().observe(TermsListActivity.this, termCus -> mAdapter.setTotalCus(termCus));
    }

    @Override
    protected void onSwipeDelete(RecyclerView.ViewHolder viewHolder) {
        TermEntity term = mAdapter.getTermAt(viewHolder.getAdapterPosition());
        String termTitle = term.getTitle();

        mViewModel.validateDeleteTerm(term,
                () -> { // success
                    mViewModel.deleteTerm(term);
                    String toastMessage = termTitle + " Deleted";
                    showToast(toastMessage);
                }, () -> { // failure
                    mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    String text = termTitle + " can't be deleted because it has at least one course associated with it";
                    showValidationError("Can't delete", text);
                });
    }

    @Override
    protected void onSwipeDeleteCancel(RecyclerView.ViewHolder viewHolder) {
        mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @OnClick(R.id.fab_add_term)
    void addTermClickHandler() {
        openActivity(TermEditorActivity.class);
    }
}
