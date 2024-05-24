package com.example.clickergame.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.clickergame.R
import com.example.clickergame.SharedViewModel
import com.example.clickergame.data.ResourceRepository
import com.example.clickergame.databinding.FragmentGameScreenBinding

class GameScreenFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()

    private lateinit var binding: FragmentGameScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Resource Tracking - Resource Bar

        viewModel.resource1.observe(viewLifecycleOwner) { resource ->
            binding.resource1Count.text = resource.amount.toString()
        }

        viewModel.resource2.observe(viewLifecycleOwner) { resource ->
            binding.resource2Count.text = resource.amount.toString()
        }

        viewModel.resource3.observe(viewLifecycleOwner) { resource ->
            binding.resource3Count.text = resource.amount.toString()
        }

        //        Resource growth observer

        viewModel.totalResource1growth.observe(viewLifecycleOwner) { resourceGrowth ->
            binding.resource1Growth.text =
                "+" + resourceGrowth.toString()
        }
        viewModel.totalResource2growth.observe(viewLifecycleOwner) { resourceGrowth ->
            binding.resource2Growth.text =
                "+" + resourceGrowth.toString()
        }


//        Chopping wood

        binding.buttonCollectResource1.setOnClickListener {
            viewModel.addWoodOnClick()
            binding.buttonCollectResource1.animate()
                .withStartAction {
                    binding.buttonCollectResource1.setImageResource(R.drawable.choping_wood_hit)
                }
                .setDuration(0)
                .withEndAction {
                    binding.buttonCollectResource1.postDelayed({
                        binding.buttonCollectResource1.setImageResource(R.drawable.choping_wood_idle)
                    }, 100)
                }
                .start()
        }

//        Collecting stone

        binding.buttonCollectResource2.setOnClickListener {
            viewModel.addStoneOnClick()
            binding.buttonCollectResource2.animate()
                .withStartAction {
                    binding.buttonCollectResource2.setImageResource(R.drawable.mining_rock_idle)
                }
                .setDuration(0)
                .withEndAction {
                    binding.buttonCollectResource2.postDelayed({
                        binding.buttonCollectResource2.setImageResource(R.drawable.mining_rock_hit)
                    }, 100)
                }
                .start()
        }

        //    Building Castle
        binding.buildButton.setOnClickListener {
            if (binding.castlePart1.isVisible) {
                if (binding.castlePart2.isVisible) {
                    if (binding.castlePart3.isVisible) {
                        if (binding.castlePart4.isVisible) {
                            if (binding.castlePart5.isVisible) {
                                viewModel.buildingCastlePart(binding.castlePart6)
                            } else {
                                viewModel.buildingCastlePart(binding.castlePart5)
                            }
                        } else {
                            viewModel.buildingCastlePart(binding.castlePart4)
                        }
                    } else {
                        viewModel.buildingCastlePart(binding.castlePart3)
                    }
                } else {
                    viewModel.buildingCastlePart(binding.castlePart2)
                }
            } else {
                viewModel.buildingCastlePart(binding.castlePart1)
            }
        }

    }

}