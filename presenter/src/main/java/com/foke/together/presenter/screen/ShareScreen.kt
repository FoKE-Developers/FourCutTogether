package com.foke.together.presenter.screen

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.createBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.foke.together.presenter.component.AppBottomBar
import com.foke.together.presenter.component.AppTopBar
import com.foke.together.presenter.component.BasicScaffold
import com.foke.together.presenter.theme.AppTheme
import com.foke.together.presenter.theme.FourCutTogetherTheme
import com.foke.together.presenter.viewmodel.ShareViewModel

@Composable
fun ShareScreen(
    popBackStack: () -> Unit,
    viewModel: ShareViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val captureImageUri = viewModel.singleImageUri
    val qrImageBitmap = viewModel.qrCodeBitmap
    DisposableEffect(Unit) {
        viewModel.updateSessionStatus()
        viewModel.setupTimer(
            finished = popBackStack
        )
        viewModel.startTimer()
        onDispose {
            viewModel.closeTimer()
        }
    }

    ShareContent(
        context = context,
        captureImageUri = captureImageUri,
        qrImageBitmap = qrImageBitmap,
        popBackStack = popBackStack,
        printImage = {
            viewModel.printImage(context)
        },
        downloadImage = {
            viewModel.downloadImage()
                .onSuccess {
                    Toast.makeText(context, "사진이 저장되었습니다.", Toast.LENGTH_SHORT).show()
                }
                .onFailure {
                    Toast.makeText(context, "사진 저장에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
        },
        shareImage = {
            viewModel.shareImage(context)
        }
    )
}

@Composable
fun ShareContent(
    context: Context,
    captureImageUri : Uri,
    qrImageBitmap : Bitmap?,
    popBackStack: () -> Unit,
    downloadImage: () -> Unit,
    printImage: () -> Unit,
    shareImage: (context: Context) -> Unit
){
    BasicScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = "이미지 저장",
                alignment = Alignment.CenterHorizontally,
            )
        },
        bottomBar = {
            AppBottomBar{
                Column(
                    modifier = Modifier.wrapContentSize()
                        .clickable{
                            printImage()
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Icon(
                        imageVector = Icons.Filled.Print,
                        contentDescription = "print",
                        tint = AppTheme.colorScheme.top
                    )
                    Text(
                        text = "출력",
                        style = AppTheme.typography.title,
                        color = AppTheme.colorScheme.border
                    )
                }

                Column(
                    modifier = Modifier.wrapContentSize()
                        .clickable{
                            downloadImage()
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Icon(
                        imageVector = Icons.Filled.Download,
                        contentDescription = "download",
                        tint = AppTheme.colorScheme.top
                    )
                    Text(
                        text = "저장",
                        style = AppTheme.typography.title,
                        color = AppTheme.colorScheme.border
                    )
                }

                Column(
                    modifier = Modifier.wrapContentSize()
                        .clickable{
                            shareImage(context)
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "sharing",
                        tint = AppTheme.colorScheme.top
                    )
                    Text(
                        text = "공유",
                        style = AppTheme.typography.title,
                        color = AppTheme.colorScheme.border
                    )
                }

                Column(
                    modifier = Modifier.wrapContentSize()
                        .clickable(true, onClick = popBackStack),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Icon(
                        modifier = Modifier.size(AppTheme.size.icon),
                        imageVector = Icons.Filled.Close,
                        contentDescription = "close",
                        tint = AppTheme.colorScheme.top
                    )
                    Text(
                        text = "닫기",
                        style = AppTheme.typography.title,
                        color = AppTheme.colorScheme.border
                    )
                }
            }
        },
    ){ paddingValues ->
        Row(
            modifier = Modifier.fillMaxSize().padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding(),
                start = AppTheme.size.layoutPadding,
                end = AppTheme.size.layoutPadding
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            // TODO: need to check to change single ImageView
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(captureImageUri)
                    .build(),
                contentDescription = "",
                modifier = Modifier
                    .aspectRatio(0.7f)
                    .fillMaxHeight(1.0f)
            )

            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(qrImageBitmap)
                    .build(),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .aspectRatio(1f)
            )
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.TABLET
)
@Composable
private fun ShareScreenPreview() {
    val qrBitmap = createBitmap(125, 125, Bitmap.Config.ARGB_8888)
    FourCutTogetherTheme {
        ShareContent(
            context = LocalContext.current,
            captureImageUri = Uri.EMPTY,
            qrImageBitmap = qrBitmap,
            popBackStack = {},
            downloadImage = {},
            printImage = {},
            shareImage = {}
        )
    }
}