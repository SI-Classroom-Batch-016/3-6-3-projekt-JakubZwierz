package com.example.clickergame.data

import com.example.clickergame.R
import com.example.clickergame.data.model.Upgrade

class UpgradeRepository(
    val listOfUpgrades : List<Upgrade> = listOf(
        Upgrade("Click Boost I","Increases the click power by 1", R.drawable.ic_launcher_background,100),
        Upgrade("Click Boost II","Increases the click power by 5", R.drawable.ic_launcher_background,400),
        Upgrade("Power Saws","Increases wood production for each lumber mill by 2", R.drawable.ic_launcher_background,300)
    )
) {
}