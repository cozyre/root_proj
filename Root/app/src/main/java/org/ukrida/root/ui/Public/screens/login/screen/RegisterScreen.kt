package org.ukrida.root.ui.Public.screens.login.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(
    onBackLogin: () -> Unit
) {

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val darkBrown = Color(0xFF2A2522)
    val cream = Color(0xFFE5C19A)
    val olive = Color(0xFF7A8A4A)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBrown)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            WaveHeader()
            Text(
                text = "✝",
                fontSize = 80.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(
                        start = 40.dp,
                        top = 63.dp
                    )
            )
        }
        Column(
            modifier = Modifier.padding(24.dp)
        ) {

            Text(
                "REGISTER",
                color = cream,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Create your PilgrimMate account",
                color = Color.LightGray
            )

            Spacer(Modifier.height(30.dp))

            RegisterField("First Name", firstName) { firstName = it }

            RegisterField("Last Name", lastName) { lastName = it }

            RegisterField("Username", username) { username = it }

            RegisterField("Email", email) { email = it }

            RegisterField("Phone Number", phone) { phone = it }

            RegisterPassword("Password", password) {
                password = it
            }

            RegisterPassword("Verify Password", confirmPassword) {
                confirmPassword = it
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = olive
                )
            ) {
                Text("REGISTER")
            }

            Spacer(Modifier.height(20.dp))

            val loginText = buildAnnotatedString {

                append("Already have account? ")

                pushStringAnnotation("LOGIN","LOGIN")

                withStyle(
                    SpanStyle(
                        color = Color.White,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Login now")
                }

                pop()
            }

            ClickableText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = loginText,
                style = LocalTextStyle.current.copy(
                    color = Color.LightGray
                )
            ){ offset ->

                loginText.getStringAnnotations(
                    "LOGIN",
                    offset,
                    offset
                ).firstOrNull()?.let{
                    onBackLogin()
                }

            }

            Spacer(Modifier.height(30.dp))

        }

    }
}
@Composable
fun RegisterField(
    title:String,
    value:String,
    onValueChange:(String)->Unit
){

    val cream = Color(0xFFE5C19A)

    Text(
        title,
        color = cream,
        fontWeight = FontWeight.Bold
    )

    OutlinedTextField(
        value=value,
        onValueChange=onValueChange,
        modifier=Modifier.fillMaxWidth(),
        singleLine=true
    )

    Spacer(Modifier.height(18.dp))
}

@Composable
fun RegisterPassword(
    title:String,
    value:String,
    onValueChange:(String)->Unit
){

    val cream = Color(0xFFE5C19A)

    Text(
        title,
        color=cream,
        fontWeight = FontWeight.Bold
    )

    OutlinedTextField(
        value=value,
        onValueChange=onValueChange,
        modifier=Modifier.fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation(),
        singleLine=true
    )

    Spacer(Modifier.height(18.dp))
}