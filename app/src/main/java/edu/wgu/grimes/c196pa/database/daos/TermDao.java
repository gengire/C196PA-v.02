package edu.wgu.grimes.c196pa.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.wgu.grimes.c196pa.database.entities.TermEntity;

@Dao
public interface TermDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TermEntity term);

    @Delete
    void delete(TermEntity term);

    @Query("delete from terms")
    void deleteAll();

    @Query("select * from terms")
    LiveData<List<TermEntity>> getAllTerms();

    @Query("select * from terms where term_id = :termId")
    TermEntity selectTermById(int termId);
}
