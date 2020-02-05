package com.ccsecurityservices.projectcerberusadmin.data_items

import java.io.Serializable

data class Employee(

    var id: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var adminRights: Boolean = false,
    var phone: String = "",
    var photoId: String = "",
    var ranking: Int = 0,
    var hasApp: Boolean = false,
    var attendanceList: MutableList<Attendance> = mutableListOf()
) : Serializable


//object DummyEmployees {
//
//    val dEmployees = listOf(
//        Employees("1","Charlie1", "Castro",
//            "ccspart2@gmail.com", true,"787-509-1818", null,
//            null,false)
//    )
//}
