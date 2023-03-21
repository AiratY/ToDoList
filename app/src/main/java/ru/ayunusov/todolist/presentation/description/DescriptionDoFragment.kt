package ru.ayunusov.todolist.presentation.description

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import ru.ayunusov.todolist.R
import ru.ayunusov.todolist.databinding.FragmentDescriptionDoBinding
import ru.ayunusov.todolist.presentation.main.MainActivity
import javax.inject.Inject

class DescriptionDoFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: DescViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentDescriptionDoBinding? = null
    private val binding get() = _binding

    private val args: DescriptionDoFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as? MainActivity)?.activityComponent?.descComponent()?.create()
            ?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDescriptionDoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupArgs()
        setupBinding()
        setupListenerToNavigate()
    }

    private fun setupToolbar() {
        binding?.toolbar?.setupWithNavController(findNavController(), AppBarConfiguration(setOf()))
        binding?.toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.editTask -> viewModel.editTask()
                R.id.deleteTask -> viewModel.deleteTask()
                else -> {
                    false
                }
            }
        }
    }

    /**
     * Передаёт аргументы ViewModel
     * */
    private fun setupArgs() {
        viewModel.setID(args.taskId)
    }

    /**
     * Задаёт параметры для DataBinding
     * */
    private fun setupBinding() {
        viewModel.task.observe(viewLifecycleOwner) { task ->
            binding?.task = task
        }
        binding?.lifecycleOwner = this.viewLifecycleOwner
    }

    /**
     * Устанавливает Listener для навигации
     * */
    private fun setupListenerToNavigate() {
        viewModel.isNavigateUp.observe(viewLifecycleOwner) {
            if (it.getContentIfNotHandle() == true) {
                findNavController().popBackStack()
            }
        }

        viewModel.isNavigateToEdit.observe(viewLifecycleOwner) { it ->
            it.getContentIfNotHandle()?.let { id ->
                navigateToEditTask(id)
            }
        }
    }

    private fun navigateToEditTask(taskId: Int) {
        val action =
            DescriptionDoFragmentDirections.actionDescriptionDoFragmentToAddTaskFragment(taskID = taskId)
        findNavController().navigate(action)
    }
}