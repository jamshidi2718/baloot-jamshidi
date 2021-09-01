package com.baloot_mahdijamshidi.ui.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baloot_mahdijamshidi.adapter.ArticleAdapter
import com.baloot_mahdijamshidi.api.Repository
import com.baloot_mahdijamshidi.classes.Network
import com.baloot_mahdijamshidi.db.ArticleDataBase
import com.baloot_mahdijamshidi.model.ArticleResponse
import com.baloot_mahdijamshidi.model.ArticlesItem
import com.bumptech.glide.load.engine.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: Repository,
    private val network: Network,
    @ApplicationContext val context: Context
) : ViewModel() {

    var isLoading = MutableLiveData<Boolean>()
    var isLastPage = MutableLiveData<Boolean>()
    private val dataSource = ArticleDataBase.getInstance(context).articleDao

    private val _articlesResponse = MutableLiveData<List<ArticlesItem>>() // response of next request
    val articlesResponse: LiveData<List<ArticlesItem>>
        get() = _articlesResponse

    var articles: List<ArticlesItem> = listOf()           // all response body

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var page = 0


    init {
        initializeArticleLoader(20, page+1)
    }

    fun initializeArticleLoader(pageSize: Int, page: Int) {


        uiScope.launch {
            if (network.check() && articles.isEmpty()) {  // first Time
                clearArticles()     //               clear db because load from net
                _articlesResponse.postValue(loadArticles(pageSize, page)?.articles!!)

            } else if (network.check()) { //         others Time

                _articlesResponse.postValue(loadArticles(pageSize, page)?.articles!!)

            } else if(articles.isEmpty()) { //       if net is disconnect and dont load any things
                isLoading.value = true
                getArticlesFromDB()
                isLoading.value = false

                Toast.makeText(context, "check your network", Toast.LENGTH_SHORT).show()

            } else{ //                               if if net is disconnect and loaded

                Toast.makeText(context, "check your network", Toast.LENGTH_SHORT).show()
                isLoading.value = false

            }
        }
    }

    private suspend fun loadArticles(pageSize: Int, page: Int): ArticleResponse? {
        return withContext(Dispatchers.IO) {
            mainRepository.getArticle("419d3a170cec4cb0b824ae34520de2ba", "bitcoin", pageSize, page)
                .body()
        }
    }

    // insert to DB
    fun insertArticles(Articles: List<ArticlesItem>?) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                dataSource.insertAllUsers(Articles!!)
            }
        }
    }

    // clear all articles
    private suspend fun clearArticles() {

        withContext(Dispatchers.IO) {
            dataSource.clearArticles()
        }

    }

    // load from DB
    suspend fun getArticlesFromDB() {

        withContext(Dispatchers.IO) {
            _articlesResponse.postValue( dataSource.getAllArticles()!!)
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        uiScope.cancel()
    }


}