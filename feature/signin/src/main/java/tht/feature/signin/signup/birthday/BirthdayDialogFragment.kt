package tht.feature.signin.signup.birthday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import tht.core.ui.delegate.viewBinding
import tht.feature.signin.databinding.DialogBirthdayBinding
import java.time.LocalDate

class BirthdayDialogFragment : BottomSheetDialogFragment() {
    private val binding: DialogBirthdayBinding by viewBinding(DialogBirthdayBinding::inflate)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNumberPicker()
        setListener()
    }

    private fun initNumberPicker() {
        val args: BirthdayDialogFragmentArgs by navArgs()
        val date = args.date.split(". ").map { it.toInt() }
        binding.apply {
            npYear.apply {
                minValue = 1900
                maxValue = LocalDate.now().year
                value = date[0]
            }
            npMonth.apply {
                minValue = 1
                maxValue = 12
                value = date[1]
            }
            npDay.apply {
                minValue = 1
                maxValue = 31
                value = date[2]
            }
        }
    }

    private fun setListener() {
        binding.btnConfirm.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                BirthdayConstant.KEY,
                binding.run {
                    "${npYear.value}. ${if (npMonth.value / 10 == 0) 0 else ""}${npMonth.value}. " +
                        "${if (npDay.value / 10 == 0) 0 else ""}${npDay.value}"
                }
            )
            findNavController().popBackStack()
        }
    }
}
