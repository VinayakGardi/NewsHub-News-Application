package com.vinayakgardi.newshub_newsapplication.Model

import com.vinayakgardi.newshub_newsapplication.Adapter.LAYOUT_CARD
import com.vinayakgardi.newshub_newsapplication.Adapter.LAYOUT_LIST
import java.io.Serializable

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
    val category: Int,
    val authorUrl : String,
    val LAYOUT_TYPE : Int = LAYOUT_LIST
) : Serializable