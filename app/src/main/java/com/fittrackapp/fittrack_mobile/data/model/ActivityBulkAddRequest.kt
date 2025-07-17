package com.fittrackapp.fittrack_mobile.data.model

import com.fittrackapp.fittrack_mobile.domain.model.Activity

data class ActivityBulkAddRequest(
    val activities: List<Activity>,
)