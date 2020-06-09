package com.rrdev.roombookingmvvm.data.network.responses


import com.google.gson.annotations.SerializedName
import com.rrdev.roombookingmvvm.data.db.entities.Rooms

data class RoomResponse(
    val error: Boolean,
    @SerializedName("room")
    val rooms: List<Rooms>
)