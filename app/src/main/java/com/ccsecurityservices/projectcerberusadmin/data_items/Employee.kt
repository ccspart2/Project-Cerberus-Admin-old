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
    var hasApp: Boolean = false,
    var attendanceList: MutableMap<String, Attendance> = mutableMapOf()
) : Serializable

