package dot.adun.vault.ui.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VaultInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    error: Boolean = false,
    visual: VisualTransformation = VisualTransformation.None,
    type: KeyboardType = KeyboardType.Text,
    actionType: ImeAction = ImeAction.Default,
    actions: KeyboardActions = KeyboardActions(),
    options: KeyboardOptions = KeyboardOptions(keyboardType = type, imeAction = actionType),
    height: Dp = 50.dp,
    tint: Color = MaterialTheme.colorScheme.outline,
    modifier: Modifier = Modifier,
    leadingContent: @Composable () -> Unit = {},
) {
    var isVisible by remember { mutableStateOf(false) }
    val isPassword = visual == PasswordVisualTransformation()
    var isFocused by remember { mutableStateOf(false) }
    val innerTint = if (error) MaterialTheme.colorScheme.error else if (isFocused) MaterialTheme.colorScheme.primary else tint
    val textStyle = TextStyle(
        color = innerTint,
        fontSize = 15.sp
    )

    Row(modifier = modifier
        .height(height)
        .background(
            color = innerTint.copy(alpha = 0.25f),
            shape = RoundedCornerShape(8.dp)
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingContent()
        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .onFocusChanged { isFocused = it.isFocused },
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            enabled = enabled,
            readOnly = !enabled,
            keyboardActions = actions,
            keyboardOptions = options,
            visualTransformation = if (isPassword && isVisible) VisualTransformation.None else visual,
            textStyle = textStyle,
            decorationBox = { innerTextField ->
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp, horizontal = 15.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    innerTextField()
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = textStyle,
                            maxLines = 1,
                            modifier = Modifier
                                .padding(start = 1.dp)
                        )
                    }
                }

            }
        )
    }
}