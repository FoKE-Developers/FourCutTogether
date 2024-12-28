package com.foke.together.presenter.frame

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.foke.together.domain.interactor.entity.CutFrame
import com.foke.together.util.AppLog

@Composable
fun DefaultCutFrame(
    cutFrame: CutFrame,
    imageUrlList : List<Uri>,
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
            modifier = Modifier.fillMaxSize()
        )

        // Cut Image
        cutFrame.photoPosition.zip(imageUrlList).forEachIndexed { index, (position, imageUrl) ->

            AppLog.e("====", "$imageUrl")

            if (index >= cutFrame.cutCount) { return@forEachIndexed }
            Box(
                modifier = Modifier
                    .width(position.width.dp)
                    .height(position.height.dp)
                    .offset(x = position.x.dp, y = position.y.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .build(),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // Additional Images
        cutFrame.additionalFrameImageSrc.forEach { layers ->
            // !!!!! TODO: add layer images
        }
    }
}