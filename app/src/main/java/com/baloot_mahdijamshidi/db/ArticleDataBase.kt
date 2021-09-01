package com.baloot_mahdijamshidi.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.baloot_mahdijamshidi.classes.Converters
import com.baloot_mahdijamshidi.model.ArticlesItem

@Database(entities = [ArticlesItem::class] , version = 1 , exportSchema = false)
@TypeConverters(Converters::class) // for insert source field
abstract class ArticleDataBase : RoomDatabase() {

    abstract val articleDao : ArticleDao

    companion object{
        @Volatile
        private var INSTANCE : ArticleDataBase? = null
        fun getInstance (context: Context): ArticleDataBase {

            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(context.applicationContext , ArticleDataBase::class.java , "articleDB.db")
                        .createFromAsset("articleDB.db")
                        .build()
                }
                return instance
            }
        }

    }

}