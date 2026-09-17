package org.ukrida.root.ui.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import org.ukrida.root.R

@Composable
fun LegalLinks(
    modifier: Modifier = Modifier,
    color: Color = Color.LightGray
) {
    val uriHandler = LocalUriHandler.current
    val landingPageUrl = stringResource(R.string.landing_page_url)
    val privacyPolicyUrl = stringResource(R.string.privacy_policy_url)
    val links = buildAnnotatedString {
        pushStringAnnotation("LANDING_PAGE", landingPageUrl)
        withStyle(SpanStyle(color = Color.White, textDecoration = TextDecoration.Underline)) {
            append("Website")
        }
        pop()
        append("  |  ")
        pushStringAnnotation("PRIVACY_POLICY", privacyPolicyUrl)
        withStyle(SpanStyle(color = Color.White, textDecoration = TextDecoration.Underline)) {
            append("Privacy Policy")
        }
        pop()
    }

    ClickableText(
        modifier = modifier,
        text = links,
        style = LocalTextStyle.current.copy(color = color, fontSize = 13.sp),
        onClick = { offset ->
            links.getStringAnnotations(start = offset, end = offset)
                .firstOrNull()
                ?.let { annotation -> uriHandler.openUri(annotation.item) }
        }
    )
}
