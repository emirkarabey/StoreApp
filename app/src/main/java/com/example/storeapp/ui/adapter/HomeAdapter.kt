package com.example.storeapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.storeapp.R
import com.example.storeapp.data.entity.Products
import com.example.storeapp.databinding.HomeRecyclerItemBinding

class HomeAdapter(private val listener: ItemClickListener) :RecyclerView.Adapter<HomeAdapter.MyViewHolder>(){

    class MyViewHolder(val binding: HomeRecyclerItemBinding): RecyclerView.ViewHolder(binding.root)

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
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder){
            binding.tvTitle.text = product[position].title
            binding.tvPrice.text = "$"+product[position].price.toString()
            Glide.with(binding.ivHome)
                .load(product[position].image)
                .into(binding.ivHome)
            if(product[position].isFav==true){
                binding.favButton.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
            }else{
                binding.favButton.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
            }
            binding.rBar.rating = product[position].rating?.rate!!.toFloat()
            binding.tvRate.text = product[position].rating?.rate.toString()
            binding.addButton.setOnClickListener {
                listener.onItemClick(product[position])
            }

            binding.favButton.setOnClickListener {
                listener.favOnItemClick(product[position])
            }
            holder.itemView.setOnClickListener {
                listener.onFragmentItemClick(product[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return product.size
    }

}