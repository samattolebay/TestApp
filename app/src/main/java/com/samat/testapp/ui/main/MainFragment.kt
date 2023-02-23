package com.samat.testapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.samat.testapp.MyApp
import com.samat.testapp.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.provideFactory(
            (context?.applicationContext as MyApp).repository, arguments?.getInt(KEY_PARENT_ID) ?: 0
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = view.findViewById<RecyclerView>(R.id.list)
        val adapter = RecordAdapter {
            parentFragmentManager.commit {
                replace(R.id.container, MainFragment::class.java, bundleOf(KEY_PARENT_ID to it))
                addToBackStack(null)
            }
        }
        list.adapter = adapter
        lifecycleScope.launch {
            viewModel.records.collectLatest { source -> adapter.submitData(source) }
        }
    }

    companion object {
        const val KEY_PARENT_ID = "id"
    }
}
