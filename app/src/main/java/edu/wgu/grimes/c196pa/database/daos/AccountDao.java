package edu.wgu.grimes.c196pa.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.wgu.grimes.c196pa.database.entities.AccountEntity;

@Dao
public interface AccountDao {
    /**
     * Inserts / Updates the given entity
     *
     * @param account The account to save
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(AccountEntity account);

    /**
     * Inserts / Updates the given list of entities
     *
     * @param account The terms to save
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<AccountEntity> account);

    /**
     * Deletes the given entity
     *
     * @param account The account to delete
     */
    @Delete
    void delete(AccountEntity account);

    /**
     * Deletes all rows from the table
     */
    @Query("delete from accounts")
    void deleteAll();
}
