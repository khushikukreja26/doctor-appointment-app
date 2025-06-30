package com.example.docapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.docapp.R
import com.example.docapp.ui.theme.BluePrimary
import com.example.docapp.ui.theme.PurpleGrey
import com.example.docapp.ui.theme.WhiteBackground
import com.example.docapp.ui.theme.poppinsFontFamily

@Composable
fun MenuHome(
    modifier: Modifier = Modifier,
    icon: Int,
    title: String,
    onClick: () -> Unit = {} // ✅ Add this
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier.size(72.dp),
            shape = RoundedCornerShape(100.dp),
            colors = ButtonDefaults.buttonColors(containerColor = WhiteBackground),
            onClick = onClick // ✅ Use it here
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = icon),
                contentDescription = "Icon",
                colorFilter = ColorFilter.tint(color = BluePrimary)
            )
        }

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = title,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.W400,
            color = PurpleGrey
        )
    }
}
