package band.effective.office.tv.screen.LeaderIdEvets

import android.util.Log
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import band.effective.office.tv.model.LeaderIdEventInfo

@Composable
fun NullInfoCard(eventInfo: LeaderIdEventInfo.NullInfo){
    Text("Error")
    Text(eventInfo.errorText)
}