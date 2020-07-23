//*********************************************************************************
//  File:             AppDatabase.java
//*********************************************************************************
//  Course:           Software Development Capstone - C868
//  Semester:         Spring 2020
//*********************************************************************************
//  Author:           Chris Grimes Copyright (2020). All rights reserved.
//  Student ID:       000981634
//  Program Mentor:   JoAnne McDermand
//*********************************************************************************
package edu.wgu.grimes.c868pa.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import edu.wgu.grimes.c868pa.database.daos.AccountDao;
import edu.wgu.grimes.c868pa.database.daos.AssessmentDao;
import edu.wgu.grimes.c868pa.database.daos.CourseDao;
import edu.wgu.grimes.c868pa.database.daos.MentorDao;
import edu.wgu.grimes.c868pa.database.daos.NoteDao;
import edu.wgu.grimes.c868pa.database.daos.TermDao;
import edu.wgu.grimes.c868pa.database.entities.AccountEntity;
import edu.wgu.grimes.c868pa.database.entities.AssessmentEntity;
import edu.wgu.grimes.c868pa.database.entities.CourseEntity;
import edu.wgu.grimes.c868pa.database.entities.MentorEntity;
import edu.wgu.grimes.c868pa.database.entities.NoteEntity;
import edu.wgu.grimes.c868pa.database.entities.TermEntity;

/**
 * Database factory for the app.  Responsible for ensuring no collisions on database calls.
 * REMEMBER TO UPDATE THE VERSION WHEN CHANGING SCHEMA INFO
 *
 * @author Chris Grimes Copyright (2020)
 * @version 1.0
 */
@Database(entities = {AccountEntity.class, TermEntity.class, CourseEntity.class, AssessmentEntity.class,
        NoteEntity.class, MentorEntity.class}, version = 6, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    // not the most performant, but thread safe.
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "grimesc868pa.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract TermDao termDao();

    public abstract CourseDao courseDao();

    public abstract AssessmentDao assessmentDao();

    public abstract NoteDao noteDao();

    public abstract MentorDao mentorDao();

    public abstract AccountDao accountDao();
}
