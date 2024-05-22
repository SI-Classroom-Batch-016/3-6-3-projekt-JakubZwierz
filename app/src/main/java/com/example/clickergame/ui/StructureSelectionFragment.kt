package com.example.clickergame.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.clickergame.SharedViewModel
import com.example.clickergame.adapter.StructureAdapter
import com.example.clickergame.data.StructureRepository
import com.example.clickergame.databinding.FragmentBuildingAreaCentralBinding
import com.example.clickergame.databinding.FragmentStructureSelectionBinding

class StructureSelectionFragment : Fragment() {

    private val args: StructureSelectionFragmentArgs by navArgs()

    private val viewModel: SharedViewModel by activityViewModels()
    var resource1amount: Int = 0

    private lateinit var binding: FragmentStructureSelectionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStructureSelectionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val constructionSiteID = args.constructionSiteId

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

        binding.buttonBack.setOnClickListener {
            findNavController().navigate(StructureSelectionFragmentDirections.actionStructureSelectionFragmentToConstructionAreaFragment())
        }

//        val resourceUpdate: (Int) -> Unit = { resourceWood ->
//            resource1amount = resourceWood
//        }

        val navController = findNavController()

//        Recycler View

        val structureRepository = StructureRepository().structureList
        val structureAdapter = StructureAdapter(
            structureRepository,
            viewModel,
            constructionSiteID,
            navController,
            viewModel.resource1
        )

        val recyclerView = binding.recyclerViewStructures
        recyclerView.adapter = structureAdapter

//        UI Buttons
    }

}