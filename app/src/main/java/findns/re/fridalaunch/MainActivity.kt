package findns.re.fridalaunch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val frida = FridaWrapper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setScrollingTextView()
        setListeners()
    }

    private fun setListeners() {
        button_start.setOnClickListener { startFrida() }
        button_kill.setOnClickListener { killFrida() }
    }

    private fun killFrida() {
        log_text.append("Killing Frida...\n")
        frida.killFrida()
    }

    private fun startFrida() {
        log_text.append("Starting Frida...\n")
        frida.startFrida()
    }

    private fun setScrollingTextView() {
        log_text.movementMethod = ScrollingMovementMethod()
    }
}
