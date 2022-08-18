package com.example.storeapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storeapp.data.entity.ProductEntity
import com.example.storeapp.data.entity.Products
import com.example.storeapp.databinding.CartRecyclerItemBinding

class CartAdapter(private val listener: CartItemClickListener) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    class CartViewHolder(val binding: CartRecyclerItemBinding): RecyclerView.ViewHolder(binding.root){

    }

    object CartDiffCallback: DiffUtil.ItemCallback<ProductEntity>(){
        override fun areItemsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: ProductEntity, newItem: ProductEntity): Boolean {
            return oldItem == newItem
        }

    }

    private val diffList = AsyncListDiffer(this,CartDiffCallback)
    var product: List<ProductEntity>
        get()=diffList.currentList
        set(value)=diffList.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartRecyclerItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        with(holder){
            with(product){
                binding.tvTitle.text = product[position].title
                binding.tvPrice.text = product[position].price.toString()
                Glide.with(binding.ivImage)
                    .load(product[position].image)
                    .into(binding.ivImage)
            }

            binding.deleteButton.setOnClickListener {
                listener.onItemClick(product[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return product.size
    }

}