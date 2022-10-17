package com.example.storeapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.storeapp.data.entity.Categories
import com.example.storeapp.databinding.HomeCategoryRecyclerItemBinding


class HomeCategoryAdapter(var categoryList:ArrayList<String>,private var listener:CategoryItemClickListener) : RecyclerView.Adapter<HomeCategoryAdapter.MyViewHolder>() {


    class MyViewHolder(val binding: HomeCategoryRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            HomeCategoryRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder){
            binding.tvCategory.text = categoryList[position].toString()

            binding.tvCategory.setOnClickListener {
                listener.onItemClick(categoryList[position])
            }
        }



    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(newList:ArrayList<String>){
        categoryList.clear()
        categoryList.addAll(newList)
        notifyDataSetChanged()
    }
}