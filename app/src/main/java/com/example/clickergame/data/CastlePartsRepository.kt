package com.example.clickergame.data

import com.example.clickergame.data.model.CastlePart

class CastlePartsRepository(
    val castleParts : List<CastlePart> = listOf(
        CastlePart("part_1"),
        CastlePart("part_2"),
        CastlePart("part_3"),
        CastlePart("part_4"),
        CastlePart("part_5"),
        CastlePart("part_6"),
    )
) {
}