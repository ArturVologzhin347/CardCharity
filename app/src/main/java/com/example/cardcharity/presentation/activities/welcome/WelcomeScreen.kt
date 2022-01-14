package com.example.cardcharity.presentation.activities.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cardcharity.R
import com.example.cardcharity.presentation.theme.CardCharityTheme
import com.example.cardcharity.presentation.ui.elements.AppButton
import com.example.cardcharity.presentation.ui.elements.ButtonText
import com.example.cardcharity.presentation.ui.elements.NestedResizeColumn
import com.example.cardcharity.presentation.ui.elements.VerticalSpace

@Composable
fun WelcomeScreen(
    reduce: (event: WelcomeEvent) -> Unit
) {
    NestedResizeColumn {
        WelcomeContainer(color = MaterialTheme.colors.primary) {
            VerticalSpace(56.dp)
            WelcomeLabel()
            VerticalSpace(56.dp)
            WelcomeText(
                text = stringResource(R.string.welcome_text_1),
                color = MaterialTheme.colors.onPrimary,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )
            VerticalSpace(16.dp)
            WelcomeText(
                text = stringResource(R.string.welcome_text_2),
                color = MaterialTheme.colors.onPrimary
            )
        }

        WelcomeContainer {
            VerticalSpace(24.dp)
            HowItWorksLabel()
            VerticalSpace(24.dp)

            Step(
                step = 1,
                text = stringResource(R.string.step_1_text)
            )
            VerticalSpace(56.dp)
            Step(
                step = 2,
                text = stringResource(R.string.step_2_text)
            )
            VerticalSpace(56.dp)
            Step(
                step = 3,
                text = stringResource(R.string.step_3_text),
                content = {
                    VerticalSpace(16.dp)
                    Text(
                        text = stringResource(R.string.step_3_text_helper),
                        style = MaterialTheme.typography.caption
                    )
                }
            )

            VerticalSpace(56.dp)

            AppButton(
                onClick = { reduce(next()) },
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier.fillMaxWidth()
            ) {
                ButtonText(
                    text = stringResource(R.string.log_in),
                    color = MaterialTheme.colors.onPrimary
                )
            }

            VerticalSpace(56.dp)
        }
    }
}

@Composable
fun Step(
    step: Int,
    text: String,
    backgroundColor: Color = MaterialTheme.colors.background,
    onBackgroundColor: Color = MaterialTheme.colors.onBackground,
    content: @Composable (ColumnScope.() -> Unit)? = null
) {
    Box(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            WelcomeStepLabel(
                step = step,
                color = onBackgroundColor
            )
            VerticalSpace(8.dp)

            WelcomeText(
                text = text,
                color = onBackgroundColor
            )

            content?.invoke(this)
        }
    }
}

@Composable
fun WelcomeStepLabel(
    step: Int,
    color: Color = MaterialTheme.colors.onBackground
) {
    Text(
        text = "${stringResource(R.string.step)} $step",
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = color
    )
}

@Composable
fun HowItWorksLabel() {
    Text(
        text = stringResource(R.string.how_it_works),
        fontSize = 42.sp,
        fontWeight = FontWeight.Light,
        color = MaterialTheme.colors.onBackground
    )
}

@Composable
fun WelcomeLabel() {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Light
                )
            ) {
                append(stringResource(R.string.welcome))
                append(" ${stringResource(R.string.to)} \n")
            }

            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.3.sp
                )
            ) {
                append(" ${stringResource(R.string.app_name)}!")
            }
        }
    )
}

@Composable
fun WelcomeText(
    text: String,
    color: Color = MaterialTheme.colors.onBackground,
    fontWeight: FontWeight = FontWeight(450),
    fontSize: TextUnit = 18.sp
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        lineHeight = 24.sp
    )
}

@Composable
fun WelcomeContainer(
    color: Color = MaterialTheme.colors.background,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        color = color,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            content = content,
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                )
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    CardCharityTheme {
        WelcomeScreen(reduce = {})
    }
}
