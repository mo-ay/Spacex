package com.ayprotech.spacex.data.network.responses.launche

import androidx.room.Embedded

data class Links(
//    val article: Any,
//    val flickr: Flickr,
    @Embedded
    val patch: Patch,
//    val presskit: Any,
//    val reddit: Reddit,
//    val webcast: Any,
//    val wikipedia: Any,
//    val youtube_id: Any
)