package com.foke.together.presenter.screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.foke.together.presenter.R
import com.foke.together.presenter.theme.FourCutTogetherTheme
import com.foke.together.presenter.viewmodel.SelectMethodViewModel

@Composable
fun SelectMethodScreen(
    navigateToCamera: () -> Unit,
    popBackStack: () -> Unit,
    viewModel: SelectMethodViewModel = hiltViewModel()
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val context = LocalContext.current
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
            modifier = Modifier
                .constrainAs(timerButton) {
                    top.linkTo(title.bottom)
                    start.linkTo(startGuideLine)
                    end.linkTo(endGuideLine)
                    bottom.linkTo(gestureButton.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
                .height(200.dp),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(id = R.string.select_method_timer_button_icon), style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center, fontSize = 60.sp)
                Text(text = stringResource(id = R.string.select_method_timer_button_text), style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center, fontSize = 36.sp)
            }
        }

        OutlinedButton(
            onClick = {
                // TODO: implement gesture function
                Toast.makeText(context, context.getString(R.string.select_method_gesture_button_toast), Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .constrainAs(gestureButton) {
                    top.linkTo(timerButton.bottom)
                    start.linkTo(startGuideLine)
                    end.linkTo(endGuideLine)
                    bottom.linkTo(bottomGuideLine)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
                .height(200.dp),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(id = R.string.select_method_gesture_button_icon), style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center, fontSize = 60.sp)
                Text(text = stringResource(id = R.string.select_method_gesture_button_text), style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center, fontSize = 36.sp)
            }
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