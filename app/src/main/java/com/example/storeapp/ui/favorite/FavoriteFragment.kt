package com.example.storeapp.ui.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.storeapp.domain.mapper.ProductEntityMapper
import com.example.storeapp.data.entity.Products
import com.example.storeapp.databinding.FragmentFavoriteBinding
import com.example.storeapp.ui.adapter.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    lateinit var favAdapter: FavoriteAdapter
    private var _binding: FragmentFavoriteBinding? = null
    private val viewModel : FavoriteViewModel by viewModels()
    private val binding get() = _binding!!
    private val mapper = ProductEntityMapper()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        viewModel.getAllFavoriteFromRoom()
        observe()
    }

    private fun setupRecycler(){
        binding.favRecycler.apply {
            favAdapter = FavoriteAdapter(object : FavoriteItemClickListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemClick(products: Products) {
                    viewModel.deleteFavorite(products.id)
                    viewModel.getAllFavoriteFromRoom()
                    observe()
                    favAdapter.notifyDataSetChanged()
                }

                override fun onCartItemClick(product: Products) {
                    addCart(product)
                    Toast.makeText(requireContext(),"Added to cart", Toast.LENGTH_LONG).show()
                }
            })

            this.layoutManager = GridLayoutManager(context,2)
            adapter = favAdapter
        }
    }

    private fun observe(){
        lifecycleScope.launchWhenCreated {
            viewModel.favList.observe(viewLifecycleOwner){
                viewModel.progressBar.postValue(true)
                val productList: List<Products> = mapper.fromEntityList(it)
                favAdapter.product = productList
                viewModel.progressBar.postValue(false)
            }

            viewModel.progressBar.observe(viewLifecycleOwner){
                if (it){
                    binding.favRecycler.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }else{
                    binding.favRecycler.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun addCart(products: Products){
        viewModel.addCart(products)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}