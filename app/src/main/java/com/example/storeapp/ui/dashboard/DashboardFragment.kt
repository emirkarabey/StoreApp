package com.example.storeapp.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.storeapp.domain.mapper.ProductEntityMapper
import com.example.storeapp.data.entity.Products
import com.example.storeapp.databinding.FragmentDashboardBinding
import com.example.storeapp.ui.adapter.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    lateinit var favAdapter: FavoriteAdapter
    private var _binding: FragmentDashboardBinding? = null
    private val viewModel : DashboardViewModel by viewModels()
    private val binding get() = _binding!!
    val mapper = ProductEntityMapper()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        viewModel.getAllFavoriteFromRoom()
        observe()
    }

    private fun initRecycler(){
        binding.favRecycler.apply {
            favAdapter = FavoriteAdapter(object : FavoriteItemClickListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onItemClick(products: Products) {
                    viewModel.deleteFavorite(products.id)
                    viewModel.getAllFavoriteFromRoom()
                    observe()
                    favAdapter.notifyDataSetChanged()
                }
            })

            this.layoutManager = GridLayoutManager(context,2)
            adapter = favAdapter
        }
    }

    private fun observe(){
        viewModel.favList.observe(viewLifecycleOwner){
            val productList: List<Products> = mapper.fromEntityList(it)
            favAdapter.product = productList
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}