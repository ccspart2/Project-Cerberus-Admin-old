package com.ccsecurityservices.projectcerberusadmin.Data_Items

import java.util.*

data class Employees(

    var ID: String?,
    var FirstName: String,
    var LastName: String,
    var Email: String,
    var AdminRights: Boolean,
    var Phone: String,
    var PhotoId: String?,
    var Ranking: Int?,
    var HasApp: Boolean
)

object DummyEmployees {

    val dEmployees = listOf(
        Employees("1","Charlie1", "Castro",
            "ccspart2@gmail.com", true,"787-509-1818", null,
            null,false)
    )
}
