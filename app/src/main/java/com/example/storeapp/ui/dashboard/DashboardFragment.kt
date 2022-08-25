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
    private var _binding: FragmentDashboardBinding? = null
    private val viewModel : DashboardViewModel by viewModels()
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

}