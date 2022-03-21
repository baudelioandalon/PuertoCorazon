package com.boreal.puertocorazon.core.utils.corefirestore.errorhandler

enum class CUAuthenticationErrorEnum(
    val defaultError: String,
    val messageError: String,
    val errorCode: Int,
    val reason: String
) {
    ERROR_USER_NOT_FOUND(
        "There is no user record corresponding to this identifier. The user may have been deleted.",
        "No se encontró el correo registrado.",
        1,
        "Aún no está registrado"
    ), ERROR_INVALID_PASSWORD(
        "The password is invalid or the user does not have a password.",
        "Usuario o contraseña invalidos",
        1,
        "Revise el correo y/o la contraseña."
    ),
    ERROR_DEFAULT(
        "Error default",
        "Error desconocido", 0, "Se desconoce la causa"
    ),
}
