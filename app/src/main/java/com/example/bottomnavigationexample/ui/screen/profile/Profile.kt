package com.example.bottomnavigationexample.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController

const val mask = "+90 (###) ### ## ##"
const val maskChar = '#'

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    var phoneNumber by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        PhoneField(
            phone = phoneNumber,
            onPhoneChanged = { phoneNumber = it })

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneField(
    phone: String,
    modifier: Modifier = Modifier,
    onPhoneChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = phone,
        onValueChange = { it ->
            if (it.isDigitsOnly()) {
                onPhoneChanged(it.take(mask.count { it == maskChar }))
            }
        },
        label = {
            Text(text = "Phone number")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        visualTransformation = { numberFilter(it) },
        modifier = modifier
            .fillMaxWidth()
        ,
    )
}


fun numberFilter(text: AnnotatedString): TransformedText {

//    val maxLength = mask.count { it == maskChar }

//    val  trimmed = if (text.length > maxLength) text.take(maxLength) else text
    val trimmed = if (text.length >= 10) text.text.substring(0..9) else text.text

    val annotatedString = buildAnnotatedString {
//        if (trimmed.isEmpty()) return@buildAnnotatedString

        if (trimmed.length < 10) {
            pushStyle(SpanStyle(color = Color.Red))
        }

        var maskIndex = 0
        for (trimIndex in trimmed.indices) {
            if (mask[maskIndex] != maskChar) {
                val nextMaskIndex = mask.indexOf(maskChar, maskIndex)
                append(mask.substring(maskIndex, nextMaskIndex))
                maskIndex = nextMaskIndex
            }
            append(trimmed[trimIndex])
            maskIndex++
        }
//        pushStyle(SpanStyle(color = Color.LightGray))
//        append(mask.takeLast(mask.length - this.length))
    }

    val phoneNumberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            var noneDigitCount = 0
            var i = 0
            while (i < offset + noneDigitCount) {
                if (mask[i++] != maskChar) noneDigitCount++
            }
            return offset + noneDigitCount
        }

        override fun transformedToOriginal(offset: Int): Int {
            return offset - mask.take(offset).count {
                it != maskChar
            }
        }

    }

    return TransformedText(annotatedString, phoneNumberOffsetTranslator)
}

class PhoneVisualTransformation(val mask: String, val maskNumber: Char) : VisualTransformation {

    private val maxLength = mask.count { it == maskNumber }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.length > maxLength) text.take(maxLength) else text

        val annotatedString = buildAnnotatedString {
            if (trimmed.length < maxLength) {
                pushStyle(SpanStyle(color = Color.Red))
            }

            var maskIndex = 0
            for (textIndex in trimmed.indices) {
                if (mask[maskIndex] != maskNumber) {
                    val nextDigitIndex = mask.indexOf(maskNumber, maskIndex)
                    append(mask.substring(maskIndex, nextDigitIndex))
                    maskIndex = nextDigitIndex
                }
                append(trimmed[textIndex])
                maskIndex++
            }
//            pushStyle(SpanStyle(color = Color.Red))
//            append(mask.takeLast(mask.length - length))
        }

        return TransformedText(annotatedString, PhoneOffsetMapper(mask, maskNumber))
    }
}

private class PhoneOffsetMapper(val mask: String, val numberChar: Char) : OffsetMapping {

    override fun originalToTransformed(offset: Int): Int {
        var noneDigitCount = 0
        var i = 0
        while (i < offset + noneDigitCount) {
            if (mask[i++] != numberChar) noneDigitCount++
        }
        return offset + noneDigitCount
    }

    override fun transformedToOriginal(offset: Int): Int =
        offset - mask.take(offset).count { it != numberChar }
}



