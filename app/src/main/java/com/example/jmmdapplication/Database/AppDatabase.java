package com.example.jmmdapplication.Database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.jmmdapplication.Database.DAO.ChallengeDAO;
import com.example.jmmdapplication.Database.DAO.ProgressDAO;
import com.example.jmmdapplication.Database.DAO.QuestionDAO;
import com.example.jmmdapplication.Database.DAO.UserDAO;
import com.example.jmmdapplication.Database.entities.Challenge;
import com.example.jmmdapplication.Database.entities.Progress;
import com.example.jmmdapplication.Database.entities.Question;
import com.example.jmmdapplication.Database.entities.User;
import com.example.jmmdapplication.Database.typeConverters.LocalDataTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters({LocalDataTypeConverter.class})
@Database(entities = {User.class, Challenge.class, Progress.class, Question.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "JMMD_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract UserDAO userDAO();
    public abstract ChallengeDAO challengeDAO();
    public abstract ProgressDAO progressDAO();
    public abstract QuestionDAO questionDAO();

    private static final RoomDatabase.Callback addDefaultValues =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriteExecutor.execute(() -> {
                        UserDAO userDao = INSTANCE.userDAO();
                        userDao.insertUser(new User("defaultUser1", "defaultPassword1", false));
                        userDao.insertUser(new User("defaultUser2", "defaultPassword2", false));
                    });
                }
            };
}
