package com.vinayakgardi.newshub_newsapplication.Activities

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.vinayakgardi.newshub_newsapplication.Adapter.ArticleAdapter
import com.vinayakgardi.newshub_newsapplication.Adapter.LAYOUT_CARD
import com.vinayakgardi.newshub_newsapplication.Adapter.LAYOUT_LIST
import com.vinayakgardi.newshub_newsapplication.Model.ArticleModel
import com.vinayakgardi.newshub_newsapplication.R
import com.vinayakgardi.newshub_newsapplication.Repository.APIResponses
import com.vinayakgardi.newshub_newsapplication.Repository.MainRepository
import com.vinayakgardi.newshub_newsapplication.Utilites.PrefUtils
import com.vinayakgardi.newshub_newsapplication.Utilites.showMessage
import com.vinayakgardi.newshub_newsapplication.ViewModel.MainViewModel
import com.vinayakgardi.newshub_newsapplication.ViewModel.MainViewModelFactory
import com.vinayakgardi.newshub_newsapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val list: ArrayList<ArticleModel> = ArrayList()
    val adapter = ArticleAdapter(list, this)
    private val refreshLiveData = MutableLiveData<Boolean>()
    private lateinit var viewModel: MainViewModel
    var layoutCurrent = LAYOUT_LIST
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        PrefUtils.init(this@MainActivity)

        binding.apply {
            setupRecyclerView()
            loadArticlesFromAPI()
            loadRefreshArticles()
            changeRecyclerView()
        }

    }

    private fun changeRecyclerView() {
        layoutCurrent = PrefUtils.getPrefInt("layout_type", LAYOUT_LIST)
        binding.mainActionBar.layoutChange.setOnClickListener {
            if(layoutCurrent == LAYOUT_LIST){
                layoutCurrent = LAYOUT_CARD
            }
            else{
                layoutCurrent = LAYOUT_LIST
            }
            updateUI()
        }
    }

    private fun updateUI() {
        PrefUtils.putPrefInt("layout_type",layoutCurrent)
        if(layoutCurrent == LAYOUT_CARD){
            binding.mainActionBar.layoutChange.setImageResource(R.drawable.layout_card)
        }
        else{
            binding.mainActionBar.layoutChange.setImageResource(R.drawable.layout_list)
        }
        val tempList : ArrayList<ArticleModel> = ArrayList()
        if(list.isNotEmpty()){
            list.forEach {
                tempList.add(it)
            }
            list.clear()
            tempList.forEach {
                it.LAYOUT_TYPE =layoutCurrent
                list.add(it)
            }
            adapter.notifyDataSetChanged()
        }
    }


    private fun loadRefreshArticles() {
        updateShimmerLayout(false)
        refreshLiveData.observe(this@MainActivity) {
            binding.swiperefresh.isRefreshing = it
            updateShimmerLayout(it)
        }
        binding.swiperefresh.setOnRefreshListener {
            viewModel.currentPage = 0
            viewModel.loadArticles()
        }

    }

    @Suppress("UNREACHABLE_CODE")
    private fun updateShimmerLayout(isLoaded: Boolean?) {
        binding.apply {
            if(!isLoaded!!){
                cardShimmerHolder.visibility = View.GONE
                listShimmerHolder.visibility = View.GONE
                return
            }
            if(layoutCurrent == LAYOUT_CARD){
                cardShimmerHolder.visibility = View.VISIBLE
                listShimmerHolder.visibility = View.GONE

            }
            else{
                cardShimmerHolder.visibility = View.GONE
                listShimmerHolder.visibility = View.VISIBLE
            }
        }



    }

    private fun loadArticlesFromAPI() {
        val mainRepository = MainRepository(context = this)
        viewModel = ViewModelProvider(
            this@MainActivity,
            MainViewModelFactory(mainRepository = mainRepository)
        )[MainViewModel::class.java]
        viewModel.loadArticles()
        viewModel.articleLiveData.observe(this) {
            when (it) {
                is APIResponses.Error -> {
                    showMessage(this@MainActivity, "Error ${it.errorMessage}")
                    refreshLiveData.value = false
                }

                is APIResponses.Loading -> {
                    showMessage(this@MainActivity, "Loading")
                    refreshLiveData.value = true
                }

                is APIResponses.Success -> {
                    refreshLiveData.value = false
                    if (it.data!!.isNotEmpty()) {
                        it.data?.forEach { model ->
                            list.add(model)
                        }
                        adapter.notifyDataSetChanged()
                    }

                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.apply {
            recyclerList.adapter = adapter
            recyclerList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1))
                        viewModel.loadArticles()
                }
            })
        }
    }
}