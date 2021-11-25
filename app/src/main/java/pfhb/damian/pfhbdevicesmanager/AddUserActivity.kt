package pfhb.damian.pfhbdevicesmanager

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_user.*
import pfhb.damian.pfhbdevicesmanager.FirestoreExt.Companion.fs

class AddUserActivity : AppCompatActivity() {
    var oddzial = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        add_user_btn_zapisz.setOnClickListener { sendData() }
    }

    private fun sendData() {
        // COLLECT DATA FROM INPUTS //
        val kod = add_user_et_kod.text.toString()
        val nazwisko = add_user_et_nazwisko.text.toString()
        val email = add_user_et_email.text.toString()
        val telefon = add_user_et_phone.text.toString()

        val data = hashMapOf<String, Any>(
            "kod" to kod,
            "nazwisko" to nazwisko,
            "email" to email,
            "telefon" to telefon,
            "oddzial" to oddzial
        )

        fs.putData("devicesmanager_users", kod, data, ::continueSendingData, ::failedSendingData)
    }

    private fun continueSendingData() {
        Toast.makeText(baseContext, "Dany zostały zapisane poprawnie!", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, MainMenu::class.java))
    }

    private fun failedSendingData() {
        Toast.makeText(baseContext, "Nie udało się wysłać danych... Spróbuj ponownie!", Toast.LENGTH_LONG).show()
    }



    fun oddzialRadioCheck(view: android.view.View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_by -> {
                    if (checked) {
                        oddzial = view.text as String
                    }
                }
                R.id.radio_ol -> {
                    if (checked) {
                        oddzial = view.text as String
                    }
                }
                R.id.radio_gd -> {
                    if (checked) {
                        oddzial = view.text as String
                    }
                }
            }
        }
        Log.d(TAG, "ODDZIAL: $oddzial")
    }
}