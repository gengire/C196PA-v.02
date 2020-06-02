package edu.wgu.grimes.c196pa.viewmodels.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import edu.wgu.grimes.c196pa.R;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;

import static edu.wgu.grimes.c196pa.utilities.DateUtils.sameDate;

public class CourseAdapter extends ListAdapter<CourseEntity, CourseAdapter.ViewHolder> {

    private OnItemClickListener listener;
    private static int TYPE_COMPLETE = 1;
    private static int TYPE_NOT_COMPLETE = 2;

    public CourseAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<CourseEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<CourseEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull CourseEntity oldItem, @NonNull CourseEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CourseEntity oldItem, @NonNull CourseEntity newItem) {
            boolean sameTitle = oldItem.getTitle().equals(newItem.getTitle());
            boolean sameCode = oldItem.getCode().equals(newItem.getCode());
            boolean sameCus = oldItem.getCompetencyUnits() == newItem.getCompetencyUnits();
            boolean sameTermId = oldItem.getTermId() == newItem.getTermId();
            boolean sameStatus = oldItem.getStatus().equals(newItem.getStatus());
            boolean sameStartDate = sameDate(oldItem.getStartDate(), newItem.getStartDate());
            boolean sameEndDate = sameDate(oldItem.getEndDate(), newItem.getEndDate());
            return sameTitle && sameCode && sameCus && sameTermId  && sameStatus && sameStartDate && sameEndDate;
        }
    };

    @Override
    public int getItemViewType(int position) {
        if ("Complete".equals(getItem(position).getStatus())) {
            return TYPE_COMPLETE;
        } else {
            return TYPE_NOT_COMPLETE;
        }
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_COMPLETE) {
            view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_course_completed, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_course_not_completed, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        CourseEntity currentCourse = getItem(position);
        String title = currentCourse.getTitle() + " - " + currentCourse.getCode();
        holder.textViewTitle.setText(title);
        holder.textViewCuAmount.setText(String.valueOf(currentCourse.getCompetencyUnits()));
        //TODO: need to figure out how to get the assessments in here
        //        holder.textViewAssessmentType.setText(currentcourse.get

    }

    public CourseEntity getCourseAt(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewCuAmount;
        private TextView textViewTitle;
        private TextView textViewAssessmentType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCuAmount = itemView.findViewById(R.id.text_view_cu_amount);
            textViewTitle = itemView.findViewById(R.id.text_view_course_item_title);
            textViewAssessmentType = itemView.findViewById(R.id.text_view_assessment_type);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CourseEntity course);
    }

    public void setOnItemClickListener(CourseAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
