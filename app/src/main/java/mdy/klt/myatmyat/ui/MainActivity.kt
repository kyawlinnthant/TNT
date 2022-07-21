package mdy.klt.myatmyat.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import mdy.klt.myatmyat.theme.MyatMyatTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyatMyatTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Calculator(name = "A Shal Gyi!!")
                }
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