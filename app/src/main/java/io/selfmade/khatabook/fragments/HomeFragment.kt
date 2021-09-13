package io.selfmade.khatabook.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import io.selfmade.khatabook.R
import io.selfmade.khatabook.activities.MainActivity
import io.selfmade.khatabook.adapter.UserAdapter
import io.selfmade.khatabook.databinding.HomeFragmentBinding
import io.selfmade.khatabook.model.CreateUser
import io.selfmade.khatabook.viewmodel.DataViewModel
import io.selfmade.khatabook.viewmodel.ModelFactory

class HomeFragment : BaseFragment(), Observer<List<CreateUser>> {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var activity: MainActivity
    private lateinit var adapter: UserAdapter
    private lateinit var dataViewModel: DataViewModel
    private lateinit var list: ArrayList<CreateUser>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list = ArrayList()

        binding.mFab.setOnClickListener {
            activity.getNavController().navigate(R.id.action_HomeFragment_to_CreateUserFragment)

        }

        initRecyclerView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity

        initModel()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun initRecyclerView() {

        adapter = UserAdapter(activity, list)

        binding.mRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.mRecyclerView.setHasFixedSize(true)
        binding.mRecyclerView.adapter = adapter

        dataViewModel.allUser(activity, 0)!!
            .observe(this@HomeFragment, this)

    }

    private fun initModel() {
        dataViewModel =
            ViewModelProvider(this@HomeFragment, ModelFactory()).get(DataViewModel::class.java)
    }

    override fun onChanged(t: List<CreateUser>?) {
        list.clear()
        adapter.addAll(t!!)
    }


}