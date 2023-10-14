package com.vinayakgardi.newshub_newsapplication.Repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.devatrii.dailynews.utils.POSTS_URL
import com.vinayakgardi.newshub_newsapplication.Model.ArticleModel
import org.json.JSONArray

class MainRepository(private val context: Context) {

    private val mutableLiveData = MutableLiveData<APIResponses<ArrayList<ArticleModel>>>()
    val liveData get() = mutableLiveData


    fun getArticles(pages: String = "1") {
        liveData.value = APIResponses.Loading()
        val requestQueue = Volley.newRequestQueue(context)
        val url =
            POSTS_URL + "page=$pages"     //https://globalnews.ca//wp-json/wp/v2/posts?_embed&per_page=7

        val stringRequest = object : StringRequest(Method.GET, url, { response ->
            if (response.isNotEmpty()) {
                val mainJSONArray = JSONArray(response)
                val tempList: ArrayList<ArticleModel> = ArrayList()
                for (i in 0 until mainJSONArray.length()) {
                    val postObject = mainJSONArray.getJSONObject(i)
                    postObject.apply {
                        val id = getInt("id")
                        val title = getJSONObject("title").getString("rendered")
                        val content = getJSONObject("content").getString("rendered")
                        val excerpt = getJSONObject("excerpt").getString("rendered")
                        val date = getString("date")
                        val embedded = getJSONObject("_embedded")
                        val authorArray = embedded.getJSONArray("author")
                        val authorName = authorArray.getJSONObject(0).getString("name")
                        val articleUrl = getJSONObject("guid").getString("rendered")
                        val authorPic = authorArray.getJSONObject(0).getJSONObject("avatar_urls")
                            .getString("96")
                        val readingTime =
                            (content.split(" ").size / 200).toString()           // reading time formula words / 200
                        val image = embedded.getJSONArray("wp:featuredmedia").getJSONObject(0)
                            .getJSONObject("media_details").getJSONObject("sizes")
                            .getJSONObject("full").getString("source_url")

                        val category = getJSONArray("categories").get(0).toString().toInt()

                        val model = ArticleModel(
                            id,
                            title,
                            excerpt,
                            content,
                            date,
                            authorName,
                            authorPic,
                            readingTime,
                            articleUrl,
                            image,
                            category
                        )

                        tempList.add(model)
                    }
                }

                liveData.value = APIResponses.Success(tempList)

            }
        }, { error ->
            liveData.value = error.localizedMessage?.let { APIResponses.Error(error = it) }
        }

        ) {

        }
        requestQueue.add(stringRequest)
    }
}