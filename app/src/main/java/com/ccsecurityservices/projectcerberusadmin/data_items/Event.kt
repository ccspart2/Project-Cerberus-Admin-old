package com.ccsecurityservices.projectcerberusadmin.data_items

import java.io.Serializable

data class Event(
    var id: String = "",
    var name: String = "",
    var locationId: String = "",
    var eventTime: String = "",
    var photoId: String = "",
    var duration: String = "",
    var state: String = "",
    var filled: Boolean = false,
    var headcount: Int = 0,
    var attendanceList: MutableList<Attendance> = mutableListOf()
) : Serializable