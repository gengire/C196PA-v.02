package edu.wgu.grimes.c196pa.database.daos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import edu.wgu.grimes.c196pa.database.entities.TermCusTuple;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;
import edu.wgu.grimes.c196pa.database.entities.TermWithCourses;

@Dao
public interface TermDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(TermEntity term);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<TermEntity> term);

    @Delete
    void delete(TermEntity term);

    @Query("delete from terms")
    void deleteAll();

    @Query("select * from terms")
    LiveData<List<TermEntity>> getAllTerms();

    @Query("select * from terms where term_id = :termId")
    TermEntity getTermById(int termId);

    @Query("select * from terms where title = :title")
    List<TermEntity> getTermByTitle(String title);

    @Query("select count(*) from terms")
    Integer getCount();

    @Transaction
    @Query("select * from terms")
    LiveData<List<TermWithCourses>> getAllTermsWithCourses();

    @Transaction
    @Query("select * from terms where term_id = :termId")
    TermWithCourses getTermWithCourses(int termId);

    @Query("select term_id, sum(competencyUnits) \"cus\" from terms " +
            "join courses using (term_id) " +
            "group by term_id")
    LiveData<List<TermCusTuple>> getTermCus();
}
