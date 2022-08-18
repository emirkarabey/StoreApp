package com.example.storeapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.storeapp.data.entity.ProductEntity
import com.example.storeapp.data.entity.Products
import com.example.storeapp.databinding.FragmentDashboardBinding
import com.example.storeapp.ui.adapter.CartAdapter
import com.example.storeapp.ui.adapter.CartItemClickListener
import com.example.storeapp.ui.adapter.HomeAdapter
import com.example.storeapp.ui.adapter.ItemClickListener
import com.example.storeapp.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    lateinit var cartAdapter: CartAdapter
    private var _binding: FragmentDashboardBinding? = null
    private val viewModel : DashboardViewModel by viewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        viewModel.getAllProductFromRoom()
        observe()
    }

    private fun initRecycler(){
        binding.cartRecycler.apply {
            cartAdapter = CartAdapter(object : CartItemClickListener{
                override fun onItemClick(productEntity: ProductEntity) {
                    viewModel.deleteProduct(productEntity.uid)
                    viewModel.getAllProductFromRoom()
                    observe()
                }
            })

            this.layoutManager = GridLayoutManager(context,2)
            adapter = cartAdapter
        }
    }

    private fun observe(){
        viewModel.cartList.observe(viewLifecycleOwner){
            cartAdapter.product = it
            it.forEach {
                println(it.price)
            }
        }
    }

}