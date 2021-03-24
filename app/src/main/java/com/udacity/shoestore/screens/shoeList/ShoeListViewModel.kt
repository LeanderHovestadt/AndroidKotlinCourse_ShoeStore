package com.udacity.shoestore.screens.shoeList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe
import timber.log.Timber
import java.util.*

class ShoeListViewModel : ViewModel() {
    private val _shoeList = MutableLiveData<MutableList<Shoe>>()
    val shoeList: LiveData<MutableList<Shoe>>
        get() = _shoeList

    init {
        Timber.i("ShoeListViewModel created!")

        initShoeList()
    }

    private fun initShoeList(){
        _shoeList.value = mutableListOf(
            Shoe(UUID.randomUUID().toString(),"Airmax pro", 43.0, "Nike", "The Airmax pro is the flagship of the Airmax collection."),
            Shoe(UUID.randomUUID().toString(),"Five Ten Freerider", 42.0, "Adidas", "The Five Ten Freerider is the best shoe for mountain biking."),
            Shoe(UUID.randomUUID().toString(),"Five Ten Freerider", 41.0, "Adidas", "The Five Ten Freerider is the best shoe for mountain biking."),
            Shoe(UUID.randomUUID().toString(),"Monaco Ducks", 38.0, "Monaco", "This shoe is Fashion!")
        )
    }

    fun addOrUpdateShoe(newShoe: Shoe){
        Timber.i("addOrUpdateShoe was called")
        var shoeIndex = _shoeList.value?.indexOfFirst { shoe: Shoe ->
            shoe.id == newShoe.id
        }
        if (shoeIndex == null || shoeIndex == -1){
            Timber.i("no shoe with id ${newShoe!!.id} was found, adding a new one")
            _shoeList.value?.add(newShoe)
        }
        else{
            Timber.i("shoe with id ${newShoe!!.id} and name ${newShoe!!.name} was found, updating it")
            _shoeList.value?.set(shoeIndex!!, newShoe!!)
        }
    }

    fun removeShoe(id: String){
        Timber.i("remove shoe with id $id")

        _shoeList.value?.removeIf { shoe: Shoe ->
            shoe.id == id
        }
    }
}