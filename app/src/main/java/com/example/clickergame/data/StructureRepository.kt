package com.example.clickergame.data

import com.example.clickergame.R
import com.example.clickergame.data.model.Structure

class StructureRepository(
    val structureList : List<Structure> = listOf(
        Structure("Lumber Mill",100,0,"", R.drawable.lumber_mill, false),
        Structure("Primitive Generator",200,150,"",R.drawable.ic_launcher_background, false),
    )
) {
}