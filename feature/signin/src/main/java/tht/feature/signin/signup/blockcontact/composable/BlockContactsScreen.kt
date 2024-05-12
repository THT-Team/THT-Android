package tht.feature.signin.signup.blockcontact.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_ui.component.progress.ThtCircularProgress
import com.example.compose_ui.component.text.headline.ThtHeadline3
import com.example.compose_ui.component.text.subtitle.ThtSubtitle1
import tht.core.ui.R
import tht.feature.signin.ui.SignupLargeButton

@Composable
internal fun BlockContactsScreen(
    loading: Boolean,
    btnEnable: Boolean,
    onBlockContactsClick: () -> Unit,
    onLaterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        Column(
            modifier = modifier
                .background(colorResource(id = R.color.black_161616))
                .padding(horizontal = 22.dp)
        ) {
            Spacer(modifier = Modifier.height(90.dp))
            ThtHeadline3(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(id = tht.feature.signin.R.string.title_block_contacts),
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.white_f9fafa),
                lineHeight = 32.64.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(20.dp))
            ThtSubtitle1(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(id = tht.feature.signin.R.string.description_block_contacts),
                fontWeight = FontWeight.Normal,
                color = colorResource(id = R.color.gray_8d8d8d),
                lineHeight = 22.4.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.weight(1f))
            SignupLargeButton(
                onClick = onBlockContactsClick,
                text = stringResource(id = tht.feature.signin.R.string.block_contacts),
                enable = btnEnable
            )
            Spacer(modifier = Modifier.height(8.dp))
            SignupLargeButton(
                onClick = onLaterClick,
                text = stringResource(id = tht.feature.signin.R.string.later),
                enable = btnEnable,
                colors =  ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.black_222222),
                    contentColor = Color.Transparent,
                    disabledBackgroundColor = colorResource(id = R.color.black_222222),
                    disabledContentColor = Color.Transparent
                ),
                textColor = colorResource(id = R.color.gray_666666)
            )
            Spacer(modifier = Modifier.height(22.dp))
        }
        ThtCircularProgress(
            modifier = Modifier.align(Alignment.Center),
            color = colorResource(id = R.color.yellow_f9cc2e),
            visible = loading
        )
    }
}
