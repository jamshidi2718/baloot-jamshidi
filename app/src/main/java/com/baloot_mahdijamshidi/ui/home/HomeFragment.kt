package com.baloot_mahdijamshidi.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.baloot_mahdijamshidi.R
import com.baloot_mahdijamshidi.adapter.ArticleAdapter
import com.baloot_mahdijamshidi.classes.PaginationScrollListener
import com.baloot_mahdijamshidi.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private var binding: FragmentHomeBinding? = null
    private lateinit var adapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            FragmentHomeBinding.inflate(
                inflater, container, false
            )

        setupUi()

        setObserver()

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun setupUi() {


        val layoutmanager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding?.recycler?.layoutManager = layoutmanager
        adapter = ArticleAdapter(homeViewModel.articles)
        binding?.recycler?.adapter = adapter


        binding?.recycler?.addOnScrollListener(object : PaginationScrollListener(layoutmanager) {
            override fun isLastPage(): Boolean {
                return homeViewModel.isLastPage.value!!
            }

            override fun isLoading(): Boolean {
                return homeViewModel.isLoading.value!!
            }

            override fun loadMoreItems() {

                homeViewModel.isLoading.value = true
                homeViewModel.initializeArticleLoader(20, homeViewModel.page+1)

            }
        })


    }


    fun setObserver() {


        // observ to response
        homeViewModel.articlesResponse.observe(viewLifecycleOwner, Observer {

            homeViewModel.articles = homeViewModel.articles + it    // add to old list

            adapter.addItems(it)                               //      add to adapter

            homeViewModel.page = homeViewModel.articles.size / 20   // Page calculation

            homeViewModel.isLastPage.value = homeViewModel.articles.size >= 100//    if(homeViewModel.ResultsCount >= it.totalResults!! ) // Developer accounts are limited to a max of 100 results

            homeViewModel.isLoading.value = false                   // stop loading

            homeViewModel.insertArticles(it)                        // Insert to DB


        })


        // observ to loading
        homeViewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it)
                binding?.progressBar?.visibility = View.VISIBLE
            else
                binding?.progressBar?.visibility = View.INVISIBLE

        })


        // observ to end off list
        homeViewModel.isLastPage.observe(viewLifecycleOwner, Observer {
            if (it) {                                       // end of pagination
                binding?.recycler?.setPadding(
                    binding?.recycler?.paddingLeft!!,
                    binding?.recycler?.paddingTop!!,
                    binding?.recycler?.paddingRight!!,
                    0
                )
            }

        })



    }
}