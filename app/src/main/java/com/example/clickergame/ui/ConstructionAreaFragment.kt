package com.example.clickergame.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.clickergame.R
import com.example.clickergame.SharedViewModel

import com.example.clickergame.databinding.FragmentBuildingAreaCentralBinding

class ConstructionAreaFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()

    private lateinit var binding: FragmentBuildingAreaCentralBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuildingAreaCentralBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Resource Tracking - Resource Bar

        viewModel.resource1.observe(viewLifecycleOwner){
                resource ->
            binding.resource1Count.text = resource.amount.toString()
        }

        viewModel.resource2.observe(viewLifecycleOwner){
                resource ->
            binding.resource2Count.text = resource.amount.toString()
        }

        viewModel.resource3.observe(viewLifecycleOwner){
                resource ->
            binding.resource3Count.text = resource.amount.toString()
        }

        var slotIsEmpty1: Boolean = true

//        Construction Slots
//        Slot 1
        viewModel.listOfPlacedStructures.observe(viewLifecycleOwner) { structure ->
            binding.constructionSite1.setImageResource(structure[0].imageID)
        }

        viewModel.listOfPlacedStructures.observe(viewLifecycleOwner){structure->
            slotIsEmpty1 = structure[0].emptySlot

            if (slotIsEmpty1) {
                binding.constructionSite1.setOnClickListener {
                    val constructionSiteID = viewModel.listOfPlacedStructures.value!![0].ID
                    Log.d(
                        "Construction Area - Passing Data",
                        "ID passed to Selection Fragement : $constructionSiteID"
                    )
                    findNavController().navigate(
                        ConstructionAreaFragmentDirections.actionConstructionAreaFragmentToStructureSelectionFragment(
                            constructionSiteID
                        )
                    )
                }
            } else {
                binding.constructionSite1.isClickable = false
            }
        }

//        Slot 2
        viewModel.listOfPlacedStructures.observe(viewLifecycleOwner) { structure ->
            binding.constructionSite2.setImageResource(structure[1].imageID)
        }

        viewModel.listOfPlacedStructures.observe(viewLifecycleOwner){structure->
            slotIsEmpty1 = structure[1].emptySlot

            if (slotIsEmpty1) {
                binding.constructionSite2.setOnClickListener {
                    val constructionSiteID = viewModel.listOfPlacedStructures.value!![1].ID
                    Log.d(
                        "Construction Area - Passing Data",
                        "ID passed to Selection Fragement : $constructionSiteID"
                    )
                    findNavController().navigate(
                        ConstructionAreaFragmentDirections.actionConstructionAreaFragmentToStructureSelectionFragment(
                            constructionSiteID
                        )
                    )
                }
            } else {
                binding.constructionSite2.isClickable = false
            }
        }
//        Slot 3
        viewModel.listOfPlacedStructures.observe(viewLifecycleOwner) { structure ->
            binding.constructionSite3.setImageResource(structure[2].imageID)
        }

        viewModel.listOfPlacedStructures.observe(viewLifecycleOwner){structure->
            slotIsEmpty1 = structure[2].emptySlot

            if (slotIsEmpty1) {
                binding.constructionSite3.setOnClickListener {
                    val constructionSiteID = viewModel.listOfPlacedStructures.value!![2].ID
                    Log.d(
                        "Construction Area - Passing Data",
                        "ID passed to Selection Fragement : $constructionSiteID"
                    )
                    findNavController().navigate(
                        ConstructionAreaFragmentDirections.actionConstructionAreaFragmentToStructureSelectionFragment(
                            constructionSiteID
                        )
                    )
                }
            } else {
                binding.constructionSite3.isClickable = false
            }
        }
//        Slot 4
        viewModel.listOfPlacedStructures.observe(viewLifecycleOwner) { structure ->
            binding.constructionSite4.setImageResource(structure[3].imageID)
        }

        viewModel.listOfPlacedStructures.observe(viewLifecycleOwner){structure->
            slotIsEmpty1 = structure[3].emptySlot

            if (slotIsEmpty1) {
                binding.constructionSite4.setOnClickListener {
                    val constructionSiteID = viewModel.listOfPlacedStructures.value!![3].ID
                    Log.d(
                        "Construction Area - Passing Data",
                        "ID passed to Selection Fragement : $constructionSiteID"
                    )
                    findNavController().navigate(
                        ConstructionAreaFragmentDirections.actionConstructionAreaFragmentToStructureSelectionFragment(
                            constructionSiteID
                        )
                    )
                }
            } else {
                binding.constructionSite4.isClickable = false
            }
        }
    }
}