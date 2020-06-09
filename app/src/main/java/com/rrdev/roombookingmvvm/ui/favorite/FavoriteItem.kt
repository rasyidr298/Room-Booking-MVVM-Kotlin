package com.rrdev.roombookingmvvm.ui.favorite

import com.rrdev.roombookingmvvm.R
import com.rrdev.roombookingmvvm.data.db.entities.Rooms
import com.rrdev.roombookingmvvm.data.db.entities.RoomsFavorite
import com.rrdev.roombookingmvvm.databinding.ItemFavoriteBinding
import com.rrdev.roombookingmvvm.databinding.ItemHomeBinding
import com.xwray.groupie.databinding.BindableItem

class FavoriteItem (
    val roomsFavorite: RoomsFavorite
): BindableItem<ItemFavoriteBinding>(){

    override fun getLayout() = R.layout.item_favorite

    override fun bind(viewBinding: ItemFavoriteBinding, position: Int) {
        viewBinding.setRoomFav(roomsFavorite)
    }

}