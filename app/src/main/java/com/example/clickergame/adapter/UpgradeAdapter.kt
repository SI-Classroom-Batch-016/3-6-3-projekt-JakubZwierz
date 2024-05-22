package com.example.clickergame.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.clickergame.data.model.Structure
import com.example.clickergame.data.model.Upgrade
import com.example.clickergame.databinding.ListUpgradeBinding

// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// Not sure if I should do this with adapter.

class UpgradeAdapter(

    private val dataset: List<Upgrade>,
    private val navController: NavController,
//    private val resourceUpdate: (Int) -> Unit,
) : RecyclerView.Adapter<UpgradeAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ListUpgradeBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ListUpgradeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val upgrade = dataset[position]

        holder.binding.upgradeName1.text = upgrade.upgradeName
        holder.binding.upgradeDescription1.text = upgrade.upgradeDescription
        holder.binding.upgradeIcon1.setImageResource(upgrade.upgradeImage)

    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}

