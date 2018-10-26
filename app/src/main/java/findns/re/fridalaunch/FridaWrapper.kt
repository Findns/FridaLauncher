package findns.re.fridalaunch

import android.util.Log
import eu.chainfire.libsuperuser.Shell
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

const val TAG = "FRIDA_WRAPPER"
const val FRIDA_PATH = "/data/local/tmp/"
const val CMD_FRIDA = "frida64 &"
const val CMD_PS_GREP = "ps | grep frida"

class FridaWrapper {

    fun startFrida(success: (() -> Unit), error: ((String) -> Unit)) {
        doAsync {
            if(Shell.SU.available()){
                //TODO check frida && install it?
                val result = Shell.SU.run(FRIDA_PATH + CMD_FRIDA)
                if(!result.isEmpty()){
                    //TODO check error
                    error(result.joinToString { "\n" })
                } else {
                    // No output/error means Frida was launched
                    Log.d(TAG, "Frida was launched!")
                    uiThread {
                        success()
                    }
                }
            } else {
                uiThread {
                    error("No access to su")
                }
            }
        }
    }

    fun killFrida (){

    }
}