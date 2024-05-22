package com.example.clickergame.data

import com.example.clickergame.data.model.Resource

class ResourceRepository(
    val resources: MutableList<Resource> = mutableListOf(
        Resource("Wood", 400),
        Resource("Stone", 10),
        Resource("Gold", 400),
    )
) {
}