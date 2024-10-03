package com.foke.together.presenter.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.foke.together.presenter.R
import com.foke.together.presenter.theme.FourCutTogetherTheme
import com.foke.together.presenter.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navigationSelectFrame: () -> Unit,
    navigateToSetting: () -> Unit,
    popBackStack: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    FourCutTogetherTheme() {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (topBarrier, bottomBarrier, startBarrier, endBarrier, play, setting, summary) = createRefs()

            Image(
                painter = painterResource(
                    id = R.drawable.fourcut_together
                ),
                contentDescription = "FourCuts Together",
                modifier = Modifier.constrainAs(play) {
                    centerTo(parent)
                    height = Dimension.wrapContent
                    width = Dimension.wrapContent
                },

            )

            IconButton(
                onClick = { navigateToSetting() },
                modifier = Modifier.constrainAs(setting) {
                    top.linkTo(parent.top, margin = 24.dp)
                    end.linkTo(parent.end, margin = 24.dp)
                    height = Dimension.wrapContent
                    width = Dimension.wrapContent
                },
            ) {
                Icon(
                    modifier = Modifier.size(85.dp),
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Setting",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Button(
                onClick = { navigationSelectFrame() },
                modifier = Modifier
                    .constrainAs(summary) {
                        top.linkTo(play.bottom)
                        start.linkTo(parent.start, margin = 64.dp)
                        end.linkTo(parent.end, margin = 64.dp)
                        bottom.linkTo(parent.bottom)
                        height = Dimension.wrapContent
                        width = Dimension.fillToConstraints
                    }
            ) {
                Text(
                    text = "시작하기",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    FourCutTogetherTheme() {
        HomeScreen(
            navigationSelectFrame = {},
            navigateToSetting = {},
            popBackStack = {}
        )
    }
}