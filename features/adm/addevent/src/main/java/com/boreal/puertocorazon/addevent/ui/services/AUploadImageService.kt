package com.boreal.puertocorazon.addevent.ui.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.graphics.drawable.IconCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.boreal.commonutils.application.CUAppInit
import com.boreal.commonutils.globalmethod.randomNumberId
import com.boreal.puertocorazon.addevent.R
import com.boreal.puertocorazon.core.BuildConfig
import com.boreal.puertocorazon.core.domain.entity.AFirestoreRepository
import com.boreal.puertocorazon.core.domain.entity.AFirestoreSetResponse
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.extension.toModel
import com.boreal.puertocorazon.core.utils.coreauthentication.awaitTask
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import com.boreal.puertocorazon.core.utils.getImageBitmap
import com.boreal.puertocorazon.core.utils.realm.getRealmObject
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch

open class AUploadImageService : LifecycleService() {

    companion object : AUploadImageService() {
        const val START_ALL_EVENT_IMAGES_SERVICE = "START_ALL_EVENT_IMAGES_SERVICE"
        const val ACTION_START_OR_RESUME_SERVICE = "ACTION_START_OR_RESUME_SERVICE"
        const val ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE"
    }

    private var pathStorageHomeEvent = ""
    private var pathStorageMainEvent = ""
    private var pathStorageGalleryEvent = ""

    private val db = FirebaseStorage.getInstance()
    private val mStorageReference = db.reference

    private var titleForError = "imageUser"
    private var imageUri = Uri.EMPTY
    private var nameImage = "PuertoCorazon/eventImage"
    private var pathStorageImage = ""
    private var collectionPath = ""
    private var documentPath = ""
    private var idEventModel = ""
    private var idEventLoaded = ""
    private var emailLoaded = ""
    private var index = 0

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.apply {
            when (intent.action) {

                START_ALL_EVENT_IMAGES_SERVICE -> {
                    if (intent.extras != null) {
                        val result = getRealmObject<AAuthModel>().toString().toModel<AAuthModel>()
                        val idEvent = intent.getStringExtra("idEventModel")
                        val email = result.email
                        emailLoaded = result.email
                        val mainImageReceived =
                            Uri.parse(intent.getStringExtra("mainImage")) ?: Uri.EMPTY
                        val homeImageReceived =
                            Uri.parse(intent.getStringExtra("homeImage")) ?: Uri.EMPTY
                        val galleryImages =
                            intent.getStringArrayExtra("galleryImages")?.map { Uri.parse(it) }
                                ?: emptyList<Uri>()

                        if (mainImageReceived == Uri.EMPTY) {
                            //FINISH SERVICE
                            return@apply
                        }
                        //Start MainImage
                        imageUri = mainImageReceived
                        idEventModel = "mainImageUrl"
                        pathStorageMainEvent =
                            "${BuildConfig.ENVIRONMENT}$email${BuildConfig.EVENTS}/$idEvent/main"
                        pathStorageImage =
                            "${BuildConfig.ENVIRONMENT}$email${BuildConfig.EVENTS}/$idEvent/main"
                        nameImage = "mainImg$idEvent.jpg"
                        collectionPath = "${BuildConfig.ENVIRONMENT}$email${BuildConfig.EVENTS}"
                        documentPath = "$idEvent"
                        idEventLoaded = idEvent ?: ""

                        uploadPhoto(
                            imageUri,
                            "Subiendo imagen",
                            pathStorageImage,
                            nameImage,
                            success = { urlProfileImageUpdated, idNotificationInitial ->
                                this@AUploadImageService.lifecycleScope.launch {
                                    updateImageReference(
                                        imageUrl = urlProfileImageUpdated,
                                        idEventModel = idEventModel,
                                        collectionPath = collectionPath,
                                        gallery = false,
                                        documentPath = documentPath,
                                        {
                                            createNotification(
                                                "La imagen se subió correctamente",
                                                "Listo"
                                            ) { notification, builder, idNotification ->

                                                notification.apply {
                                                    builder.setSilent(
                                                        true
                                                    )
                                                    val message =
                                                        NotificationCompat.MessagingStyle.Message(
                                                            "Se subio correctamente",
                                                            System.currentTimeMillis(),
                                                            Person.Builder()
                                                                .also {
                                                                    it.setName(
                                                                        "Actualizacion"
                                                                    )
                                                                    it.setIcon(
                                                                        IconCompat.createWithAdaptiveBitmap(
                                                                            imageUri.getImageBitmap()
                                                                        )
                                                                    )
                                                                }
                                                                .build()
                                                        )

                                                    builder.setStyle(
                                                        NotificationCompat.MessagingStyle(
                                                            Person.Builder()
                                                                .also {
                                                                    it.setName(
                                                                        "Actualizacion"
                                                                    )
                                                                }
                                                                .build()
                                                        )
                                                            .addMessage(
                                                                message
                                                            )
                                                    )
                                                    notify(
                                                        idNotification,
                                                        builder.build()
                                                    )
                                                    //STEP 2 SUBIR FOTO DE INICIO -> HOME
                                                    imageUri = homeImageReceived
                                                    idEventModel = "homeImageUrl"
                                                    pathStorageHomeEvent =
                                                        "${BuildConfig.ENVIRONMENT}$email${BuildConfig.EVENTS}/$idEvent/home"
                                                    pathStorageImage =
                                                        "${BuildConfig.ENVIRONMENT}$email${BuildConfig.EVENTS}/$idEvent/home"
                                                    nameImage = "homeImg$idEvent.jpg"

                                                    uploadPhoto(
                                                        imageUri,
                                                        "Subiendo imagen",
                                                        pathStorageImage,
                                                        nameImage,
                                                        success = { urlHome, idInitial ->
                                                            this@AUploadImageService.lifecycleScope.launch {
                                                                updateImageReference(
                                                                    imageUrl = urlHome,
                                                                    idEventModel = idEventModel,
                                                                    collectionPath = collectionPath,
                                                                    gallery = false,
                                                                    documentPath = documentPath,
                                                                    {
                                                                        createNotification(
                                                                            "La imagen se subió correctamente",
                                                                            "Listo"
                                                                        ) { notification, builder, idNotification ->

                                                                            notification.apply {
                                                                                builder.setSilent(
                                                                                    true
                                                                                )
                                                                                val message2 =
                                                                                    NotificationCompat.MessagingStyle.Message(
                                                                                        "Se subio correctamente",
                                                                                        System.currentTimeMillis(),
                                                                                        Person.Builder()
                                                                                            .also {
                                                                                                it.setName(
                                                                                                    "Actualizacion"
                                                                                                )
                                                                                                it.setIcon(
                                                                                                    IconCompat.createWithAdaptiveBitmap(
                                                                                                        imageUri.getImageBitmap()
                                                                                                    )
                                                                                                )
                                                                                            }
                                                                                            .build()
                                                                                    )

                                                                                builder.setStyle(
                                                                                    NotificationCompat.MessagingStyle(
                                                                                        Person.Builder()
                                                                                            .also {
                                                                                                it.setName(
                                                                                                    "Actualizacion"
                                                                                                )
                                                                                            }
                                                                                            .build()
                                                                                    )
                                                                                        .addMessage(
                                                                                            message2
                                                                                        )
                                                                                )
                                                                                notify(
                                                                                    idInitial,
                                                                                    builder.build()
                                                                                )

                                                                                uploadGallery(
                                                                                    galleryImages
                                                                                )
                                                                            }

                                                                        }
                                                                    }, {
                                                                        cancelNotification(
                                                                            "Fallo al actualizar referencia de $titleForError",
                                                                            it
                                                                        )
                                                                    }
                                                                )
                                                            }

                                                        },
                                                        failure = {
                                                            cancelNotification(
                                                                "Fallo al actualizar imagen de $titleForError",
                                                                it
                                                            )
                                                        }
                                                    )
                                                }

                                            }
                                        }, {
                                            cancelNotification(
                                                "Fallo al actualizar referencia de $titleForError",
                                                it
                                            )
                                        }
                                    )
                                }

                            },
                            failure = {
                                cancelNotification(
                                    "Fallo al actualizar imagen de $titleForError",
                                    it
                                )
                            }
                        )


                    }
                }
                ACTION_STOP_SERVICE -> {
                    stopForeground(true)
                }

            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun uploadGallery(gallery: List<Uri>) {
        pathStorageImage =
            "${BuildConfig.ENVIRONMENT}$emailLoaded${BuildConfig.EVENTS}/$idEventLoaded/gallery"
        nameImage = "homeImg$idEventLoaded$index.jpg"

        if (gallery.isEmpty()) {
            stopSelf()
            return
        }
        uploadPhoto(
            gallery[index],
            "Subiendo galeria ${index + 1}/${gallery.size}",
            pathStorageImage,
            nameImage,
            success = { urlHome, idInitial ->
                this@AUploadImageService.lifecycleScope.launch {
                    updateImageReference(
                        imageUrl = urlHome,
                        idEventModel = idEventModel,
                        collectionPath = collectionPath,
                        gallery = true,
                        documentPath = documentPath,
                        {
                            if (index == gallery.size - 1) {
                                stopSelf()
                                createNotification(
                                    "La galeria se subió correctamente",
                                    "Listo"
                                ) { notification, builder, idNotification ->

                                    notification.apply {
                                        builder.setSilent(
                                            true
                                        )
                                        val message2 =
                                            NotificationCompat.MessagingStyle.Message(
                                                "La galeria se subió correctamente",
                                                System.currentTimeMillis(),
                                                Person.Builder()
                                                    .also {
                                                        it.setName(
                                                            if (gallery.size > 1) {
                                                                "Se subieron ${gallery.size} fotos a galeria"
                                                            } else {
                                                                "Se subio una foto a galeria"
                                                            }

                                                        )
                                                        it.setIcon(
                                                            IconCompat.createWithAdaptiveBitmap(
                                                                imageUri.getImageBitmap()
                                                            )
                                                        )
                                                    }
                                                    .build()
                                            )

                                        builder.setStyle(
                                            NotificationCompat.MessagingStyle(
                                                Person.Builder()
                                                    .also {
                                                        it.setName(
                                                            "Actualizacion"
                                                        )
                                                    }
                                                    .build()
                                            )
                                                .addMessage(
                                                    message2
                                                )
                                        )
                                        notify(
                                            idInitial,
                                            builder.build()
                                        )
                                    }

                                }
                            } else {
                                createNotification(
                                    "La imagen se subió correctamente",
                                    "Listo"
                                ) { notification, builder, idNotification ->

                                    notification.apply {
                                        builder.setSilent(
                                            true
                                        )
                                        val message2 =
                                            NotificationCompat.MessagingStyle.Message(
                                                "Se subio correctamente",
                                                System.currentTimeMillis(),
                                                Person.Builder()
                                                    .also {
                                                        it.setName(
                                                            "Actualizacion"
                                                        )
                                                        it.setIcon(
                                                            IconCompat.createWithAdaptiveBitmap(
                                                                imageUri.getImageBitmap()
                                                            )
                                                        )
                                                    }
                                                    .build()
                                            )

                                        builder.setStyle(
                                            NotificationCompat.MessagingStyle(
                                                Person.Builder()
                                                    .also {
                                                        it.setName(
                                                            "Actualizacion"
                                                        )
                                                    }
                                                    .build()
                                            )
                                                .addMessage(
                                                    message2
                                                )
                                        )
                                        notify(
                                            idInitial,
                                            builder.build()
                                        )
                                        if (index < gallery.size) {
                                            index += 1
                                            uploadGallery(gallery)
                                        }
                                    }

                                }
                            }


                        }, {
                            cancelNotification(
                                "Fallo al actualizar referencia de $titleForError",
                                it
                            )
                        }
                    )
                }

            },
            failure = {
                cancelNotification(
                    "Fallo al actualizar imagen de $titleForError",
                    it
                )
            }
        )
    }

    private fun cancelNotification(titleReceived: String, cause: String) {
        stopSelf()
        createNotification(
            titleReceived,
            cause
        ) { notification, builder, idNotification ->
            Log.e(titleReceived, cause)
            notification.apply {
                builder.setSilent(
                    true
                )
                val message =
                    NotificationCompat.MessagingStyle.Message(
                        cause,
                        System.currentTimeMillis(),
                        Person.Builder()
                            .also {
                                it.setName(
                                    titleReceived
                                )
                                it.setIcon(
                                    IconCompat.createWithAdaptiveBitmap(
                                        imageUri.getImageBitmap()
                                    )
                                )
                            }
                            .build()
                    )

                builder.setStyle(
                    NotificationCompat.MessagingStyle(
                        Person.Builder()
                            .also {
                                it.setName(
                                    titleReceived
                                )
                            }
                            .build()
                    )
                        .addMessage(
                            message
                        )
                )
                notify(
                    idNotification,
                    builder.build()
                )
            }

        }
    }

    private fun uploadPhoto(
        uri: Uri,
        message: String,
        pathStorage: String,
        nameImage: String,
        idNotificationReceived: Int = 0,
        success: (url: String, idNotification: Int) -> Unit,
        failure: (cause: String) -> Unit
    ) {
        createNotification(
            "Subiendo nueva imagen",
            message
        ) { notification, builder, idNotification ->

            notification.apply {
                builder.setSilent(true)
                startForeground(
                    if (idNotificationReceived != 0) {
                        idNotificationReceived
                    } else {
                        idNotification
                    }, builder.build()
                )

                val imageRef = mStorageReference.child("$pathStorage/$nameImage")
                imageRef.putFile(uri).addOnProgressListener {
                    it.apply {
                        when ((bytesTransferred / totalByteCount)) {
                            1.toLong() -> {
                                Log.i("uploadingImage", "Casi listo")
                                builder.setProgress(100, 100, false)
                                notify(
                                    if (idNotificationReceived != 0) {
                                        idNotificationReceived
                                    } else {
                                        idNotification
                                    }, builder.build()
                                )
                            }
                            else -> {
                                val progressUpload =
                                    (bytesTransferred.toFloat() / totalByteCount * 100).toInt()
                                builder.setProgress(100, progressUpload, false)
                                notify(
                                    if (idNotificationReceived != 0) {
                                        idNotificationReceived
                                    } else {
                                        idNotification
                                    }, builder.build()
                                )
                                Log.i("uploadingImage", "$progressUpload %")
                            }
                        }
                    }
                }.addOnCompleteListener {
                    it.result!!.storage.downloadUrl.addOnSuccessListener { url ->
                        Log.i("uploadingImage", "$url")
                        success(
                            url.toString(), if (idNotificationReceived != 0) {
                                idNotificationReceived
                            } else {
                                idNotification
                            }
                        )
                    }.addOnFailureListener { exception ->
                        failure(exception.message ?: "Falló subiendo el archivo")
                        stopForeground(true)
                        stopSelf()
                        createNotification(
                            "Falló subiendo el archivo $nameImage",
                            "No se subió la foto"
                        ) { notification2, builder2, idNotification2 ->

                            notification2.let { not2 ->
                                builder2.setSilent(true)
                                stopSelf()
                                not2.notify(idNotification, builder2.build())
                            }

                        }

                    }

                }

            }
        }

    }

    private suspend fun updateImageReference(
        imageUrl: String,
        idEventModel: String,
        collectionPath: String,
        gallery: Boolean = false,
        documentPath: String,
        updatePathSuccess: () -> Unit,
        updatePathFailure: (messageError: String) -> Unit
    ) {
        val request = if (gallery) {
            UploadReference.updateImageGalleryReference(
                AFirestoreSetResponse(
                    collectionPath = collectionPath,
                    documentPath = documentPath,
                    modelToSet = imageUrl
                )
            )
        } else {
            UploadReference.updateImageReference(
                AFirestoreSetResponse(
                    collectionPath = collectionPath,
                    documentPath = documentPath,
                    modelToSet = mapOf("eventData.$idEventModel" to imageUrl)
                )
            )
        }
        with(request) {
            if (first) {
                updatePathSuccess()
            } else {
                updatePathFailure(
                    second?.messageError ?: CUFirestoreErrorEnum.ERROR_DEFAULT.messageError
                )
            }
        }

    }


    private fun createNotification(
        title: String,
        contentText: String,
        icon: Int = R.drawable.ic_pc_logo,
        pendingIntent: Intent? = null,
        method: (notification: NotificationManagerCompat, builder: NotificationCompat.Builder, idNotification: Int) -> Unit
    ) {

        val idUnique = randomNumberId()
        val channelID = "PuertoCorazonUploadImages"
        val intent = pendingIntent?.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val builder = NotificationCompat.Builder(CUAppInit.getAppContext(), channelID)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(contentText)
            .setContentIntent(
                if (pendingIntent == null) {
                    null
                } else {
                    PendingIntent.getActivity(CUAppInit.getAppContext(), 0, intent, 0)
                }
            )

        with(NotificationManagerCompat.from(CUAppInit.getAppContext())) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel =
                    NotificationChannel(channelID, channelID, importance).apply {
                        description = channelID
                    }

                (CUAppInit.getAppContext()
                    .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).apply {
                    createNotificationChannel(channel)
                }

                method.invoke(this, builder, idUnique)

            }
        }

    }

}

class UploadReference {
    companion object : AFirestoreRepository() {
        suspend fun updateImageGalleryReference(
            request: AFirestoreSetResponse<String, CUFirestoreErrorEnum>
        ): Pair<Boolean, CUFirestoreErrorEnum?> =
            try {
                firestoreInstance.run {
                    with(
                        collection(request.collectionPath)
                            .document(request.documentPath)
                            .update(
                                "eventData.imageGallery",
                                FieldValue.arrayUnion(request.modelToSet ?: "")
                            )
                            .awaitTask()
                    ) {
                        if (isSuccessful) {
                            Pair(true, null)
                        } else {
                            Pair(
                                false, if (exception is FirebaseFirestoreException) {
                                    errorResponse(exception as FirebaseFirestoreException)
                                } else {
                                    CUFirestoreErrorEnum.ERROR_DEFAULT
                                }
                            )
                        }
                    }
                }
            } catch (exception: Exception) {
                Pair(false, validationError(exception.message ?: ""))
            }

        suspend fun updateImageReference(
            request: AFirestoreSetResponse<Map<String, String>, CUFirestoreErrorEnum>
        ): Pair<Boolean, CUFirestoreErrorEnum?> =
            try {
                firestoreInstance.run {
                    with(
                        collection(request.collectionPath)
                            .document(request.documentPath)
                            .update(request.modelToSet!!).awaitTask()
                    ) {
                        if (isSuccessful) {
                            Pair(true, null)
                        } else {
                            Pair(
                                false, if (exception is FirebaseFirestoreException) {
                                    errorResponse(exception as FirebaseFirestoreException)
                                } else {
                                    CUFirestoreErrorEnum.ERROR_DEFAULT
                                }
                            )
                        }
                    }
                }
            } catch (exception: Exception) {
                Pair(false, validationError(exception.message ?: ""))
            }
    }
}