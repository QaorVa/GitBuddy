package com.example.gitbuddy.ui.followers

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gitbuddy.R
import com.example.gitbuddy.data.response.User
import com.example.gitbuddy.databinding.FragmentFollowBinding
import com.example.gitbuddy.ui.detail.DetailActivity
import com.example.gitbuddy.ui.search.UserAdapter
import com.example.gitbuddy.utils.ConnectionUtils

class FollowersFragment: Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null
    private val binding: FragmentFollowBinding
        get() = _binding ?: throw IllegalStateException("no binding")
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_USERNAME).toString()

        _binding = FragmentFollowBinding.bind(view)

        adapter = UserAdapter(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USERNAME, data.username)
                startActivity(intent)
            }
        })

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
            val layoutManager = LinearLayoutManager(requireActivity())
            val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
            rvUser.addItemDecoration(itemDecoration)
        }

        viewModel = ViewModelProvider(this)[FollowersViewModel::class.java]
        viewModel.setFollowersList(username)
        viewModel.getFollowersList().observe(viewLifecycleOwner) { followersList ->
            if (followersList != null) {
                adapter.submitList(followersList)
                ConnectionUtils.showError(binding.group, false)
            }

            if(followersList.isEmpty()) {
                ConnectionUtils.showError(binding.group, true)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            ConnectionUtils.showLoading(binding.progressBar, it)
        }

        viewModel.isError.observe(viewLifecycleOwner) {
            ConnectionUtils.showError(binding.group, it)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}