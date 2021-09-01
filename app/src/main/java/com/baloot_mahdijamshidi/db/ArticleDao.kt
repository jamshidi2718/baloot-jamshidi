package com.baloot_mahdijamshidi.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.baloot_mahdijamshidi.model.ArticlesItem

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllUsers(ArticleRoomList : List<ArticlesItem>?)

    @Query("SELECT * FROM article")
    fun getAllArticles(): List<ArticlesItem>?

    @Query("DELETE FROM article")
    fun clearArticles()
}