package findns.re.fridalaunch

import android.util.Log
import eu.chainfire.libsuperuser.Shell
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

const val TAG = "FRIDA_WRAPPER"
const val FRIDA_PATH = "/data/local/tmp/"
const val CMD_FRIDA = "frida64"
const val CMD_PS_GREP = "pgrep"
const val CMD_KILL = "kill"

class FridaWrapper {

    fun startFrida(success: (() -> Unit), error: ((String) -> Unit)) {
        doAsync {
            if(Shell.SU.available()){
                //TODO check frida && install it?
                val result = Shell.SU.run("$FRIDA_PATH$CMD_FRIDA &")
                if(result.isNotEmpty()){
                    //TODO check error
                    val errorList = result.joinToString("\n")
                    uiThread { error(errorList) }
                } else {
                    // No output/error means Frida was launched
                    Log.d(TAG, "Frida was launched!")
                    uiThread { success() }
                }
            } else {
                uiThread { error("No access to su") }
            }
        }
    }

    fun killFrida (success: () -> Unit, error: (String) -> Unit){
        doAsync {
            val pgrep = Shell.SU.run("$CMD_PS_GREP $CMD_FRIDA")
            if(pgrep.isNotEmpty()){
                val pid = pgrep.first()
                val kill = Shell.SU.run("$CMD_KILL $pid")
                if(kill.isNotEmpty()) {
                    val errorList = kill.joinToString("\n")
                    uiThread { error("Couldn't kill pid: $pid, error: \n $errorList") }
                } else {
                    uiThread { success() }
                }
            } else {
                uiThread { error("Couldn't find Frida in ps output") }
            }
        }
    }
}