package pfhb.damian.pfhbdevicesmanager


import android.app.DownloadManager
import android.content.ContentValues.TAG
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

class FirestoreExt {
    companion object{
        val fs = FirestoreExt()
    }

    var result : Map<String, Any> = mapOf()
    var arrayResult : ArrayList<Map<String,Any>> = arrayListOf()
    var db_prefix = ""

    private var database : FirebaseFirestore = FirebaseFirestore.getInstance()
    private fun getDataByDocumentIdMethod(dbName : String, docId : String, method: () -> Unit, onFailureMethod: (() -> Unit)?){
        Log.d(TAG, "RESULT: FireExt is called!")
        database.collection("$db_prefix:$dbName")
            .document(docId)
            .get()
            .addOnSuccessListener{
                if (it.data != null) {
                    Log.d(TAG, "RESULT: AddOnSuccess called...")
                    result = it.data!!
                    Log.d(TAG, "RESULT: GOT FROM FireExt! $result")
                    method()
                } else{
                    Log.d(TAG, "RESULT: IS NULL! FROM FireExt!")
                    if(onFailureMethod != null)
                        onFailureMethod()
                }
            }
            .addOnFailureListener { Log.d(TAG, "RESULT: AddOnFailure called...")
                if(onFailureMethod != null)
                    onFailureMethod()
            }
            .addOnCanceledListener { Log.d(TAG, "RESULT: AddOnCancelled called....") }
        Log.d(TAG, "RESULT: FireExt before return")
    }
    private fun getDataByCollectionMethod(dbName: String, method: () -> Unit, onFailureMethod: (() -> Unit)?, sortBy: String?){
        Log.d(TAG, "RESULT: FireExt is called!")
        val query : Task<QuerySnapshot> = if (sortBy != null) {
            database.collection("$db_prefix:$dbName")
                .orderBy(sortBy, Query.Direction.ASCENDING)
                .get()
        } else {
            database.collection("$db_prefix:$dbName")
                .get()
        }

        query.addOnSuccessListener{
            for(doc in it) {
                Log.d(TAG, "RESULT: AddOnSuccess called...")
                var temp : Map<String, Any> = mapOf("docId" to doc.id)
                temp = temp + doc.data
                arrayResult.add( temp)
                Log.d(TAG, "RESULT: GOT FROM FireExt! $arrayResult")
            }
            method()
        }
            .addOnFailureListener { Log.d(TAG, "RESULT: AddOnFailure called...")
                if(onFailureMethod != null)
                    onFailureMethod()}
            .addOnCanceledListener { Log.d(TAG, "RESULT: AddOnCancelled called....") }
        Log.d(TAG, "RESULT: FireExt before return")
    }

    fun getData(collection: String, documentId: String?, method: () -> Unit, onFailureMethod: (() -> Unit)?, sortBy: String?){
        clear()

        if (documentId.isNullOrEmpty()) {
            getDataByCollectionMethod(collection, method, onFailureMethod, sortBy)
        } else {
            getDataByDocumentIdMethod(collection, documentId, method, onFailureMethod)
        }
    }

    fun getData(collection: String, method : () -> Unit, sortBy: String) {
        getData(collection, null, method, null, sortBy)
    }
    fun getData(collection: String, method : () -> Unit, onFailureMethod : () -> Unit, sortBy: String){
        getData(collection, null, method, onFailureMethod, sortBy)
    }
    fun getData(collection: String, docId: String, method: () -> Unit){
        getData(collection, docId, method, null, null)
    }
    fun getData(collection: String, method: () -> Unit){
        getData(collection, null, method, null, null)
    }
    fun getData(collection: String, docId: String, method: () -> Unit, onFailureMethod: () -> Unit){
        getData(collection, docId, method, onFailureMethod, null)
    }



    fun putData(collection: String, docId: String, data : HashMap<String, Any>, method: () -> Unit, onFailureMethod: (() -> Unit)?){

        database.collection("$db_prefix:$collection")
            .document(docId)
            .set(data)
            .addOnSuccessListener {
                Log.d(TAG, "RESULT: PUT onSuccess called")

                method()
            }
            .addOnFailureListener {
                if (onFailureMethod != null) {
                    onFailureMethod()
                }
                Log.d(TAG, "RESULT: PUT onFailure called")
            }
    }

    fun putData(collection: String, docId: String, data: HashMap<String, Any>, method: () -> Unit){
        putData(collection, docId, data, method, null)
    }
    fun putListedData(collection: String, docId: String, data: HashMap<String, List<String>>, method: () -> Unit, onFailureMethod: () -> Unit){
        database.collection("$db_prefix:$collection")
            .document(docId)
            .set(data)
            .addOnSuccessListener {
                Log.d(TAG, "RESULT: PUT onSuccess called")

                method()
            }
            .addOnFailureListener {
                onFailureMethod()
                Log.d(TAG, "RESULT: PUT onFailure called")
            }
    }

    fun deleteData(collection: String, docId: String, method: () -> Unit, onFailureMethod: (() -> Unit)?){
        database.collection("$db_prefix:$collection")
            .document(docId)
            .delete()
            .addOnSuccessListener {
                method()
            }
            .addOnFailureListener {
                if (onFailureMethod != null) {
                    onFailureMethod()
                }
            }
    }

    fun clear(){
        result = mapOf()
        arrayResult = arrayListOf()
    }

}