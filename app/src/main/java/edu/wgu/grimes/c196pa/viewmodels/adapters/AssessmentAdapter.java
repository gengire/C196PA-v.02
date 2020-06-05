package edu.wgu.grimes.c196pa.viewmodels.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;

import edu.wgu.grimes.c196pa.R;
import edu.wgu.grimes.c196pa.database.entities.AssessmentEntity;

import static edu.wgu.grimes.c196pa.utilities.DateUtils.sameDate;
import static edu.wgu.grimes.c196pa.utilities.StringUtils.getFormattedDate;

public class AssessmentAdapter extends ListAdapter<AssessmentEntity, AssessmentAdapter.ViewHolder> {

    private static final DiffUtil.ItemCallback<AssessmentEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<AssessmentEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull AssessmentEntity oldItem, @NonNull AssessmentEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull AssessmentEntity oldItem, @NonNull AssessmentEntity newItem) {
            boolean sameTitle = oldItem.getTitle().equals(newItem.getTitle());
            boolean sameCourseId = oldItem.getCourseId() == newItem.getCourseId();
            boolean sameCompletionDate = sameDate(oldItem.getCompletionDate(), newItem.getCompletionDate());
            return sameTitle && sameCourseId && sameCompletionDate;
        }
    };
    private AssessmentAdapter.OnItemClickListener listener;

    public AssessmentAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public AssessmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_assessment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.ViewHolder holder, int position) {
        AssessmentEntity currentAssessment = getItem(position);
        holder.textViewTitle.setText(currentAssessment.getTitle());
        Date cDate = currentAssessment.getCompletionDate();
        String completionDate = cDate == null ? "???? " : getFormattedDate(cDate);
        holder.textViewCompletionDate.setText(completionDate);
    }

    public AssessmentEntity getAssessmentAt(int position) {
        return getItem(position);
    }

    public void setOnItemClickListener(AssessmentAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(AssessmentEntity assessment);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewCompletionDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_assessment_title);
            textViewCompletionDate = itemView.findViewById(R.id.text_view_assessment_completion_date);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }
}
