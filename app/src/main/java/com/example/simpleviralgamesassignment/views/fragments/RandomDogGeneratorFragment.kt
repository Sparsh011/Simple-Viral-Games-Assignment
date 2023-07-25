package com.example.simpleviralgamesassignment.views.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.simpleviralgamesassignment.R
import com.example.simpleviralgamesassignment.application.MyApplication
import com.example.simpleviralgamesassignment.databinding.RandomDogGeneratorFragmentBinding
import com.example.simpleviralgamesassignment.model.entities.CachedDogsEntity
import com.example.simpleviralgamesassignment.model.network.ConnectivityObserverInterface
import com.example.simpleviralgamesassignment.model.network.NetworkConnectivityObserver
import com.example.simpleviralgamesassignment.viewmodel.CachedDogsViewModel
import com.example.simpleviralgamesassignment.viewmodel.CachedDogsViewModelFactory
import com.example.simpleviralgamesassignment.viewmodel.RandomDogGeneratorViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class RandomDogGeneratorFragment : Fragment(R.layout.random_dog_generator_fragment) {
    private lateinit var randomDogGeneratorViewModel : RandomDogGeneratorViewModel
    private var _binding : RandomDogGeneratorFragmentBinding? = null
    private lateinit var connectivityObserver : ConnectivityObserverInterface
    private var networkStatus = ConnectivityObserverInterface.Status.Unavailable
    private lateinit var cachedDogsViewModel : CachedDogsViewModel
    private val TAG = "RandomDogFragTag"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = RandomDogGeneratorFragmentBinding.inflate(layoutInflater, container, false)
        return _binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white_toolbar)

        randomDogGeneratorViewModel = ViewModelProvider(this)[RandomDogGeneratorViewModel::class.java]

        val repository = (requireActivity().application as MyApplication).repository
        val viewModelFactory = CachedDogsViewModelFactory(repository)
        cachedDogsViewModel = ViewModelProvider(this, viewModelFactory)[CachedDogsViewModel::class.java]

        connectivityObserver = NetworkConnectivityObserver(requireContext())

        setupToolbar()

        _binding?.toolbarRandomDogs?.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        _binding?.btnGetRandomDogImage?.setOnClickListener {
            if (networkStatus == ConnectivityObserverInterface.Status.Available) {
                randomDogGeneratorViewModel.getRandomDogImageFromAPI()
            }
            else {
                Toast.makeText(requireContext(), "Please Check Your Internet Connection!", Toast.LENGTH_SHORT).show()
            }
        }

        observeRandomDogImageResponse()
        observeConnection()
    }

    private fun setupToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(_binding?.toolbarRandomDogs)
        _binding?.toolbarRandomDogs?.navigationIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_arrow_back_ios_24)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = resources.getString(R.string.generate_dogs)
    }

    private fun observeConnection() {
        connectivityObserver.observeConnection().onEach { status ->
            networkStatus = status
            if (status == ConnectivityObserverInterface.Status.Unavailable) {
                Toast.makeText(requireContext(), "No Internet Connection!", Toast.LENGTH_SHORT).show()
            }
            if (status == ConnectivityObserverInterface.Status.Lost) {
                Toast.makeText(requireContext(), "Internet Connection Lost!", Toast.LENGTH_SHORT).show()
            }
        }.launchIn(lifecycleScope)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeRandomDogImageResponse() {
        randomDogGeneratorViewModel.randomDogImageResponse.observe(viewLifecycleOwner) { randomDogImageResponse ->
            randomDogImageResponse?.let {
                setDogImage(it.message)

                val dog = CachedDogsEntity(it.message, System.currentTimeMillis())
                cachedDogsViewModel.insertDogToCache(dog) { id ->
                    manageCache(id)
                }
            }
        }
    }

    private fun manageCache(id: Long) {
        lifecycleScope.launch {
            val currentCount = cachedDogsViewModel.checkCount(id - 20)
            Log.d(TAG, "Current count : $currentCount")
            if (currentCount > 0) {
                cachedDogsViewModel.deleteItem(id - 20)
                val newCount = cachedDogsViewModel.checkCount(id - 20)
                Log.d(TAG, "New count: $newCount")
            }
        }
    }

    private fun setDogImage(url: String) {
        _binding?.ivRandomDog?.let {
            Glide.with(requireContext())
                .load(url)
                .into(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}