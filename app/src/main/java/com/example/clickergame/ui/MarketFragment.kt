package com.example.clickergame.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.clickergame.SharedViewModel
import com.example.clickergame.databinding.FragmentMarketBinding

class MarketFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()

    private lateinit var binding: FragmentMarketBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarketBinding.inflate(layoutInflater)
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

//        Resource exchange
//        Wood

        viewModel.resource1.observe(viewLifecycleOwner) { wood ->
            if (wood.amount >= 100) {
                binding.buttonBuyResource1.isClickable = true
                binding.buttonBuyResource1.setOnClickListener {
                    viewModel.exchangeWoodForGold()
                }
            } else {
                binding.buttonBuyResource1.isClickable = false
            }
        }

//        Stone

        viewModel.resource2.observe(viewLifecycleOwner) { stone ->
            if (stone.amount >= 100) {
                binding.buttonBuyResource2.isClickable = true
                binding.buttonBuyResource2.setOnClickListener {
                    viewModel.exchangeStoneForGold()
                }
            } else {
                binding.buttonBuyResource2.isClickable = false
            }
        }
    }
}
