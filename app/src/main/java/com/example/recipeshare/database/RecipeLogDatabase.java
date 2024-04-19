package com.example.recipeshare.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.recipeshare.MainActivity;
import com.example.recipeshare.database.entities.RecipeLog;
import com.example.recipeshare.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TODO: Refer to Gymlog video 3 @ 16 min

//TODO: version represents the dimension of our database, I think it's 1 idk...on Video7 @ 24min
@Database(entities = {RecipeLog.class, User.class}, version = 1, exportSchema = false)
public abstract class RecipeLogDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "RecipeLogDatabase";
    public static final String RECIPE_LOG_TABLE = "recipeLogTable";
    public static final String USER_TABLE = "userTable";
    private static volatile RecipeLogDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4; //TODO: is this correct?
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static RecipeLogDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (RecipeLogDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            RecipeLogDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .addCallback(addDefaultRecipes)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);

                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);
            });
        }
    };

    private static final RoomDatabase.Callback addDefaultRecipes = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            databaseWriteExecutor.execute(() -> {
                RecipeLogDAO dao = INSTANCE.recipeLogDAO();
                dao.deleteAll();
                RecipeLog recipe1 = new RecipeLog("PB&J", "1tb Penaut Butter, 1tb Jelly, 2 Slices of bread.", "Spread penaut butter on 1 slice of bread. Spread jelly on other slice of bread. Put both slices together mixing PB&J", "Admin1", true);
                dao.insert(recipe1);

                RecipeLog recipe2 = new RecipeLog("Cereal", "1cup of cereal, 1 cup of milk.", "Put 1 cup of cereal in a bowl. Pour 1 cup of milk into bowl.", "TestUser1", false);
                dao.insert(recipe2);
            });
        }
    };


    public abstract RecipeLogDAO recipeLogDAO();
    public abstract UserDAO userDAO();

}
