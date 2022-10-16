package com.example.storeapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.storeapp.R
import com.example.storeapp.data.entity.Products
import com.example.storeapp.databinding.FragmentHomeBinding
import com.example.storeapp.ui.adapter.HomeAdapter
import com.example.storeapp.ui.adapter.ItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BottomSheetDialogFragment() {

    lateinit var homeAdapter: HomeAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel : HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        viewModel.getData()
        observe()
    }

    override fun onResume() {
        initRecycler()
        viewModel.getData()
        observe()
        super.onResume()

    }

    private fun observe(){
        lifecycleScope.launchWhenCreated {
            viewModel.productList.observe(viewLifecycleOwner){
                homeAdapter.product = it
            }
        }
    }
    private fun initRecycler(){
        binding.homeRecycler.apply {
            homeAdapter = HomeAdapter(object : ItemClickListener{

                override fun onItemClick(product: Products) {
                    addRoom(product)
                    Toast.makeText(requireContext(),"Added to cart",Toast.LENGTH_LONG).show()
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun favOnItemClick(product: Products) {
                    if (product.isFav==true){
                        product.isFav=false
                        deleteFavorite(product)
                        homeAdapter.notifyDataSetChanged()
                    }else{
                        product.isFav=true
                        addFavorite(product)
                        homeAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFragmentItemClick(product: Products) {
                    showDialog(product = product)
                }
            })
            this.layoutManager = GridLayoutManager(context,2)
            adapter = homeAdapter
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addRoom(products: Products){
        viewModel.addProduct(products)
    }

    private fun addFavorite(products: Products){
        viewModel.addFavorite(products)
    }

    private fun deleteFavorite(products: Products){
        viewModel.deleteFavorite(products)
    }

    @SuppressLint("SetTextI18n")
    private fun showDialog(product: Products){
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(R.layout.home_bottom_sheet_dialog)
        val btnEdit= dialog.findViewById<RelativeLayout>(R.id.rl_edit)
        val tvTitle: TextView? = dialog.findViewById<TextView>(R.id.tvTitle)
        val ivImage: ImageView? = dialog.findViewById<ImageView>(R.id.ivImage)
        val tvPrice: TextView? = dialog.findViewById<TextView>(R.id.tv_price)
        val tvCategory: TextView? = dialog.findViewById<TextView>(R.id.tvCategory)
        val tvDescription: TextView? = dialog.findViewById<TextView>(R.id.tvDescription)

        if (ivImage != null) {
            Glide.with(ivImage)
                .load(product.image)
                .into(ivImage)
        }

        if (tvTitle != null) {
            tvTitle.text = "Title: " + product.title.toString()
        }
        if (tvPrice != null) {
            tvPrice.text = "Price: "+product.price.toString()+" $"
        }
        if (tvCategory != null) {
            tvCategory.text = "Category: "+product.category.toString()
        }
        if (tvDescription != null) {
            tvDescription.text = "Description: "+product.description.toString()
        }
        btnEdit?.setOnClickListener {
            Toast.makeText(requireContext(), "Clicked on Edit", Toast.LENGTH_SHORT).show()
        }

        dialog.show()
    }
}