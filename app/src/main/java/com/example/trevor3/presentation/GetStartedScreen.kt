package com.example.trevor3.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trevor3.R


/* COLOR
* Color(60313f)
#e5c3ba
#a25c67
#a26f80
#b899a4
#c45c74*/
@Composable
fun GetStartedView(
    navController: NavController
) {
    val first = FontFamily(
        Font(R.font.chewy_regular, FontWeight.Bold),
    )
    val colors = listOf(Color(0xFFEEede7),Color(0xffe5c3ba),Color(0xFFE7D2CC),Color(0xFFB9B7BD),Color(0xffa26f80), Color(0xFF868B8E))
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(colors = colors)
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(80.dp))
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
            ){
                Text(
                    text = "Trevor",
                    color = Color.White,
                    fontSize = 80.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontFamily = first,
                        lineHeight = 50.sp
                    )
                )
            }
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                )
            }
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ){
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Screen.LoginScreen.route)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xffa26f80),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(
                            vertical = 15.dp, // The padding inside the button, vertical
                            horizontal = 90.dp // The padding inside the button, horizontal
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 20.dp,
                            pressedElevation = 20.dp,
                        )

                    )
                    {
                        Text(
                            text = "Get Started",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(Modifier.height(20.dp))
                    Text(
                        text = "Keep track of what is important to you!",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier =  Modifier . padding(start = 5.dp,end = 5.dp)
                    )
                }

            }
        }
    }
}