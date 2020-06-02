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
import edu.wgu.grimes.c196pa.database.entities.NoteEntity;

public class NoteAdapter extends ListAdapter<NoteEntity, NoteAdapter.ViewHolder> {

    private OnItemClickListener listener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<NoteEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<NoteEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull NoteEntity oldItem, @NonNull NoteEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull NoteEntity oldItem, @NonNull NoteEntity newItem) {
            boolean sameCourse = oldItem.getCourse_id() == newItem.getCourse_id();
            boolean sameTitle = oldItem.getTitle().equals(newItem.getTitle());
            boolean sameDesc = TextUtils.equals(oldItem.getDescription(), newItem.getDescription());
            return sameCourse && sameTitle && sameDesc;
        }
    };

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        NoteEntity currentNote = getItem(position);
        holder.textViewTitle.setText(currentNote.getTitle());
        holder.textViewDescription.setText(currentNote.getDescription());
    }

    public NoteEntity getNoteAt(int position) { return getItem(position); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_note_title);
            textViewDescription = itemView.findViewById(R.id.text_view_note_description);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(NoteEntity note);
    }

    public void setOnItemClickListener(NoteAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
