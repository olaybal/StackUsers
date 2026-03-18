package com.olaybal.stackusers.presentation.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.olaybal.stackusers.R
import com.olaybal.stackusers.databinding.FragmentDetailsBinding
import com.olaybal.stackusers.domain.model.User
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var binding: FragmentDetailsBinding? = null

    private val viewModel: DetailsViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentBinding = FragmentDetailsBinding.bind(view)
        binding = fragmentBinding

        observeUiState()

        val userId = requireArguments().getLong(ARG_USER_ID)
        viewModel.loadUser(userId)
    }

    private fun observeUiState() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                DetailsUiState.Loading -> showLoading()
                is DetailsUiState.Success -> showUser(state.user)
                is DetailsUiState.Error -> showError(state.message)
            }
        }
    }

    private fun showLoading() {
        binding?.progressBar?.visibility = View.VISIBLE
        binding?.tvError?.visibility = View.GONE
    }

    private fun showUser(user: User) {
        binding?.progressBar?.visibility = View.GONE
        binding?.tvError?.visibility = View.GONE

        binding?.tvName?.text = user.name
        binding?.tvReputation?.text = getString(
            R.string.search_details_reputation,
            user.reputation
        )
        binding?.tvLocation?.text =
            getString(
                R.string.search_details_location,
                user.location ?: "N/A"
            )
        binding?.tvCreationDate?.text =
            getString(
                R.string.search_details_created,
                user.creationDate?.toReadableDate() ?: "N/A"
            )

        Glide.with(this)
            .load(user.avatarUrl)
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(binding!!.ivAvatar)
    }

    private fun showError(message: String) {
        binding?.progressBar?.visibility = View.GONE
        binding?.tvError?.visibility = View.VISIBLE
        binding?.tvError?.text = message
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun Long.toReadableDate(): String {
        val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        return formatter.format(Date(this * 1000))
    }

    private companion object {
        const val ARG_USER_ID = "userId"
    }
}