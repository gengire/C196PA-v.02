//*********************************************************************************
//  File:             AccountDao.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import edu.wgu.grimes.c868pa.database.entities.AccountEntity;

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
     * @param accounts The accounts to save
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<AccountEntity> accounts);

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

    @Query("select * from accounts where username = :username")
    AccountEntity getAccountByUsername(String username);

    @Query("select count(*) from accounts where username = :username")
    int getCountByUsername(String username);

    @Query("select * from accounts")
    LiveData<List<AccountEntity>> getAllAccounts();

    @Query("select * from accounts where account_id = :accountId")
    AccountEntity getAccountById(int accountId);

}
