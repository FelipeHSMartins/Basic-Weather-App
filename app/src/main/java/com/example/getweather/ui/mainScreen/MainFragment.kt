package com.example.getweather.ui.mainScreen

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.getweather.R
import com.example.getweather.data.dataSource.cache.WeatherDatabase
import com.example.getweather.data.repositories.WeatherRepository
import com.example.getweather.databinding.FragmentMainBinding
import java.util.*

class MainFragment : Fragment() {

    lateinit var binding: FragmentMainBinding
    lateinit var application: Application

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false)
        // View Model
        application = requireNotNull(this.activity).application
        val dataSource = WeatherDatabase.getInstance(application)
        val repository = WeatherRepository(dataSource)
        val viewModelFactory = Factory(application, repository)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.mainViewModel = viewModel


        // Change City Button
        val changeCityButton = binding.changeCityButton
        changeCityButton.setOnClickListener {
            if (viewModel.changeCityOptionShowing) {
                hideChangeCityInterface()
            }
            else {
                showChangeCityInterface()
            }
            viewModel.updateCityOptionStatus()
        }

        // Confirm City Change Button
        binding.confirmCityChangeButton.setOnClickListener {
            val cityInput = binding.editCityNameEdittext.text.toString()
            if (viewModel.cityInputIsValid(cityInput)) {
                viewModel.changeCity(cityInput)
                hideChangeCityInterface()
            }
            else {
                Toast.makeText(context, R.string.invalid_city_toast, Toast.LENGTH_LONG).show()
            }
        }


        return binding.root
    }

    // Show city-changing options
    private fun showChangeCityInterface() {
        binding.confirmCityChangeButton.visibility = View.VISIBLE
        binding.editCityNameEdittext.visibility = View.VISIBLE
    }

    // Hide city-changing options
    private fun hideChangeCityInterface() {
        binding.confirmCityChangeButton.visibility = View.GONE
        binding.editCityNameEdittext.visibility = View.GONE
    }

}