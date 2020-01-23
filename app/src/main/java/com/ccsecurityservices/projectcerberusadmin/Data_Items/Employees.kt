package com.ccsecurityservices.projectcerberusadmin.Data_Items

import java.util.*

data class Employees(

    var ID: String,
    var FirstName: String,
    var LastName: String,
    var Email: String,
    var AdminRights: Boolean,
    var Phone: String,
    var PhotoId: String?,
    var Password: String?,
    var Ranking: Int?,
    var LastInvited: Date?,
    var Active: Boolean,
    var HasApp: Boolean
)

object DummyEmployees {

    val dEmployees = listOf<Employees>(
        Employees("1","Charlie1", "Castro",
            "ccspart2@gmail.com", true,"787-509-1818", null,
            null,null,null,true, true),
        Employees("2","Charlie2", "Castro",
            "ccspart2@gmail.com", true,"787-509-1818", null,
            null,null,null,true, true),
        Employees("3","Charlie3", "Castro",
            "ccspart2@gmail.com", true,"787-509-1818", null,
            null,null,null,true, true),
        Employees("4","Charlie4", "Castro",
            "ccspart2@gmail.com", true,"787-509-1818", null,
            null,null,null,true, true),
        Employees("5","Charlie5", "Castro",
            "ccspart2@gmail.com", true,"787-509-1818", null,
            null,null,null,true, true)
    )
}
