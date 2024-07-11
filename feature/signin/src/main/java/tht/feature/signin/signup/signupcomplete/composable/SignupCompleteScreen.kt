package tht.feature.signin.signup.signupcomplete.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.progress.ThtCircularProgress
import tht.core.ui.R
import tht.feature.signin.ui.SignupLargeButton

@Composable
internal fun SignupCompleteScreen(
    loading: Boolean,
    btnEnable: Boolean,
    profileImage: String?,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,
    error: Throwable? = null
) {
    Box {
        Column(
            modifier = modifier
                .background(colorResource(id = R.color.black_161616))
                .padding(horizontal = 22.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            //TODO: ProfileImage
            SignupLargeButton(
                onClick = onComplete,
                text = stringResource(id = tht.feature.signin.R.string.yes),
                enable = btnEnable
            )
            Spacer(modifier = Modifier.height(22.dp))
        }
        ThtCircularProgress(
            modifier = Modifier.align(Alignment.Center),
            color = colorResource(id = R.color.yellow_f9cc2e),
            visible = loading
        )
    }
    if (error != null) {
        // error dialog
    }
}
