package com.foke.together.presenter.screen

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Print
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
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
import com.foke.together.presenter.component.AppTopBar
import com.foke.together.presenter.component.BasicScaffold
import com.foke.together.presenter.theme.AppTheme
import com.foke.together.presenter.viewmodel.ShareViewModel
import com.foke.together.util.AppLog
import com.foke.together.util.ImageFileUtil
import androidx.core.graphics.createBitmap

@Composable
fun ShareScreen(
    popBackStack: () -> Unit,
    viewModel: ShareViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val captureImageUri = viewModel.singleImageUri
    val qrImageBitmap by viewModel.qrCodeBitmap.collectAsState()
    DisposableEffect(Unit) {
        viewModel.updateSessionStatus()
        onDispose { }
    }

    ShareContent(
        context = context,
        captureImageUri = captureImageUri,
        qrImageBitmap = qrImageBitmap
    )
}

@Composable
fun ShareContent(
    context: Context,
    captureImageUri : Uri,
    qrImageBitmap : Bitmap?
){
    BasicScaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = "이미지 저장",
                alignment = Alignment.CenterHorizontally
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(
                    start = AppTheme.size.layoutPadding,
                    end = AppTheme.size.layoutPadding,
                    bottom = AppTheme.size.layoutPadding
                ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Column(
                    modifier = Modifier.wrapContentSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Icon(
                        imageVector = Icons.Outlined.Print,
                        contentDescription = "print",
                        tint = AppTheme.colorScheme.border
                    )
                    Text(
                        text = "출력",
                        style = AppTheme.typography.title,
                        color = AppTheme.colorScheme.border
                    )
                }

                Column(
                    modifier = Modifier.wrapContentSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Icon(
                        imageVector = Icons.Outlined.Download,
                        contentDescription = "download",
                        tint = AppTheme.colorScheme.border
                    )
                    Text(
                        text = "저장",
                        style = AppTheme.typography.title,
                        color = AppTheme.colorScheme.border
                    )
                }

                Column(
                    modifier = Modifier.wrapContentSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = "sharing",
                        tint = AppTheme.colorScheme.border
                    )
                    Text(
                        text = "공유",
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
            // TODO: need check to change single ImageView
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(captureImageUri)
                    .build(),
                contentDescription = "",
                modifier = Modifier
                    .aspectRatio(0.4f)
                    .fillMaxHeight(0.6f)
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

private fun saveToLocal(context: Context, viewModel: ShareViewModel) {
    viewModel.downloadImage()
        .onSuccess {
            Toast.makeText(context, "사진이 저장되었습니다.", Toast.LENGTH_SHORT).show()
        }
        .onFailure {
            Toast.makeText(context, "사진 저장에 실패했습니다.", Toast.LENGTH_SHORT).show()
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
            qrImageBitmap = qrBitmap
        )
    }
}