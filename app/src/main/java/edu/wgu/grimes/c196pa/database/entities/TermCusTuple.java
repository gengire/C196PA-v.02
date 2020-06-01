package edu.wgu.grimes.c196pa.database.entities;

import androidx.room.ColumnInfo;

public class TermCusTuple {
    @ColumnInfo(name = "term_id")
    public int termId;

    @ColumnInfo(name = "cus")
    public int cus;
}
