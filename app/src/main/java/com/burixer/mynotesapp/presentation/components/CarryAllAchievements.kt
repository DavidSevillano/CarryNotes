package com.burixer.mynotesapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.burixer.mynotesapp.presentation.model.Achievement

@Composable
fun CarryAllAchievements(achievement: Achievement) {

    val backgroundColor = if (achievement.isUnlocked) Color(0xFF333333) else Color(0xFF2A2A2A)
    val borderColor = if (achievement.isUnlocked) Color(0xFFFFD700) else Color(0xFF444444)
    val contentAlpha = if (achievement.isUnlocked) 1f else 0.5f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                1.dp,
                borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    if (achievement.isUnlocked) Color(0xFFFFD700).copy(alpha = 0.2f)
                    else Color.Gray.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (achievement.isUnlocked) Icons.Default.EmojiEvents else Icons.Default.Lock,
                contentDescription = if (achievement.isUnlocked) "Logro desbloqueado" else "Logro bloqueado",
                tint = if (achievement.isUnlocked) Color(0xFFFFD700) else Color.Gray,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(id = achievement.title),
                color = Color.White.copy(alpha = contentAlpha),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = achievement.description),
                color = Color.Gray.copy(alpha = contentAlpha),
                fontSize = 14.sp
            )
        }
    }
}