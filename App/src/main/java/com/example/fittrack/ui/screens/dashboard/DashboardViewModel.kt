import androidx.lifecycle.ViewModel
import com.example.fittrack.navigation.Navigator

class DashboardViewModel : ViewModel() {
    fun onBack() {
        Navigator.goBack()
    }
}