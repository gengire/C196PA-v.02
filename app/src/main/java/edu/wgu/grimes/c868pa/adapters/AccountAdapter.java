//*********************************************************************************
//  File:             AccountAdapter.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

import edu.wgu.grimes.c868pa.R;
import edu.wgu.grimes.c868pa.database.entities.AccountEntity;

import static edu.wgu.grimes.c868pa.utilities.DateUtils.sameDate;
import static edu.wgu.grimes.c868pa.utilities.StringUtils.getFormattedDate;

/**
 * Account Adapter, Used to bind the AccountEntity to the RecyclerView
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class AccountAdapter extends ListAdapter<AccountEntity, AccountAdapter.ViewHolder> {

    /**
     * Used to more optimally handle how the recycler view handles changes to the items in it
     */
    private static final DiffUtil.ItemCallback<AccountEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<AccountEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull AccountEntity oldItem, @NonNull AccountEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull AccountEntity oldItem, @NonNull AccountEntity newItem) {
            boolean sameUsername = oldItem.getUsername().equals(newItem.getUsername());
            boolean samePassword = oldItem.getPassword().equals(newItem.getPassword());
            boolean sameLastLoginDate = sameDate(oldItem.getLastLogin(), newItem.getLastLogin());
            return sameUsername && samePassword && sameLastLoginDate;
        }
    };

    private OnItemClickListener<AccountEntity> listener;

    public AccountAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_account, parent, false);
        return new AccountAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AccountEntity currentAccount = getItem(position);
        holder.textViewUsername.setText(currentAccount.getUsername());
        Date llDate = currentAccount.getLastLogin();
        String lastLoginDate = llDate == null ? "????" : getFormattedDate(llDate);
        holder.textViewLastLoginDate.setText(lastLoginDate);
    }

    public AccountEntity getAccountAt(int position) {
        return getItem(position);
    }

    public void setOnItemClickListener(OnItemClickListener<AccountEntity> listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewUsername;
        private TextView textViewLastLoginDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.text_view_username);
            textViewLastLoginDate = itemView.findViewById(R.id.text_view_last_login_date);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }
}
