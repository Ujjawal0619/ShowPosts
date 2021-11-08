package com.example.showposts.network

import com.example.showposts.data.Model
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("posts")
    fun getPosts(): Call<Model>

    companion object {
        const val BASE_URL = "https://gorest.co.in/public/v1/"
    }
}
