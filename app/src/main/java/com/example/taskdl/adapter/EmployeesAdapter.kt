package com.example.taskdl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskdl.model.EmployeesResponseModel
import com.example.taskdl.R
import com.example.taskdl.databinding.CardItemBinding
import com.example.taskdl.view_model.EmployeesViewModel

class EmployeesAdapter : RecyclerView.Adapter<EmployeesAdapter.EmployeesViewHolder>() {

    var list: List<EmployeesResponseModel> ? = null
    var viewModel: EmployeesViewModel? = null
    var pageType: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false)
        return EmployeesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size?:0
    }

    override fun onBindViewHolder(holder: EmployeesViewHolder, position: Int) {
        val employeesResponseModel: EmployeesResponseModel = list?.get(position) ?: EmployeesResponseModel()
        holder.bind(employeesResponseModel,position)
    }

    /*class AllMatchesViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.card_item, parent, false)) {
        fun bind(matchesResponseModel: MatchesResponseModel) {
            // abc
            Log.i("adapter_value","${matchesResponseModel.activityType}")
        }

    }*/

    fun updateCardAtPosition(
        statusDrawable: Int,
        position: Int
    ) {
        list?.get(position)?.apply {
            this.statusDrawable = statusDrawable
        }
    }


    inner class EmployeesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cardItemBinding: CardItemBinding? = null

        init {
            cardItemBinding = DataBindingUtil.bind(itemView)
        }

        fun bind(userCityMapModel: EmployeesResponseModel, position: Int) {
            cardItemBinding?.apply {

                if (userCityMapModel.status)
                    userCityMapModel.statusDrawable = R.drawable.ic_active
                else
                    userCityMapModel.statusDrawable = R.drawable.ic_inactive

                this.matchesResponseModel = userCityMapModel
                this.matchesViewModel = viewModel
                this.pageType = this@EmployeesAdapter.pageType
                this.position = adapterPosition
            }
        }
    }


}