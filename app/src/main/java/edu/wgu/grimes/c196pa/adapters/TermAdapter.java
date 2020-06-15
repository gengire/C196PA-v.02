//*********************************************************************************
//  File:             TermAdapter.java
//*********************************************************************************
//  Course:           Mobile Applications Development - C196
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c196pa.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.wgu.grimes.c196pa.R;
import edu.wgu.grimes.c196pa.database.entities.TermCusTuple;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;

import static edu.wgu.grimes.c196pa.utilities.DateUtils.sameDate;
import static edu.wgu.grimes.c196pa.utilities.StringUtils.getFormattedDate;

public class TermAdapter extends ListAdapter<TermEntity, TermAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<TermEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<TermEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull TermEntity oldItem, @NonNull TermEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TermEntity oldItem, @NonNull TermEntity newItem) {
            boolean sameTitle = oldItem.getTitle().equals(newItem.getTitle());
            boolean sameStartDate = sameDate(oldItem.getStartDate(), newItem.getStartDate());
            boolean sameEndDate = sameDate(oldItem.getEndDate(), newItem.getEndDate());
            return sameTitle && sameStartDate && sameEndDate;
        }
    };
    Map<Integer, Integer> termCus = new HashMap<>();
    private OnItemClickListener<TermEntity> listener;

    public TermAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_term, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TermEntity currentTerm = getItem(position);
        holder.textViewTitle.setText(currentTerm.getTitle());
        Date sDate = currentTerm.getStartDate();
        Date eDate = currentTerm.getEndDate();
        String startDate = sDate == null ? "????" : getFormattedDate(sDate);
        String endDate = eDate == null ? "???? " : getFormattedDate(eDate);
        String dateRange = startDate + " - " + endDate;
        holder.textViewDateRange.setText(dateRange);
        Integer cus = termCus.get(currentTerm.getId());
        String sCus = cus == null ? "0" : String.valueOf(cus);
        holder.textViewCompetencyUnits.setText(sCus);
    }

    public TermEntity getTermAt(int position) {
        return getItem(position);
    }

    public void setTotalCus(List<TermCusTuple> allTermCus) {
        if (allTermCus != null) {
            for (TermCusTuple tc : allTermCus) {
                termCus.put(tc.termId, tc.cus);
            }
            notifyDataSetChanged();
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDateRange;
        private TextView textViewCompetencyUnits;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDateRange = itemView.findViewById(R.id.text_view_date_range);
            textViewCompetencyUnits = itemView.findViewById(R.id.text_view_competency_units_value);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }
}
