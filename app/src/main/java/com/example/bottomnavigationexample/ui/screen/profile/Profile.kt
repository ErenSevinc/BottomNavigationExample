package com.example.bottomnavigationexample.ui.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController

const val mask = "+90 (xxx) xxx xx xx"
const val maskChar = 'x'
//const val mask = "XXXX-XXXX-XXXX-XXXX"
//const val maskChar = 'x'

@Composable
fun ProfileScreen(navController: NavController) {
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    Column {
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
            onPhoneChanged(it.take(mask.count { it == maskChar }))
        },
        label = {
            Text(text = "Phone number")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        visualTransformation = PhoneVisualTransformation(mask, maskChar) ,
        modifier = modifier.fillMaxWidth(),
    )
}



class PhoneVisualTransformation(val mask: String, val maskChar: Char) : VisualTransformation {

    //private val maxLength = mask.count { it == maskChar }

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.length >= 10) text.text.substring(0..9) else text.text

        val annotatedString = buildAnnotatedString {
            if (trimmed.isEmpty()) return@buildAnnotatedString
            var maskIndex = 0
            var textIndex = 0
            while (textIndex < trimmed.length && maskIndex < mask.length) {
                if (mask[maskIndex] != maskChar) {
                    val nextDigitIndex = mask.indexOf(maskChar, maskIndex)
                    append(mask.substring(maskIndex, nextDigitIndex))
                    maskIndex = nextDigitIndex
                }
                append(trimmed[textIndex++])
                maskIndex++
            }
        }

        return TransformedText(annotatedString, PhoneOffsetMapper())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PhoneVisualTransformation) return false
        if (mask != other.mask) return false
        if (maskChar != other.maskChar) return false
        return true
    }

    override fun hashCode(): Int {
        return mask.hashCode()
    }
}

private class PhoneOffsetMapper() : OffsetMapping {

    override fun originalToTransformed(offset: Int): Int {

        var noneDigitCount = 0
        var i = 0
        while (i < offset + noneDigitCount) {
            if (mask[i++] != maskChar) noneDigitCount++
        }
        return offset + noneDigitCount
    }

    override fun transformedToOriginal(offset: Int): Int =
        offset - mask.take(offset).count {
            it != maskChar
        }
}



