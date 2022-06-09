package com.example.cupcake.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel : ViewModel() {
    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity
    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
        updateePrice()
    }

    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor
    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date
    fun setDate(pickupDate: String) {
        _date.value = pickupDate
        updateePrice()
    }
    val dateOptions = getPickupOptions()


    private val _price = MutableLiveData<Double>()
    val price: LiveData<String> = Transformations.map(_price){
        NumberFormat.getCurrencyInstance().format(it)
    }
    init {
        resetOrder()
    }


    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }


    private fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }



    private fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _date.value = dateOptions[1]
        _price.value = 0.0
    }

    private fun updateePrice() {
        _price.value = (_quantity.value ?: 0).times(PRICE_PER_CUPCAKE)
      //  if (_date.value.equals(dateOptions[0]))
            if (_date.value==dateOptions[0])
            _price.value = _price.value?.plus(3)
    }


}