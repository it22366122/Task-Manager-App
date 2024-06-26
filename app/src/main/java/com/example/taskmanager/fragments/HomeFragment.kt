package com.example.taskmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.taskmanager.MainActivity
import com.example.taskmanager.R
import com.example.taskmanager.adapter.TaskAdapter
import com.example.taskmanager.databinding.FragmentHomeBinding
import com.example.taskmanager.model.Task
import com.example.taskmanager.viewmodel.TaskViewModel


class HomeFragment : Fragment(R.layout.fragment_home),SearchView.OnQueryTextListener , MenuProvider {

    private var homeBinding: FragmentHomeBinding?=null
    private val binding get( ) = homeBinding!!

    private lateinit var taskViewModel:TaskViewModel
    private lateinit var taskAdapter: TaskAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View,savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost =requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED )

        taskViewModel = (activity as MainActivity).taskViewModel


        binding.addTaskFab.setOnClickListener{
            it.findNavController().navigate(R.id.action_homeFragment_to_addTaskFragment)

        }
    }
    private fun updateUI(note:List<Task>?){
        if(note != null){
            if(note.isNotEmpty()){
                binding.emptyTaskImage.visibility= View.GONE
                binding.homeRecyclerView.visibility= View.VISIBLE

            }else{
                binding.emptyTaskImage.visibility = View.VISIBLE
                binding.homeRecyclerView.visibility = View.GONE
            }
        }
    }
    private fun setupHomeRecyclerView(){
        taskAdapter = TaskAdapter()
        binding.homeRecyclerView.apply{
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            setHasFixedSize(true)
            adapter = taskAdapter
        }

        activity?.let {
            taskViewModel.getAllTasks().observe(viewLifecycleOwner){
                    note ->taskAdapter.differ.submitList(note)
                updateUI(note)
            }
        }
    }
    private fun searchNote(query: String?) {
        val searchQuery = "%$query"

        taskViewModel.searchTask(searchQuery).observe(this) { list ->
            taskAdapter.differ.submitList((list))
        }
    }
    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false

    }
    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null){
            searchNote((newText))

        }

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu,menu)

        val menuSearch = menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return  false
    }


}