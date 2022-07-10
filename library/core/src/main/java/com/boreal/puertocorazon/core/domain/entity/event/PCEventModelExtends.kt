package com.boreal.puertocorazon.core.domain.entity.event

import com.boreal.puertocorazon.core.utils.getFormat
import com.boreal.puertocorazon.core.utils.getHourAMPM


fun PCEventModel.getCity() =
    (addressPlace.substring(
        addressPlace.indexOf(',') + 1,
        addressPlace.indexOfLast { it == ',' }).trim()).substringBeforeLast(",")

fun PCEventModel.getHourEvent() =
    if (schedule.isNotEmpty()) {
        with(schedule.first()) {
            "${getHourAMPM(timestamp = startTime)} - ${getHourAMPM(timestamp = endTime)}"
        }
    } else {
        ""
    }

fun PCEventModel.getDateEvent() =
    if (schedule.isNotEmpty()) {
        with(schedule.first()) {
            startTime.getFormat("EEEE dd MMM yy")
        }
    } else {
        ""
    }

fun PCEventModel.getAddressLatLng() = "${place.latitude}, ${place.longitude}"