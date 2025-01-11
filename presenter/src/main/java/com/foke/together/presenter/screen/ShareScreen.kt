package com.foke.together.presenter.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
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
import androidx.compose.ui.unit.dp
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
    val context = LocalContext.current

    DisposableEffect(Unit) {
        viewModel.updateSessionStatus()
        onDispose { }
    }

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (finalPic, buttonColumn, printButton, shareButton, downloadButton, homeButton ) = createRefs()
        val frameType = 0
        val topGuideLine = createGuidelineFromTop(0.1f)
        val bottomGuideLine = createGuidelineFromBottom(0.1f)
        val startGuideLine = createGuidelineFromStart(0.1f)
        val endGuideLine = createGuidelineFromEnd(0.1f)

        DisposableEffect(Unit) {
            saveToLocal(context, viewModel)
            onDispose { }
        }

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
                modifier = Modifier
                    .width(70.dp)
                    .weight(1f)
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
                modifier = Modifier
                    .width(70.dp)
                    .weight(1f)
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
                modifier = Modifier
                    .width(70.dp)
                    .weight(1f)
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Share",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            // TODO: add android native share button
            IconButton(
                onClick = {
                    saveToLocal(context, viewModel)
                          },
                modifier = Modifier
                    .width(70.dp)
                    .weight(1f)
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Filled.Download,
                    contentDescription = "Download",
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
        }
    }
}

private fun saveToLocal(context: Context, viewModel: ShareViewModel) {
    viewModel.downloadImage()
        .onSuccess {
            Toast.makeText(context, "사진이 저장되었습니다.", Toast.LENGTH_SHORT).show()
        }
        .onFailure {
            Toast.makeText(context, "사진 저장에 실패했습니다.", Toast.LENGTH_SHORT).show()
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