package com.vinayakgardi.newshub_newsapplication.ViewModel

import androidx.lifecycle.ViewModel
import com.vinayakgardi.newshub_newsapplication.Repository.MainRepository

class MainViewModel(val mainRepository: MainRepository) : ViewModel() {
    var currentPage = 0
    val articleLiveData get() = mainRepository.liveData
    fun loadArticles(){
        currentPage++
        mainRepository.getArticles(currentPage.toString())
    }
}