package com.example.storeapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.storeapp.databinding.FragmentHomeBinding
import com.example.storeapp.ui.adapter.HomeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

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

    private fun observe(){
        //viewModel.characterList.observe(viewLifecycleOwner, Observer {
        //    it?.let {
        //        binding.characterRecyclerView.visibility = View.VISIBLE
        //        countryAdapter.diffList = it.results!!
        //    }
        //})
        lifecycleScope.launchWhenCreated {
            viewModel.characterList.observe(viewLifecycleOwner){
                homeAdapter.product = it
            }
        }

    }
    //card view deki image view kare olucak altına sepete ekle butonu eklenicek ve favoriler olucak
    private fun initRecycler(){
        binding.homeRecycler.apply {
            homeAdapter = HomeAdapter()
            this.layoutManager = GridLayoutManager(context,2)
            adapter = homeAdapter
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}