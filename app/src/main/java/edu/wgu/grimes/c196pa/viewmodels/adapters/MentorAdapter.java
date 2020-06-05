package edu.wgu.grimes.c196pa.viewmodels.adapters;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import edu.wgu.grimes.c196pa.R;
import edu.wgu.grimes.c196pa.database.entities.MentorEntity;

public class MentorAdapter extends ListAdapter<MentorEntity, MentorAdapter.ViewHolder> {

    private OnItemClickListener listener;

    public MentorAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<MentorEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<MentorEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull MentorEntity oldItem, @NonNull MentorEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MentorEntity oldItem, @NonNull MentorEntity newItem) {
            boolean sameCourse = oldItem.getCourseId() == newItem.getCourseId();
            boolean sameFirstName = TextUtils.equals(oldItem.getFirstName(), newItem.getFirstName());
            boolean sameLastName = TextUtils.equals(oldItem.getLastName(), newItem.getLastName());
            boolean samePhone = TextUtils.equals(oldItem.getPhoneNumber(), newItem.getPhoneNumber());
            boolean sameEmail = TextUtils.equals(oldItem.getEmail(), newItem.getEmail());
            return sameCourse && sameFirstName && sameLastName && samePhone && sameEmail;
        }
    };

    @NonNull
    @Override
    public MentorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mentor, parent, false);
        return new MentorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MentorAdapter.ViewHolder holder, int position) {
        MentorEntity currentMentor = getItem(position);
        holder.textViewName.setText(currentMentor.getFirstName() + " " + currentMentor.getLastName());
        holder.textViewPhone.setText(currentMentor.getPhoneNumber());
        holder.textViewEmail.setText(currentMentor.getEmail());
    }

    public MentorEntity getMentorAt(int position) { return getItem(position); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewPhone;
        private TextView textViewEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_mentor_name);
            textViewPhone = itemView.findViewById(R.id.text_view_mentor_phone);
            textViewEmail = itemView.findViewById(R.id.text_view_mentor_email);

            itemView.setOnClickListener(view -> {
               int position = getAdapterPosition();
               if (listener != null && position != RecyclerView.NO_POSITION) {
                   listener.onItemClick(getItem(position));
               }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(MentorEntity note);
    }

    public void setOnItemClickListener(MentorAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
