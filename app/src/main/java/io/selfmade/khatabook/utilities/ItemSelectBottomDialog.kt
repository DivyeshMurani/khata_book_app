package io.selfmade.khatabook.utilities

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.selfmade.khatabook.adapter.BottomSheetAdapter
import io.selfmade.khatabook.databinding.BottomSheetDialogItemSelecteBinding
import io.selfmade.khatabook.model.CreateItem
import java.io.Serializable

class ItemSelectBottomDialog : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetDialogItemSelecteBinding
    private var list = ArrayList<CreateItem>()
    private lateinit var adapter: BottomSheetAdapter

    private lateinit var itemSelect: OnItemSelect

    companion object {
        val TAG = "ActionBottomDialog"
        fun getInstance(list: List<CreateItem>): BottomSheetDialogFragment {
            val it = BottomSheetDialogFragment()
            val b = Bundle()
            b.putSerializable("data", list as Serializable)
            it.arguments = b
            return it
        }

    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = BottomSheetDialogItemSelecteBinding.inflate(inflater)
        Logs.e("onCreateView")
        initRecyclerView()
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Logs.e("onAttach")
        if (itemSelect is OnItemSelect) {
            itemSelect = context as OnItemSelect
        } else {
            throw RuntimeException("Must implement ItemClickListener")
        }

    }

    private fun initRecyclerView() {

        val l = requireArguments().getSerializable("data") as List<CreateItem>

        list.addAll(l)

        Toast.makeText(context, "${list.size}", Toast.LENGTH_SHORT).show()

        Logs.e("list :${list.size}")

        adapter =
            BottomSheetAdapter(requireContext(), list, object : BottomSheetAdapter.doItemSelect {
                override fun doItem(position: Int) {
                    itemSelect.onItemSelect(list[position])

                }
            })

        binding.mRecyclerView.setHasFixedSize(true)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.mRecyclerView.adapter = adapter
    }

    public interface OnItemSelect {
        fun onItemSelect(it: CreateItem)
    }


}