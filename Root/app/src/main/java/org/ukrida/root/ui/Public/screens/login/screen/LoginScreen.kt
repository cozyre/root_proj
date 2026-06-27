package org.ukrida.root.ui.Public.screens.login.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit) {

    var identifier by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("User") }

    val topBrown = Color(0xFF9A775B)
    val darkBrown = Color(0xFF2A2522)
    val cream = Color(0xFFE5C19A)
    val olive = Color(0xFF7A8A4A)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF444444)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(0.dp)
        ) {
            Column {
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
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    darkBrown,
                                    Color(0xFF302A26)
                                )
                            )
                        )
                        .padding(24.dp)
                ) {

                    Text(
                        text = "LOGIN",
                        color = cream,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Login As",
                        color = cream
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        FilterChip(
                            selected = selectedRole == "User",
                            onClick = {
                                selectedRole = "User"
                            },
                            label = {
                                Text("User")
                            }
                        )
                        FilterChip(
                            selected = selectedRole == "Admin",
                            onClick = {
                                selectedRole = "Admin"
                            },
                            label = {
                                Text("Admin")
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Welcome to Root",
                        color = Color.LightGray
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    OutlinedTextField(
                        value = identifier,
                        onValueChange = { identifier = it },
                        label = {
                            Text(
                                "Username / Email",
                                color = cream
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = {
                            Text(
                                "Password",
                                color = cream
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = {
                            // Sementara langsung masuk Home
                            onLoginSuccess()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = olive
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            "LOGIN",
                            fontSize = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    ClickableText(
                        modifier = Modifier.fillMaxWidth(),
                        text = buildAnnotatedString {

                            append("Not having account? ")

                            pushStringAnnotation(
                                tag = "REGISTER",
                                annotation = "register"
                            )

                            withStyle(
                                style = SpanStyle(
                                    color = Color.White,
                                    textDecoration = TextDecoration.Underline,
                                    fontWeight = FontWeight.Medium
                                )
                            ) {
                                append("Register now")
                            }

                            pop()
                        },
                        style = LocalTextStyle.current.copy(
                            color = Color.LightGray,
                            fontSize = 14.sp
                        ),
                        onClick = {
                            onRegisterClick()
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun WaveHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(Color(0xFF9A775B))
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val path = Path().apply {
                moveTo(0f, size.height * 0.75f)
                cubicTo(
                    size.width * 0.20f,
                    size.height * 0.35f,

                    size.width * 0.55f,
                    size.height * 1.05f,

                    size.width,
                    size.height * 0.75f
                )
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            drawPath(
                path = path,
                color = Color(0xFF2A2522)
            )
        }
    }
}