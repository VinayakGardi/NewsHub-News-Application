package com.vinayakgardi.newshub_newsapplication.Model

data class ArticleModel(
    val id : Int,
    val title: String,
    val excerpt : String,
    val content : String ,
    val date : String,
    val authorName : String ,
    val authorPic : String ,
    val readingTime : String,
    val articleUrl : String,
    val image : String,
    val category: Int
)
