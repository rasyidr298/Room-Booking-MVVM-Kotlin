package com.rrdev.roombookingmvvm.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rrdev.mvvmtrial.data.db.AppDatabase
import com.rrdev.roombookingmvvm.R
import com.rrdev.roombookingmvvm.data.db.entities.Booking
import com.rrdev.roombookingmvvm.data.network.MyApi
import com.rrdev.roombookingmvvm.data.network.NetworkConnectionInterceptor
import com.rrdev.roombookingmvvm.data.repositories.BookingRepository
import com.rrdev.roombookingmvvm.util.Coroutines
import com.rrdev.roombookingmvvm.util.hide
import com.rrdev.roombookingmvvm.util.show
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.booking_fragment.*

class BookingFragment : Fragment() {

    private lateinit var viewModel: BookingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.booking_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val networkConnectionInterceptor = NetworkConnectionInterceptor(requireContext())
        val api = MyApi(networkConnectionInterceptor)
        val db = AppDatabase(requireContext())
        val repository = BookingRepository(api,db)
        val factory = BookingViewModelFactory(repository)

        viewModel = ViewModelProviders.of(this,factory).get(BookingViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = Coroutines.main {
        pbBooking.show()
        viewModel.booking.await().observe(this, Observer {
            pbBooking.hide()
            initRecyclerView(it.toBookingItem())
        })
    }

    private fun initRecyclerView(bookingItem: List<BookingItem>){
        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(bookingItem)
        }
        rvBooking.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun List<Booking>.toBookingItem() : List<BookingItem>{
        return this.map {
            BookingItem(it)
        }
    }

}
