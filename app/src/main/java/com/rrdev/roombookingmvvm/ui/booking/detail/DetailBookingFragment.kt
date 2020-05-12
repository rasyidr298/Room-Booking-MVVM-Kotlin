package com.rrdev.roombookingmvvm.ui.booking.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rrdev.roombookingmvvm.R

class DetailBookingFragment : Fragment() {

    companion object {
        fun newInstance() = DetailBookingFragment()
    }

    private lateinit var viewModel: DetailBookingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_booking_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailBookingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
