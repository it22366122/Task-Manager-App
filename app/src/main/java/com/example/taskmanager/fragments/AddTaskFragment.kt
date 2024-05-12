package com.example.taskmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.taskmanager.MainActivity
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentAddTaskBinding
import com.example.taskmanager.model.Task
import com.example.taskmanager.viewmodel.TaskViewModel


class AddTaskFragment : Fragment() class AddNoteFragment : Fragment(R.layout.fragment_add_task),
    MenuProvider {

    private var addNoteBinding:FragmentAddTaskBinding? = null
    private val binding get() = addNoteBinding!!

    private lateinit var  notesViewModel: TaskViewModel
    private lateinit var  addnoteView:View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addNoteBinding = FragmentAddTaskBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost =requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED )

        notesViewModel = (activity as MainActivity).taskViewModel
        addnoteView = view
    }

    private fun saveNote(view: View){
        val noteTitle = binding.addTaskTitle.text.toString().trim()
        val noteDesc = binding.addNoteDesc.text.toString().trim()

        if(noteTitle.isNotEmpty()){
            val note = Task(0,noteTitle,noteDesc)
            notesViewModel.addTask(note)
            Toast.makeText(addnoteView.context,"Note saved", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment,false)
        }else{
            Toast.makeText(addnoteView.context,"Please enter note title", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_task,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId){

            R.id.saveMenu ->{
                saveNote(addnoteView)
                true
            }
            else -> false


        }

    }

    override fun onDestroy() {
        super.onDestroy()
        addNoteBinding = null
    }


}