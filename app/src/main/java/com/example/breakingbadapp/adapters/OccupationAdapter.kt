package com.example.breakingbadapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingbadapp.databinding.OccupationRowLayoutBinding
import com.example.breakingbadapp.utils.ListDiffUtil

class OccupationAdapter : RecyclerView.Adapter<OccupationAdapter.OccupationViewHolder>() {

    private var occupations = emptyList<String>()

    class OccupationViewHolder(
        private val binding: OccupationRowLayoutBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(occupation: String) {
            binding.occupation = occupation
            binding.executePendingBindings()
        }

        companion object {

            fun from(parent: ViewGroup): OccupationViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OccupationRowLayoutBinding.inflate(layoutInflater, parent, false)
                return OccupationViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OccupationViewHolder {
        return OccupationViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: OccupationViewHolder, position: Int) {
        holder.bind(occupations[position])
    }

    override fun getItemCount(): Int {
        return occupations.size
    }

    fun setData(newData: List<String>) {
        val listDiffUtil = ListDiffUtil(occupations, newData)
        val diffUtilResult = DiffUtil.calculateDiff(listDiffUtil)
        occupations = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}