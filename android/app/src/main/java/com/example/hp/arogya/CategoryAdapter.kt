package com.example.hp.arogya

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by hp on 10-10-2018.
 */
class CategoryAdapter(val context: Context, val categories: List<Category>, val itemClick: (Category) -> Unit): RecyclerView.Adapter<CategoryAdapter.Holder>() {

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        holder?.bindCategory(categories[position], context)
    }

    override fun getItemCount(): Int {
        return categories.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.home_category, parent, false)
        return Holder(view, itemClick)
    }

    inner class Holder(itemView: View?, val itemClick: (Category) -> Unit): RecyclerView.ViewHolder(itemView) {

        val categoryName = itemView?.findViewById<TextView>(R.id.categoryTitle)

        fun bindCategory(category: Category, context: Context) {

            categoryName?.text = category.title
            itemView.setOnClickListener { itemClick(category) }
        }
    }
}