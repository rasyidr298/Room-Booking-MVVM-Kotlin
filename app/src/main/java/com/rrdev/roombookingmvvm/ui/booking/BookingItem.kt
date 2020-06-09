package com.rrdev.roombookingmvvm.ui.booking

import com.rrdev.roombookingmvvm.R
import com.rrdev.roombookingmvvm.data.db.entities.Booking
import com.rrdev.roombookingmvvm.databinding.ItemBookingBinding
import com.xwray.groupie.databinding.BindableItem

class BookingItem (
    val booking:Booking
): BindableItem<ItemBookingBinding>(){

    override fun getLayout() = R.layout.item_booking

    override fun bind(viewBinding: ItemBookingBinding, position: Int) {
        viewBinding.setBooking(booking)
    }

}