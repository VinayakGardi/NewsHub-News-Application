package com.vinayakgardi.newshub_newsapplication.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vinayakgardi.newshub_newsapplication.Repository.MainRepository

class MainViewModelFactory(val mainRepository: MainRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(mainRepository) as T
    }
}