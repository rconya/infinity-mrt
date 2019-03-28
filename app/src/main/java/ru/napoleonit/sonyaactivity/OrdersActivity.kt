package ru.napoleonit.sonyaactivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_orders.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import com.google.android.gms.auth.GoogleAuthException
import com.google.android.gms.auth.UserRecoverableAuthException
import com.google.android.gms.auth.GoogleAuthUtil
import android.os.AsyncTask
import android.accounts.AccountManager
import android.app.Activity
import android.content.Intent
import java.io.IOException
import com.google.android.gms.common.AccountPicker




class OrdersActivity : AppCompatActivity() {


    fun avtorise() = run {

        val body = FormBody.Builder()
            .add("entry_number1", "test@email.com")
            .build()

        val request = Request.Builder()
            .url("https://docs.google.com/forms/d/1FAIpQLSfMGmYYGt-YuEs8iJA7A43V3lm9EdMOHJ1aK0gGM1BnOTBCiA/formResponse")
            .post(body)
            .build()

        val client = OkHttpClient()

        client.newCall(request).execute().body()!!.string()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

//        GlobalScope.launch(Dispatchers.Main) {
//
//            val token =  withContext(Dispatchers.IO) {
//                avtorise()
//            }
//
//            Log.e("OrdersActivity", token)
//
//            supportActionBar?.title = token
//        }


        signInButton.onClick {
            val intent = AccountPicker.newChooseAccountIntent(
                null, null, arrayOf("com.google"),
                false, null, null, null, null
            )
            startActivityForResult(intent, 123)
        }

//        supportActionBar?.title = "Мои зака"
        sonyaButton.onClick {
            startActivity<NewOrderActivity>()
        }



    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            val accountName = data!!.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
            val getToken = object : AsyncTask<Void, Void, String>() {
                override fun doInBackground(vararg params: Void): String {
                    try {
                        return GoogleAuthUtil.getToken(
                            this@OrdersActivity, accountName,
                            SCOPES
                        )
                    } catch (userAuthEx: UserRecoverableAuthException) {
                        startActivityForResult(userAuthEx.intent, 123)
                    } catch (ioEx: IOException) {
                        Log.d(TAG, "IOException")
                    } catch (fatalAuthEx: GoogleAuthException) {
                        Log.d(TAG, "Fatal Authorization Exception" + fatalAuthEx.localizedMessage)
                    }

                    return "no token"
                }

                override fun onPostExecute(token: String) {
                    Log.e(TAG, token)
                }

            }
            getToken.execute(null, null, null)
        }
    }

    private val G_PLUS_SCOPE = "oauth2:https://www.googleapis.com/auth/plus.me"
    private val USERINFO_SCOPE = "https://www.googleapis.com/auth/userinfo.profile"
    private val EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email"
    private val SCOPES = "$G_PLUS_SCOPE $USERINFO_SCOPE $EMAIL_SCOPE"

    private val TAG = "OrdersActivity"
}
