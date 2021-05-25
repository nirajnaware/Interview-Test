package com.example.taskdl.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.taskdl.model.MatchesResponseModel
import com.example.taskdl.R
import com.example.taskdl.databinding.CardItemBinding
import com.example.taskdl.view_model.MatchesViewModel

class AllMatchesAdapter : RecyclerView.Adapter<AllMatchesAdapter.AllMatchesViewHolder>() {

    var list: List<MatchesResponseModel> ? = null
    var viewModel: MatchesViewModel? = null
    var pageType: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllMatchesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false)
        return AllMatchesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size?:0
    }

    override fun onBindViewHolder(holder: AllMatchesViewHolder, position: Int) {
        val matchesResponseModel: MatchesResponseModel = list?.get(position) ?: MatchesResponseModel()
        holder.bind(matchesResponseModel,position)
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


    inner class AllMatchesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cardItemBinding: CardItemBinding? = null

        init {
            cardItemBinding = DataBindingUtil.bind(itemView)
        }

        fun bind(userCityMapModel: MatchesResponseModel, position: Int) {
            cardItemBinding?.apply {


                /*if (userCityMapModel.status)
                    ivActive.setBackgroundResource(R.drawable.ic_active)
                else
                    ivActive.setBackgroundResource(R.drawable.ic_inactive)*/

                if (userCityMapModel.status)
                    userCityMapModel.statusDrawable = R.drawable.ic_active
                else
                    userCityMapModel.statusDrawable = R.drawable.ic_inactive

                this.matchesResponseModel = userCityMapModel
                this.matchesViewModel = viewModel
                this.pageType = this@AllMatchesAdapter.pageType
                this.position = adapterPosition


                Log.i("userCityMapModel",userCityMapModel.employee_name+" "+userCityMapModel.id +" "+ userCityMapModel.status)

            }
        }
    }


}