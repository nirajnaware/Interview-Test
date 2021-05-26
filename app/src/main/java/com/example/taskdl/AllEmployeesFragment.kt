package com.example.taskdl

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskdl.adapter.EmployeesAdapter
import com.example.taskdl.databinding.FragmentAllEmployeesBinding
import com.example.taskdl.view_model.EmployeesViewModel
import com.example.taskdl.view_model.ViewModelFactory
import com.example.taskdl.model.EmployeesResponseModel
import com.example.taskdl.room.db.AppDb
import com.example.taskdl.utils.*


class AllEmployeesFragment : Fragment() {

    var mAdapter: EmployeesAdapter? = EmployeesAdapter()
    var layoutBinding: FragmentAllEmployeesBinding? = null
    var employeesViewModel: EmployeesViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        layoutBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_all_employees, container, false)

        employeesViewModel = ViewModelProviders.of(
            this@AllEmployeesFragment,
            ViewModelFactory(
                RetrofitInitializer().apiService
            )
        ).get(EmployeesViewModel::class.java)

        layoutBinding?.apply {
            lifecycleOwner = this@AllEmployeesFragment
        }

        setobservers()

        return layoutBinding?.root
    }

    private fun setobservers() {
        layoutBinding?.apply {
            employeesViewModel?.apply {

                getEmp(requireContext())

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
                                }
                            }
                            Status.ERROR -> {
                                if (keyNoInternetException == it.message) {
                                    layoutNoInternet.clNoInternet.visibility = View.VISIBLE
                                    layoutNoInternet.tvNoInternetMsg.text = keyNoInternetException
                                } else {
                                    layoutError.clSomethingWentWrong.visibility = View.VISIBLE
                                    layoutError.tvMessage.text = keyAPIException
                                }
                            }
                            Status.LOADING -> {
                            }
                        }
                    }
                })

                /*
                * Observe , when click on star
                * */
                onClickStarLiveData.observe(viewLifecycleOwner, Observer {
                    it?.let {
                        AppDb.getInstance(requireContext()).empResponseDao().apply {
                            Log.i("nituuu", "true")
                            updateEmpById(true, it?.id ?: 0)
                            //mAdapter?.notifyDataSetChanged()
                            //getEmp(requireContext())
                            mAdapter?.updateCardAtPosition(R.drawable.ic_active, cardPosition)
                        }
                        onClickStarLiveData.value = null
                    }
                })

            }

            layoutNoInternet.btnTryAgain.setOnClickListener {
                employeesViewModel?.getEmp(requireContext())
            }
        }
    }

    private fun setAdapter(employeesResponseList: List<EmployeesResponseModel>) {
        layoutBinding?.apply {
            rvList?.apply {
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                mAdapter?.list = employeesResponseList
                mAdapter?.viewModel = employeesViewModel
                mAdapter?.pageType = keyAllEmp
                adapter = mAdapter
            }
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            (requireActivity() as OnFragmentTitleChangeListener).onFragmentTitle(
                getString(R.string.nav_all_matches),
                false
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}