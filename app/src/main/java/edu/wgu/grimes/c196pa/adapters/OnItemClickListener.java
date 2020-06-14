package edu.wgu.grimes.c196pa.adapters;

import edu.wgu.grimes.c196pa.database.entities.HasId;

public interface OnItemClickListener<T extends HasId> {
    void onItemClick(T entity);
}
