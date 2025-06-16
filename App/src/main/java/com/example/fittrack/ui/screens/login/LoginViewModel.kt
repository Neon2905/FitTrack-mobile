import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrack.data.remote.ApiClient
import com.example.fittrack.navigation.NavRoute
import com.example.fittrack.navigation.Navigator
import com.example.fittrack.utils.Toast
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _api = ApiClient.apiService
    private val _status = mutableStateOf("")

    var isSigningIn = mutableStateOf(false)
        private set

    var username = mutableStateOf("username")
        private set

    var password = mutableStateOf("password")
        private set

    var title = mutableStateOf("Login")
        private set

    fun onUsernameChange(newUsername: String) {
        username.value = newUsername
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    fun onLoginClicked() {
        if (username.value.isNotBlank() && password.value.isNotBlank()) {
            viewModelScope.launch {
                try {
                    isSigningIn.value = true
                    val response = _api.register(username.value, password.value)
                    _status.value = if (response.success) "Login success" else response.message

                    if (response.success) {
                        Navigator.navigate(NavRoute.Dashboard.route)
                    }
                } catch (e: java.net.SocketTimeoutException) {
                    Toast.show("Cannot connect to server, please check your internet connection")
                    Log.e("LoginViewModel", "Login error", e)
                }
                catch (e: Exception) {
                    // TODO: Decide to add status variable or not
                    _status.value = "Network error: ${e.message}"
                    Log.e("LoginViewModel", "Login error", e)
                } finally {
                    isSigningIn.value = false
                }
            }
        }
    }
}
