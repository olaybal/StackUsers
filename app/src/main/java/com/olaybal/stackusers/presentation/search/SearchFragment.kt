package com.olaybal.stackusers.presentation.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.olaybal.stackusers.R
import com.olaybal.stackusers.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private var binding: FragmentSearchBinding? = null

    private val viewModel: SearchViewModel by viewModels()

    private val userAdapter by lazy {
        UserAdapter { user ->
            val bundle = Bundle().apply {
                putLong("userId", user.id)
            }
            findNavController().navigate(
                R.id.action_searchFragment_to_detailsFragment,
                bundle,
            )
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentBinding = FragmentSearchBinding.bind(view)
        binding = fragmentBinding

        setupRecyclerView()
        setupListeners()
        observeUiState()
    }

    private fun setupRecyclerView() {
        binding?.rvUsers?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }
    }

    private fun setupListeners() {
        binding?.btnSearch?.setOnClickListener {
            val query = binding?.etSearch?.text?.toString().orEmpty()
            viewModel.search(query)
        }
    }

    private fun observeUiState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            render(state)
        }
    }

    private fun render(state: SearchUiState) {
        when (state) {
            SearchUiState.Idle -> {
                showLoading(false)
                showMessage(false)
                userAdapter.submitList(emptyList())
            }

            SearchUiState.Loading -> {
                showLoading(true)
                showMessage(false)
            }

            is SearchUiState.Success -> {
                showLoading(false)
                showMessage(false)
                userAdapter.submitList(state.users)
            }

            SearchUiState.Empty -> {
                showLoading(false)
                userAdapter.submitList(emptyList())
                showMessage(true, "No users found")
            }

            is SearchUiState.Error -> {
                showLoading(false)
                userAdapter.submitList(emptyList())
                showMessage(true, state.message)
            }
        }
    }

    private fun showLoading(isVisible: Boolean) {
        binding?.progressBar?.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showMessage(
        isVisible: Boolean,
        message: String = ""
    ) {
        binding?.tvMessage?.visibility = if (isVisible) View.VISIBLE else View.GONE
        binding?.tvMessage?.text = message
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}