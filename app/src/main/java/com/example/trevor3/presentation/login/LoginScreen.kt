package com.example.trevor3.presentation.login

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.trevor3.R
import com.example.trevor3.presentation.Screen
import kotlinx.coroutines.delay


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignInView(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
){
    val state by viewModel.state


    var emailInput by remember {
        mutableStateOf(state.email)
    }
    var passwordInput by remember {
        mutableStateOf(state.password)
    }
    var colorBorder by remember {
        mutableStateOf(Color.Transparent)
    }

    // Handling success popup
    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            //Toast.makeText(context, "Hello ${state.user?.name}", Toast.LENGTH_LONG).show()
            //delay(3000)  // Showing the toast for 3 seconds
            navController.navigate(Screen.HomePageScreen.route)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffF2F1F6))
    )
    {
        Column (
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.12f)
                    .fillMaxWidth()
                    .background(color = Color(0xffa26f80)),
                contentAlignment = Alignment.BottomCenter
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arc_3),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.85f)
            )
            {
                Text(text = "Hello!",
                    color= Color.Black,
                    fontSize = 40.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "Sign into your account",
                    color= Color.Black,
                    fontSize = 25.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        lineHeight = 40.sp
                    )
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = "E M A I L",
                    color= Color.Black,
                    fontSize = 15.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 6.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = emailInput,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = { newValue ->
                        emailInput = newValue.trimEnd()
                        viewModel.onEvent(LoginEvent.EmailChanged(newValue))  // Trigger the event on value change
                    },
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp)
                        .border(2.dp, colorBorder, RoundedCornerShape(8.dp)),
                    label = { Text(text = "Email") },
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.Gray,
                        unfocusedLabelColor = Color.Gray,
                        focusedLabelColor = Color.Gray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "P A S S W O R D",
                    color= Color.Black,
                    fontSize = 15.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 6.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp)
                    .background(color = Color.White, RoundedCornerShape(8.dp))
                    .border(2.dp, colorBorder, RoundedCornerShape(8.dp)),
                    verticalAlignment = Alignment.CenterVertically)
                {
                    TextField(
                        value = passwordInput,
                        shape = RoundedCornerShape(8.dp),
                        onValueChange = { newValue ->
                            passwordInput = newValue
                            viewModel.onEvent(LoginEvent.PasswordChanged(newValue))
                        },
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        label = { Text(text = "Password") },
                        colors = TextFieldDefaults.colors(
                            cursorColor = Color.Gray,
                            unfocusedLabelColor = Color.Gray,
                            focusedLabelColor = Color.Gray,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        ),
                        visualTransformation =if (state.hiddenPassword) PasswordVisualTransformation() else VisualTransformation.None
                        ,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                    Spacer(Modifier.width(3.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .height(30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = state.imageId),
                            contentDescription = null,
                            modifier = Modifier.clickable(onClick={
                                viewModel.onEvent(LoginEvent.PasswordVisibilityChanged(""))
                            })
                        )
                    }
                }
                Spacer(modifier = Modifier.height(22.dp))
                Button(
                    onClick = {
                        if(state.email!=""&&state.password!="") {
                            viewModel.onEvent(LoginEvent.Validate(passwordInput, emailInput))
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xffb899a4),
                        contentColor = Color(0xffa26f80)
                    )
                ) {
                    Text(
                        text = "Login",
                        fontSize =22.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                if (state.error != null) {
                    Text(
                        text = "No user found with this credentials!",
                        color = Color.Red,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    colorBorder = Color.Red
                }
                Spacer(modifier = Modifier.height(7.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Not a member?",
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    ClickableText(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color(0xffa26f80),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            ) {
                                append("Sign Up")
                            }
                        },
                        onClick = {

                            navController.navigate(
                               Screen.AddUserScreen.route
                           )
                        }
                    )
                }
                Spacer(modifier = Modifier.height(25.dp))
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .fillMaxWidth()
                    .background(color = Color(0xffa26f80)),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arc_3),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            scaleY = -1f
                        },
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}