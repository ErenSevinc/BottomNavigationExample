package com.example.bottomnavigationexample.ui.screen.search

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

private val phoneTextList = listOf(
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        )
    ),
    mutableStateOf(
        TextFieldValue(
            text = "",
            selection = TextRange(0)
        )
    )
)
private val requesterList = listOf(
    FocusRequester(),
    FocusRequester(),
    FocusRequester(),
    FocusRequester(),
    FocusRequester(),
    FocusRequester(),
    FocusRequester(),
    FocusRequester(),
    FocusRequester(),
    FocusRequester(),
)

@Composable
fun SearchScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        ContentView()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ContentView() {

    val isBackspaceState = remember { mutableStateOf(false) }

    LaunchedEffect(null) {
        delay(300L)
        requesterList[0].requestFocus()
    }

    Row(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            text = "+90 (",
            fontSize = 24.sp

        )
        phoneTextList.forEachIndexed { index, mutableState ->
            if (index == 3) {
                Text(
                    text = ") ",
                    fontSize = 24.sp
                )
            }
            if (index == 6) {
                Text(text = "  ")
            }
            if (index == 8) {
                Text(text = "  ")
            }
            InputPhoneView(
                state = mutableState,
                onValueChange = {
                    if (mutableState.value.text != "") {
                        if (it.text != mutableState.value.text) {
                            if (isBackspaceState.value) {
                                nextFocus(index +1, requesterList)

                                mutableState.value = TextFieldValue(
                                    text = (it.text.lastOrNull() ?: "").toString(),
                                    selection = TextRange(it.text.length)
                                )

                                isBackspaceState.value = false
                            } else {
                                mutableState.value = TextFieldValue(
                                    text = (it.text.lastOrNull() ?: "").toString(),
                                    selection = TextRange(it.text.length)
                                )
                            }
                        } else {
                            if (it.text == "") {
                                mutableState.value =
                                    TextFieldValue(text = "", selection = TextRange(0))
                            }
                            return@InputPhoneView
                        }
                    }
                    mutableState.value = TextFieldValue(
                        text = (it.text.lastOrNull() ?: "").toString(),
                        selection = TextRange(it.text.length)
                    )
                    if (it.text != "") {
                        nextFocus(index + 1, requesterList)
                    }
                },
                focusRequester = requesterList[index],
                index = index,
                backspaceState = isBackspaceState
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputPhoneView(
    state: MutableState<TextFieldValue>,
    index: Int,
    backspaceState: MutableState<Boolean>,
    onValueChange: (value: TextFieldValue) -> Unit,
    focusRequester: FocusRequester
) {
    val focusState = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    BasicTextField(
        readOnly = false,
        value = state.value,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(horizontal = 2.dp)
            .clip(RoundedCornerShape(5.dp))
            .wrapContentSize()
            .background(Color.LightGray)
            .onFocusChanged {
                focusState.value = it.isFocused
            }
            .onKeyEvent {
                if (it.type == KeyEventType.KeyUp
                    && it.key == Key.Backspace
                    && focusState.value
                ) {
                    prevFocus(index - 1, requesterList)
                    backspaceState.value = true
                }

                false
            }
            .focusRequester(focusRequester),
        maxLines = 1,
        decorationBox = { innerTextField ->

            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(30.dp),
                contentAlignment = Alignment.Center,
            ) {
                innerTextField()
            }
        },
        cursorBrush = SolidColor(Color.Black),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusState.value = false
                focusManager.clearFocus()
                keyboardController?.hide()
                var number = ""
                phoneTextList.forEachIndexed { index, mutableState ->
                    number += mutableState.value.text
                }
                Toast.makeText(context, number, Toast.LENGTH_LONG).show()
                Log.e("NUMBER", number)
            }
        )
    )
}

private fun nextFocus(
    index: Int,
    requesterList: List<FocusRequester>,
) {
    if (index <= requesterList.size - 1) {
        requesterList[index - 1].freeFocus()
        requesterList[index].requestFocus()
    }
}

private fun prevFocus(
    index: Int,
    requesterList: List<FocusRequester>
) {
    if (index >= 0) {
        requesterList[index + 1].freeFocus()
        requesterList[index].requestFocus()
    }
}