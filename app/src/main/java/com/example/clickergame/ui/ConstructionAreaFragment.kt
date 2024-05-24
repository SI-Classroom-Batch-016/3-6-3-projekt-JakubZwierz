package com.example.clickergame.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.example.clickergame.R
import com.example.clickergame.SharedViewModel
import com.example.clickergame.data.model.Structure
import com.example.clickergame.databinding.FragmentBuildingAreaCentralBinding
import com.example.clickergame.databinding.FragmentBuildingAreaNorthBinding

class ConstructionAreaFragment : Fragment() {

//    private val args: ConstructionAreaFragmentArgs by navArgs()

    private val viewModel: SharedViewModel by activityViewModels()

//    For each layout that can be "switched" into, we need to create a binding variable.
//    The currentBinding is the actual active binding. Since we are switching between binding,
//    we will have to using casting to be able to bind elements that are defined in the corresponding
//    layout.

//    TODO
//    Is it possible to set casting for the whole fragment and then just change castings ?

    private lateinit var currentBinding: ViewBinding
    private lateinit var bindingAreaCentral: FragmentBuildingAreaCentralBinding
    private lateinit var bindingAreaNorth: FragmentBuildingAreaNorthBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = arguments?.let { ConstructionAreaFragmentArgs.fromBundle(it) }

        var layout_ID: String? = args?.layoutID ?: "home"

        bindingAreaCentral = FragmentBuildingAreaCentralBinding.inflate(inflater, container, false)
        bindingAreaNorth = FragmentBuildingAreaNorthBinding.inflate(inflater, container, false)

        currentBinding = when (layout_ID) {
            "home" -> {
                bindingAreaCentral
            }

            "north" -> {
                bindingAreaNorth
            }

            else -> {
                bindingAreaCentral
            }
        }
//        currentBinding = bindingAreaCentral
        return currentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        In the onViewCreated lifecycle, we want to bind observers and buttons for the in initial binding.
//        The initial binding is defined in the currentBinding variable.
//        We need to set everything as functions, because the bindings will change every time we
//        change the layout.

        setupResourceObservers()
        setupConstructionSiteObservers()
        setupButtonListeners()
        setupResourceGrowthObserver()
    }

//    Resource observers
//    This functions sets up the resource amount observers.

    private fun setupResourceObservers() {
        viewModel.resource1.observe(viewLifecycleOwner) { resource ->
            when (currentBinding) {
                is FragmentBuildingAreaCentralBinding -> {
                    (currentBinding as FragmentBuildingAreaCentralBinding).resource1Count.text =
                        resource.amount.toString()
                }

                is FragmentBuildingAreaNorthBinding -> {
                    (currentBinding as FragmentBuildingAreaNorthBinding).resource1Count.text =
                        resource.amount.toString()
                }
            }
        }

        viewModel.resource2.observe(viewLifecycleOwner) { resource ->
            when (currentBinding) {
                is FragmentBuildingAreaCentralBinding -> {
                    (currentBinding as FragmentBuildingAreaCentralBinding).resource2Count.text =
                        resource.amount.toString()
                }

                is FragmentBuildingAreaNorthBinding -> {
                    (currentBinding as FragmentBuildingAreaNorthBinding).resource2Count.text =
                        resource.amount.toString()
                }
            }
        }

        viewModel.resource3.observe(viewLifecycleOwner) { resource ->
            when (currentBinding) {
                is FragmentBuildingAreaCentralBinding -> {
                    (currentBinding as FragmentBuildingAreaCentralBinding).resource3Count.text =
                        resource.amount.toString()
                }

                is FragmentBuildingAreaNorthBinding -> {
                    (currentBinding as FragmentBuildingAreaNorthBinding).resource3Count.text =
                        resource.amount.toString()
                }
            }
        }
    }

    //    Resource Growth Observers
//    This function sets up resource growth seconds observers.
    private fun setupResourceGrowthObserver() {

        viewModel.totalResource1growth.observe(viewLifecycleOwner) { resourceGrowth ->
            when (currentBinding) {
                is FragmentBuildingAreaCentralBinding -> {
                    (currentBinding as FragmentBuildingAreaCentralBinding).resource1Growth.text =
                        "+" + resourceGrowth.toString()
                }

                is FragmentBuildingAreaNorthBinding -> {
                    (currentBinding as FragmentBuildingAreaNorthBinding).resource1Growth.text =
                        "+" + resourceGrowth.toString()
                }
            }
        }
        viewModel.totalResource2growth.observe(viewLifecycleOwner) { resourceGrowth ->
            when (currentBinding) {
                is FragmentBuildingAreaCentralBinding -> {
                    (currentBinding as FragmentBuildingAreaCentralBinding).resource2Growth.text =
                        "+" + resourceGrowth.toString()
                }

                is FragmentBuildingAreaNorthBinding -> {
                    (currentBinding as FragmentBuildingAreaNorthBinding).resource2Growth.text =
                        "+" + resourceGrowth.toString()
                }
            }
        }
    }

//  Construction Slots Observers

    //    This is function which will setup observers, that will track contents of the building slots.
//    The main list is saved in shared view model.
    private fun setupConstructionSiteObservers() {
        viewModel.listOfPlacedStructures.observe(viewLifecycleOwner) { structures ->
            updateConstructionSite(structures, 0)
            updateConstructionSite(structures, 1)
            updateConstructionSite(structures, 2)
            updateConstructionSite(structures, 3)
            updateConstructionSite(structures, 4)
            updateConstructionSite(structures, 5)
            updateConstructionSite(structures, 6)
            updateConstructionSite(structures, 7)
        }
    }

    //    This function binds the layout elements and populates the information from the structure list
//    that is being observed in the setupConstructionSiteObservers function.
//    Each number corresponds the slot in the structure list.
    private fun updateConstructionSite(structures: Array<Structure>, index: Int) {
        when (currentBinding) {
            is FragmentBuildingAreaCentralBinding -> {
                val binding = currentBinding as FragmentBuildingAreaCentralBinding
                when (index) {
                    0 -> {
                        binding.constructionSite1.setImageResource(structures[index].imageID)
                        setupConstructionSiteClickListener(
                            binding.constructionSite1,
                            structures,
                            index
                        )
                    }

                    1 -> {
                        binding.constructionSite2.setImageResource(structures[index].imageID)
                        setupConstructionSiteClickListener(
                            binding.constructionSite2,
                            structures,
                            index
                        )
                    }

                    2 -> {
                        binding.constructionSite3.setImageResource(structures[index].imageID)
                        setupConstructionSiteClickListener(
                            binding.constructionSite3,
                            structures,
                            index
                        )
                    }

                    3 -> {
                        binding.constructionSite4.setImageResource(structures[index].imageID)
                        setupConstructionSiteClickListener(
                            binding.constructionSite4,
                            structures,
                            index
                        )
                    }
                }
            }

            is FragmentBuildingAreaNorthBinding -> {
                val binding = currentBinding as FragmentBuildingAreaNorthBinding
                when (index) {
                    4 -> {
                        binding.constructionSite5.setImageResource(structures[index].imageID)
                        setupConstructionSiteClickListener(
                            binding.constructionSite5,
                            structures,
                            index
                        )
                    }

                    5 -> {
                        binding.constructionSite6.setImageResource(structures[index].imageID)
                        setupConstructionSiteClickListener(
                            binding.constructionSite6,
                            structures,
                            index
                        )
                    }

                    6 -> {
                        binding.constructionSite7.setImageResource(structures[index].imageID)
                        setupConstructionSiteClickListener(
                            binding.constructionSite7,
                            structures,
                            index
                        )
                    }

                    7 -> {
                        binding.constructionSite8.setImageResource(structures[index].imageID)
                        setupConstructionSiteClickListener(
                            binding.constructionSite8,
                            structures,
                            index
                        )
                    }

                }
            }
        }
    }

    //    This function sets up the clickable functionality for each of the slots. However only if
//    the slots is not already occupied.
//    If the slot is empty, the button will navigate to the construction menu and will also provide
//    the ID which is required for identifying the correct construction slot.
    private fun setupConstructionSiteClickListener(
        view: View,
        structures: Array<Structure>,
        index: Int
    ) {
        val structure = structures[index]
        if (structure.emptySlot) {
            view.setOnClickListener {
                val constructionSiteID = structure.ID
                Log.d(
                    "Construction Area - Passing Data",
                    "ID passed to Selection Fragment : $constructionSiteID"
                )
                findNavController().navigate(
                    ConstructionAreaFragmentDirections.actionConstructionAreaFragmentToStructureSelectionFragment(
                        constructionSiteID
                    )
                )
            }
        } else {
            view.isClickable = false
        }
    }

//  This function sets up the buttons for layout "navigation".

    private fun setupButtonListeners() {
        when (currentBinding) {
            is FragmentBuildingAreaCentralBinding -> {
                val binding = currentBinding as FragmentBuildingAreaCentralBinding
                if (viewModel.constructionAreaNorthUnlocked) {
                    binding.navigateNorth.setOnClickListener {
                        var action =
                            ConstructionAreaFragmentDirections.actionConstructionAreaFragmentSelf()
                        action.layoutID = "north"
                        findNavController().navigate(action)
                    }
                } else {
                    binding.navigateNorth.isVisible = false
                }
            }

            is FragmentBuildingAreaNorthBinding -> {
                val binding = currentBinding as FragmentBuildingAreaNorthBinding
                if (viewModel.constructionAreaNorthUnlocked) {
                    binding.navigateCentral.setOnClickListener {
                        var action =
                            ConstructionAreaFragmentDirections.actionConstructionAreaFragmentSelf()
                        action.layoutID = "home"
                        findNavController().navigate(action)
                    }
                } else {
                    binding.navigateCentral.isClickable = true
                }
            }
        }
    }
}
