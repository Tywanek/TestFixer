package com.radlab.testfixer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.radlab.testfixer.data.Rate
import com.radlab.testfixer.databinding.ItemRateBinding


class RatesAdapter( private val list: List<Rate>,  private val callback: OnRateClickListener) : RecyclerView.Adapter<RatesAdapter.HeroViewHolder>() {

    interface OnRateClickListener {
        fun onRateClick(item: Rate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        return HeroViewHolder(
            ItemRateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(list[position],callback)
    }

    class HeroViewHolder(
        private val binding: ItemRateBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(rate: Rate, callback: OnRateClickListener) {
            binding.apply {
                item = rate
                listener = callback
            }
        }
    }
}