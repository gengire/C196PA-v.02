package edu.wgu.grimes.c196pa;

import android.content.Intent;
import android.os.Bundle;
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
import edu.wgu.grimes.c196pa.utilities.Constants;
import edu.wgu.grimes.c196pa.viewmodels.TermsListViewModel;
import edu.wgu.grimes.c196pa.viewmodels.adapters.TermAdapter;

public class TermsListActivity extends AppCompatActivity {

    private TermsListViewModel mViewModel;
    private TermAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @BindView(R.id.fab_add_term)
    FloatingActionButton fabAddTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initRecyclerView();
        initViewModel();
        initSwipeDelete();

        ButterKnife.bind(this);
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
        RecyclerView mRecyclerView = findViewById(R.id.terms_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new TermAdapter();
        mAdapter.setOnItemClickListener(term -> {
            Intent intent = new Intent(TermsListActivity.this, TermEditorActivity.class);
            intent.putExtra(Constants.TERM_ID_KEY, term.getId());
            startActivity(intent);
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    private void initViewModel() {
        ViewModelProvider.Factory factory =
                new ViewModelProvider.AndroidViewModelFactory(getApplication());
        mViewModel = new ViewModelProvider(this, factory).get(TermsListViewModel.class);
        mViewModel.getAllTerms().observe(TermsListActivity.this, termEntities -> {
            mAdapter.setTerms(termEntities);
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
                mViewModel.delete(mAdapter.getTermAt(viewHolder.getAdapterPosition()));
                Toast.makeText(TermsListActivity.this, "Term Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(mRecyclerView);
    }

}
