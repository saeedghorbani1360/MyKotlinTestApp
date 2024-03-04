package com.example.mykotlintestapp.first

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.mykotlintestapp.R
import com.example.mykotlintestapp.databinding.FragmentFirstBinding
import com.example.mykotlintestapp.dialog.CustomDialog
import com.example.mykotlintestapp.login.LoginFragmentDirections

class FirstFragment : Fragment() {

    companion object {
        fun newInstance() = FirstFragment()
    }

    private lateinit var viewModel: FirstViewModel

    private lateinit var binding : FragmentFirstBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val FirstFragmentArgs by navArgs<FirstFragmentArgs>()
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_first,container,false)
        binding.logname.text = "Welcome "+FirstFragmentArgs.user

        var dataset = arrayOf(
            LoginInfo(1 ,"Saeed",System.currentTimeMillis(),"Developer"),
            LoginInfo(2 ,"Reza",System.currentTimeMillis(),"IT manager"),
            LoginInfo(3 ,"Ali",System.currentTimeMillis(),"Officer"),
            LoginInfo(4 ,"Fardin",System.currentTimeMillis(),"Driver"),
            LoginInfo(5 ,"Saeed",System.currentTimeMillis(),"Developer"),
            LoginInfo(6 ,"Reza",System.currentTimeMillis(),"IT manager"),
            LoginInfo(7 ,"Ali",System.currentTimeMillis(),"Officer"),
            LoginInfo(8 ,"Fardin",System.currentTimeMillis(),"Driver"),
            LoginInfo(9 ,"Saeed",System.currentTimeMillis(),"Developer"),
            LoginInfo(10,"Reza",System.currentTimeMillis(),"IT manager"),
            LoginInfo(11,"Ali",System.currentTimeMillis(),"Officer"),
            LoginInfo(12,"Fardin",System.currentTimeMillis(),"Driver"))

        val customAdapter = LoginListAdapter(dataset)

        val cl = object :
            LoginListAdapter.OnClickListener, CustomDialog.OnEventListener {
            private var itemId=-1
            override fun onClick(item: LoginInfo) {
                val dialog = CustomDialog(context,this)
                itemId = item.id
                dialog.setTitle(getString(R.string.delete))
                dialog.setBody("Delete row?")
                dialog.setPositive(getString(R.string.delete))
                dialog.setNegative("Cancel")
                dialog.show()

//                Toast.makeText(requireContext(),"User: ${item.name}  Role: ${item.role}",Toast.LENGTH_SHORT).show()
            }

            override fun onLongClick(item: LoginInfo) {
//                Toast.makeText(requireContext(),"User: ${item.name}  Role: ${item.role}",Toast.LENGTH_SHORT).show()
            }

            override fun onPositive() {
                if (itemId != -1) {
                    dataset = dataset.filter {
                        it.id != itemId
                    }.toTypedArray()
                }
                customAdapter.reloadData(dataset)
            }

            override fun onNegative() {
            }

        }
        customAdapter.setOnClickListener(cl)

        val recyclerView: RecyclerView = binding.loginHist
        recyclerView.adapter = customAdapter

        val itemDecoration = DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)

        val itemTouchHelper = ItemTouchHelper(SimpleCallback(customAdapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        val manager = LinearLayoutManager(requireContext())
        binding.loginHist.layoutManager = manager
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FirstViewModel::class.java)
        // TODO: Use the ViewModel
    }
    

}


