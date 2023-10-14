package com.vinayakgardi.newshub_newsapplication.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vinayakgardi.newshub_newsapplication.Adapter.ArticleAdapter
import com.vinayakgardi.newshub_newsapplication.Model.ArticleModel
import com.vinayakgardi.newshub_newsapplication.Repository.APIResponses
import com.vinayakgardi.newshub_newsapplication.Repository.MainRepository
import com.vinayakgardi.newshub_newsapplication.Utilites.showMessage
import com.vinayakgardi.newshub_newsapplication.ViewModel.MainViewModel
import com.vinayakgardi.newshub_newsapplication.ViewModel.MainViewModelFactory
import com.vinayakgardi.newshub_newsapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val list: ArrayList<ArticleModel> = ArrayList()
    val adapter = ArticleAdapter(list, this)

    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            setupRecyclerView()
            loadArticlesFromAPI()


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
                }

                is APIResponses.Loading -> showMessage(this@MainActivity, "Loading")
                is APIResponses.Success -> {
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
        }
    }
}