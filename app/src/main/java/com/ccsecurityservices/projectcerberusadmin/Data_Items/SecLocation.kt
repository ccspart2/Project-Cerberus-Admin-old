package com.ccsecurityservices.projectcerberusadmin.Data_Items

import java.io.Serializable

data class SecLocation(

    var id: String = "",
    var name: String = "",
    var entrances: Int = 0,
    var positions: Int = 0,
    var suggestedCount: Int = 0
) : Serializable

object DummyLocations {
    val dLocations = listOf(
        SecLocation("", "Choliseo", 10, 100, 200),
        SecLocation("", "Bahia Urbana", 4, 5, 25)
    )
}