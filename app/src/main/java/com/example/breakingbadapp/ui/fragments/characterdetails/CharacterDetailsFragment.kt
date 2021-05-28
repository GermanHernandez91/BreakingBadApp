package com.example.breakingbadapp.ui.fragments.characterdetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.breakingbadapp.adapters.OccupationAdapter
import com.example.breakingbadapp.databinding.FragmentCharacterDetailsBinding
import com.example.breakingbadapp.models.Character
import com.example.breakingbadapp.utils.NetworkResult
import kotlinx.coroutines.launch

class CharacterDetailsFragment : Fragment() {

    private val args by navArgs<CharacterDetailsFragmentArgs>()

    private var binding: FragmentCharacterDetailsBinding? = null

    private lateinit var viewModel: CharacterDetailsViewModel

    private val mAdapter by lazy { OccupationAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CharacterDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = this
        binding?.viewModel = viewModel

        setupRecyclerView()

        lifecycleScope.launch {
            requestApiData()
        }

        return binding?.root
    }

    private fun setupRecyclerView() {
        binding?.occupationsRecyclerView?.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun requestApiData() {
        viewModel.getCharacterById(args.charId)
        viewModel.characterResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        viewModel.character.postValue(it)
                        binding?.characterImageView?.load(it.img)
                        it.occupation?.let { value -> mAdapter.setData(value) }
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    Log.i("requestApiData", "Loading...")
                }
                else -> {
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}