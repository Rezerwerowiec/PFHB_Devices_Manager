package pfhb.damian.pfhbdevicesmanager

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_add_device.*

class AddDeviceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_device)

        add_device_imei.visibility = View.INVISIBLE
        add_device_pin.visibility = View.INVISIBLE
        add_device_sim.visibility = View.INVISIBLE
        add_device_puk.visibility = View.INVISIBLE

    }

    fun typRadioCheck(view: android.view.View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            when (view.getId()) {
                R.id.radio_laptop -> {
                    if (checked) {
                        add_device_imei.visibility = View.INVISIBLE
                        add_device_pin.visibility = View.VISIBLE
                        add_device_sim.visibility = View.INVISIBLE
                        add_device_puk.visibility = View.INVISIBLE
                    }
                }
                R.id.radio_telefon -> {
                    if (checked) {
                        add_device_imei.visibility = View.VISIBLE
                        add_device_pin.visibility = View.VISIBLE
                        add_device_sim.visibility = View.VISIBLE
                        add_device_puk.visibility = View.VISIBLE
                    }
                }
                R.id.radio_inne -> {
                    if (checked) {
                        add_device_imei.visibility = View.VISIBLE
                        add_device_pin.visibility = View.VISIBLE
                        add_device_sim.visibility = View.VISIBLE
                        add_device_puk.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}