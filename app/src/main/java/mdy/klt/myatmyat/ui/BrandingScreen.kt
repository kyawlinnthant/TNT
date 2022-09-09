package mdy.klt.myatmyat.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import mdy.klt.myatmyat.R
import mdy.klt.myatmyat.navigation.destination.Destinations
import mdy.klt.myatmyat.theme.Dimen
import mdy.klt.myatmyat.theme.dimen

@Composable
fun BrandingScreen(navController: NavController) {
    val viewModel: MyViewModel = hiltViewModel()
    var logoAnimation by remember {
        mutableStateOf(false)
    }

    val alphaAnimation = animateFloatAsState(
        targetValue = if (logoAnimation) 0f else 1f, animationSpec = tween(
            durationMillis = 1000
        )
    )

    LaunchedEffect(key1 = true) {
        logoAnimation = true
        delay(100)
        navController.popBackStack()
        navController.navigate(Destinations.HistoryList.route)
    }
    BrandingView(alpha = alphaAnimation.value)
}

@Composable
fun BrandingView(alpha: Float, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = modifier.weight(1f), contentAlignment = Alignment.Center) {
                Image(modifier = modifier
                    .size(MaterialTheme.dimen.base_10x)
                    .alpha(alpha = alpha),
                painter = painterResource(id = R.drawable.ic_money), contentDescription = "splash screen icon")
            }
        }
    }
}