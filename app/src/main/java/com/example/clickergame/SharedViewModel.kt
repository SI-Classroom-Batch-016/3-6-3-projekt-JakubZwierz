package com.example.clickergame

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clickergame.adapter.StructureAdapter
import com.example.clickergame.data.ResourceRepository
import com.example.clickergame.data.StructureRepository
import com.example.clickergame.data.UpgradeRepository
import com.example.clickergame.data.model.Resource
import com.example.clickergame.data.model.Structure
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {

//----------------------
// Structures
//----------------------

    val structureID: String = ""

    // List of structures that are available to purchase.
    val listOfStructures: Array<Structure> = StructureRepository().structureList.toTypedArray()

    // List of structures that have been purchased in game for a specific area.
    // The number of structures must equal the number of the available slots.
    private val _listOfPlacedStructures = MutableLiveData(
        arrayOf(
            Structure("", 0, 0, "csite_1", R.drawable.tools_transparent, true),
            Structure("", 0, 0, "csite_2", R.drawable.tools_transparent, true),
            Structure("", 0, 0, "csite_3", R.drawable.tools_transparent, true),
            Structure("", 0, 0, "csite_4", R.drawable.tools_transparent, true),
            Structure("", 0, 0, "csite_5", R.drawable.tools_transparent, true),
            Structure("", 0, 0, "csite_6", R.drawable.tools_transparent, true),
            Structure("", 0, 0, "csite_7", R.drawable.tools_transparent, true),
            Structure("", 0, 0, "csite_8", R.drawable.tools_transparent, true),
        )
    )

    val listOfPlacedStructures: LiveData<Array<Structure>>
        get() = _listOfPlacedStructures


    //    This function check if user has resources available to purchase another structure and then places the structure on the selected spot.
    fun placeStructure(structure: Structure, ID: String) {

//        First we are defining how many resources are available.
        val resourceAmount: Int = resource1.value!!.amount.toString().toInt()

//    We are defining the ID for the construction slot. We are doing it by loading whole list of construction slots and then we are filtering the list by IDs.
//    If the ID provided in the function parameter is correct, meaning equals the ID of the slot, then the index of that slot is selected.
        val currentList = _listOfPlacedStructures.value
        val correctStructureSlot = currentList!!.indexOfFirst {
            it.ID == ID
//            We are checking if the index is not empty, to make sure that correct ID has been found. Then we check if there are enough resources.
        }
        if (correctStructureSlot != -1 && resourceAmount >= structure.woodCost) {

            Log.d(
                "Placing Structure",
                "Available resources: $resourceAmount Resource cost : ${structure.woodCost}"
            )
//  Next we are creating a variable for a temporary list, that will also be editable.
//            We are replacing the correct entry in that list using the Index we got earlier.
//            We also want to keep the old ID , so we are replacing this entries ID with the ID provided in the parameter.
//            Then we are converting our list back to array and replace the old list.
            val updatedList = currentList.toMutableList()
            updatedList[correctStructureSlot] = structure
            currentList[correctStructureSlot].ID = ID
//            We also need to change the value for the empty slot. This is required for the ConstructionAreaFragment to indicate that nothing else can be build on that slot.
            currentList[correctStructureSlot].emptySlot = false
            _listOfPlacedStructures.value = updatedList.toTypedArray()

//            Here we are implementing what is actually happening when the building is constructed.
            if (resourceAmount >= structure.woodCost) {
                resource1growth += 2
//            We also need to update out live data. In this case we are removing the structure resource cost.
                _resource1.value?.let { resource ->
                    resource.amount -= structure.woodCost
                    _resource1.postValue(resource)
                }
                addLumberMill()

//                And then we are restarting our coroutine which is generating the resource.
                generatingWoodJob?.cancel()
                generateWood()
            }

            Log.d(
                "Placing Structure",
                "Structure name: ${updatedList[correctStructureSlot].name}, Structure ID: ${updatedList[correctStructureSlot].ID}"
            )
        } else {
            Log.d("Placing Structure", "Index not found")
        }
    }

    val structureList: List<Structure> = StructureRepository().structureList

    var structureIndex: Int = 0

    private var _structure = MutableLiveData<Structure>(structureList[0])
    val structure: LiveData<Structure>
        get() = _structure


//----------------------
// Resources & Materials
//----------------------

    //    Coroutines Jobs

    private var generatingWoodJob: Job? = null

    //    Resource variables

    private val resourceList: MutableList<Resource> = ResourceRepository().resources

    // Resource storage

    val maximumWoodStored: Int = 400

    // Wood generation
    private var powerSawActive: Boolean = false
    private var lumberMillCount: Int = 0

    private var resource1growth: Int = 0
//    private var totalResource1growth : Int = 0

    private var _totalResource1growth = MutableLiveData<Int>()
    val totalResource1growth: LiveData<Int>
        get() = _totalResource1growth

    fun updateTotalResource1Growth() {
        _totalResource1growth.value = if (!powerSawActive) {
            resource1growth
        } else {
            resource1growth + lumberMillCount * 2
        }
    }


    private var resourcePerClick: Int = 1

    private var _resource1 = MutableLiveData<Resource>(resourceList[0])
    val resource1: LiveData<Resource>
        get() = _resource1

    private var _resource2 = MutableLiveData<Resource>(resourceList[1])
    val resource2: LiveData<Resource>
        get() = _resource2

    private var _resource3 = MutableLiveData<Resource>(resourceList[2])
    val resource3: LiveData<Resource>
        get() = _resource3

    fun addWoodOnClick() {
        _resource1.value?.let { resource ->
            resource.amount += resourcePerClick
            _resource1.postValue(resource)
        }
    }

    fun addStoneOnClick() {
        _resource2.value?.let { resource ->
            resource.amount += 1
            _resource2.postValue(resource)
        }
    }

    fun increaseAmountWood() {
        if (maximumWoodStored > _resource1.value!!.amount) {
            _resource1.value?.let { resource ->
                resource.amount += totalResource1growth.value!!
                _resource1.postValue(resource)
            }
        }
    }

    fun increaseAmountStone() {
        _resource2.value?.let { resource ->
            resource.amount += totalResource1growth.value!!
            _resource2.postValue(resource)
        }
    }

    fun generateWood() {
        generatingWoodJob?.cancel()

        generatingWoodJob = viewModelScope.launch {
            while (true) {
                delay(200)
                increaseAmountWood()
            }
        }
    }

//    Market functions

    fun exchangeWoodForGold() {
        _resource1.value?.let { resource ->
            resource.amount -= 100
            _resource1.postValue(resource)
        }
        _resource3.value?.let { resource ->
            resource.amount += 25
            _resource3.postValue(resource)
        }
    }

    fun exchangeStoneForGold() {
        _resource2.value?.let { resource ->
            resource.amount -= 100
            _resource2.postValue(resource)
        }
        _resource3.value?.let { resource ->
            resource.amount += 40
            _resource3.postValue(resource)
        }
    }

//    Character upgrades

    fun increaseResourcePerClickBy1() {
        resourcePerClick++

        _resource3.value?.let { resource ->
            resource.amount -= UpgradeRepository().listOfUpgrades[0].upgradeCost
            _resource3.postValue(resource)
        }
    }

    fun increaseResourcePerClickBy5() {
        resourcePerClick += 5
        _resource3.value?.let { resource ->
            resource.amount -= UpgradeRepository().listOfUpgrades[1].upgradeCost
            _resource3.postValue(resource)
        }
    }

    fun upgradePowerSaws() {
        powerSawActive = true
        updateTotalResource1Growth()
        _resource3.value?.let { resource ->
            resource.amount -= UpgradeRepository().listOfUpgrades[2].upgradeCost
            _resource3.postValue(resource)
        }
    }

    fun addLumberMill() {
        lumberMillCount++
        updateTotalResource1Growth()
    }
}