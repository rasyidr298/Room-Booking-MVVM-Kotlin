package com.rrdev.roombookingmvvm.data.network.responses


import com.google.gson.annotations.SerializedName
import com.rrdev.roombookingmvvm.data.db.entities.Profile

data class ProfileResponse(
    var text: String?,
    var status: Boolean?,
    @SerializedName("data")
    var profile: Profile
)