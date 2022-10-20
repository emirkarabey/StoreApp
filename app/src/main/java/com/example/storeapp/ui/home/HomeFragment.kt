package com.example.storeapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.storeapp.data.entity.Products
import com.example.storeapp.databinding.FragmentHomeBinding
import com.example.storeapp.ui.adapter.CategoryItemClickListener
import com.example.storeapp.ui.adapter.HomeAdapter
import com.example.storeapp.ui.adapter.HomeCategoryAdapter
import com.example.storeapp.ui.adapter.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var homeAdapter: HomeAdapter
    lateinit var categoryAdapter: HomeCategoryAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryList: ArrayList<String>
    private val viewModel : HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        categoryList = arrayListOf()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel.getData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHomeRecycler()
        setupCategoryRecycler()
        viewModel.getData()
        observe()
        refresh()

    }
//on view create a çok giriyor sürekli internetten veri çekiyor bunu çöz

    private fun refresh(){
        with(binding){
            swipeRefreshLayout.setOnRefreshListener {
                progressBar.visibility = View.VISIBLE
                homeRecycler.visibility = View.GONE
                categoryRecycler.visibility = View.GONE
                viewModel.getData()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun observe(){
        lifecycleScope.launchWhenCreated {
            viewModel.productList.observe(viewLifecycleOwner){
                binding.progressBar.visibility = View.VISIBLE
                homeAdapter.product = it
                binding.progressBar.visibility = View.GONE
            }

            viewModel.progressBar.observe(viewLifecycleOwner){
                if (it){
                    binding.homeRecycler.visibility = View.GONE
                    binding.categoryRecycler.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }else{
                    binding.homeRecycler.visibility = View.VISIBLE
                    binding.categoryRecycler.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }


    private fun setupCategoryRecycler(){
        binding.categoryRecycler.apply {
            categoryAdapter = HomeCategoryAdapter(categoryList,object : CategoryItemClickListener{
                override fun onItemClick(category: String) {
                    viewModel.getCategoryProduct(category)
                    viewModel.getData()
                    setupHomeRecycler()

                }

            })
            lifecycleScope.launchWhenCreated {
                viewModel.categoryList.observe(viewLifecycleOwner){
                    categoryAdapter.setList(it)
                }
            }
            adapter = categoryAdapter
        }
    }


    private fun setupHomeRecycler(){
        binding.homeRecycler.apply {
            homeAdapter = HomeAdapter(object : ItemClickListener{

                override fun onItemClick(product: Products) {
                    addCart(product)
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

                    val action = HomeFragmentDirections.actionNavigationHomeToBottomSheetFragment(product)
                    findNavController().navigate(action)
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

    private fun addCart(products: Products){
        viewModel.addCart(products)
    }

    private fun addFavorite(products: Products){
        viewModel.addFavorite(products)
    }

    private fun deleteFavorite(products: Products){
        viewModel.deleteFavorite(products)
    }
}