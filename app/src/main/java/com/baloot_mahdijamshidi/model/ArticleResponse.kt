package com.baloot_mahdijamshidi.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

data class ArticleResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("articles")
	var articles: List<ArticlesItem>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

@Entity(tableName = "article")
data class ArticlesItem(

	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	val id: Int ,

	@ColumnInfo(name = "publishedAt")
	@field:SerializedName("publishedAt")
	val publishedAt: String? = null,
	@ColumnInfo(name = "author")
	@field:SerializedName("author")
	val author: String? = null,
	@ColumnInfo(name = "urlToImage")
	@field:SerializedName("urlToImage")
	val urlToImage: String? = null,
	@ColumnInfo(name = "description")
	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("source")
	val source: Source? = null,

	@ColumnInfo(name = "title")
	@field:SerializedName("title")
	val title: String? = null,
	@ColumnInfo(name = "url")
	@field:SerializedName("url")
	val url: String? = null,
	@ColumnInfo(name = "content")
	@field:SerializedName("content")
	val content: String? = null
)

data class Source(

	@field:SerializedName("name")
	val name: String = "",

	@field:SerializedName("id")
	val id: String = ""
)
