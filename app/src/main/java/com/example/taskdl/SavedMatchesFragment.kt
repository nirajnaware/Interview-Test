package com.example.taskdl

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskdl.adapter.AllMatchesAdapter
import com.example.taskdl.databinding.FragmentSavedMatchesBinding
import com.example.taskdl.model.MatchesResponseModel
import com.example.taskdl.room.db.AppDb
import com.example.taskdl.utils.*
import com.example.taskdl.view_model.MatchesViewModel
import com.example.taskdl.view_model.ViewModelFactory


class SavedMatchesFragment : Fragment() {

    //var mAdapter: AllMatchesAdapter? = AllMatchesAdapter()
    var layoutBinding: FragmentSavedMatchesBinding? = null
    var matchesViewModel: MatchesViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        layoutBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_saved_matches, container, false)

        matchesViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                RetrofitInitializer().apiService
            )
        ).get(MatchesViewModel::class.java)

        layoutBinding?.apply {
            lifecycleOwner = this@SavedMatchesFragment
        }

        setObservers()

        return layoutBinding?.root

    }

    private fun setObservers() {
        matchesViewModel?.apply {
            getSavedEmp(requireContext())

            layoutBinding?.apply {
                matchesResponseModelListLiveData.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        when (it.status) {
                            Status.SUCCESS -> {
                                if (it.data?.isNotEmpty() == true) {
                                    setAdapter(it.data)
                                    layoutNoData.clNoData.visibility = View.GONE
                                    layoutNoInternet.clNoInternet.visibility = View.GONE
                                    layoutError.clSomethingWentWrong.visibility = View.GONE
                                } else {
                                    layoutNoData.clNoData.visibility = View.VISIBLE
                                    layoutNoData.tvMessage.text = keyNoDataException
                                    rvList.visibility = View.GONE
                                }
                            }
                            Status.ERROR -> {
                                if (keyNoInternetException == it.message) {
                                    layoutNoInternet.clNoInternet.visibility = View.VISIBLE
                                    layoutNoInternet.tvNoInternetMsg.text = keyNoInternetException
                                    rvList.visibility = View.GONE
                                } else {
                                    layoutError.clSomethingWentWrong.visibility = View.VISIBLE
                                    layoutError.tvMessage.text = keyAPIException
                                    rvList.visibility = View.GONE
                                }
                            }
                            Status.LOADING -> {
                            }
                        }
                    }
                })
            }

            /*
            * Observe , when click on star , to inactive
            * */
            onClickStarLiveData.observe(viewLifecycleOwner, Observer {
                it?.let {
                    showAlertDialog(it)
                    onClickStarLiveData.value = null
                }
            })
        }
    }

    private fun setAdapter(matchesResponseList: List<MatchesResponseModel>) {
        layoutBinding?.apply {
            rvList?.apply {
                val mAdapter = AllMatchesAdapter()
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                mAdapter.list = matchesResponseList
                mAdapter.viewModel = matchesViewModel
                mAdapter.pageType = keySavedEmp
                adapter = mAdapter
            }
        }
    }

    override fun onResume() {
        super.onResume()
        try {

            (requireActivity() as OnFragmentTitleChangeListener).onFragmentTitle(
                getString(R.string.nav_saved_matches),
                false
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showAlertDialog(it: MatchesResponseModel) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(R.string.alert_title)
        alertDialog.setMessage(R.string.alert_msg)
        alertDialog.setPositiveButton(
            keyYes
        ) { _, _ ->

            AppDb.getInstance(requireContext()).empResponseDao().apply {
                updateEmpById(false, it.id ?: 0)
                //mAdapter?.updateCardAtPosition(R.drawable.ic_inactive, matchesViewModel?.cardPosition?:-1)
                matchesViewModel?.getSavedEmp(requireContext())
            }

            Toast.makeText(requireContext(), R.string.inactive_employee, Toast.LENGTH_LONG).show()
        }
        alertDialog.setNegativeButton(
            keyNo
        ) { _, _ -> }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
    }


}