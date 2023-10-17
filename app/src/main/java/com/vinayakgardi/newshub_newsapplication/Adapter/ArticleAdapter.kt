package com.vinayakgardi.newshub_newsapplication.Adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vinayakgardi.newshub_newsapplication.Model.ArticleModel
import com.vinayakgardi.newshub_newsapplication.Utilites.imageWithGlide
import com.vinayakgardi.newshub_newsapplication.databinding.ItemCardNewsBinding

class ArticleAdapter(var list : ArrayList<ArticleModel>, val context: Context ): RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    class ViewHolder (var binding: ItemCardNewsBinding ) : RecyclerView.ViewHolder(binding.root){
      fun bind(model : ArticleModel, context: Context){
          binding.apply {
              newsTitle.text = Html.fromHtml(model.title)
              newsExcerpt.text = Html.fromHtml(model.excerpt)
              imageWithGlide(model.image,newsImage,context)
          }
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(ItemCardNewsBinding.inflate(LayoutInflater.from(context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val model = list[position]
        holder.bind(model,context)
    }

    override fun getItemCount(): Int  = list.size
}