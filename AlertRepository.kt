
package screen

import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.database.*

object AlertRepository {

    val alerts = mutableStateListOf<Alert>()

    private val database =
        FirebaseDatabase.getInstance()
            .getReference("alerts")

    init {

        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                alerts.clear()

                for (alertSnapshot in snapshot.children) {

                    val alert =
                        alertSnapshot.getValue(Alert::class.java)

                    if (alert != null) {
                        alerts.add(alert)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun addAlert(alert: Alert) {

        val id = database.push().key ?: return

        database.child(id).setValue(alert)
    }
}