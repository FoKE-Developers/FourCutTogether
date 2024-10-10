package com.foke.together.presenter.frame

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.foke.together.presenter.R
import com.foke.together.presenter.theme.FourCutTogetherTheme
import com.foke.together.util.AppPolicy
import com.foke.together.util.ImageFileUtil
import com.foke.together.util.TimeUtil
import kotlinx.coroutines.launch

@Composable
fun MakerFaireFrame(
    cameraImageUrlList : List<Uri>? = null,
    backgroundColor : Color = Color(0xFFF5B934),
    decorateImageUrl: String? = null
) {
    ConstraintLayout(
        modifier = Modifier
            .aspectRatio(ratio = 0.3333f)
            .background(backgroundColor)
            .border(1.dp, Color.White)
    ) {
        val (cameraColumn,decorateImage, curTime) = createRefs()
        val logoImageGuideLineStart = createGuidelineFromStart(0.15f)
        val logoImageGuideLineEnd = createGuidelineFromEnd(0.15f)
        LazyColumn(
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
            modifier = Modifier
                .constrainAs(cameraColumn) {
                    top.linkTo(parent.top, margin = 15.dp)
                    bottom.linkTo(decorateImage.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .wrapContentSize()
        ) {
            items(AppPolicy.CAPTURE_COUNT){
                Box(
                    modifier = Modifier
                        .aspectRatio(1.5f)
                        .background(color = Color.White)
                ){
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(cameraImageUrlList?.get(it))
                            .build(),
                        contentDescription = "",
                        modifier = Modifier.fillParentMaxSize()
                    )
                }
            }
        }

        Image(
            painter =  painterResource(
                id = R.drawable.maker_faire_logo
            ),
            contentDescription = "for decorate",
            modifier = Modifier.constrainAs(decorateImage){
                top.linkTo(cameraColumn.bottom)
                bottom.linkTo(curTime.top)
                start.linkTo(logoImageGuideLineStart)
                end.linkTo(logoImageGuideLineEnd)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }
        )

        Text(
            text = TimeUtil.getCurrentDisplayTime(),
            modifier = Modifier.constrainAs(curTime){
                top.linkTo(decorateImage.bottom)
                bottom.linkTo(parent.bottom, margin = 15.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultFrame() {
    FourCutTogetherTheme {
        MakerFaireFrame()
    }
}