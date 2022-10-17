package com.example.storeapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.storeapp.R
import com.example.storeapp.data.entity.Products
import com.example.storeapp.databinding.FragmentHomeBinding
import com.example.storeapp.ui.adapter.CategoryItemClickListener
import com.example.storeapp.ui.adapter.HomeAdapter
import com.example.storeapp.ui.adapter.HomeCategoryAdapter
import com.example.storeapp.ui.adapter.ItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BottomSheetDialogFragment() {

    lateinit var homeAdapter: HomeAdapter
    lateinit var categoryAdapter: HomeCategoryAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryList: ArrayList<String>
    private val viewModel : HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        categoryList = arrayListOf<String>()
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
        initCategoryRecycler()
        viewModel.getData()
        observe()
        super.onResume()

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
                    binding.tvCategory.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }else{
                    binding.homeRecycler.visibility = View.VISIBLE
                    binding.categoryRecycler.visibility = View.VISIBLE
                    binding.tvCategory.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }


    private fun initCategoryRecycler(){
        binding.categoryRecycler.apply {
            categoryAdapter = HomeCategoryAdapter(categoryList,object : CategoryItemClickListener{
                override fun onItemClick(category: String) {
                    viewModel.getCategoryProduct(category)
                    initRecycler()
                    viewModel.getData()
                    observe()
                }

            })
            lifecycleScope.launchWhenCreated {
                viewModel.categoryList.observe(viewLifecycleOwner){
                    categoryAdapter.setList(it)
                }
            }
            this.layoutManager = GridLayoutManager(context,4)
            adapter = categoryAdapter
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


    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    private fun showDialog(product: Products)=binding.apply{
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(R.layout.home_bottom_sheet_dialog)
        val btnEdit= dialog.findViewById<RelativeLayout>(R.id.rl_edit)
        val tvTitle: TextView? = dialog.findViewById<TextView>(R.id.tv_title)
        val ivImage: ImageView? = dialog.findViewById<ImageView>(R.id.iv_dialog)
        val tvPrice: TextView? = dialog.findViewById<TextView>(R.id.tv_price)
        val tvCategory: TextView? = dialog.findViewById<TextView>(R.id.tv_category)
        val tvDescription: TextView? = dialog.findViewById<TextView>(R.id.tv_description)
        val addButton: Button? = dialog.findViewById<Button>(R.id.add_button)
        val favButton: ImageButton? = dialog.findViewById<ImageButton>(R.id.fav_button)
        val rBar: RatingBar? = dialog.findViewById<RatingBar>(R.id.rBar)
        val tvBar: TextView? = dialog.findViewById<TextView>(R.id.tv_rate)

        if (rBar != null) {
            rBar.rating = product.rating!!.rate
        }
        if (tvBar != null) {
            tvBar.text = product.rating!!.rate.toString()
        }
        if (ivImage != null) {
            Glide.with(ivImage)
                .load(product.image)
                .into(ivImage)
        }

        if (tvTitle != null) {
            tvTitle.text = "Title: " + product.title.toString()
        }

        if (tvPrice != null) {
            tvPrice.text = "Price: $"+product.price.toString()
        }

        if (tvCategory != null) {
            tvCategory.text = "Category: "+product.category.toString()
        }

        if (tvDescription != null) {
            tvDescription.text = "Description: "+product.description.toString()
        }

        if (product.isFav==true){
            favButton?.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
        }else{
            favButton?.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
        }

        favButton?.setOnClickListener {
            if (product.isFav==true){
                product.isFav=false
                deleteFavorite(product)
                homeAdapter.notifyDataSetChanged()
                favButton.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
            }else{
                product.isFav=true
                addFavorite(product)
                homeAdapter.notifyDataSetChanged()
                favButton.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
            }
        }
        addButton?.setOnClickListener {
            addRoom(product)
            Toast.makeText(requireContext(),"Added to cart",Toast.LENGTH_LONG).show()
        }
        btnEdit?.setOnClickListener {
            Toast.makeText(requireContext(), "Clicked on Edit", Toast.LENGTH_SHORT).show()
        }

        dialog.show()
    }
}