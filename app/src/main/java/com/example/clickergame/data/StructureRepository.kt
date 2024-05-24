package com.example.clickergame.data

import com.example.clickergame.R
import com.example.clickergame.data.model.Structure

class StructureRepository(
    val structureList : List<Structure> = listOf(
        Structure("Lumber Mill",100,0,"lumber", R.drawable.lumber_mill, false),
        Structure("House",200,150,"generator",R.drawable.house, false),
        Structure("Inn",200,150,"generator",R.drawable.inn, false),
        Structure("Shed",200,150,"generator",R.drawable.shed, false),
        Structure("Mine",200,150,"mine",R.drawable.mine, false),
        Structure("Outpost",200,150,"generator",R.drawable.outpost, false),
        Structure("Quarry",200,40,"quarry",R.drawable.quarry, false),
        Structure("Factory",200,150,"generator",R.drawable.lumber_factory, false),
        Structure("Planks Storage",200,150,"generator",R.drawable.wood_pile, false),
    )
) {
}