package com.foke.together.presenter.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import com.foke.together.presenter.theme.FourCutTogetherTheme
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.foke.together.presenter.viewmodel.ShareViewModel
import com.foke.together.util.AppLog
import com.foke.together.util.ImageFileUtil

@Composable
fun ShareScreen(
    popBackStack: () -> Unit,
    viewModel: ShareViewModel = hiltViewModel()
) {
    DisposableEffect(Unit) {
        viewModel.updateSessionStatus()
        onDispose { }
    }

    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (finalPic, buttonColumn, printButton, shareButton, downloadButton, homeButton ) = createRefs()
        val frameType = 0
        val topGuideLine = createGuidelineFromTop(0.1f)
        val bottomGuideLine = createGuidelineFromBottom(0.1f)
        val startGuideLine = createGuidelineFromStart(0.1f)
        val endGuideLine = createGuidelineFromEnd(0.1f)

        // TODO: need check to change single ImageView
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(viewModel.singleImageUri)
                .build(),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(finalPic) {
                    top.linkTo(topGuideLine)
                    bottom.linkTo(bottomGuideLine)
                    start.linkTo(startGuideLine)
                    end.linkTo(buttonColumn.start)
                    width = Dimension.wrapContent
                    height = Dimension.fillToConstraints
                }
                .aspectRatio(0.3333f)
        )

        Column(
            modifier = Modifier.constrainAs(buttonColumn){
                top.linkTo(topGuideLine)
                bottom.linkTo(bottomGuideLine)
                start.linkTo(finalPic.end)
                end.linkTo(endGuideLine)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            IconButton(
                onClick = {
                    viewModel.closeSession()
                    popBackStack()
                          },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(
                onClick = {

                    AppLog.e("", "", "asdf: ${viewModel.twoImageUri}")

                    ImageFileUtil.printFromUri(context, viewModel.twoImageUri)
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Filled.Print,
                    contentDescription = "Print",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            IconButton(
                onClick = {
                    val contentUri = FileProvider.getUriForFile(
                        context,
                        "com.foke.together.fileprovider",
                        viewModel.singleImageUri.toFile()
                    )
                    ImageFileUtil.shareUri(context, contentUri)
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Share",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Box(
                modifier = Modifier.weight(1f)
            ){
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(viewModel.qrCodeBitmap)
                        .build(),
                    contentDescription = "qr code",
                    modifier = Modifier.fillMaxSize()
                )
            }

//            TODO: add android native share button
//            IconButton(
//                onClick = {
//                viewModel.downloadImage()
//                },
//                modifier = Modifier.weight(1f)
//            ) {
//                Icon(
//                    modifier = Modifier.fillMaxSize(),
//                    imageVector = Icons.Filled.Download,
//                    contentDescription = "Download",
//                    tint = MaterialTheme.colorScheme.primary
//                )
//            }
        }
    }
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