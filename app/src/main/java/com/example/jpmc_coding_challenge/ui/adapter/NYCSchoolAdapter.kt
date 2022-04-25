package com.example.jpmc_coding_challenge.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jpmc_coding_challenge.R
import com.example.jpmc_coding_challenge.data.model.NYCSchool
import com.example.jpmc_coding_challenge.databinding.NycSchoolRowItemBinding

class NYCSchoolAdapter(
    private val schools: List<NYCSchool>,
    private val moveToSat: (String) -> Unit):
    RecyclerView.Adapter<NYCSchoolAdapter.NYCSchoolViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NYCSchoolViewHolder(
            NycSchoolRowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: NYCSchoolViewHolder, position: Int) {
        holder.onBind(schools[position])
    }

    override fun getItemCount() = schools.size

    inner class NYCSchoolViewHolder(private val binding: NycSchoolRowItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun onBind(school: NYCSchool) {
            binding.apply {
                tvSchoolName.text = school.schoolName
                tvSchoolCityAndZipCode.text =
                    root.context.resources.getString(
                        R.string.city_and_zip,
                        school.city,
                        school.zip
                    )
                btnSatScores.setOnClickListener {
                    moveToSat(school.dbn)
                }
            }
        }
    }
}

