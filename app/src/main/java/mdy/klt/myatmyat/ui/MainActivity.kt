package mdy.klt.myatmyat.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import mdy.klt.myatmyat.theme.MyatMyatTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyatMyatTheme {
                Surface {
                    val navController = rememberNavController()
                    MainScreen(navController = navController)
                }
                // A surface container using the 'background' color from the theme
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyatMyatTheme {
        Greeting("Android")
    }
}

// val ourSoldPercentage = totalSoldAmount * 0.1 //10 percentage
// val ourSoldNetBalance = ourPercentage * 1.7 //17 percentage commission
// val ourPayoffPercentage = totalPayoffAmount * 0.1 //10 percentage
// val ourWinningNetBalance = ourSoldNetBalance - ourPayoffPercentage