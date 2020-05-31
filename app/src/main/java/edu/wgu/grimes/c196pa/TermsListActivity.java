package edu.wgu.grimes.c196pa;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;
import edu.wgu.grimes.c196pa.utilities.Constants;
import edu.wgu.grimes.c196pa.viewmodels.TermsListViewModel;
import edu.wgu.grimes.c196pa.viewmodels.adapters.TermAdapter;

public class TermsListActivity extends AppCompatActivity {

    private TermsListViewModel mViewModel;
    private TermAdapter mAdapter;

    @BindView(R.id.recycler_view_terms_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.fab_add_term)
    FloatingActionButton fabAddTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        initRecyclerView();
        initViewModel();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_term_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_terms:
                mViewModel.deleteAll();
                Toast.makeText(this, "All terms deleted", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.add_sample_terms:
                mViewModel.addSampleData();
                Toast.makeText(this, "Sample terms added", Toast.LENGTH_SHORT).show();
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

    private void initRecyclerView() {
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

    private void initViewModel() {
        ViewModelProvider.Factory factory =
                new ViewModelProvider.AndroidViewModelFactory(getApplication());
        mViewModel = new ViewModelProvider(this, factory).get(TermsListViewModel.class);
        mViewModel.getAllTerms().observe(TermsListActivity.this, terms -> {
            mAdapter.setTerms(terms);
        });
    }


    private void initSwipeDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                TermEntity term = mAdapter.getTermAt(viewHolder.getAdapterPosition());
                String termTitle = term.getTitle();

                mViewModel.validateDelete(term,
                    () -> { // success
                        mViewModel.delete(term);
                        Toast.makeText(TermsListActivity.this, termTitle + " Deleted", Toast.LENGTH_SHORT).show();
                    }, () -> { // failure
                        mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                        Toast toast = Toast.makeText(TermsListActivity.this, termTitle + " can't be deleted because it has courses associated with it", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    });
            }
        }).attachToRecyclerView(mRecyclerView);
    }

}
