package ru.ayunusov.todolist.presentation.add

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import ru.ayunusov.todolist.R
import ru.ayunusov.todolist.databinding.FragmentAddTaskBinding
import ru.ayunusov.todolist.di.FragmentScope
import ru.ayunusov.todolist.presentation.main.MainActivity
import javax.inject.Inject

@FragmentScope
class AddTaskFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val args: AddTaskFragmentArgs by navArgs()
    private val viewModel: AddTaskViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).activityComponent.addTaskComponent().create()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupToolbar()
        setArgsToViewModel()
        setupNavigate()
        setupCompose()
        setupErrorListener()
    }

    /**
     * Прослушиватель Ошибок
     * */
    private fun setupErrorListener() {
        viewModel.isShowError.observe(viewLifecycleOwner){
            if(it.getContentIfNotHandle() == true) {
                showError(getString(R.string.erorr_name_task_null))
            }
        }
    }

    /**
     * Настройка Toolbar
     * */
    private fun setupToolbar() {
        binding?.addToolbar?.setupWithNavController(
            findNavController(),
            AppBarConfiguration(setOf())
        )
        binding?.addToolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.addTask -> viewModel.saveTask()
                else -> {
                    false
                }
            }
        }
    }

    /**
     * Привязка Compose
     * */
    private fun setupCompose() {
        binding?.composeView?.setContent {
            StartDatePicker(
                isVisible = viewModel.isVisibilityDatePicker.value,
                year = viewModel.year.value ?: 0,
                month = viewModel.month.value ?: 0,
                day = viewModel.day.value ?: 1,
                onDismiss = viewModel::onDatePickerDismiss,
                onChange = viewModel::onDateChange
            )

            StartTimePicker(
                isVisible = viewModel.isVisibilityTimePicker.value,
                hour = viewModel.hour.value ?: 0,
                minute = viewModel.minute.value ?: 0,
                onChange = viewModel::onTimeChange,
                onDismiss = viewModel::onTimePickerDismiss
            )
        }
    }

    /**
     * Устанавливает observe на навигацию
     * */
    private fun setupNavigate() {
        viewModel.isNavigate.observe(viewLifecycleOwner) {
            it.getContentIfNotHandle()?.let { navigate ->
                when (navigate) {
                    AddTaskViewModel.Companion.NAVIGATE.NAVIGATE_BACK -> navigateToBack()
                    AddTaskViewModel.Companion.NAVIGATE.NAVIGATE_TO_LIST -> navigateToList()
                }
            }
        }
    }

    /**
     * Передаёт параметры ViewModel
     * */
    private fun setArgsToViewModel() {
        viewModel.setArgs(args.date, args.taskID)
    }

    /**
     * Настройка DataBinding
     * */
    private fun setupBinding() {
        binding?.viewmodel = viewModel
        binding?.lifecycleOwner = this
    }

    /**
     * Переход на основной экран
     * */
    private fun navigateToBack() {
        findNavController().popBackStack()
    }

    /**
     * Переход на основной экран
     * */
    private fun navigateToList() {
        val action = AddTaskFragmentDirections.actionAddTaskFragmentToToDoFragment()
        findNavController().navigate(action)
    }

    /**
     * Показывает ошибку
     * */
    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}