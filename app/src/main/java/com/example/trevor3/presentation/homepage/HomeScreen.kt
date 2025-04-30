package com.example.trevor3.presentation.homepage

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.trevor3.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun HomePageView(
   navController: NavController, viewModel: HomeViewModel = hiltViewModel()
) {
    val colors = listOf(
        Color(0xFFEEede7),
        Color(0xffe5c3ba),
        Color(0xFFE7D2CC),
        Color(0xFFB9B7BD),
        Color(0xffa26f80), Color(0xFF868B8E)
    )

    val currentDateTime = LocalDateTime.now()
    val formattedDateTime =
        currentDateTime.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH))
    val fontBold = FontFamily(Font(R.font.montserrat_bold))
    val fontMed = FontFamily(Font(R.font.montserrat_medium))
    val options = listOf("Day", "Week")

    val selectedIndex = viewModel.state.value.index
    val selectedTextColor = Color(0xFFF23847)
    val unselectedTextColor = Color(0xFFA9A9A9)
    val highlightColor = Color(0xFFE6979E)
    val backgroundColor = Color.White
    val shadowElevation = 8.dp


    val dialogState = rememberMaterialDialogState()





    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.White
            )
            .padding(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                Modifier
                    .fillMaxHeight(0.2f)
            ) {
                Box(
                    Modifier
                        .fillMaxWidth(1f)
                        .fillMaxHeight()
                        .padding(top = 30.dp, start = 20.dp)
                )
                {
                    Column(modifier = Modifier.fillMaxSize()) {

                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Today",
                            color = Color(0xFFA9A9A9),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = formattedDateTime,
                            color = Color.Black,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = fontBold
                        )

                    }

                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {


                options.forEachIndexed { index, option ->
                    val baseModifier = Modifier
                        .clickable {
                            viewModel.onEvent(HomeEvent.OptionHasChanged(index))
                        }

                    if (selectedIndex == index) {
                        // SELECTED ITEM
                        Box(
                            modifier = baseModifier
                                // Give a pill shape with shadow
                                .shadow(
                                    elevation = shadowElevation,
                                    shape = RoundedCornerShape(50),
                                    ambientColor = highlightColor.copy(alpha = 0.9f),
                                    spotColor = highlightColor.copy(alpha = 0.9f)
                                )
                        ) {
                            Surface(
                                color = backgroundColor,
                                shape = RoundedCornerShape(50)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = option, fontSize = 16.sp, color = selectedTextColor)
                                }
                            }
                        }
                    } else {
                        // UNSELECTED ITEM
                        Surface(
                            modifier = baseModifier,
                            color = backgroundColor,
                            shape = RoundedCornerShape(50)
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = option, fontSize = 16.sp, color = unselectedTextColor)
                            }
                        }
                    }
                }

            }
            // WE HAVE SELECTED TODAY

            Spacer(modifier = Modifier.height(40.dp))

            if (selectedIndex == 0) {
                if (viewModel.state.value.tremorCountDay.isEmpty()) {
                    // If there are no tremor times, show a centered "No Tremors" text.
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "No Tremors",
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.Black
                        )
                    }
                } else {
                    if (viewModel.state.value.tremorCountDay.size == 1) {

                        Box(

                        ) {
                            // Extract the time of  the tremor .
                            val tremorTime = viewModel.state.value.tremorCountDay[0]
                            val date = Date(tremorTime)
                            val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())

                            OneLineDay(sdf.format(date))
                        }

                    } else {
                        MultiTremorChartDay(tremorTimes = viewModel.state.value.tremorCountDay)

                        Spacer(modifier = Modifier.height(40.dp))
                    }

                }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            thickness = 2.dp,
                            color = Color.LightGray
                        )
                        Text(
                            text = "or",
                            color = Color.LightGray,
                            modifier = Modifier.padding(horizontal = 8.dp),
                            style = TextStyle(
                                fontFamily = fontMed,
                                fontSize = 20.sp,

                                )
                        )
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            thickness = 2.dp,
                            color = Color.LightGray
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))


                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = { dialogState.show() },
                            colors = ButtonDefaults.buttonColors(Color(0XFFEBE8F4)),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier
                                .padding(8.dp)
                                .height(48.dp)
                        ) {

                            Icon(
                                painter = painterResource(id = R.drawable.calendar_icon),
                                contentDescription = "Calendar Icon",
                                tint = Color.Black,//Color(0xFFE6979E),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Pick a Day",
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    MaterialDialog(dialogState = dialogState, buttons = {
                        positiveButton("OK")
                        negativeButton("Cancel")
                    }) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Select the day ",
                                fontSize = 20.sp, // Adjust the font size as needed
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            datepicker(
                                initialDate = LocalDate.now(),
                            ) { date ->
                                val selectedDay = date.dayOfMonth
                                val selectedMonth = date.monthValue
                                val selectedYear = date.year

                                val formattedDate = String.format(
                                    "%04d-%02d-%02d",
                                    selectedYear,
                                    selectedMonth,
                                    selectedDay
                                )
                                viewModel.onEvent(HomeEvent.DayWasSelected(formattedDate))
                                dialogState.hide()

                            }

                        }

                }
            }
            // WE HAVE SELECTED WEEK
            else if(selectedIndex == 1){

                Box(

                ) {

                    WeeklyTremorChart(viewModel.state.value.tremorCountWeek)
                }

                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        thickness = 2.dp,
                        color = Color.LightGray
                    )
                    Text(
                        text = "or",
                        color = Color.LightGray,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        style = TextStyle(
                            fontFamily = fontMed,
                            fontSize = 20.sp,

                            )
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        thickness = 2.dp,
                        color = Color.LightGray
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))


                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { dialogState.show() },
                        colors = ButtonDefaults.buttonColors(Color(0XFFEBE8F4)),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .padding(8.dp)
                            .height(48.dp)
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.calendar_icon),
                            contentDescription = "Calendar Icon",
                            tint = Color.Black,//Color(0xFFE6979E),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Pick a Week",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                MaterialDialog(dialogState = dialogState, buttons = {
                    positiveButton("OK")
                    negativeButton("Cancel")
                }) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Select the Monday of the Week ",
                            fontSize = 20.sp, // Adjust the font size as needed
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        datepicker(
                            initialDate = LocalDate.now(),
                            allowedDateValidator = { date ->
                                date.dayOfWeek == java.time.DayOfWeek.MONDAY
                            }
                        ) { date ->
                            val selectedDay = date.dayOfMonth
                            val selectedMonth = date.monthValue
                            val selectedYear = date.year

                            val calendar = Calendar.getInstance().apply {
                                set(Calendar.YEAR, selectedYear)
                                set(Calendar.MONTH, selectedMonth - 1) // Calendar.MONTH is 0-indexed
                                set(Calendar.DAY_OF_MONTH, selectedDay)
                            }

                            val weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)

                            viewModel.onEvent(HomeEvent.WeekWasSelected(weekOfYear,selectedYear))
                            dialogState.hide()
                        }


                    }
                }


            }
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(
                            color = Color(0xFFA9A9A9),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    //Average Duration
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(Color.White, shape = RoundedCornerShape(10.dp))
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Spacer(modifier = Modifier.height(40.dp))
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally

                        ) {
                            Text(
                                text = "Average Duration",
                                fontSize = 20.sp,
                                style = TextStyle(
                                    fontFamily = fontMed,
                                    color = Color.Black

                                ),
                                modifier = Modifier.padding(bottom = 6.dp)
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {

                                val totalSeconds = (viewModel.state.value.averageDuration )
                                val minutes = totalSeconds / 60
                                val seconds = totalSeconds % 60

                                Text(
                                    text = String.format("%d:%02d", minutes.toInt(), seconds.toInt()),
                                    style = TextStyle(
                                        fontSize = 25.sp,
                                        color = Color(0xFFE6979E),
                                        fontFamily = fontBold
                                    )
                                )
                                Text(
                                    text = "m",
                                    style = TextStyle(
                                        fontSize = 25.sp,
                                        color = Color(0xFFE6979E),
                                        fontFamily = fontBold
                                    )
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.width(20.dp))

                //Average Intensity
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(
                            color = Color(0xFFA9A9A9),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Spacer(modifier = Modifier.height(40.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        //Average Duration
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .background(Color.White, shape = RoundedCornerShape(10.dp))
                                .padding(12.dp),
                            //contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally

                            ) {
                                Text(
                                    text = "Average Intensity",
                                    fontSize = 20.sp,
                                    style = TextStyle(
                                        fontFamily = fontMed,
                                        color = Color.Black

                                    ),
                                    modifier = Modifier.padding(bottom = 10.dp)
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {

                                    Text(
                                        text = String.format(
                                            "%.2f",
                                            viewModel.state.value.averageIntensity
                                        ),
                                        style = TextStyle(
                                            fontSize = 25.sp,
                                            color = Color(0xFFE6979E),
                                            fontFamily = fontBold
                                        )
                                    )

                                }
                            }
                        }
                    }
                }
            }

        }
    }
}


val colors = listOf(Color(0xFFEEede7),Color(0xffe5c3ba),Color(0xFFE7D2CC),Color(0xFFB9B7BD),Color(0xffa26f80), Color(0xFF868B8E))
@Composable
fun OneLineDay(tremorTime: String) {
    // For debugging: Hardcode a big box height so we can see the curve.
    // If you embed this in another layout, ensure .height(...) is large enough.
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)
        .background(Color(0xFFE7D2CC))
        .padding(20.dp)
        .background(Color.White),
        contentAlignment = Alignment.TopCenter


    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {

            val leftPadding = 40f
            val rightPadding = 40f
            val topPadding = 20f
            val bottomPadding = 40f
            val chartWidth = size.width - leftPadding - rightPadding
            val chartHeight = size.height - topPadding - bottomPadding


            val bottomLeft = Offset(x = leftPadding, y = size.height - bottomPadding)
            val bottomRight = Offset(x = size.width - rightPadding, y = size.height - bottomPadding)
            val topLeft = Offset(x = leftPadding, y = topPadding)


            // 2) Label y-axis with "0" at bottom, "1" at top, label "Time" at bottom-right.
            drawContext.canvas.nativeCanvas.apply {
                val textPaint = android.graphics.Paint().apply {
                    textSize = 50f
                    color = android.graphics.Color.BLACK
                }
                // Label "0" near the bottom-left
                drawText("0", leftPadding + 5f,  bottomLeft.y - 5f, textPaint)
                // Label "1" near the top-left (slightly below top so it won't get clipped)
                // Increase the y from 0f to ~40f or 50f if you need more space
                drawText("1", leftPadding+5f, bottomLeft.y/2, textPaint)
                // Label "Time" near the bottom-right

                drawText(tremorTime,bottomRight.x/2 -60, bottomRight.y - 5f, textPaint)
            }

            // 3) Build a forced “arch” path from:
            //    - bottom-left (0, canvasHeight)
            //    - to top-middle (canvasWidth/2, 0)
            //    - to bottom-right (canvasWidth, canvasHeight)
            val path = Path().apply {
                moveTo(bottomLeft.x + 80, bottomLeft.y-50 ) // start at bottom-left
                quadraticBezierTo(

                    x1 = (bottomLeft.x + chartWidth / 2f)  ,
                    y1 = topLeft.y ,
                    x2 = bottomRight.x -50  ,
                    y2 = bottomRight.y - 50
                )

            }

            // 4) Draw the forced arch using a moderate stroke so you can clearly see it.
            drawPath(
                path = path,
                color = Color(0xffe5c3ba),
                style = Stroke(
                    width = 3.dp.toPx(),
                    cap = StrokeCap.Round
                )
            )
            // 8) Draw a large dot at the apex
            //    The apex here is the control point.
            val apexX = bottomLeft.x + chartWidth / 2f
            val apexY = bottomLeft.y/2 - 10

            drawCircle(
                color = Color(0xffe5c3ba),
                center = Offset(apexX, apexY),
                radius = 6.dp.toPx() // bigger radius for visibility
            )

        }


    }
}
@Composable
fun MultiTremorChartDay(tremorTimes: List<Long>) {
    val sortedTimes = tremorTimes.sorted()
    val dateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())

    // Chart Container
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color(0xFFE7D2CC)) // outer bg
            .padding(20.dp)               // outer padding (to simulate all-around spacing)
            .background(Color.White, shape = RoundedCornerShape(12.dp)) // inner chart bg
    ) {
        Row(modifier = Modifier.fillMaxSize().padding(16.dp)) {

            // Y-Axis Labels (left)
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (i in sortedTimes.size downTo 0) {
                    Text(
                        text = i.toString(),
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
            }

            // Chart Canvas (right)
            Box(modifier = Modifier.fillMaxSize()) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val leftPadding = 60f
                    val rightPadding = 60f
                    val bottomPadding = 60f
                    val topPadding = 20f
                    val usableWidth = size.width - leftPadding - rightPadding
                    val usableHeight = size.height - topPadding - bottomPadding

                    val tremorCount = sortedTimes.size
                    val rowHeight = usableHeight / tremorCount
                    val spacing = usableWidth / (tremorCount - 1)

                    val path = Path()

                    sortedTimes.forEachIndexed { index, _ ->
                        val x = leftPadding + index * spacing
                        val y = topPadding + usableHeight - (index+1) * rowHeight

                        if (index == 0) path.moveTo(x, y)
                        else path.lineTo(x, y)
                    }

                    drawPath(
                        path = path,
                        color = Color(0xffe5c3ba),
                        style = Stroke(
                            width = 3.dp.toPx(),
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )

                    sortedTimes.forEachIndexed { index, time ->
                        val x = leftPadding + index * spacing
                        val y = topPadding + usableHeight - (index+1) * rowHeight

                        // Circle
                        drawCircle(
                            color = Color(0xffe5c3ba),
                            radius = 6.dp.toPx(),
                            center = Offset(x, y)
                        )

                        // Label inside bottomPadding
                        drawContext.canvas.nativeCanvas.drawText(
                            dateFormat.format(Date(time)),
                            x-65f ,
                            size.height+35f , // <- inside white box now
                            android.graphics.Paint().apply {
                                textSize = 40f
                                color = android.graphics.Color.BLACK
                                isFakeBoldText = true


                            }
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun WeeklyTremorChart(tremorCounts: List<Int>) {
    require(tremorCounts.size == 7) { "List must have 7 values, one for each weekday" }

    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val maxCount = (tremorCounts.maxOrNull() ?: 0).coerceAtLeast(2) // ensure visual scale

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(Color(0xFFE7D2CC))
            .padding(20.dp)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val topPadding = 60f
            var bottomPadding = 80f
            val leftPadding = 70f
            var rightPadding = 70f

            val usableWidth = size.width - leftPadding - rightPadding
            val usableHeight = size.height - topPadding - bottomPadding

            val spacing = usableWidth / 7  // 7 points = 6 gaps
            val spacingY = usableHeight/ maxCount+1

            // Y-Axis labels (0 to maxCount)
            val labelPaint = android.graphics.Paint().apply {
                textSize = 60f
                color = android.graphics.Color.BLACK
                isFakeBoldText = true
            }

            for (i in 0..maxCount) {
                val y = topPadding + usableHeight - (i.toFloat() / maxCount) * usableHeight
                drawContext.canvas.nativeCanvas.drawText(
                    i.toString(),
                    leftPadding -20f ,
                    y + 20f,
                    labelPaint
                )
            }

            // X-Axis labels + points + line path
            val pointColor =  Color(0xffe5c3ba)
            val path = Path()
            val pointRadius = 6.dp.toPx()

            tremorCounts.forEachIndexed { index, count ->
                val x = leftPadding + (index+1) * spacing
                val y = topPadding + usableHeight - (count.toFloat() / maxCount) * usableHeight

                // Draw line path
                if (index == 0) path.moveTo(x, y)
                else path.lineTo(x, y)

                // Draw point
                drawCircle(
                    color = pointColor,
                    radius = pointRadius,
                    center = Offset(x, y)
                )

                // Draw day label
                drawContext.canvas.nativeCanvas.drawText(
                    daysOfWeek[index],
                    x ,
                    size.height - 10f,
                    android.graphics.Paint().apply {
                        textSize = 60f
                        color = android.graphics.Color.BLACK
                        textAlign = android.graphics.Paint.Align.CENTER
                        rightPadding=10f
                        bottomPadding= 10f
                        isFakeBoldText = true
                    }
                )
            }

            // Draw the connecting line
            drawPath(
                path = path,
                color = pointColor.copy(alpha = 0.6f),
                style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
            )
        }
    }
}













