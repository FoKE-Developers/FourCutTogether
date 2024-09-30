package com.foke.together.presenter.frame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.foke.together.presenter.R
import com.foke.together.presenter.ui.theme.FourCutTogetherTheme
import com.foke.together.presenter.ui.theme.highContrastDarkColorScheme
import com.foke.together.presenter.ui.theme.mediumContrastLightColorScheme

val frameRatio = 0.3333f
@Composable
fun FourCutFrame(
    designColorScheme: ColorScheme = mediumContrastLightColorScheme,
    cameraImageUrlList : List<String>? = null,
) {
    ConstraintLayout(
        modifier = Modifier
            .aspectRatio(ratio = 0.3333f)
            .background(color = designColorScheme.surface)
            .padding(10.dp)
    ) {
        val (startBarrier, endBarrier, cameraColumn, background, decorateImage, curTime) = createRefs()
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
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }

        ) {
            items(4){
                //TODO: add camera image
                // change Box -> ImageView
                Box(
                    modifier = Modifier
                        .aspectRatio(1.5f)
                        .background(color = designColorScheme.inverseSurface)
                )
            }
        }

        Icon(
            imageVector =  ImageVector.vectorResource(
                id = R.drawable.fourcut_together
            ),
            contentDescription = "for decorate",
            modifier = Modifier
                .constrainAs(decorateImage) {
                    top.linkTo(cameraColumn.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .size(
                    width = 48.dp,
                    height = 48.dp
                ),
            tint = designColorScheme.primaryContainer
        )
        Text(
            text = "2024.09.27",
            modifier = Modifier.constrainAs(curTime){
                top.linkTo(decorateImage.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            color = designColorScheme.inverseSurface,
            fontSize = 12.sp
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun DefaultFrame() {
    FourCutTogetherTheme {
        FourCutFrame(
            designColorScheme = highContrastDarkColorScheme
        )
    }
}