package com.rrdev.roombookingmvvm.ui.home

import com.rrdev.roombookingmvvm.R
import com.rrdev.roombookingmvvm.data.db.entities.Rooms
import com.rrdev.roombookingmvvm.databinding.ItemHomeBinding
import com.xwray.groupie.databinding.BindableItem

class HomeItem (
    val rooms: Rooms
): BindableItem<ItemHomeBinding>(){

    override fun getLayout() = R.layout.item_home

    override fun bind(viewBinding: ItemHomeBinding, position: Int) {
        viewBinding.setRoom(rooms)
    }

}