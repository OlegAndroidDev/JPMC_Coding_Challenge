package com.example.jpmc_coding_challenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jpmc_coding_challenge.R
import com.example.jpmc_coding_challenge.data.model.NYCSchool
import com.example.jpmc_coding_challenge.data.model.UIState
import com.example.jpmc_coding_challenge.databinding.NycSchoolFragmentBinding
import com.example.jpmc_coding_challenge.presentation.SchoolViewModel
import com.example.jpmc_coding_challenge.ui.adapter.NYCSchoolAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class NYCSchoolFragment: Fragment() {

    private var _binding: NycSchoolFragmentBinding? = null
    private val binding: NycSchoolFragmentBinding
        get() = _binding!!

    private val schoolViewModel: SchoolViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = NycSchoolFragmentBinding.inflate(layoutInflater)
        configureObserver()
        return binding.root
    }

    private fun configureObserver() {
        schoolViewModel.schoolData.observe(viewLifecycleOwner) { state ->
            when(state) {
                is UIState.Loading -> {
                    binding.apply {
                        pbLoadingSpinner.visibility = View.VISIBLE
                        tvLoadingText.visibility = View.VISIBLE
                        rvSchools.visibility = View.GONE
                    }
                }
                is UIState.Error -> {
                    binding.apply {
                        pbLoadingSpinner.visibility = View.GONE
                        tvLoadingText.text = state.msg
                    }
                }
                is UIState.Success -> {
                    binding.apply {
                        pbLoadingSpinner.visibility = View.GONE
                        tvLoadingText.visibility = View.GONE
                        rvSchools.apply {
                            adapter = NYCSchoolAdapter(state.response as List<NYCSchool>,::moveToSAT)
                            layoutManager = LinearLayoutManager(context)
                            visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun moveToSAT(schoolName: String) {
        parentFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container_fragment,SATScoreFragment.newInstance(schoolName))
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}