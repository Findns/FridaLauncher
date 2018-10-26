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
        appendToLog("Killing Frida...")
        frida.killFrida(success = {
            appendToLog("Frida was killed.")
        }, error = {
            appendToLog("Error while killing Frida:")
            appendToLog(it)
        })
    }

    private fun startFrida() {
        appendToLog("Starting Frida...")
        frida.startFrida(success = {
            appendToLog("Frida was started successfully!")
        }, error = {
            appendToLog("Error while starting Frida:")
            appendToLog(it)
        })
    }

    private fun appendToLog(string: String){
        log_text.append(string + "\n")
    }

    private fun setScrollingTextView() {
        log_text.movementMethod = ScrollingMovementMethod()
    }
}
