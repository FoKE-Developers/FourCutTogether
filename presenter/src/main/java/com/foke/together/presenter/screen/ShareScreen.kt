package com.foke.together.presenter.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.foke.together.presenter.frame.FourCutFrame
import com.foke.together.presenter.ui.theme.FourCutTogetherTheme
import com.foke.together.presenter.ui.theme.highContrastDarkColorScheme

@Composable
fun ShareScreen(
    popBackStack: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (finalPic, printButton, shareButton, downloadButton, homeButton ) = createRefs()
        // viewmodel과 합쳐야함 frameType
        val topGuideLine = createGuidelineFromTop(0.1f)
        val bottomGuideLine = createGuidelineFromBottom(0.1f)
        val startGuideLine = createGuidelineFromStart(0.2f)
        val endGuideLine = createGuidelineFromEnd(0.2f)

        val frameBarrier = createEndBarrier(finalPic)

        createVerticalChain(
            homeButton, printButton, shareButton, downloadButton,
            chainStyle = ChainStyle.Spread
        )

        val frameType = 0
        when(frameType){
            0 -> {
                Card(
                    modifier = Modifier
                        .constrainAs(finalPic){
                            top.linkTo(topGuideLine)
                            bottom.linkTo(bottomGuideLine)
                            start.linkTo(parent.start, margin = 30.dp)
                            width = Dimension.wrapContent
                            height = Dimension.fillToConstraints
                        }
                ){
                    FourCutFrame(
                        designColorScheme = highContrastDarkColorScheme,
                    )
                }
            }
        }
        IconButton(
            onClick = { popBackStack() },
            modifier = Modifier.constrainAs(homeButton) {
                top.linkTo(topGuideLine)
                end.linkTo(parent.end, margin = 30.dp)
                bottom.linkTo(printButton.top)
                height = Dimension.wrapContent
                width = Dimension.wrapContent
            },
        ) {
            Icon(
                modifier = Modifier.size(120.dp),
                imageVector = Icons.Filled.Home,
                contentDescription = "Home",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        IconButton(
            onClick = {  },
            modifier = Modifier.constrainAs(printButton) {
                top.linkTo(homeButton.bottom)
                end.linkTo(parent.end, margin = 30.dp)
                bottom.linkTo(shareButton.top)
                height = Dimension.wrapContent
                width = Dimension.wrapContent
            },
        ) {
            Icon(
                modifier = Modifier.size(120.dp),
                imageVector = Icons.Filled.Print,
                contentDescription = "Print",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        IconButton(
            onClick = {  },
            modifier = Modifier.constrainAs(shareButton) {
                top.linkTo(printButton.bottom)
                end.linkTo(parent.end, margin = 30.dp)
                bottom.linkTo(downloadButton.top)
                height = Dimension.wrapContent
                width = Dimension.wrapContent
            },
        ) {
            Icon(
                modifier = Modifier.size(120.dp),
                imageVector = Icons.Filled.Share,
                contentDescription = "Share",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        IconButton(
            onClick = {  },
            modifier = Modifier.constrainAs(downloadButton) {
                top.linkTo(shareButton.bottom)
                end.linkTo(parent.end, margin = 30.dp)
                bottom.linkTo(parent.bottom)
                height = Dimension.wrapContent
                width = Dimension.wrapContent
            },
        ) {
            Icon(
                modifier = Modifier.size(120.dp),
                imageVector = Icons.Filled.Download,
                contentDescription = "Download",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    FourCutTogetherTheme() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ShareScreen(
                popBackStack = {}
            )
        }
    }
}