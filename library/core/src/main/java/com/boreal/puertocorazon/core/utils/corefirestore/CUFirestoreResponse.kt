package com.boreal.puertocorazon.core.utils.corefirestore

import android.util.Log
import com.boreal.puertocorazon.core.utils.corefirestore.errorhandler.CUFirestoreErrorEnum
import com.google.firebase.firestore.*

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CUFirestoreResponse {

    private val db = Firebase.firestore

    fun replaceDataField(
        collectionPath: String,
        documentPath: String,
        fieldName: String,
        data: Any
    ) {

        db.collection(collectionPath)
            .document(documentPath)
            .update(fieldName, data, FieldValue.arrayRemove()).addOnSuccessListener {
                Log.e(this::class.java.simpleName, "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { exception ->
                Log.e(this::class.java.simpleName, "Error writing document", exception)
            }

        //OBTENER DATE DE SERVER
//        FieldValue.serverTimestamp()


        //ACTUALIZA EL VALOR, INCREMENTO
//        washingtonRef.update("population", FieldValue.increment(50))

        //ACTUALIZAR UN MAP
        // Assume the document contains:
// {
//   name: "Frank",
//   favorites: {
        //   food: "Pizza",
        //   color: "Blue",
        //   subject: "recess"
        //   }
//   age: 12
// }
//
// To update age and favorite color:
//        db.collection("users").document("frank")
//            .update(mapOf(
//                "age" to 13,
//                "favorites.color" to "Red"
//            ))


        //ACTUALIZAR UN ARRAY
//        // Atomically add a new region to the "regions" array field.
//        washingtonRef.update("regions", FieldValue.arrayUnion("greater_virginia"))
//
//// Atomically remove a region from the "regions" array field.
//        washingtonRef.update("regions", FieldValue.arrayRemove("east_coast"))
    }

    fun addDocument(
        collectionPath: String,
        documentPath: String, data: Any, replace: Boolean = false
    ) {

        val city = hashMapOf(
            "name" to "Los Angeles",
            "state" to "CA",
            "country" to "USA"
        )

        if (replace) {
            db.collection(collectionPath).document(documentPath)
                .set(data) // <- DATA
                .addOnSuccessListener {
                    Log.e(this::class.java.simpleName, "DocumentSnapshot successfully written!")
                }
                .addOnFailureListener { exception ->
                    Log.e(this::class.java.simpleName, "Error writing document", exception)
                }
        } else {
            db.collection(collectionPath).document(documentPath)
                .set(data, SetOptions.merge()) // <- DATA, SavingOptions
                .addOnSuccessListener {
                    Log.e(this::class.java.simpleName, "DocumentSnapshot successfully written!")
                }
                .addOnFailureListener { exception ->
                    Log.e(this::class.java.simpleName, "Error writing document", exception)
                }
        }
    }

    fun addCollection(collectionPath: String, data: Any) {

        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )

        db.collection(collectionPath)
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.e(
                    this::class.java.simpleName,
                    "DocumentSnapshot added with ID: ${documentReference.id}"
                )
            }
            .addOnFailureListener { e ->
                Log.e(this::class.java.simpleName, "Error adding document", e)
            }


    }

    fun getDocuments(collectionPath: String, origin: Source = Source.SERVER) {
        db.collection(collectionPath)
            .get(origin)
            .addOnSuccessListener { result ->
//                val city = documentSnapshot.toObject<City>()
                for (document in result) {
                    Log.e(this::class.java.simpleName, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.e(this::class.java.simpleName, "Error getting documents.", exception)
            }
    }


    private fun validationError(errorReceived: String): String {
        return if (errorReceived.contains(CUFirestoreErrorEnum.ERROR_INVALID_PATH.defaultError)) {
            CUFirestoreErrorEnum.ERROR_INVALID_PATH.messageError
        } else {
            "${CUFirestoreErrorEnum.ERROR_DEFAULT.messageError} \n$errorReceived"
        }
    }



    //Recibir query
//    .orderBy("population")
//    .orderBy("name")
//    .orderBy("state")
//    .orderBy("name", Query.Direction.DESCENDING).limit(3)
//    .whereGreaterThan("population", 100000).orderBy("population")
//    .startAt("Springfield")
//    .startAt(1000000)
//    .startAfter
//    .endAt()
//    .endBefore()
//    .limit(25)
//    Por ejemplo, si usas startAt(A)
//    en una consulta, se muestra el alfabeto completo.
//    En cambio, si usas startAfter(A), el resultado es B-Z.

    fun listenDocumentsInCollection() {
//        Ref.whereEqualTo("state", "CA")
//        Ref.whereLessThan("population", 100000)
//        Ref.whereNotEqualTo
//        Ref.whereArrayContains
//        Ref.whereArrayContainsAny("regions", listOf("west_coast", "east_coast"))
//        Ref.whereIn("country", listOf("USA", "Japan"))
//        Ref.whereIn("regions", listOf(arrayOf("west_coast"), arrayOf("east_coast")))
//        Ref.whereNotIn("country", listOf("USA", "Japan"))
//        Ref.whereGreaterThanOrEqualTo("name", "San Francisco")
//        Ref.whereGreaterThan
//
//        Consultas compuestas
//        Ref.whereEqualTo("state", "CO").whereEqualTo("name", "Denver")
//        Ref.whereEqualTo("state", "CA").whereLessThan("population", 1000000)

//        Consulta de grupos
//        db.collectionGroup("landmarks").whereEqualTo("type", "museum").get()
//            .addOnSuccessListener { queryDocumentSnapshots ->
//                // ...
//            }

//        CACHE O REMOTO
//        val source = if (querySnapshot.metadata.isFromCache)
//            "local cache"
//        else
//            "server"
//        Log.d(TAG, "Data fetched from $source")

//        Habilitar el acceso a la red
//        db.enableNetwork().addOnCompleteListener {
//            // Do online things
//            // ...
//        }

//        REMOVER LISTENER
//        val query = db.collection("cities")
//        val registration = query.addSnapshotListener { snapshots, e ->
//            // ...
//        }
//
//// ...
//
//// Stop listening to changes
//        registration.remove()
        db.collection("cities")
            .whereEqualTo("state", "CA")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.e(this::class.java.simpleName, "Listen failed.", e)
                    return@addSnapshotListener
                }
//                for (dc in value!!.documentChanges) {
//                    when (dc.type) {
//                        DocumentChange.Type.ADDED -> Log.d(TAG, "New city: ${dc.document.data}")
//                        DocumentChange.Type.MODIFIED -> Log.d(TAG, "Modified city: ${dc.document.data}")
//                        DocumentChange.Type.REMOVED -> Log.d(TAG, "Removed city: ${dc.document.data}")
//                    }
//                }

                val cities = ArrayList<String>()
                for (doc in value!!) {
                    doc.getString("name")?.let {
                        cities.add(it)
                    }
                }
                Log.e(this::class.java.simpleName, "Current cites in CA: $cities")
            }
    }

}