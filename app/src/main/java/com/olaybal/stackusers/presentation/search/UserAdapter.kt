package com.olaybal.stackusers.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.olaybal.stackusers.databinding.ItemUserBinding
import com.olaybal.stackusers.domain.model.User


class UserAdapter(
    private val onUserClick: (User) -> Unit,
) : ListAdapter<User, UserAdapter.UserViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return UserViewHolder(binding, onUserClick)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class UserViewHolder(
        private val binding: ItemUserBinding,
        private val onUserClick: (User) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.tvUserName.text = user.name
            binding.tvReputation.text = "Reputation: ${user.reputation}"

            binding.root.setOnClickListener {
                onUserClick(user)
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}