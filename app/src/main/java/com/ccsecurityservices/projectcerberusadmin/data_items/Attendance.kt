package com.ccsecurityservices.projectcerberusadmin.data_items

import java.io.Serializable

data class Attendance(
    var id: String = "",
    var employeeId: String = "",
    var status: String = "",
    var timeOfInvitation: String = "",
    var confirmationTime: String = "",
    var rejectionTime: String = ""
) : Serializable