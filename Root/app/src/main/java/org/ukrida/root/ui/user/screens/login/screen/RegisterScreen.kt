package org.ukrida.root.ui.user.screens.login.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.ukrida.root.ui.theme.DarkBrown
import org.ukrida.root.ui.theme.H1Color
import org.ukrida.root.ui.theme.MicroElement
import org.ukrida.root.ui.user.screens.login.viewmodel.RegisterViewModel
import org.ukrida.root.utils.Resource

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    // Navigate to login on success
    LaunchedEffect(state) {
        if (state is Resource.Success) {
            onRegisterSuccess()
        }
    }

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBrown)
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
                    .padding(start = 40.dp, top = 63.dp)
            )
        }

        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                "REGISTER",
                color = H1Color,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Create your ROOT account",
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
            //Show error
            (state as? Resource.Error)?.let { error ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = error.message, color = Color.Red)
            }

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = {
                    viewModel.register(
                        firstName,
                        lastName,
                        username,
                        email,
                        phone,
                        password,
                        confirmPassword
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MicroElement
                )
            ) {
                if (state is Resource.Loading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
                } else {
                    Text("REGISTER", fontSize = 18.sp)
                }
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
                    onNavigateToLogin()
                }

            }

            Spacer(Modifier.height(30.dp))

        }

    }
}
@Composable
fun RegisterField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val cream = Color(0xFFE5C19A)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        label = {
            Text(
                text = title,
                color = H1Color
            )
        },
        textStyle = TextStyle(
            color = Color.White
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = H1Color,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = H1Color,
            unfocusedLabelColor = H1Color,
            cursorColor = H1Color
        )
    )

    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun RegisterPassword(
    title: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val cream = Color(0xFFE5C19A)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        label = {
            Text(
                text = title,
                color = H1Color
            )
        },
        textStyle = TextStyle(
            color = Color.White
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = H1Color,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = H1Color,
            unfocusedLabelColor = H1Color,
            cursorColor = H1Color
        )
    )

    Spacer(modifier = Modifier.height(20.dp))
}