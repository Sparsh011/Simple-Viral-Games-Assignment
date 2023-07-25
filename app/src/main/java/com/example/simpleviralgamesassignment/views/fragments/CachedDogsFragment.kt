package com.example.simpleviralgamesassignment.views.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpleviralgamesassignment.R
import com.example.simpleviralgamesassignment.application.MyApplication
import com.example.simpleviralgamesassignment.databinding.CachedDogsFragmentBinding
import com.example.simpleviralgamesassignment.viewmodel.CachedDogsViewModel
import com.example.simpleviralgamesassignment.viewmodel.CachedDogsViewModelFactory
import com.example.simpleviralgamesassignment.views.adapter.CachedDogsAdapter

class CachedDogsFragment : Fragment(R.layout.cached_dogs_fragment) {
    private var _binding : CachedDogsFragmentBinding? = null
    private lateinit var cachedDogsViewModel : CachedDogsViewModel
    private lateinit var cachedDogsAdapter : CachedDogsAdapter
    private val TAG = "CachedDogsTag"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = CachedDogsFragmentBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white_toolbar)
        setupToolbar()

        val repository = (requireActivity().application as MyApplication).repository
        val viewModelFactory = CachedDogsViewModelFactory(repository)
        cachedDogsViewModel = ViewModelProvider(this, viewModelFactory)[CachedDogsViewModel::class.java]
        cachedDogsAdapter = CachedDogsAdapter(requireContext())

        _binding?.toolbarCachedDogs?.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        _binding?.btnClearCache?.setOnClickListener {
            cachedDogsViewModel.clearCache()
        }

        setupRecyclerView()
        observeCachedDogs()
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(_binding?.toolbarCachedDogs)
        _binding?.toolbarCachedDogs?.navigationIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_arrow_back_ios_24)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.my_recently_generated_dogs)
    }

    private fun setupRecyclerView() {
        _binding?.rvCachedDogs?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = cachedDogsAdapter
        }
    }


    private fun observeCachedDogs() {
        cachedDogsViewModel.recentDogsLiveData.observe(viewLifecycleOwner) {
            Log.d(TAG, "observeCachedDogs: $it")
            cachedDogsAdapter.differ.submitList(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}