package de.ltheinrich.etopa.utils

import android.app.Activity
import android.app.ActivityManager
import android.content.*
import android.content.Context.POWER_SERVICE
import android.os.Build
import android.os.PowerManager
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.core.content.ContextCompat
import androidx.core.view.iterator
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputLayout
import de.ltheinrich.etopa.*
import org.json.JSONObject
import java.util.*
import kotlin.reflect.KClass


typealias Handler = (response: JSONObject) -> Unit
typealias StringHandler = (response: String) -> Unit
typealias ErrorHandler = (error: VolleyError) -> Unit

const val emptyPin = "******"
const val emptyPinHash = "8326de6693e2dc5e15d9d2031d26844c"

const val emptyPassword = "************"
const val emptyPasswordHash = "08d299150597a36973bf282c1ce59602eaa12c3607d3034d7ea29bb64710d65c"
const val emptyKeyHash = "c353cdd4c437c0dc01d6378525e25c1d"

var library: Boolean = false

fun inputString(inputLayout: TextInputLayout): String {
    return inputLayout.editText?.text.toString()
}

enum class MenuType {
    DISABLED, SIMPLE, FULL
}

class Common constructor(activity: Activity) {

    var instance: String? = null
    var username: String = ""
    var passwordHash: String = ""
    var keyHash: String = ""
    var token: String = ""
    var storage: Storage? = null
    var pinHash: String = ""
    var backActivity: Class<*> = MainActivity::class.java
    var offline: Boolean = false
    var menuType: MenuType = MenuType.SIMPLE

    fun handleMenu(item: MenuItem) = when (item.itemId) {
        R.id.action_add -> {
            openActivity(AddActivity::class)
            true
        }
        R.id.action_settings -> {
            openActivity(SettingsActivity::class)
            true
        }
        R.id.action_licenses -> {
            openActivity(LicensesActivity::class)
            true
        }
        android.R.id.home -> {
            backKey(KeyEvent.KEYCODE_BACK)
            true
        }
        else -> {
            false
        }
    }

    fun backKey(keyCode: Int): Boolean {
        Log.d("Moo", "???!?")
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (backActivity == AppActivity::class.java && pinHash.isEmpty())
                openActivity(MainActivity::class)
            else if (backActivity == MainActivity::class.java) {
                Log.d("Booo", "WTF")
                activity.moveTaskToBack(true)
            } else
                openActivity(backActivity)
            return true
        }
        return false
    }

    fun createMenu(menu: Menu?): Boolean {
        activity.menuInflater.inflate(R.menu.toolbar_menu, menu)
        val simpleItems = arrayOf(R.id.action_licenses)
        when (menuType) {
            MenuType.DISABLED -> menu?.iterator()?.forEach { item -> item.isVisible = false }
            MenuType.SIMPLE -> simpleItems.forEach { itemId ->
                menu?.findItem(itemId)?.isVisible = true
            }
            MenuType.FULL -> menu?.iterator()?.forEach { item -> item.isVisible = true }
        }
        return true
    }

    fun lockListener(activity: Activity) {
        Log.d("Test", "Hallo1")
        val intentFilter = IntentFilter(Intent.ACTION_SCREEN_OFF)
        activity.registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                if (intent.action == Intent.ACTION_SCREEN_OFF) {
                    Log.d("Test", Intent.ACTION_SCREEN_OFF)
                    triggerRestart(activity)
                }
            }
        }, intentFilter)
    }

    fun triggerRestart(context: Activity) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        context.finish()
        Runtime.getRuntime().exit(0)
    }

    fun parseSecretUri(uri: String): Pair<String, String?> {
        val otpAuth = uri.removePrefix("otpauth://")
        if (otpAuth != uri) {
            val totp = otpAuth.removePrefix("totp/")
            if (totp != otpAuth) {
                val name = totp.split('?', limit = 2)
                if (name.size == 2) {
                    val data = name[1].split('&')
                    for (d in data) {
                        if (d.startsWith("secret=")) {
                            val secret = d.split('=', limit = 2)
                            if (secret.size == 2)
                                return Pair(secret[1], urlDecode(name[0]))
                        }
                    }
                }
            }
        }
        return Pair(uri, null)
    }

    private fun urlDecode(url: String): String {
        return url.replace("%20", " ").replace("%21", "!").replace("%22", "\"").replace("%23", "#")
            .replace("%24", "$").replace("%25", "%").replace("%26", "&").replace("%27", "'")
            .replace("%28", "(").replace("%29", ")").replace("%2A", "*").replace("%2B", "+")
            .replace("%2C", ",").replace("%2D", "-").replace("%2E", ".").replace("%2F", "/")
            .replace("%3A", ":").replace("%3B", ";").replace("%3C", "<").replace("%3D", "=")
            .replace("%3E", ">").replace("%3F", "?").replace("%40", "@").replace("%5B", "[")
            .replace("%5C", "\\").replace("%5D", "]").replace("%7B", "{").replace("%7C", "|")
            .replace("%7D", "}")
    }

    fun decryptLogin(preferences: SharedPreferences) {
        instance = preferences.getString("instance", encrypt(pinHash, "etopa.de"))?.let {
            decrypt(
                pinHash,
                it
            )
        }.toString()
        username = decrypt(
            pinHash,
            preferences.getString("username", encrypt(pinHash, "")).orEmpty()
        )
        passwordHash = decrypt(
            pinHash,
            preferences.getString("passwordHash", encrypt(pinHash, "")).orEmpty()
        )
        keyHash = decrypt(
            pinHash,
            preferences.getString("keyHash", encrypt(pinHash, "")).orEmpty()
        )
        token = decrypt(
            pinHash,
            preferences.getString("token", encrypt(pinHash, "")).orEmpty()
        )
    }

    fun encryptLogin(preferences: SharedPreferences, pinHash: String) {
        val editor = preferences.edit()
        val defaultInstance = activity.getString(R.string.default_instance)

        if (instance.isNullOrEmpty() || instance == defaultInstance) {
            editor.remove("instance")
            instance = defaultInstance
        } else {
            editor.putString("instance", encrypt(pinHash, instance!!))
        }

        if (passwordHash == emptyPasswordHash) {
            passwordHash = decrypt(
                this.pinHash,
                preferences.getString("passwordHash", encrypt(this.pinHash, "")).orEmpty()
            )
        }

        if (keyHash == emptyKeyHash) {
            keyHash = decrypt(
                this.pinHash,
                preferences.getString("keyHash", encrypt(this.pinHash, "")).orEmpty()
            )
        }

        editor.putString("username", encrypt(pinHash, username))
        editor.putString("passwordHash", encrypt(pinHash, passwordHash))
        editor.putString("keyHash", encrypt(pinHash, keyHash))
        val secretStorage = preferences.getString("secretStorage", null)
        if (secretStorage != null) {
            editor.putString(
                "secretStorage",
                encrypt(pinHash, decrypt(this.pinHash, secretStorage))
            )
        }

        editor.remove(token)
        setPin(editor, pinHash)
    }

    fun setPin(editor: SharedPreferences.Editor, pinHash: String) {
        val splitAt = Random().nextInt(30)
        val uuid = UUID.randomUUID().toString()
        val pinSetEncrypted =
            encrypt(
                pinHash,
                uuid.substring(0, splitAt) + "etopan_pin_set" + uuid.substring(splitAt)
            )

        editor.putString("pin_set", pinSetEncrypted)
        editor.apply()

        this.pinHash = pinHash
    }

    fun newLogin(preferences: SharedPreferences) {
        request("user/login",
            { response ->
                if (response.has("token")) {
                    token = response.getString("token")
                    val editor = preferences.edit()
                    editor.putString("token", encrypt(pinHash, token))
                    editor.apply()
                    openActivity(AppActivity::class)
                } else {
                    toast(R.string.incorrect_login)
                    openActivity(SettingsActivity::class, Pair("incorrectLogin", "incorrectLogin"))
                }
            },
            Pair("username", username),
            Pair("password", passwordHash),
            error_handler = { offlineLogin(preferences) })
    }

    fun offlineLogin(preferences: SharedPreferences) {
        toast(R.string.network_unreachable)
        if (preferences.contains("secretStorage")) {
            openActivity(AppActivity::class)
        }
    }

    fun request(
        url: String,
        handler: Handler,
        vararg data: Pair<String, String>,
        error_handler: ErrorHandler = { error: VolleyError ->
            Log.e(
                "HTTP Request",
                error.toString()
            )
        },
    ) {
        val jsonObjectRequest = object : JsonObjectRequest(
            Method.POST, "https://$instance/$url", null,
            Response.Listener { response ->
                offline = false
                handler(response)
            },
            Response.ErrorListener { error ->
                offline = true
                Log.e("Network error", error.toString())
                error_handler(error)
            }
        ) {
            override fun getHeaders(): Map<String, String> {
                return data.toMap()
            }
        }
        http.add(jsonObjectRequest)
    }

    fun requestString(
        url: String,
        handler: StringHandler,
        vararg data: Pair<String, String>,
        error_handler: ErrorHandler = { error: VolleyError ->
            Log.e(
                "HTTP Request",
                error.toString()
            )
        },
    ) {
        val stringRequest = object : StringRequest(
            Method.POST, "https://$instance/$url",
            Response.Listener { response ->
                offline = false
                handler(response)
            },
            Response.ErrorListener { error ->
                offline = true
                error_handler(error)
            }
        ) {
            override fun getHeaders(): Map<String, String> {
                return data.toMap()
            }
        }
        http.add(stringRequest)
    }

    fun <T : Activity> openActivity(
        cls: KClass<T>,
        vararg extras: Pair<String, String>,
    ) {
        val app = Intent(activity, cls.java)
        for ((key, value) in extras) {
            app.putExtra(key, value)
        }
        activity.startActivity(app)
    }

    private fun openActivity(
        cls: Class<*>,
        vararg extras: Pair<String, String>,
    ) {
        val app = Intent(activity, cls)
        for ((key, value) in extras) {
            app.putExtra(key, value)
        }
        activity.startActivity(app)
    }

    fun toast(stringId: Int, length: Int = Toast.LENGTH_LONG) {
        Toast.makeText(activity, stringId, length).show()
    }

    fun toast(text: String, length: Int = Toast.LENGTH_LONG) {
        Toast.makeText(activity, text, length).show()
    }

    fun checkSdk(minSdk: Int): Boolean {
        return Build.VERSION.SDK_INT >= minSdk
    }

    fun checkBackground(): Boolean {
        val appProcessInfo = ActivityManager.RunningAppProcessInfo()
        ActivityManager.getMyMemoryState(appProcessInfo)
        val powerManager = activity.getSystemService(POWER_SERVICE) as PowerManager
        @Suppress("DEPRECATION")
        return !(if (checkSdk(Build.VERSION_CODES.KITKAT_WATCH)) powerManager.isInteractive else powerManager.isScreenOn)
        /* && !(appProcessInfo.importance == IMPORTANCE_FOREGROUND || appProcessInfo.importance == IMPORTANCE_VISIBLE) */
    }

    fun biometricAvailable(): Boolean {
        return BiometricManager.from(activity)
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK) == BiometricManager.BIOMETRIC_SUCCESS
    }

    fun hideKeyboard(currentFocus: View?) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (currentFocus != null)
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }

    fun copyToClipboard(toCopy: String) {
        val clipboard = ContextCompat.getSystemService(
            activity,
            ClipboardManager::class.java
        )
        val clip = ClipData.newPlainText(toCopy, toCopy)
        clipboard?.setPrimaryClip(clip)
    }

    companion object {
        @Volatile
        private var INSTANCE: Common? = null
        fun getInstance(activity: Activity): Common =
            if (library) {
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                        ?: Common(activity).also {
                            INSTANCE = it
                        }
                }
            } else {
                System.loadLibrary("etopan")
                library = true
                getInstance(activity)
            }
    }

    private val activity: Activity by lazy {
        activity
    }

    private val http: RequestQueue by lazy {
        Volley.newRequestQueue(activity.applicationContext)
    }

    external fun hashKey(key: String): String

    external fun hashPassword(password: String): String

    external fun hashPin(pin: String): String

    external fun hashName(name: String): String

    external fun hashArgon2Hashed(passwordHash: String): String

    external fun encrypt(key: String, data: String): String

    external fun decrypt(key: String, data: String): String

    external fun generateToken(secret: String): String
}
