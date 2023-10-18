package com.vinayakgardi.newshub_newsapplication.Activities

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.devatrii.dailynews.utils.HTMLImageGetter
import com.vinayakgardi.newshub_newsapplication.Model.ArticleModel
import com.vinayakgardi.newshub_newsapplication.Utilites.convertDateFormat
import com.vinayakgardi.newshub_newsapplication.Utilites.imageWithGlide
import com.vinayakgardi.newshub_newsapplication.databinding.ActivityArticleBinding
import com.zzhoujay.richtext.RichText
import java.lang.Exception


class ArticleActivity : AppCompatActivity() {

    lateinit var binding : ActivityArticleBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityArticleBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            val model = intent.getSerializableExtra("model") as ArticleModel
            imageWithGlide(model.image,detailedImage,this@ArticleActivity)
            imageWithGlide(model.authorPic,detailedAuthorImage,this@ArticleActivity)
            detailedAuthorName.text = model.authorName
            detailedArticleDate.text = convertDateFormat(model.date)
            detailedTitle.text = Html.fromHtml(model.title)
            detailedDescription.text = Html.fromHtml(model.content)
            val authorUrl = model.authorUrl
            detailedButton.setOnClickListener {
                val urlIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(authorUrl)
                )
                startActivity(urlIntent)
            }
            val richText = RichText.fromHtml(model.content).imageGetter(HTMLImageGetter(resources,detailedDescription,this@ArticleActivity))
            richText.autoFix(true)
            richText.urlClick{
               try{
                   val intent = CustomTabsIntent.Builder().build()
                   intent.launchUrl(this@ArticleActivity, Uri.parse(it))
               }
               catch (e : Exception){
                   e.printStackTrace()
               }

                true
            }
           richText.into(detailedDescription)

        }
    }
}