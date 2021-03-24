package com.udacity.shoestore.screens.shoeList

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeListBinding
import com.udacity.shoestore.databinding.ViewShoeItemBinding
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class ShoeListFragment : Fragment() {

    private val shoeListViewModel: ShoeListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Timber.i("onCreateView called")

        // Inflate the layout for this fragment
        val binding: FragmentShoeListBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_shoe_list, container, false
        )

        // init viewModel
        binding.shoeListViewModel = shoeListViewModel
        binding.lifecycleOwner = this

        shoeListViewModel.shoeList.observe(viewLifecycleOwner, Observer { shoeList ->
            binding.shoeListLinearLayout.removeAllViewsInLayout()
            shoeList.forEach { shoe ->
                val viewShoeItemBinding: ViewShoeItemBinding = DataBindingUtil.inflate(
                    inflater, R.layout.view_shoe_item, binding.shoeListLinearLayout, true)
                viewShoeItemBinding.lifecycleOwner = this

                Timber.i("Display shoe: ${shoe.name}")
                viewShoeItemBinding.shoe = shoe

                viewShoeItemBinding.shoeItem.setOnClickListener { v: View ->
                    v.findNavController()
                        .navigate(ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailFragment(viewShoeItemBinding.shoe))
                }
            }
        })

        // get args using NavArgs
        val shoeArg by navArgs<ShoeListFragmentArgs>()
        if (shoeArg.newShoe != null) {
            Timber.i("shoe argument with id ${shoeArg.newShoe!!.id} and name ${shoeArg.newShoe!!.name} was passed")
            shoeListViewModel.addOrUpdateShoe(shoeArg.newShoe!!)
        }
        else if (shoeArg.shoeToDelete != null){
            Timber.i("shoe to delete with id ${shoeArg.shoeToDelete!!} was passed")
            shoeListViewModel.removeShoe(shoeArg.shoeToDelete!!)
        }

        binding.floatingActionButton.setOnClickListener {
            it.findNavController()
                .navigate(ShoeListFragmentDirections.actionShoeListFragmentToShoeDetailFragment())
        }
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logout_menu, menu)
    }

}