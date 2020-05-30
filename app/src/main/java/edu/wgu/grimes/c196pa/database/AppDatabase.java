package edu.wgu.grimes.c196pa.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import edu.wgu.grimes.c196pa.database.daos.AssessmentDao;
import edu.wgu.grimes.c196pa.database.daos.CourseDao;
import edu.wgu.grimes.c196pa.database.daos.TermDao;
import edu.wgu.grimes.c196pa.database.entities.AssessmentEntity;
import edu.wgu.grimes.c196pa.database.entities.CourseEntity;
import edu.wgu.grimes.c196pa.database.entities.TermEntity;

@Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class}, version = 5, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();

    // not the most performant, but thread safe.
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "grimesc196pa.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
