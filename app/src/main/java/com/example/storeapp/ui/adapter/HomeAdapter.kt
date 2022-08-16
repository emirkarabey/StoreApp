package com.example.storeapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storeapp.R
import com.example.storeapp.data.Products
import com.example.storeapp.databinding.HomeRecyclerItemBinding

class HomeAdapter :RecyclerView.Adapter<HomeAdapter.MyViewHolder>(){

    class MyViewHolder(val binding: HomeRecyclerItemBinding): RecyclerView.ViewHolder(binding.root){

    }

    object HomeDiffCallback: DiffUtil.ItemCallback<Products>(){
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem == newItem
        }

    }

    private val diffList = AsyncListDiffer(this,HomeDiffCallback)
    var product: List<Products>
        get()=diffList.currentList
        set(value)=diffList.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = HomeRecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.home_recycler_item,parent,false)
        //return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder){
            with(product){
                binding.tvTitle.text = product[position].title
                binding.tvPrice.text = product[position].price.toString()
                Glide.with(binding.ivImage)
                    .load(product[position].image)
                    .circleCrop()
                    .into(binding.ivImage)
            }
        }
    }

    override fun getItemCount(): Int {
        return product.size
    }

}