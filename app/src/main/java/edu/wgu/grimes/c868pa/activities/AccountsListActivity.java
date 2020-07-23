//*********************************************************************************
//  File:             AccountsListActivity.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.activities;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c868pa.R;
import edu.wgu.grimes.c868pa.adapters.AccountAdapter;
import edu.wgu.grimes.c868pa.adapters.TermAdapter;
import edu.wgu.grimes.c868pa.database.entities.AccountEntity;
import edu.wgu.grimes.c868pa.utilities.Constants;
import edu.wgu.grimes.c868pa.viewmodels.AccountsListViewModel;
import edu.wgu.grimes.c868pa.viewmodels.TermsListViewModel;

/**
 * Accounts List Activity, responsible for managing the accounts list
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class AccountsListActivity extends AbstractListActivity {

    /**
     * Local View Model for the terms list
     */
    private AccountsListViewModel mViewModel;

    /**
     * Adapter for the terms in the recycler view
     */
    private AccountAdapter mAdapter;

    @BindView(R.id.recycler_view_accounts_list)
    protected RecyclerView mRecyclerView;

    @BindView(R.id.fab_add_account)
    protected FloatingActionButton fabAddAccount;

    @Override
    protected int getContentView() {
        return R.layout.activity_accounts_list;
    }

    @Override
    protected void initButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    protected int getMenu() {
        return R.menu.menu_account_list;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.delete_:
//                mViewModel.deleteAll();
//                showToast("All terms deleted");
//                return true;
//            case R.id.add_sample_terms:
//                mViewModel.addSampleData();
//                showToast("Sample terms added");
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new AccountAdapter();

        mAdapter.setOnItemClickListener(account -> {
            Intent intent = new Intent(AccountsListActivity.this, AccountEditorActivity.class);
            intent.putExtra(Constants.ACCOUNT_ID_KEY, account.getId());
            openActivity(intent);
        });
        mRecyclerView.setAdapter(mAdapter);
        initSwipeDelete();
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this, factory).get(AccountsListViewModel.class);
        mViewModel.getAllAccounts().observe(AccountsListActivity.this, terms -> mAdapter.submitList(terms));
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    protected void onSwipeDelete(RecyclerView.ViewHolder viewHolder) {
        AccountEntity account = mAdapter.getAccountAt(viewHolder.getAdapterPosition());
        String username = account.getUsername();

        mViewModel.validateDeleteAccount(account,
                () -> { // success
                    mViewModel.deleteAccount(account);
                    String toastMessage = username + " Deleted";
                    showToast(toastMessage);
                }, () -> { // failure
                    mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    String text = username + " cant' be deleted at this time.";
                    showValidationError("Can't delete", text);
                });
    }

    @Override
    protected void onSwipeDeleteCancel(RecyclerView.ViewHolder viewHolder) {
        mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
    }

    @OnClick(R.id.fab_add_account)
    void addAccountClickHandler() {
        openActivity(AccountEditorActivity.class);
    }
}