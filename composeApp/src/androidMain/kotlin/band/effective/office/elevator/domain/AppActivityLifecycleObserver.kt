package band.effective.office.elevator.domain

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import band.effective.office.elevator.AppActivity
import band.effective.office.elevator.OfficeElevatorConfig
import com.google.android.gms.auth.api.proxy.AuthApiStatusCodes
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes.SIGN_IN_CANCELLED
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dev.icerock.moko.resources.compose.stringResource
import io.github.aakira.napier.Napier
import band.effective.office.elevator.MR


class AppActivityLifecycleObserver(
    activity: AppActivity,
    private val registry: ActivityResultRegistry
) : DefaultLifecycleObserver {

    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .requestIdToken(OfficeElevatorConfig.webClient)
        .build()

    private val signInClient = GoogleSignIn.getClient(activity, gso)

    private lateinit var launcher: ActivityResultLauncher<Intent>

    private lateinit var callback: SignInResultCallback

    override fun onCreate(owner: LifecycleOwner) {
        launcher = registry.register(
            "key",
            owner,
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task, callback)
        }
    }

    private fun handleSignInResult(
        task: Task<GoogleSignInAccount>,
        callback: SignInResultCallback
    ) {
        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
            Napier.d { "ID_TOKEN = ${account.idToken}" }
            callback.onSuccess()
        } catch (e: ApiException) {
            Napier.e(
                "signInResult:failed code=" + e.statusCode + " | description: ${
                    GoogleSignInStatusCodes.getStatusCodeString(e.statusCode)
                }"
            )
        }
    }

    fun signIn(callback: SignInResultCallback) {
        this.callback = callback
        launcher.launch(signInClient.signInIntent)
    }

    fun signOut() {
        signInClient.signOut()
    }
}
