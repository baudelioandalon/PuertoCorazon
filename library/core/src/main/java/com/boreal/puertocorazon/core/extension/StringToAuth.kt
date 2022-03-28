package com.boreal.puertocorazon.core.extension

import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.google.gson.Gson

inline fun <reified T : AAuthModel> String.toModel()  = Gson().fromJson(
        "{\"" + Gson().toJson(toString()).replace(":", "\":\"")
            .replace("}", "\"}").substringAfter("proxy[")
            .replace("]\"", "").replace("{", "").replace(",", ",\"")
            .replace("}", "") + "}",
        T::class.java
    )
