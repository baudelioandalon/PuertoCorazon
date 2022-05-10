package com.boreal.puertocorazon.core.extension

import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.google.gson.Gson

inline fun <reified T : AAuthModel> String.toModel()  = Gson().fromJson(
        "{\"" + Gson().toJson(toString()).replace(":", "\":\"")
            .replace("}", "\"}").substringAfter("proxy[")
            .replace("]\"", "").replace("\"https\":\"","\"https:").replace("{", "").replace(",", ",\"")
            .replace("}", "") + "}",
        T::class.java
    )


//proxy[
// {id:0},
// {aud:puertocorazonapp},
// {auth_time:1652223215},
// {dateCreated:0},
// {email:baudelioandalon@gmail.com},
// {name:Baudelio Andalon},
// {email_verified:true},
// {sign_in_provider:GOOGLE},
// {picture:https://lh3.googleusercontent.com/a/AATXAJyKQqntLDk7CPQHxuJHHcNIDDP-lecuSqJ_rcpx=s96-c},
// {exp:1652226815},
// {iat:1652223215},
// {userType:Client},
// {user_id:bVjbbCAA1AgI9jaSkoKeBkdzWJs2}
// ]
