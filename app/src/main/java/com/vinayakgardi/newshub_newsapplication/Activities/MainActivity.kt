package com.vinayakgardi.newshub_newsapplication.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vinayakgardi.newshub_newsapplication.Adapter.ArticleAdapter
import com.vinayakgardi.newshub_newsapplication.Model.ArticleModel
import com.vinayakgardi.newshub_newsapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

   lateinit var binding: ActivityMainBinding
   val list: ArrayList<ArticleModel> = ArrayList()
    val adapter  = ArticleAdapter(list,this)
    override fun onCreate( savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            recyclerList.adapter = adapter
            list.add(
                ArticleModel(
                    0,
                    "Sample Title",
                    "This is a sample",
                    "Sample Content",
                    "21-01-2003",
                    "Sample Author",
                    "sample pic",
                    "20mins",
                    "www.google.co.in",
                    "https://globalnews.ca/wp-content/uploads/2023/10/20231012151048-65284db33fa9d6d65444f070jpeg.jpg?quality=85&strip=all&w=720",
                    2
                )
            )
            list.add(
                ArticleModel(
                    0,
                    "Sample Title",
                    "This is a sample",
                    "Sample Content",
                    "21-01-2003",
                    "Sample Author",
                    "sample pic",
                    "20mins",
                    "www.google.co.in",
                    "https://globalnews.ca/wp-content/uploads/2023/10/20231012151048-65284db33fa9d6d65444f070jpeg.jpg?quality=85&strip=all&w=720",
                    2
                )
            )
            list.add(
                ArticleModel(
                    0,
                    "Sample Title",
                    "This is a sample",
                    "Sample Content",
                    "21-01-2003",
                    "Sample Author",
                    "sample pic",
                    "20mins",
                    "www.google.co.in",
                    "https://globalnews.ca/wp-content/uploads/2023/10/20231012151048-65284db33fa9d6d65444f070jpeg.jpg?quality=85&strip=all&w=720",
                    2
                )
            )
        }
    }
}