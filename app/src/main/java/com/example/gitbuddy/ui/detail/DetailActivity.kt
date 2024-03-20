package com.example.gitbuddy.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.gitbuddy.R
import com.example.gitbuddy.databinding.ActivityDetailBinding
import com.example.gitbuddy.utils.ConnectionUtils.showError
import com.google.android.material.tabs.TabLayoutMediator
import com.example.gitbuddy.utils.ConnectionUtils.showLoading

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]


        if (username != null) {
            viewModel.setUserDetail(username)
        }

        viewModel.getUserDetail().observe(this) { userDetail ->
            if (userDetail != null) {
                binding.apply {
                    tvDetailName.text = userDetail.name
                    tvDetailUsername.text = userDetail.username
                    if(tvDetailName.text == "") {
                        tvDetailName.text = userDetail.username
                    }
                    tvDetailFollowers.text = " ${userDetail.followers} Followers"
                    tvDetailFollowing.text = " ${userDetail.following} Following"
                    Glide.with(this@DetailActivity)
                        .load(userDetail.avatarUrl)
                        .into(ivDetailImg)
                }

            }

            if(userDetail.username == "" || userDetail.username.isEmpty()) {
                showError(binding.group, true)
            }
        }

        viewModel.isLoading.observe(this) {
            showLoading(binding.progressBar, it)
        }

        viewModel.isError.observe(this) {
            showError(binding.group, it)
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            TabLayoutMediator(tlTab, viewPager) {tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}