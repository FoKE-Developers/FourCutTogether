package com.foke.together.presenter.frame

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.foke.together.domain.interactor.entity.DefaultCutFrameSet
import com.foke.together.util.TimeUtil

@Composable
fun DefaultCutFrame(
    cutFrame: DefaultCutFrameSet,
    imageUrlList : List<Uri>,
    qrImageBitmap: Bitmap? = null
) {
    Box(
        modifier = Modifier
            .width(cutFrame.width.dp)
            .height(cutFrame.height.dp)
    ) {
        // Background Image
        Image(
            painter = painterResource(id = cutFrame.frameImageSrc),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.TopStart,
            contentScale = ContentScale.Crop,
        )

        // Cut Image
        cutFrame.photoPosition.zip(imageUrlList).forEachIndexed { index, (position, imageUrl) ->
            if (index >= cutFrame.cutCount) { return@forEachIndexed }
            Box(
                modifier = Modifier
                    .width(position.width.dp)
                    .height(position.height.dp)
                    .rotate(position.rotation)
                    .offset(x = position.x.dp, y = position.y.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // Additional Overlay Images
        cutFrame.additionalFrameImageSrc.forEach { layers ->
            Image(
                painter = painterResource(id = layers),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.TopStart,
                contentScale = ContentScale.Crop,
            )
        }

        // Copyright Text Sticker
        Text(
            modifier = Modifier
                .width(cutFrame.copyrightPosition.width.dp)
                .height(cutFrame.copyrightPosition.height.dp)
                .background(cutFrame.copyrightPosition.backgroundColor)
                .rotate(cutFrame.copyrightPosition.rotation)
                .offset(x = cutFrame.copyrightPosition.x.dp, y = cutFrame.copyrightPosition.y.dp),
            text = stringResource(com.foke.together.presenter.R.string.copyright),
            color = cutFrame.copyrightPosition.color,
            fontSize = cutFrame.copyrightPosition.fontSize.sp,
            fontWeight = cutFrame.copyrightPosition.fontWeight,
            textAlign = cutFrame.copyrightPosition.textAlign
        )

        // Date Text Sticker
        if (cutFrame.isDateString) {
            Text(
                modifier = Modifier
                    .width(cutFrame.datePosition.width.dp)
                    .height(cutFrame.datePosition.height.dp)
                    .rotate(cutFrame.datePosition.rotation)
                    .offset(x = cutFrame.datePosition.x.dp, y = cutFrame.datePosition.y.dp),
                text = TimeUtil.getCurrentDisplayTime(),
                color = cutFrame.datePosition.color,
                fontSize = cutFrame.datePosition.fontSize.sp,
                fontWeight = cutFrame.datePosition.fontWeight,
                textAlign = cutFrame.datePosition.textAlign
            )
        }

        // QR Code Sticker
        if (cutFrame.isQrCode) {
            qrImageBitmap?.let {
                Box(
                    modifier = Modifier
                        .width(cutFrame.qrCodePosition.size.dp)
                        .height(cutFrame.qrCodePosition.size.dp)
                        .offset(x = cutFrame.qrCodePosition.x.dp, y = cutFrame.qrCodePosition.y.dp),
                ) {
                    Image(
                        bitmap = qrImageBitmap.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}