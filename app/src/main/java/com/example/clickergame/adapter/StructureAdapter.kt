package com.example.clickergame.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.clickergame.R
import com.example.clickergame.SharedViewModel
import com.example.clickergame.data.model.Resource
import com.example.clickergame.data.model.Structure
import com.example.clickergame.databinding.ListStructureBinding
import com.example.clickergame.ui.ConstructionAreaFragmentDirections
import com.example.clickergame.ui.StructureSelectionFragmentDirections

class StructureAdapter (
    private val dataset: List<Structure>,
    private val viewModel: SharedViewModel,
    private val constructionSiteID : String,
    private val navController : NavController,
    private val resource1LiveData: LiveData<Resource>,
//    private val resourceUpdate: (Int) -> Unit,
) : RecyclerView.Adapter<StructureAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding: ListStructureBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ListStructureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

//        Log.d("Structure Adapter","Resource 1 amount passed from structure selection : $resource1amount")

        val structure = dataset[position]

        holder.binding.structureName.text = structure.name
        holder.binding.structureCost.text = structure.woodCost.toString()
        holder.binding.structureCostStone.text = structure.stoneCost.toString()
        holder.binding.structureImage.setImageResource(structure.imageID)

//        TODO extract into lambda  function 
//        Observer should not be used in adapter
//        Observer forever is running , independent of view lifecycles

        resource1LiveData.observeForever {
            resource ->
            if (resource.amount>=structure.woodCost){
                holder.binding.buttonBuyStructure.isClickable = true
                holder.binding.buttonBuyStructure.setOnClickListener {
                    viewModel.placeStructure(structure, constructionSiteID)
                    navController.navigate(StructureSelectionFragmentDirections.actionStructureSelectionFragmentToConstructionAreaFragment())

                    val information : CharSequence = "Building constructed!"
                    val duration = Toast.LENGTH_SHORT

                    val toast = Toast.makeText( it.context, information , duration)
                    toast.show()
                }
            } else {
                holder.binding.buttonBuyStructure.isClickable = false
            }
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}