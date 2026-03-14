package com.example.jetsnack.ui.home.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jetsnack.R
import com.example.jetsnack.model.OrderLine
import com.example.jetsnack.model.SnackRepo
import com.example.jetsnack.model.SnackbarManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CartViewModel(
    private val snackbarManager: SnackbarManager, snackRepository: SnackRepo
) : ViewModel() {
    private val _orderLines: MutableStateFlow<List<OrderLine>> =
        MutableStateFlow(snackRepository.getCart())
    val orderLine: StateFlow<List<OrderLine>> = _orderLines.asStateFlow()

    private var requestCount = 0
    private fun shouldRandomlyFail(): Boolean = ++requestCount %5 == 0

    fun increaseSnackCount(snackId: Long) {
        if(shouldRandomlyFail()) {
            snackbarManager.showMessage(R.string.cart_increase_error)
        } else {
            val currentCount = _orderLines.value.first { it.snack.id == snackId }.count
            updateSnackCount(snackId, currentCount + 1)
        }
    }

    fun removeSnack(snackId: Long) {
        _orderLines.value = _orderLines.value.filter { it.snack.id != snackId }
    }

    fun decreaseSnackCount(snackId: Long) {
        if(shouldRandomlyFail()) {
            snackbarManager.showMessage(R.string.cart_increase_error)
        } else {
            val currentCount = _orderLines.value.first { it.snack.id == snackId }.count
            updateSnackCount(snackId, currentCount - 1)
        }
    }

    private fun updateSnackCount(snackId: Long, newCount: Int) {
        _orderLines.value = _orderLines.value.map {
            if(it.snack.id == snackId) {
                it.copy(count = newCount)
            } else {
                it
            }
        }
    }

    /**
     * Factory for CartViewModel that takes SnackbarManager as a dependency
     */
    companion object {
        fun provideFactory(
            snackbarManager: SnackbarManager = SnackbarManager,
            snackRepository: SnackRepo = SnackRepo,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CartViewModel(snackbarManager, snackRepository) as T
            }
        }
    }
}