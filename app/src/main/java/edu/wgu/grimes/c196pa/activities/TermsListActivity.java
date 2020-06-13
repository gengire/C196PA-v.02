package edu.wgu.grimes.c196pa.activities;

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
import edu.wgu.grimes.c196pa.R;
import edu.wgu.grimes.c196pa.adapters.TermAdapter;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;
import edu.wgu.grimes.c196pa.utilities.Constants;
import edu.wgu.grimes.c196pa.viewmodels.TermsListViewModel;

public class TermsListActivity extends AbstractListActivity {

    @BindView(R.id.recycler_view_terms_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.fab_add_term)
    FloatingActionButton fabAddTerm;
    private TermsListViewModel mViewModel;
    private TermAdapter mAdapter;

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
                Toast.makeText(TermsListActivity.this, "All terms deleted", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.add_sample_terms:
                mViewModel.addSampleData();
                Toast.makeText(TermsListActivity.this, "Sample terms added", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.fab_add_term)
    void addTermClickHandler() {
        openActivity(TermEditorActivity.class);
    }

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

    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this, factory).get(TermsListViewModel.class);
        mViewModel.getAllTerms().observe(TermsListActivity.this, terms -> {
            mAdapter.submitList(terms);
        });
        mViewModel.getAllTermCus().observe(TermsListActivity.this, termCus -> {
            mAdapter.setTotalCus(termCus);
        });
    }


    @Override
    protected void handleSwipeDelete(RecyclerView.ViewHolder viewHolder) {
        TermEntity term = mAdapter.getTermAt(viewHolder.getAdapterPosition());
        String termTitle = term.getTitle();

        mViewModel.validateDeleteTerm(term,
                () -> { // success
                    mViewModel.deleteTerm(term);
                    String text = termTitle + " Deleted";
                    Toast.makeText(TermsListActivity.this, text, Toast.LENGTH_SHORT).show();
                }, () -> { // failure
                    mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    String text = termTitle + " can't be deleted because it has at least one course associated with it";
                    showValidationError("Can't delete", text);
                });
    }

    @Override
    protected void onSwipeCancel(RecyclerView.ViewHolder viewHolder) {
        mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
