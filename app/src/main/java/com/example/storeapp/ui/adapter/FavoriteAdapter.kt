package com.example.storeapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storeapp.R
import com.example.storeapp.data.entity.ProductEntity
import com.example.storeapp.databinding.FavRecyclerItemBinding

class FavoriteAdapter(private val listener: FavoriteItemClickListener) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(val binding: FavRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    object FavDiffCallback : DiffUtil.ItemCallback<ProductEntity>() {
        override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
            return oldItem == newItem
        }

    }

    private val diffList = AsyncListDiffer(this, FavDiffCallback)
    var product: List<ProductEntity>
        get() = diffList.currentList
        set(value) = diffList.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            FavRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        with(holder) {
            with(product) {
                binding.tvTitle.text = product[position].title
                binding.tvPrice.text = product[position].price.toString()
                Glide.with(binding.ivImage)
                    .load(product[position].image)
                    .into(binding.ivImage)
                if(product[position].isFav==true){
                    binding.favButton.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                }else{
                    binding.favButton.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                }
            }
            binding.favButton.setOnClickListener {
                listener.onItemClick(product[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return product.size
    }
}