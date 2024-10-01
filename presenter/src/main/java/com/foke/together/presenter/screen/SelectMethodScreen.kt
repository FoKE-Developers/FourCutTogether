package com.foke.together.presenter.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.foke.together.presenter.theme.FourCutTogetherTheme

@Composable
fun SelectMethodScreen(
    navigateToCamera: () -> Unit,
    popBackStack: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (backKey, title, timerButton, gestureButton) = createRefs()
        val topGuideLine = createGuidelineFromTop(0.1f)
        val bottomGuideLine = createGuidelineFromBottom(0.1f)
        val startGuideLine = createGuidelineFromStart(0.2f)
        val endGuideLine = createGuidelineFromEnd(0.2f)

        IconButton(
            onClick = { popBackStack() },
            modifier = Modifier.constrainAs(backKey) {
                top.linkTo(topGuideLine)
                start.linkTo(parent.start)
                end.linkTo(title.start)
                height = Dimension.wrapContent
                width = Dimension.wrapContent
            },
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "backKey",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = "Select Method",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(topGuideLine)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(timerButton.top)
            },
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )

        OutlinedButton(
            onClick = { navigateToCamera() },
            modifier = Modifier.constrainAs(timerButton) {
                top.linkTo(title.bottom)
                start.linkTo(startGuideLine)
                end.linkTo(endGuideLine)
                bottom.linkTo(gestureButton.top)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Timer", style = MaterialTheme.typography.titleLarge)
            Text(text = "⏱\uFE0F", style = MaterialTheme.typography.titleLarge)
        }

        OutlinedButton(
            onClick = { navigateToCamera() },
            modifier = Modifier.constrainAs(gestureButton) {
                top.linkTo(timerButton.bottom)
                start.linkTo(startGuideLine)
                end.linkTo(endGuideLine)
                bottom.linkTo(bottomGuideLine)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Gesture", style = MaterialTheme.typography.titleLarge)
            Text(text = "\uD83D\uDC4B", style = MaterialTheme.typography.titleLarge)
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
            SelectMethodScreen(
                navigateToCamera = {},
                popBackStack = {}
            )
        }
    }
}