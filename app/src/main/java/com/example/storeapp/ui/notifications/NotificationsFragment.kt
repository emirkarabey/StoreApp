package com.example.storeapp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.storeapp.data.entity.ProductEntity
import com.example.storeapp.databinding.FragmentNotificationsBinding
import com.example.storeapp.ui.adapter.CartAdapter
import com.example.storeapp.ui.adapter.CartItemClickListener
import com.example.storeapp.ui.dashboard.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    lateinit var cartAdapter: CartAdapter
    private var _binding: FragmentNotificationsBinding? = null
    private val viewModel : NotificationsViewModel by viewModels()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
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
            cartAdapter = CartAdapter(object : CartItemClickListener {
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