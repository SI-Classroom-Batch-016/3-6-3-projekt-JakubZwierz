package com.example.clickergame.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.clickergame.SharedViewModel
import com.example.clickergame.adapter.StructureAdapter
import com.example.clickergame.adapter.UpgradeAdapter
import com.example.clickergame.data.StructureRepository
import com.example.clickergame.data.UpgradeRepository
import com.example.clickergame.databinding.FragmentBuildingAreaCentralBinding
import com.example.clickergame.databinding.FragmentBuildingAreaNorthBinding
import com.example.clickergame.databinding.FragmentStructureSelectionBinding
import com.example.clickergame.databinding.FragmentUpgradeShopBinding

class UpgradeFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()

    private lateinit var binding: FragmentUpgradeShopBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpgradeShopBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        //        Recycler View

//        val upgradeRepository = UpgradeRepository().listOfUpgrades
//        val upgradeAdapter = UpgradeAdapter(upgradeRepository, navController)
//
//        val recyclerView = binding.recyclerViewUpgrades
//        recyclerView.adapter = upgradeAdapter

        val upgradeList = UpgradeRepository().listOfUpgrades

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

//        Resource growth observer

        viewModel.totalResource1growth.observe(viewLifecycleOwner) { resourceGrowth ->
            binding.resource1Growth.text =
                        "+" + resourceGrowth.toString()
                }
        viewModel.totalResource2growth.observe(viewLifecycleOwner) { resourceGrowth ->
            binding.resource2Growth.text =
                        "+" + resourceGrowth.toString()
        }

//        Upgrade Data Bindings
        //        Upgrade 1
        binding.upgradeName1.text = upgradeList[0].upgradeName
        binding.upgradeDescription1.text = upgradeList[0].upgradeDescription

        //        Upgrade 2
        binding.upgradeName2.text = upgradeList[1].upgradeName
        binding.upgradeDescription2.text = upgradeList[1].upgradeDescription

        //        Upgrade 3
        binding.upgradeName3.text = upgradeList[2].upgradeName
        binding.upgradeDescription3.text = upgradeList[2].upgradeDescription

//      Buttons - Buy Upgrades
//        Upgrade 1

        binding.buttonBuyUpgrade1.setOnClickListener {
               viewModel.resource3.observe(viewLifecycleOwner){gold ->
                   if (gold.amount >= upgradeList[0].upgradeCost ){
                       viewModel.increaseResourcePerClickBy1()
                   }
               }
        }

//        Upgrade 2

        binding.buttonBuyUpgrade2.setOnClickListener {
            viewModel.resource3.observe(viewLifecycleOwner){gold ->
                if (gold.amount >= upgradeList[1].upgradeCost ){
                    viewModel.increaseResourcePerClickBy5()
                }
            }
        }

//        Upgrade 3

        binding.buttonBuyUpgrade3.setOnClickListener {
            viewModel.resource3.observe(viewLifecycleOwner){gold ->
                if (gold.amount >= upgradeList[2].upgradeCost ){
                    viewModel.upgradePowerSaws()
                }
            }
        }
//    Buying Areas

        binding.buyAreaNorth.setOnClickListener {
            viewModel.resource3.observe(viewLifecycleOwner){gold ->
                if(gold.amount >= 300){
                    viewModel.unlockArea()
                } else {
                    val information : CharSequence = "Not enough gold!"
                    val duration = Toast.LENGTH_SHORT

                    val toast = Toast.makeText(activity, information , duration)
                    toast.show()
                }
            }

        }
    }
}