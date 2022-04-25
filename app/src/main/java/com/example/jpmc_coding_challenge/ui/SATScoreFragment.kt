package com.example.jpmc_coding_challenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jpmc_coding_challenge.R
import com.example.jpmc_coding_challenge.data.model.SatScore
import com.example.jpmc_coding_challenge.data.model.UIState
import com.example.jpmc_coding_challenge.databinding.SatScoreFragmentBinding
import com.example.jpmc_coding_challenge.presentation.SchoolViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SATScoreFragment: Fragment() {

    private var dbn = ""
    private var _binding : SatScoreFragmentBinding? = null
    private val binding: SatScoreFragmentBinding
        get() = _binding!!

    private val schoolViewModel: SchoolViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SatScoreFragmentBinding.inflate(layoutInflater)

        dbn = arguments?.getString(DBN).toString()
        schoolViewModel.getSatScores()

        configureObserver()
        return binding.root
    }

    private fun configureObserver() {
        schoolViewModel.satData.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UIState.Loading -> {
                    binding.apply {
                        pbSatLoadingSpinner.visibility = View.VISIBLE
                        tvSatLoadingText.visibility = View.VISIBLE
                    }
                }
                is UIState.Error -> {
                    binding.apply {
                        pbSatLoadingSpinner.visibility = View.GONE
                        tvSatLoadingText.text = state.msg
                    }
                }
                is UIState.Success -> {
                    val satScore = findSchoolByDBN(state.response as List<SatScore>)
                    if (satScore == null) {
                        binding.apply {
                            pbSatLoadingSpinner.visibility = View.GONE
                            tvSatLoadingText.text = resources.getString(R.string.no_sats)
                        }
                    } else {
                        binding.apply {
                            pbSatLoadingSpinner.visibility = View.GONE
                            tvSatLoadingText.visibility = View.GONE
                            tvSatSchoolName.apply {
                                text = satScore.schoolName
                                visibility = View.VISIBLE
                            }
                            tvAveragesTitle.visibility = View.VISIBLE
                            tvTakers.apply {
                                text = resources.getString(R.string.sat_takers, satScore.testTakers)
                                visibility = View.VISIBLE
                            }
                            tvAvgReading.apply {
                                text =
                                    resources.getString(R.string.reading_avg, satScore.avgReading)
                                visibility = View.VISIBLE
                            }
                            tvAvgMath.apply {
                                text = resources.getString(R.string.math_avg, satScore.avgMath)
                                visibility = View.VISIBLE
                            }
                            tvAvgWriting.apply {
                                text =
                                    resources.getString(R.string.writing_avg, satScore.avgWriting)
                                visibility = View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }

    private fun findSchoolByDBN(satScores: List<SatScore>): SatScore? {
        return satScores.singleOrNull { it.dbn == dbn }
    }

    companion object {
        private const val DBN = "dbn"
        fun newInstance(dbn:String) : SATScoreFragment {
            val fragment = SATScoreFragment()
            val bundle = Bundle()
            bundle.putString(DBN, dbn)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}