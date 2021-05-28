package com.example.breakingbadapp.models


import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("char_id")
    val charId: Int,
    @SerializedName("appearance")
    val appearance: List<Float>?,
    @SerializedName("img")
    val img: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("occupation")
    val occupation: List<String>?,
    @SerializedName("status")
    val status: String?
)