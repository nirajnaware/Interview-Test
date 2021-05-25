package com.example.taskdl

import android.content.Intent
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
import com.example.taskdl.adapter.AllMatchesAdapter
import com.example.taskdl.view_model.MatchesViewModel
import com.example.taskdl.view_model.ViewModelFactory
import com.example.taskdl.databinding.FragmentAllMatchesBinding
import com.example.taskdl.model.MatchesResponseModel
import com.example.taskdl.room.db.AppDb
import com.example.taskdl.utils.OnFragmentTitleChangeListener
import com.example.taskdl.utils.keyAllEmp


class AllMatchesFragment : Fragment() {

    var mAdapter: AllMatchesAdapter? = AllMatchesAdapter()
    var layoutBinding: FragmentAllMatchesBinding? = null
    var matchesViewModel: MatchesViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        layoutBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_all_matches, container, false)

        matchesViewModel = ViewModelProviders.of(
            this@AllMatchesFragment,
            ViewModelFactory(
                RetrofitInitializer().apiService
            )
        ).get(MatchesViewModel::class.java)

        layoutBinding?.apply {
            lifecycleOwner = this@AllMatchesFragment
        }

        setobservers()

        return layoutBinding?.root
    }

    private fun setobservers() {
        matchesViewModel?.apply {

            getEmp(requireContext())

            matchesResponseModelListLiveData.observe(viewLifecycleOwner, Observer {
                it?.let {
                    setAdapter(it)
                }
            })

            /*
            * Observe , when click on star
            * */
            onClickStarLiveData.observe(viewLifecycleOwner, Observer {
                it?.let {
                    AppDb.getInstance(requireContext()).empResponseDao().apply {
                        Log.i("nituuu","true")
                        updateEmpById(true, it?.id?:0)
                        //mAdapter?.notifyDataSetChanged()
                        //getEmp(requireContext())
                        mAdapter?.updateCardAtPosition(R.drawable.ic_active, cardPosition)
                    }
                    onClickStarLiveData.value = null
                }
            })

        }
    }

    private fun setAdapter(matchesResponseList: List<MatchesResponseModel>) {
        layoutBinding?.apply {
            rvList?.apply {
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                mAdapter?.list = matchesResponseList
                mAdapter?.viewModel = matchesViewModel
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
        }catch (e: Exception) {
            e.printStackTrace()
        }
    }

}