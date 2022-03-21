package com.boreal.puertocorazon.core.utils.corefirestore

import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun getCollection(collectionPath: String, documentPath: String,  origin: Source = Source.SERVER) {
    Firebase.firestore.apply {

//        val ref = collection(collectionPath)
//            .document(documentPath)



//            .addOnSuccessListener { documentSnapshot ->
////                val city = documentSnapshot.toObject<City>()
//                if (documentSnapshot != null) {
//                    Log.e(
//                        this::class.java.simpleName,
//                        "DocumentSnapshot data: ${documentSnapshot.data}"
//                    )
//                } else {
//                    Log.e(this::class.java.simpleName, "Error getting documents.")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.e(this::class.java.simpleName, "Error getting documents.", exception)
//            }
    }
}