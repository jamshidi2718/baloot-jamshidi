package com.baloot_mahdijamshidi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.baloot_mahdijamshidi.R
import com.baloot_mahdijamshidi.model.ArticlesItem
import com.baloot_mahdijamshidi.ui.home.HomeFragmentDirections
import kotlinx.android.synthetic.main.article_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ArticleAdapter(private var articles: List<ArticlesItem>?) :
    RecyclerView.Adapter<ArticleAdapter.MainViewHolder>() {

    @Inject
    lateinit var context: Context

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name = itemView.title
        var des = itemView.des
        var author = itemView.author
        var data = itemView.data
        fun bindUsers(article: ArticlesItem) {
            name.text = article.title
            des.text = article.description
            author.text = article.author
            data.text =  data(article.publishedAt!!)?.getUIStringTimeStampWithDate()

            itemView.item.setOnClickListener {

                it.findNavController().navigate( HomeFragmentDirections.actionNavigationHomeToInfoFragment(article.title!! , article.content!!))

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindUsers(articles!![position])
    }

    override fun getItemCount(): Int = articles?.size!!

    fun addItems(list: List<ArticlesItem>) {
        articles = articles!! + list
        this.notifyDataSetChanged()
    }

    // region data
    fun Date.getUIStringTimeStampWithDate(): String {
        val dateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",
            Locale.getDefault())
        dateFormat.timeZone = TimeZone.getDefault()
        return dateFormat.format(this)
    }

    fun data( data:String):Date?{
        return try {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val value: Date = formatter.parse(data)

            value
        } catch (e: Exception) {
            null
        }
    }

    // endregion
}