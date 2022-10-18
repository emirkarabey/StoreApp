package com.example.storeapp.ui.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.storeapp.domain.mapper.ProductEntityMapper
import com.example.storeapp.data.entity.Products
import com.example.storeapp.databinding.FragmentCartBinding
import com.example.storeapp.ui.adapter.CartAdapter
import com.example.storeapp.ui.adapter.CartItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    lateinit var cartAdapter: CartAdapter
    private var _binding: FragmentCartBinding? = null
    private val viewModel: CartViewModel by viewModels()
    private val binding get() = _binding!!
    private val mapper = ProductEntityMapper()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        viewModel.getAllProductFromRoom()
        observe()
    }

    private fun setupRecycler() {
        binding.cartRecycler.apply {
            cartAdapter = CartAdapter(object : CartItemClickListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemClick(product: Products) {
                    viewModel.deleteProduct(product.id)
                    viewModel.getAllProductFromRoom()
                    observe()
                    cartAdapter.notifyDataSetChanged()
                }
            })

            this.layoutManager = GridLayoutManager(context, 2)
            adapter = cartAdapter
        }
    }

    private fun observe() {
        lifecycleScope.launchWhenCreated {

            viewModel.cartList.observe(viewLifecycleOwner) {
                viewModel.progressBar.postValue(true)
                val productList: List<Products> = mapper.fromEntityList(it)
                cartAdapter.product = productList
                viewModel.progressBar.postValue(false)
            }

            viewModel.progressBar.observe(viewLifecycleOwner){
                if (it){
                    binding.cartRecycler.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }else{
                    binding.cartRecycler.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}