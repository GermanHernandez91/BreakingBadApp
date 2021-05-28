package com.example.breakingbadapp.ui.fragments.characters

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breakingbadapp.R
import com.example.breakingbadapp.adapters.CharactersAdapter
import com.example.breakingbadapp.databinding.FragmentCharactersListBinding
import com.example.breakingbadapp.utils.NetworkResult
import kotlinx.coroutines.launch

class CharactersListFragment : Fragment(), SearchView.OnQueryTextListener {

    private var binding: FragmentCharactersListBinding? = null

    private lateinit var viewModel: CharactersListViewModel

    private val mAdapter by lazy { CharactersAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CharactersListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersListBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = this

        setHasOptionsMenu(true)
        setupRecyclerView()

        lifecycleScope.launch {
            requestApiData()
        }

        return binding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView

        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    private fun setupRecyclerView() {
        binding?.charactersRecyclerView?.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        showShimmerEffect()
    }

    private fun requestApiData() {
        viewModel.getCharacters()
        viewModel.characters.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    val errorMessage = response.message.toString()
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun showShimmerEffect() {
        binding?.charactersRecyclerView?.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding?.charactersRecyclerView?.hideShimmer()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { searchApiData(it) }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }

    private fun searchApiData(searchQuery: String) {
        showShimmerEffect()
        viewModel.searchCharacterByName(searchQuery)
        viewModel.searchCharacters.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    val errorMessage = response.message.toString()
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}