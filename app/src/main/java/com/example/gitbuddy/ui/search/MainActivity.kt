package com.example.gitbuddy.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitbuddy.data.response.User
import com.example.gitbuddy.databinding.ActivityMainBinding
import com.example.gitbuddy.ui.detail.DetailActivity
import com.example.gitbuddy.utils.ConnectionUtils.showError
import com.example.gitbuddy.utils.ConnectionUtils.showLoading

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Thread.sleep(3000)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        adapter = UserAdapter(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USERNAME, data.username)
                startActivity(intent)
            }
        })

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        with(binding) {
            val layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.layoutManager = layoutManager
            rvUser.setHasFixedSize(true)
            val itemDecoration = DividerItemDecoration(this@MainActivity, layoutManager.orientation)
            rvUser.addItemDecoration(itemDecoration)
            rvUser.adapter = adapter

            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchUser()
                    searchView.hide()
                    false
                }


        }
        viewModel.getSearchUsers().observe(this) { searchList ->

            if (searchList != null) {
                adapter.submitList(searchList)
                showError(binding.group, false)
            }

            if(searchList.isEmpty()) {
                showError(binding.group, true)
            }
        }

        viewModel.isLoading.observe(this) {
            showLoading(binding.progressBar, it)
        }

        viewModel.isError.observe(this) {
            showError(binding.group, it)
        }
    }

    private fun searchUser() {
        Log.e("Searching", "searching user...")
        binding.apply {
            val query = searchView.text.toString()
            if(query.isEmpty()) {
                Log.e("isEmpty", "query is empty")
                return
            }

            viewModel.setSearchUser(query)
        }
    }

}