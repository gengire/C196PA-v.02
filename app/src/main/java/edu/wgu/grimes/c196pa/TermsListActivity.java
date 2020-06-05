package edu.wgu.grimes.c196pa;

import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.muddzdev.styleabletoast.StyleableToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;
import edu.wgu.grimes.c196pa.utilities.Constants;
import edu.wgu.grimes.c196pa.viewmodels.TermsListViewModel;
import edu.wgu.grimes.c196pa.viewmodels.adapters.TermAdapter;

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
                StyleableToast.makeText(TermsListActivity.this, "All terms deleted", R.style.toast_message).show();
                return true;
            case R.id.add_sample_terms:
                mViewModel.addSampleData();
                StyleableToast.makeText(TermsListActivity.this, "Sample terms added", R.style.toast_message).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.fab_add_term)
    void addTermClickHandler() {
        Intent intent = new Intent(TermsListActivity.this, TermEditorActivity.class);
        startActivity(intent);
    }

    protected void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new TermAdapter();

        mAdapter.setOnItemClickListener(term -> {
            Intent intent = new Intent(TermsListActivity.this, TermEditorActivity.class);
            intent.putExtra(Constants.TERM_ID_KEY, term.getId());
            startActivity(intent);
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
                    StyleableToast.makeText(TermsListActivity.this, text, R.style.toast_message).show();
                }, () -> { // failure
                    mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    String text = termTitle + " can't be deleted because it has courses associated with it";
                    StyleableToast.makeText(TermsListActivity.this, text, R.style.toast_validation_failure).show();
                });
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
