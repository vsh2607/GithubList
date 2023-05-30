package com.example.realfinalgithubproject.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.realfinalgithubproject.adapter.UserAdapter
import com.example.realfinalgithubproject.data.model.GithubUser
import com.example.realfinalgithubproject.databinding.FollowFragmentBinding


class FollowingFragment: Fragment() {

    private var _binding: FollowFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FollowingViewModel
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = arguments
        username = args?.getString(UserDetailActivity.EXTRA_USERNAME).toString()
        _binding = FollowFragmentBinding.inflate(inflater, container, false)

        showLoading(true)
        viewModel = ViewModelProvider(this)[FollowingViewModel::class.java]
        viewModel.getFollowing(username)
        viewModel.listFollowing.observe(viewLifecycleOwner) {
            if(it.isEmpty()){
                    binding.tvPageInfo.visibility = View.VISIBLE
                    showLoading(false)
            }else{
                getGithubUserList(it)
            }
        }
        return binding.root
    }

    private fun getGithubUserList(userList: List<GithubUser>) {
        adapter = UserAdapter(userList)
        binding.rvUser.layoutManager = LinearLayoutManager(activity)
        showLoading(false)
        binding.rvUser.adapter = adapter
        adapter.setOnClickCallBack(object : UserAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: GithubUser) {
                val intent = Intent(requireContext(), UserDetailActivity::class.java)
                intent.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                intent.putExtra(UserDetailActivity.EXTRA_URL, data.avatarUrl)
                intent.putExtra(UserDetailActivity.EXTRA_ID, data.id)
                startActivity(intent)
            }

        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbProgressBar.visibility = View.VISIBLE
        } else {
            binding.pbProgressBar.visibility = View.GONE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}