package com.ccsecurityservices.projectcerberusadmin.data_items

import java.io.Serializable

data class Event(
    var id: String = "",
    var name: String = "",
    var locationId: String = "",
    var eventDate: String = "",
    var eventTime: String = "",
    var photoId: String = "",
    var duration: Int = 0,
    var filled: Boolean = false,
    var headcount: Int = 0,
    var description: String = "",
    var attendanceList: MutableMap<String, Attendance> = mutableMapOf(),
    var eventPassed: Boolean = false

) : Serializable