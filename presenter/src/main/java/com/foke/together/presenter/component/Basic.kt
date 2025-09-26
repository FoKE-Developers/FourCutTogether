package com.foke.together.presenter.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Start
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.isUnspecified
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.foke.together.presenter.theme.AppTheme

@Composable
fun OptionButton(
    modifier: Modifier = Modifier,
    title: String,
    selected: Boolean,
    onSelected: () -> Unit,
){
    Surface(
        modifier = modifier,
        color = when{
            selected -> AppTheme.colorScheme.top
            else -> AppTheme.colorScheme.bottom
        },
        shape = AppTheme.shapes.container,
        onClick = onSelected,
    ){
        Text(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .padding(AppTheme.size.buttonPadding),
            text = title,
            color = when{
                selected -> AppTheme.colorScheme.tint
                else -> AppTheme.colorScheme.border
            },
            style = AppTheme.typography.body,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MultiOptionsButton(
    modifier: Modifier = Modifier,
    optionList : List<String>,
    selectedOptionIndex : Int,
    onChangeOption : (Int) -> Unit
){
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        optionList.forEachIndexed { index, title ->
            OptionButton(
                modifier = Modifier.wrapContentSize(),
                title = title,
                selected = selectedOptionIndex == index,
                onSelected = { onChangeOption(index) }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderPreference(
    modifier: Modifier = Modifier,
    title: String,
    sliderText: String,
    iconVector: ImageVector = Icons.Default.Start,
    sliderValue: Float,
    sliderRange: ClosedFloatingPointRange<Float>,
    onSliderValueChange: (Float) -> Unit
){
    ConstraintLayout(
        modifier = modifier,
    ){
        val (
            iconConstraint,
            titleConstraint,
            sliderValueConstraint,
            sliderPlusConstraint,
            sliderMinusConstraint,
            sliderConstraint
        ) = createRefs()


        Icon(
            modifier = Modifier.constrainAs(iconConstraint) {
                top.linkTo(parent.top)
                bottom.linkTo(sliderConstraint.top)
                start.linkTo(parent.start)
                end.linkTo(titleConstraint.start)
            },
            imageVector = iconVector,
            tint = AppTheme.colorScheme.bottom,
            contentDescription = "icon"
        )

        Text(
            modifier = Modifier.constrainAs(titleConstraint){
                top.linkTo(parent.top)
                bottom.linkTo(sliderConstraint.top)
                start.linkTo(iconConstraint.end)
                end.linkTo(sliderValueConstraint.start)
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
            }
                .padding(start = AppTheme.size.buttonPadding),
            text = title,
            style = AppTheme.typography.body,
            color = AppTheme.colorScheme.border
        )

        Text(
            modifier = Modifier.constrainAs(sliderValueConstraint) {
                top.linkTo(titleConstraint.top)
                bottom.linkTo(titleConstraint.bottom)
                start.linkTo(titleConstraint.end)
                end.linkTo(parent.end)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
                horizontalChainWeight = 2f
            }
                .padding(AppTheme.size.buttonPadding),
            text = sliderText,
            color = AppTheme.colorScheme.border,
            style = AppTheme.typography.body
        )


        createHorizontalChain(
            sliderMinusConstraint,
            sliderConstraint,
            sliderPlusConstraint,
        )

        Icon(
            modifier = Modifier.size(AppTheme.size.icon).constrainAs(sliderMinusConstraint) {
                top.linkTo(iconConstraint.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(sliderConstraint.start)
            },
            imageVector = Icons.Default.Remove,
            contentDescription = "slider minus",
            tint = AppTheme.colorScheme.border
        )

        Icon(
            modifier = Modifier.size(AppTheme.size.icon).constrainAs(sliderPlusConstraint) {
                top.linkTo(sliderValueConstraint.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(sliderConstraint.end)
                end.linkTo(parent.end)
            },
            imageVector = Icons.Default.Add,
            contentDescription = "slider minus",
            tint = AppTheme.colorScheme.border
        )

        Slider(
            modifier = Modifier.constrainAs(sliderConstraint) {
                top.linkTo(sliderMinusConstraint.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(sliderMinusConstraint.end)
                end.linkTo(sliderPlusConstraint.start)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            value = sliderValue,
            onValueChange = onSliderValueChange,
            valueRange = sliderRange,
            track = { sliderState ->
                SliderDefaults.Track(
                    modifier = Modifier.scale(scaleX = 1f, scaleY = 0.3f),
                    sliderState = sliderState,
                    colors = SliderDefaults.colors(
                        activeTrackColor = AppTheme.colorScheme.middle,
                        inactiveTrackColor = AppTheme.colorScheme.border
                    )
                )
            },
            thumb = {
                Card(
                    modifier = Modifier.size(AppTheme.size.buttonPadding),
                    elevation = CardDefaults.cardElevation(AppTheme.size.buttonPadding),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = AppTheme.colorScheme.bottom
                    ),
                    content = {}
                )
            },
            colors = SliderColors(
                thumbColor = AppTheme.colorScheme.top,
                activeTrackColor = AppTheme.colorScheme.middle,
                activeTickColor = AppTheme.colorScheme.bottom,
                inactiveTrackColor = AppTheme.colorScheme.middle,
                inactiveTickColor = AppTheme.colorScheme.bottom,
                disabledThumbColor = AppTheme.colorScheme.top,
                disabledActiveTrackColor = AppTheme.colorScheme.middle,
                disabledActiveTickColor = AppTheme.colorScheme.bottom,
                disabledInactiveTrackColor = AppTheme.colorScheme.middle,
                disabledInactiveTickColor = AppTheme.colorScheme.bottom,
            )
        )
    }
}

@Composable
fun BasicScaffold(
    modifier: Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,

    ){
    Scaffold(
        modifier = modifier
            .background(AppTheme.colorScheme.bottom),
        topBar = topBar,
        bottomBar = bottomBar,
        floatingActionButton = floatingActionButton,
        content = content,
        containerColor = AppTheme.colorScheme.bottom,
        contentColor = AppTheme.colorScheme.top,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    alignment : Alignment.Horizontal = Alignment.Start,
    titleStyle: TextStyle = AppTheme.typography.head,
    leftIcon : @Composable () -> Unit = {},
    rightIcon : @Composable RowScope.() -> Unit = {}
){
    when(alignment){
        Alignment.CenterHorizontally -> {
            CenterAlignedTopAppBar(
                modifier = Modifier.fillMaxWidth().wrapContentSize().padding(
                    start = AppTheme.size.layoutPadding,
                    end = AppTheme.size.layoutPadding
                ),
                title = {
                    Text(
                        text = title,
                        style = titleStyle,
                        textAlign = TextAlign.Center,
                        color = AppTheme.colorScheme.top
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.colorScheme.bottom,
                    scrolledContainerColor = AppTheme.colorScheme.bottom,
                    titleContentColor = AppTheme.colorScheme.border,
                    navigationIconContentColor = AppTheme.colorScheme.border,
                    actionIconContentColor = AppTheme.colorScheme.border
                ),
                navigationIcon = leftIcon,
                actions = rightIcon
            )
        }
        Alignment.Start -> {
            TopAppBar(
                modifier = Modifier.fillMaxWidth().wrapContentSize().padding(
                    start = AppTheme.size.layoutPadding,
                    end = AppTheme.size.layoutPadding
                ),
                title = {
                    Text(
                        text = title,
                        style = titleStyle,
                        textAlign = TextAlign.Center,
                        color = AppTheme.colorScheme.border
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.colorScheme.bottom,
                    scrolledContainerColor = AppTheme.colorScheme.bottom,
                    titleContentColor = AppTheme.colorScheme.border,
                    navigationIconContentColor = AppTheme.colorScheme.border,
                    actionIconContentColor = AppTheme.colorScheme.border
                ),
                navigationIcon = leftIcon,
                actions = rightIcon
            )
        }
    }
}

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    containerColor : Color = AppTheme.colorScheme.top,
    contentColor : Color = AppTheme.colorScheme.tint,
    disabledContainerColor : Color = AppTheme.colorScheme.bottom,
    disabledContentColor : Color = AppTheme.colorScheme.border,
    onClick: () -> Unit,
    content : @Composable RowScope.() -> Unit
){
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = AppTheme.shapes.button,
        colors = ButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor,
        ),
        content = content
    )
}


@Composable
fun AppText(
    text: String,
    style : TextStyle = AppTheme.typography.body,
    textAlign: TextAlign = TextAlign.Start,
    modifier: Modifier = Modifier,
    color : Color = AppTheme.colorScheme.border
){
    var resizedTextStyle by remember {
        mutableStateOf(style)
    }
    var shouldDraw by remember {
        mutableStateOf(false)
    }

    val defaultFontSize = style

    Text(
        text = text,
        color = color,
        textAlign = textAlign,
        modifier = modifier.drawWithContent {
            if (shouldDraw) {
                drawContent()
            }
        },
        softWrap = false,
        style = resizedTextStyle,
        onTextLayout = { result ->
            if (result.didOverflowWidth) {
                if (style.fontSize.isUnspecified) {
                    resizedTextStyle = resizedTextStyle.copy(
                        fontSize = defaultFontSize.fontSize
                    )
                }
                resizedTextStyle = resizedTextStyle.copy(
                    fontSize = resizedTextStyle.fontSize * 0.95
                )
            } else {
                shouldDraw = true
            }
        }
    )
}

@Composable
fun AppTextField(
    modifier : Modifier = Modifier,
    value : String,
    onValueChange : (String) -> Unit,
    label : String,
    enabled: Boolean = true,
){
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                style = AppTheme.typography.label,
                color = AppTheme.colorScheme.border
            )
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = AppTheme.colorScheme.border,
            unfocusedTextColor = AppTheme.colorScheme.border,
            focusedContainerColor = AppTheme.colorScheme.middle,
            unfocusedContainerColor = AppTheme.colorScheme.middle,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        enabled = enabled
    )
}

@Composable
fun AppBottomBar(
    content: @Composable (RowScope.() -> Unit)
){
    BottomAppBar(
        modifier = Modifier.fillMaxWidth().padding(
            start = AppTheme.size.layoutPadding,
            end = AppTheme.size.layoutPadding,
            bottom = AppTheme.size.layoutPadding
        ),
        containerColor = AppTheme.colorScheme.bottom,
        contentColor = AppTheme.colorScheme.top,
    ){
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Composable
fun AppCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
){
    Checkbox(
        modifier = modifier,
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = CheckboxColors(
            checkedCheckmarkColor = AppTheme.colorScheme.tint,
            uncheckedCheckmarkColor = AppTheme.colorScheme.bottom,
            checkedBoxColor = AppTheme.colorScheme.top,
            uncheckedBoxColor = AppTheme.colorScheme.bottom,
            disabledCheckedBoxColor = AppTheme.colorScheme.bottom,
            disabledUncheckedBoxColor = AppTheme.colorScheme.bottom,
            disabledIndeterminateBoxColor= AppTheme.colorScheme.bottom,
            checkedBorderColor = AppTheme.colorScheme.border,
            uncheckedBorderColor = AppTheme.colorScheme.top,
            disabledBorderColor = AppTheme.colorScheme.border,
            disabledUncheckedBorderColor = AppTheme.colorScheme.border,
            disabledIndeterminateBorderColor= AppTheme.colorScheme.border,
        )
    )
}