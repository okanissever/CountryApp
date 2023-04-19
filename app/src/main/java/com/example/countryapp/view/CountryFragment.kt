package com.example.countryapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.countryapp.R
import com.example.countryapp.databinding.FragmentCountryBinding
import com.example.countryapp.viewmodel.CountryViewModel
import com.example.countryapp.common.viewBinding
import com.example.countryapp.util.downloadFromUrl
import com.example.countryapp.util.placeHolderProgressBar


class CountryFragment : Fragment(R.layout.fragment_country) {
    private val binding by viewBinding(FragmentCountryBinding::bind)
    private val viewModel: CountryViewModel by viewModels()
    private var countryUuid = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            countryUuid = CountryFragmentArgs.fromBundle(it).countryUuid
        }
        viewModel.getDataFromRoom(countryUuid)
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.countryLiveData.observe(viewLifecycleOwner){country->
            country?.let {
                binding.apply {
                    countryName.text = country.countryName
                    countryCapital.text = country.countryCapital
                    countryCurrency.text = country.countryCurrency
                    countryLanguage.text = country.countryLanguage
                    countryRegion.text = country.countryRegion
                    context?.let {
                        countryImage.downloadFromUrl(country.imageUrl, placeHolderProgressBar(it))
                    }
                }

            }
        }
    }


}