package com.vinayakgardi.newshub_newsapplication.Adapter

import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinayakgardi.newshub_newsapplication.Activities.ArticleActivity
import com.vinayakgardi.newshub_newsapplication.Model.ArticleModel
import com.vinayakgardi.newshub_newsapplication.Utilites.imageWithGlide
import com.vinayakgardi.newshub_newsapplication.databinding.ItemCardNewsBinding
import com.vinayakgardi.newshub_newsapplication.databinding.ItemListNewsBinding

const val LAYOUT_CARD = 1
const  val LAYOUT_LIST = 2
class ArticleAdapter(var list : ArrayList<ArticleModel>, val context: Context ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class cardviewHolder (var binding: ItemCardNewsBinding ) : RecyclerView.ViewHolder(binding.root){
      fun bind(model : ArticleModel, context: Context){
          binding.apply {
              newsTitle.text = Html.fromHtml(model.title)
              newsExcerpt.text = Html.fromHtml(model.excerpt)
              imageWithGlide(model.image,newsImage,context)

              itemNewsCard.setOnClickListener {
                   val intent = Intent().apply {
                       putExtra("model",model)
                       setClass(context,ArticleActivity::class.java)
                   }
                  context.startActivity(intent)

              }

          }
      }
    }

    class listViewHolder(var binding: ItemListNewsBinding) : RecyclerView.ViewHolder(binding.root){
     fun bind( context: Context,model : ArticleModel ){
         binding.apply {
             imageWithGlide(model.image,newsListImage,context)
             newsListAuthorName.text = Html.fromHtml(model.authorName)
             newsListTitle.text = Html.fromHtml(model.title)
             newsListReadTime.text = ". ${model.readingTime} mins"

             itemNewsList.setOnClickListener {
                 val intent = Intent().apply {
                     putExtra("model",model)
                     setClass(context,ArticleActivity::class.java)
                 }
                 context.startActivity(intent)
             }
         }
     }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       if(viewType==1){
           return cardviewHolder(ItemCardNewsBinding.inflate(LayoutInflater.from(context),parent,false))
       }else
        return listViewHolder(ItemListNewsBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model  = list[position]
        if(getItemViewType(position) == LAYOUT_CARD){
            (holder as cardviewHolder).bind(model,context)
        }
        (holder as listViewHolder).bind(context,model)
    }


    override fun getItemViewType(position: Int): Int {
         return if(list[position].LAYOUT_TYPE == LAYOUT_CARD){
             LAYOUT_CARD
         }
        else{
            LAYOUT_LIST
         }
    }

    override fun getItemCount(): Int  = list.size
}