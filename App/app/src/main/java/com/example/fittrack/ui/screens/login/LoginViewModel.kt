import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrack.data.remote.ApiClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    val api = ApiClient.apiService
    val status = mutableStateOf("")

    private val _navigation = MutableSharedFlow<NavigationEvent>()
    val navigation: SharedFlow<NavigationEvent> = _navigation

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
                    isSigningIn.value = true;
                    val response = api.register(username.value, password.value)
                    status.value = if (response.success) "Login success" else response.message

                    Log.i("SUCCESS", "Login response: ${response.success}")
                    if (response.success) {
                        _navigation.emit(NavigationEvent.ToDashboard)
                    }
                } catch (e: Exception) {
                    status.value = "Network error: ${e.message}"
                    Log.e("LoginViewModel", "Login error", e)
                } finally {
                    isSigningIn.value = false;
                }
            }
        }
    }

    sealed class NavigationEvent {
        data object ToDashboard : NavigationEvent()
    }
}
