package com.example.storeapp.ui.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.storeapp.R
import com.example.storeapp.data.entity.Products
import com.example.storeapp.databinding.FragmentBottomSheetBinding
import com.example.storeapp.ui.home.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel : HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showDialog()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun addCart(products: Products){
        viewModel.addCart(products)
    }

    private fun addFavorite(products: Products){
        viewModel.addFavorite(products)
    }

    private fun deleteFavorite(products: Products){
        viewModel.deleteFavorite(products)
    }

    @SuppressLint("SetTextI18n")
    fun showDialog(){
        val args = arguments
        val product: Products? = args?.getParcelable("productForDialog")
        with(binding){
            if (product != null) {
                Glide.with(ivDialog)
                    .load(product.image)
                    .into(ivDialog)
                tvTitle.text = product.title
                tvCategory.text = product.category
                tvDescription.text = product.description
                tvPrice.text = "$"+product.price.toString()
                tvRate.text = product.rating?.rate.toString()
                rBar.rating = product.rating!!.rate
                favButton.setOnClickListener {
                    if (product.isFav==true){
                        product.isFav=false
                        deleteFavorite(product)
                        favButton.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                    }else{
                        product.isFav=true
                        addFavorite(product)
                        favButton.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                    }
                }
                addButton.setOnClickListener {
                    addCart(product)
                    Toast.makeText(requireContext(),"Added to cart", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}