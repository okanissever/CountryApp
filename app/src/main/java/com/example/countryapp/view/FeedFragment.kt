package com.example.countryapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.countryapp.R
import com.example.countryapp.adapter.CountryAdapter
import com.example.countryapp.databinding.FragmentFeedBinding
import com.example.countryapp.viewmodel.FeedViewModel
import com.example.countryapp.common.viewBinding


class FeedFragment : Fragment(R.layout.fragment_feed) {
    private val binding by viewBinding(FragmentFeedBinding::bind)
    private val viewModel: FeedViewModel by viewModels()
    private val adapter = CountryAdapter(arrayListOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.countryList.visibility = View.GONE
            viewModel.refreshFromAPI()
            binding.countryError.visibility = View.GONE
            binding.countryLoading.visibility = View.VISIBLE
            binding.swipeRefreshLayout.isRefreshing = false
        }
        viewModel.refreshData()
        binding.countryList.adapter = adapter
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.countries.observe(viewLifecycleOwner){countries->
            countries?.let {
                binding.countryList.visibility = View.VISIBLE
                adapter.updateCountryList(it)
            }
        }
        viewModel.countryError.observe(viewLifecycleOwner){error->
            error?.let {
                if(it){
                    binding.countryList.visibility = View.GONE
                    binding.countryLoading.visibility = View.GONE
                    binding.countryError.visibility = View.VISIBLE
                }
                else{
                    binding.countryError.visibility = View.GONE
                }
            }
        }
        viewModel.countryLoading.observe(viewLifecycleOwner){loading->
            loading?.let {
                if(it){
                    binding.countryList.visibility = View.GONE
                    binding.countryLoading.visibility = View.VISIBLE
                    binding.countryError.visibility = View.GONE
                }
                else{
                    binding.countryLoading.visibility = View.GONE
                }
            }

        }
    }

}