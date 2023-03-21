package ru.ayunusov.todolist.presentation.todolist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import ru.ayunusov.todolist.R
import ru.ayunusov.todolist.databinding.FragmentToDoBinding
import ru.ayunusov.todolist.presentation.main.MainActivity
import javax.inject.Inject

class ToDoFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<ToDoViewModel> { viewModelFactory }

    private var _binding: FragmentToDoBinding? = null
    private val binding get() = _binding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).activityComponent.todoListComponent().create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentToDoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupListenerToNavigate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Устанавливает Listener для навигации
     * */
    private fun setupListenerToNavigate() {
        viewModel.isAddNewTask.observe(viewLifecycleOwner) {
            it.getContentIfNotHandle()?.let { date ->
                navigateAddNewTask(date)
            }
        }

        viewModel.openTaskEvent.observe(viewLifecycleOwner) { it ->
            it.getContentIfNotHandle()?.let { id ->
                navigateToDetailTask(id)
            }
        }
    }

    /**
     * Осуществляет переход на экран детального описания задачи
     * */
    private fun navigateToDetailTask(taskId: Int) {
        val action = ToDoFragmentDirections.actionToDoFragmentToDescriptionDoFragment(taskId)
        findNavController().navigate(action)
    }

    /**
     * Устанавливает настройки для RecyclerView
     * */
    private fun setupRecyclerView() {
        val recycler = binding?.listTaskRecyclerView
        recycler?.isNestedScrollingEnabled = false
        recycler?.setHasFixedSize(false)
        val adapter = TaskListAdapter(viewModel)

        viewModel.listTask.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
        drawable?.let {
            dividerItemDecoration.setDrawable(drawable)
        }

        recycler?.addItemDecoration(dividerItemDecoration)
        recycler?.adapter = adapter
    }

    /**
     * Переход на экран добавления новой задачи
     * */
    private fun navigateAddNewTask(date: Long) {
        val action = ToDoFragmentDirections.actionToDoFragmentToAddTaskFragment(date)
        findNavController().navigate(action)
    }

    /**
     * Задаёт параметры для DataBinding
     * */
    private fun setupBinding() {
        binding?.viewmodel = viewModel
        binding?.lifecycleOwner = this.viewLifecycleOwner
    }
}