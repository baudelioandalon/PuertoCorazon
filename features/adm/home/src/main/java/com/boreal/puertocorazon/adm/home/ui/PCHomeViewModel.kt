package com.boreal.puertocorazon.adm.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.boreal.puertocorazon.adm.home.usecase.HomeUseCase
import com.boreal.puertocorazon.core.domain.entity.AFirestoreGetResponse
import com.boreal.puertocorazon.core.domain.entity.AFirestoreStatusRequest
import com.boreal.puertocorazon.core.domain.entity.auth.AAuthModel
import com.boreal.puertocorazon.core.domain.entity.event.PCEventModel
import com.boreal.puertocorazon.core.usecase.AuthUseCase
import com.boreal.puertocorazon.core.usecase.EmptyIn
import com.boreal.puertocorazon.core.usecase.UseCase
import com.boreal.puertocorazon.core.utils.CUBaseViewModel
import com.boreal.puertocorazon.core.utils.log
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class PCHomeViewModel(
    private val getHomeUseCase:
    UseCase<HomeUseCase.Input, HomeUseCase.Output>,
    private val getAuthUseCase:
    UseCase<EmptyIn, AuthUseCase.Output>
) : CUBaseViewModel() {

    val eventList: LiveData<AFirestoreGetResponse<List<PCEventModel>>>
        get() = _eventList
    private val _eventList =
        MutableLiveData<AFirestoreGetResponse<List<PCEventModel>>>()

    val authUser: LiveData<Pair<AFirestoreStatusRequest, AAuthModel?>>
        get() = _authUser
    private val _authUser = MutableLiveData<Pair<AFirestoreStatusRequest, AAuthModel?>>(
        Pair(
            AFirestoreStatusRequest.NONE,
            null
        )
    )

    fun requestEvents() {
        executeFlow {
            _eventList.value = AFirestoreGetResponse(status = AFirestoreStatusRequest.LOADING)
            getHomeUseCase.execute(
                HomeUseCase.Input(
                    "eventData",
                    "DEBUG/baudelio_andalon@hotmail.com/Events"
                )
            ).collect {
                _eventList.value = it.response
            }
        }
    }

    fun getLocalUser() {
        executeFlow {
            _authUser.value = Pair(AFirestoreStatusRequest.LOADING, AAuthModel())
            getAuthUseCase.execute(EmptyIn).collect {
                _authUser.value = it.response
            }
        }
    }

}