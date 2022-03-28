package com.boreal.puertocorazon.core.utils.corefirestore.errorhandler

import com.google.firebase.firestore.FirebaseFirestoreException

enum class CUFirestoreErrorEnum(
    val defaultError: String,
    val messageError: String,
    val errorCode: Int,
    val reason: String
) {
    ERROR_INVALID_PATH(
        "Invalid document reference. Document references must have an even number of segments",
        "La referencia del documento es invalida", FirebaseFirestoreException.Code.INVALID_ARGUMENT.value(),
        ""
    ),
    ERROR_INVALID_FIELD_PATH(
        "Invalid field path",
        "Campo de ruta invalido", FirebaseFirestoreException.Code.INVALID_ARGUMENT.value(),
        ""
    ),
    ERROR_NOT_FOUND(
        "NOT_FOUND: No document to update:",
        "No se encontró el documento a actualizar.", FirebaseFirestoreException.Code.NOT_FOUND.value(),
        ""
    ),
    ERROR_DESERIALIZE_OBJECT(
        "Could not deserialize object.",
        "Ocurrio un error al deserializar un modelo, favor de validar la integridad de los datos.", 7000,
        ""
    ),
    ERROR_PAYMENT_PAYED(
        "NOT_FOUND: No document to update:",
        "No se pudo completar porque el pago ya fue efectuado.", FirebaseFirestoreException.Code.ABORTED.value(),
        ""
    ),
    ERROR_PERMISSION_DENIED(
        "PERMISSION_DENIED: Missing or insufficient permissions.",
        "No tienes permisos de lectura y/o escritura.", FirebaseFirestoreException.Code.PERMISSION_DENIED.value(),
        ""
    ),
    ERROR_DEFAULT(
        "Error default",
        "Error desconocido", 0, "Se desconoce la causa"
    ),
    ERROR_UNAVAILABLE(
        "",
        "",
        FirebaseFirestoreException.Code.UNAVAILABLE.value(),
        "The service is currently unavailable. This is a most likely a transient condition and may be corrected by retrying with a backoff."
    )
}
///**
//     * The service is currently unavailable. This is a most likely a transient condition and may be
//     * corrected by retrying with a backoff.
//     */
//    var UNAVAILABLE: FirebaseFirestoreException.Code? = null,
//    var CANCELLED: FirebaseFirestoreException.Code? = null,
//
//    /** Unknown error or an error from a different error domain.  */
//    var UNKNOWN: FirebaseFirestoreException.Code? = null,
//
//    /**
//     * Client specified an invalid argument. Note that this differs from [ ][.FAILED_PRECONDITION]. `INVALID_ARGUMENT` indicates arguments that are problematic
//     * regardless of the state of the system (e.g., an invalid field name).
//     */
//    var INVALID_ARGUMENT: FirebaseFirestoreException.Code? = null,
//
//    /**
//     * Deadline expired before operation could complete. For operations that change the state of the
//     * system, this error may be returned even if the operation has completed successfully. For
//     * example, a successful response from a server could have been delayed long enough for the
//     * deadline to expire.
//     */
//    var DEADLINE_EXCEEDED: FirebaseFirestoreException.Code? = null,
//
//    /** Some requested document was not found.  */
//    var NOT_FOUND: FirebaseFirestoreException.Code? = null,
//
//    /** Some document that we attempted to create already exists.  */
//    var ALREADY_EXISTS: FirebaseFirestoreException.Code? = null,
//
//    /** The caller does not have permission to execute the specified operation.  */
//    var PERMISSION_DENIED: FirebaseFirestoreException.Code? = null,
//
//    /**
//     * Some resource has been exhausted, perhaps a per-user quota, or perhaps the entire file system
//     * is out of space.
//     */
//    var RESOURCE_EXHAUSTED: FirebaseFirestoreException.Code? = null,
//
//    /**
//     * Operation was rejected because the system is not in a state required for the operation's
//     * execution.
//     */
//    var FAILED_PRECONDITION: FirebaseFirestoreException.Code? = null,
//
//    /**
//     * The operation was aborted, typically due to a concurrency issue like transaction aborts, etc.
//     */
//    var ABORTED: FirebaseFirestoreException.Code? = null,
//
//    /** Operation was attempted past the valid range.  */
//    var OUT_OF_RANGE: FirebaseFirestoreException.Code? = null,
//
//    /** Operation is not implemented or not supported/enabled.  */
//    var UNIMPLEMENTED: FirebaseFirestoreException.Code? = null,
//
//    /**
//     * Internal errors. Means some invariants expected by underlying system has been broken. If you
//     * see one of these errors, something is very broken.
//     */
//    var INTERNAL: FirebaseFirestoreException.Code? = null,
//
//    /**
//     * The service is currently unavailable. This is a most likely a transient condition and may be
//     * corrected by retrying with a backoff.
//     */
//    var UNAVAILABLE: FirebaseFirestoreException.Code? = null,
//
//    /** Unrecoverable data loss or corruption.  */
//    var DATA_LOSS: FirebaseFirestoreException.Code? = null,
//
//    /** The request does not have valid authentication credentials for the operation.  */
//    var UNAUTHENTICATED: FirebaseFirestoreException.Code? = null
//}
