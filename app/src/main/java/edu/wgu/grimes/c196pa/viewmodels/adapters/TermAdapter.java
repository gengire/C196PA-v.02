package edu.wgu.grimes.c196pa.viewmodels.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.wgu.grimes.c196pa.R;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;

import static edu.wgu.grimes.c196pa.utilities.StringUtils.getFormattedDate;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {

    private List<TermEntity> terms = new ArrayList<>();
    private OnItemClickListener listener;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_term, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TermEntity currentTerm = terms.get(position);
        holder.textViewTitle.setText(currentTerm.getTitle());
        Date sDate = currentTerm.getStartDate();
        Date eDate = currentTerm.getEndDate();
        String startDate = sDate == null ? "????" : getFormattedDate(sDate);
        String endDate = eDate == null ? "???? " : getFormattedDate(eDate);
        String dateRange = startDate + " - " + endDate;
        holder.textViewDateRange.setText(dateRange);
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }

    public void setTerms(List<TermEntity> terms) {
        this.terms = terms;
        notifyDataSetChanged();
    }

    public TermEntity getTermAt(int position) {
        return terms.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDateRange;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDateRange = itemView.findViewById(R.id.text_view_date_range);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(terms.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(TermEntity term);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
