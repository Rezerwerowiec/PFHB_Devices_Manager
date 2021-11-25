package pfhb.damian.pfhbdevicesmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        main_menu_dodaj_urzadz.setOnClickListener { startActivity(Intent(this, AddDeviceActivity::class.java)) }
        main_menu_dodaj_uzytk.setOnClickListener { startActivity(Intent(this, AddUserActivity::class.java)) }
        main_menu_przegl_urzadz.setOnClickListener { startActivity(Intent(this, BrowseDevicesActivity::class.java)) }
        main_menu_przegl_uzytk.setOnClickListener { startActivity(Intent(this, BrowseUsersActivity::class.java)) }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}