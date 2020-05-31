package edu.wgu.grimes.c196pa.viewmodels.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.wgu.grimes.c196pa.R;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    private List<CourseEntity> courses = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        CourseEntity currentCourse = courses.get(position);
        String title = currentCourse.getTitle() + " - " + currentCourse.getCode();
        holder.textViewTitle.setText(title);
        holder.textViewCuAmount.setText(String.valueOf(currentCourse.getCompetencyUnits()));
        //TODO: need to figure out how to get the assessments in here
        //        holder.textViewAssessmentType.setText(currentcourse.get

    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void setCourses(List<CourseEntity> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    public CourseEntity getCourseAt(int position) {
        return courses.get(position);
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
                    listener.onItemClick(courses.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CourseEntity term);
    }

    public void setOnItemClickListener(CourseAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
