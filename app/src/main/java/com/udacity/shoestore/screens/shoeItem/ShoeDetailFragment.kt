package com.udacity.shoestore.screens.shoeItem

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.screens.shoeList.ShoeListFragmentArgs
import com.udacity.shoestore.screens.shoeList.ShoeListFragmentDirections
import timber.log.Timber
import java.util.*

class ShoeDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Timber.i("onCreateView called.")

        val binding: FragmentShoeDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_detail, container, false)
        binding.lifecycleOwner = this

        val shoeArg by navArgs<ShoeDetailFragmentArgs>()
        if (shoeArg.shoe != null) {
            binding.shoe = shoeArg.shoe!!
        }

        binding.saveButton.setOnClickListener { v: View ->

            if (binding.editTextShoeName.text.isEmpty()
                || binding.editTextShoeSize.text.isEmpty() || (binding.editTextShoeSize.text.toString()
                    .toDoubleOrNull() ?: 0.0) == 0.0
                || binding.editTextShoeBrand.text.isEmpty()
                || binding.editTextShoeDescription.text.isEmpty()
            ) {
                Toast.makeText(context, "Please enter valid data.", Toast.LENGTH_LONG).show()
            } else {
                // TODO: make sure why the binding does not get updated automatically and why we can not just use shoe here
                v.findNavController().navigate(
                    ShoeDetailFragmentDirections.actionShoeDetailFragmentToShoeListFragment(
                        Shoe(
                            binding.shoe?.id ?: UUID.randomUUID().toString(),
                            binding.editTextShoeName.text.toString(),
                            binding.editTextShoeSize.text.toString().toDoubleOrNull() ?: 0.0,
                            binding.editTextShoeBrand.text.toString(),
                            binding.editTextShoeDescription.text.toString()
                        )
                    )
                )
            }
        }

        binding.cancelButton.setOnClickListener { v: View ->
            v.findNavController()
                .navigate(ShoeDetailFragmentDirections.actionShoeDetailFragmentToShoeListFragment())
        }

        binding.fabDelete.setOnClickListener { v: View ->
            v.findNavController().navigate(
                ShoeDetailFragmentDirections.actionShoeDetailFragmentToShoeListFragment(
                    null,
                    binding.shoe?.id
                )
            )
        }

        return binding.root
    }

}