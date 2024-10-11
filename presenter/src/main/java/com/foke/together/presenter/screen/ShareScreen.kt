package com.foke.together.presenter.screen

import android.net.Uri
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import com.foke.together.presenter.frame.FourCutFrame
import com.foke.together.presenter.theme.FourCutTogetherTheme
import com.foke.together.presenter.theme.highContrastDarkColorScheme
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.foke.together.presenter.viewmodel.ShareViewModel
import com.foke.together.util.ImageFileUtil

@Composable
fun ShareScreen(
    popBackStack: () -> Unit,
    viewModel: ShareViewModel = hiltViewModel()
) {
    val finalSingleImageUri = viewModel.getFinalSingleImageUri()
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (finalPic, printButton, shareButton, downloadButton, homeButton ) = createRefs()
        val frameType = 0
        val topGuideLine = createGuidelineFromTop(0.1f)
        val bottomGuideLine = createGuidelineFromBottom(0.1f)
        val startGuideLine = createGuidelineFromStart(0.2f)
        val endGuideLine = createGuidelineFromEnd(0.2f)
        val frameBarrier = createEndBarrier(finalPic)
        createVerticalChain(
            homeButton, printButton, shareButton, downloadButton,
            chainStyle = ChainStyle.Spread
        )

        // TODO: need check to change single ImageView
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(finalSingleImageUri)
                .build(),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(finalPic) {
                    top.linkTo(topGuideLine)
                    bottom.linkTo(bottomGuideLine)
                    start.linkTo(parent.start, margin = 30.dp)
                    width = Dimension.wrapContent
                    height = Dimension.fillToConstraints
                }
                .aspectRatio(0.3333f)
        )

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
            onClick = {
                val finalTwoRowImageUri = viewModel.getFinalTwoImageUri()
                ImageFileUtil.printFromUri(context,finalTwoRowImageUri)
            },
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
            onClick = {
                val finalFile = finalSingleImageUri.toFile()
                val contentUri = FileProvider.getUriForFile(
                    context,
                    "com.foke.together.fileprovider",
                    finalFile
                )
                ImageFileUtil.shareUri(context, contentUri)
            },
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
            onClick = { viewModel.downloadImage() },
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
    // TODO: Lifecycle 맞춰서 로딩하기
//    LifecycleEventEffect(Lifecycle.Event.ON_CREATE) {
//        context.filesDir.listFiles().forEach {
//                file -> if (file.name.contains("final_single_row.jpg")) {
//                finalSingleImageUri = Uri.fromFile(file)
//            }
//        }
//    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    FourCutTogetherTheme {
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