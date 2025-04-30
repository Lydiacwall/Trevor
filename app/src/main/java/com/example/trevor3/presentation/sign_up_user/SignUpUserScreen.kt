package com.example.trevor3.presentation.sign_up_user

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.trevor3.presentation.Screen

import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignUpUserView(
    navController: NavController,
    viewModel: SignUpUserViewModel = hiltViewModel()
) {
    val state by viewModel.state

    var emailBorder by remember {
        mutableStateOf(Color.Transparent)
    }
    var lastNameBorder by remember {
        mutableStateOf(Color.Transparent)
    }
    var firstNameBorder by remember {
        mutableStateOf(Color.Transparent)
    }
    var passwordBorder by remember {
        mutableStateOf(Color.Transparent)
    }
    var verifyPasswordBorder by remember {
        mutableStateOf(Color.Transparent)
    }

    var verifyError by remember {
        mutableStateOf(false)
    }

    var nameInput by remember {
        mutableStateOf(state.nameInput)
    }
    var surnameInput by remember {
        mutableStateOf(state.surname)
    }
    var emailInput by remember {
        mutableStateOf(state.emailInput)
    }
    var passwordInput by remember {
        mutableStateOf(state.passwordInput)
    }
    var verifyPasswordInput by remember {
        mutableStateOf(state.verifyPassword)
    }


    val dateDialogState = rememberMaterialDialogState()

    var isExpanded by remember {
        mutableStateOf(false)
    }

    val colors = listOf(Color(0xFFEEede7),Color(0xffe5c3ba),Color(0xFFE7D2CC),Color(0xFFB9B7BD),Color(0xffa26f80), Color(0xFF868B8E))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(colors = colors)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth(0.8f)
                .fillMaxHeight()
        )
        {
            Text(text = "Welcome to Trevor!",
                color= Color.Black,
                fontSize = 23.sp,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    lineHeight = 30.sp
                )
            )
            Text(text = "Create your account!",
                color= Color.Black,
                fontSize = 23.sp,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    lineHeight = 40.sp
                )
            )
            Spacer(modifier = Modifier.height(15.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .verticalScroll(rememberScrollState())
            )
            {
                Text(
                    text = "N A M E",
                    color = Color.Black,
                    fontSize = 15.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 6.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = nameInput,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = {
                        nameInput = it
                        viewModel.onEvent(SignUpUserEvent.NameChanged(nameInput))
                    },
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp)
                        .border(2.dp, firstNameBorder, RoundedCornerShape(8.dp)),
                    label = { Text(text = "Name") },
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
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "S U R N A M E",
                    color = Color.Black,
                    fontSize = 15.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 6.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = surnameInput,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = {
                        surnameInput = it
                        viewModel.onEvent(SignUpUserEvent.SurnameChanged(surnameInput))
                    },
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp)
                        .border(2.dp, lastNameBorder, RoundedCornerShape(8.dp)),
                    label = { Text(text = "Surname") },
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
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "E M A I L",
                    color = Color.Black,
                    fontSize = 15.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 6.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = emailInput,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = {
                        emailInput = it
                        viewModel.onEvent(SignUpUserEvent.EmailChanged(emailInput))
                    },
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp)
                        .border(2.dp, emailBorder, RoundedCornerShape(8.dp)),
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
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "P A S S W O R D",
                    color = Color.Black,
                    fontSize = 15.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 6.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = passwordInput,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = {
                        passwordInput = it
                        viewModel.onEvent(SignUpUserEvent.PasswordChanged(passwordInput))
                    },
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp)
                        .border(2.dp, passwordBorder, RoundedCornerShape(8.dp)),
                    label = { Text(text = "Password") },
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
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "V E R I F Y  P A S S W O R D",
                    color = Color.Black,
                    fontSize = 15.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 6.dp, top = 10.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = verifyPasswordInput,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = {
                        verifyPasswordInput = it
                        viewModel.onEvent(SignUpUserEvent.VerifiedPasswordChanged(verifyPasswordInput))
                        if(passwordInput==verifyPasswordInput){
                            verifyPasswordBorder = Color.Transparent
                            verifyError = false
                        }
                    },
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(4.dp)
                        .border(2.dp, verifyPasswordBorder, RoundedCornerShape(8.dp)),
                    label = { Text(text = "Verify Password") },
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
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Spacer(modifier = Modifier.height(4.dp))
                var textColor by remember {
                    mutableStateOf(Color.Gray)
                }

                Spacer(modifier = Modifier.height(6.dp))


            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    if(passwordInput!=verifyPasswordInput){
                        verifyPasswordBorder = Color.Red
                        verifyError = true
                    }
                    else{
                        viewModel.onEvent(SignUpUserEvent.AddUser(
                            firstName = nameInput,
                            lastName = surnameInput,
                            email = emailInput,
                            password = passwordInput,
                            verifyPassword = verifyPasswordInput
                           ))
                        if(!state.isSuccess){
                            when (state.wrongField) {
                                "Email" -> {
                                    emailBorder = Color.Red
                                    lastNameBorder = Color.Transparent
                                    firstNameBorder = Color.Transparent
                                    passwordBorder = Color.Transparent
                                }
                                "Last Name" -> {
                                    emailBorder = Color.Transparent
                                    lastNameBorder = Color.Red
                                    firstNameBorder = Color.Transparent
                                    passwordBorder = Color.Transparent
                                }
                                "First Name" -> {
                                    emailBorder = Color.Transparent
                                    lastNameBorder = Color.Transparent
                                    firstNameBorder = Color.Red
                                    passwordBorder = Color.Transparent
                                }
                                "Password" -> {
                                    emailBorder = Color.Transparent
                                    lastNameBorder = Color.Transparent
                                    firstNameBorder = Color.Transparent
                                    passwordBorder = Color.Red
                                }
                                "Email must be gmail for statistics !"->{
                                    emailBorder = Color.Red
                                    lastNameBorder = Color.Transparent
                                    firstNameBorder = Color.Transparent
                                    passwordBorder = Color.Transparent
                                }
                                else -> {
                                    emailBorder = Color.Transparent
                                    lastNameBorder = Color.Transparent
                                    firstNameBorder = Color.Transparent
                                    passwordBorder = Color.Transparent
                                }
                            }
                        }
                        else
                        {
                            navController.navigate(Screen.HomePageScreen.route)
                        }
                    }
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffb899a4),
                    contentColor = Color(0xffa26f80)
                )
            ) {
                Text(
                    text = "Sign Up",
                    fontSize =25.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            if(verifyError){
                Text(
                    text = "Passwords don't match!",
                    color = Color.Red,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            else
            {
                if (state.error != null) {
                    Text(
                        text = state.error.toString(),
                        color = Color.Red,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already a member?",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(5.dp))
                ClickableText(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xffa26f80),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        ) {
                            append("Log In")
                        }
                    },
                    onClick = {
                        navController.navigate(
                            Screen.LoginScreen.route
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
        }

    }
}


