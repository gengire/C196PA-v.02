//*********************************************************************************
//  File:             AssessmentAdapter.java
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
import edu.wgu.grimes.c868pa.database.entities.AssessmentEntity;

import static edu.wgu.grimes.c868pa.utilities.DateUtils.sameDate;
import static edu.wgu.grimes.c868pa.utilities.StringUtils.getFormattedDate;

/**
 * Assessment Adapter, Used to binds the AssessmentEntity to the RecyclerView
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
public class AssessmentAdapter extends ListAdapter<AssessmentEntity, AssessmentAdapter.AssessmentViewHolder> {

    /**
     * Used to more optimally handle how the recycler view handles changes to the items in it
     */
    private static final DiffUtil.ItemCallback<AssessmentEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<AssessmentEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull AssessmentEntity oldItem, @NonNull AssessmentEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull AssessmentEntity oldItem, @NonNull AssessmentEntity newItem) {
            boolean sameTitle = oldItem.getAssessmentTitle().equals(newItem.getAssessmentTitle());
            boolean sameCourseId = oldItem.getCourseId() == newItem.getCourseId();
            boolean sameCompletionDate = sameDate(oldItem.getCompletionDate(), newItem.getCompletionDate());
            return sameTitle && sameCourseId && sameCompletionDate;
        }
    };
    private OnItemClickListener<AssessmentEntity> listener;

    public AssessmentAdapter() {
        super(DIFF_CALLBACK);
    }

    public AssessmentEntity getAssessmentAt(int position) {
        return getItem(position);
    }

    public void setOnItemClickListener(OnItemClickListener<AssessmentEntity> listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_assessment, parent, false);
        return new AssessmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        AssessmentEntity currentAssessment = getItem(position);
        holder.mTitle.setText(currentAssessment.getAssessmentTitle());
        Date cDate = currentAssessment.getCompletionDate();
        String completionDate = cDate == null ? "???? " : getFormattedDate(cDate);
        holder.mCompletionDate.setText(completionDate);
    }

    protected class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTitle;
        private final TextView mCompletionDate;

        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.text_view_assessment_title);
            mCompletionDate = itemView.findViewById(R.id.text_view_assessment_completion_date);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }
}
