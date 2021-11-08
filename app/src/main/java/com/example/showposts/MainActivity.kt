package com.example.showposts

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.showposts.data.Model
import com.example.showposts.data.Post
import com.example.showposts.databinding.ActivityMainBinding
import com.example.showposts.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private val allPost:ArrayList<Post> = ArrayList()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.apply {
            adapter = PostAdapter(allPost)
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }
        binding.recyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        getAllPost()
    }
    private fun getAllPost() {

        val call: Call<Model> = RetrofitClient().getApi().getPosts()
        call.enqueue(object : Callback<Model> {
            override fun onResponse(call: Call<Model>, response: Response<Model>) {
                val data: Model? = response.body()
                if (data != null) {
                    allPost.addAll(data.data)
                }
                binding.progressBar.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Model>, t: Throwable?) {
                Toast.makeText(applicationContext, t?.message.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }
}