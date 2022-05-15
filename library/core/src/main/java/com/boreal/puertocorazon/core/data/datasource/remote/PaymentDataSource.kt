package com.boreal.puertocorazon.core.data.datasource.remote

import android.util.Base64
import com.boreal.commonutils.globalmethod.getDeviceId
import com.boreal.puertocorazon.core.BuildConfig
import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentRequest
import com.boreal.puertocorazon.core.domain.entity.payment.PCPaymentResponse
import com.boreal.puertocorazon.core.utils.payment.ConektaCardModel
import com.boreal.puertocorazon.core.utils.payment.ConektaErrorModel
import com.boreal.puertocorazon.core.utils.payment.ConektaTokenModel
import com.boreal.puertocorazon.core.utils.retrofit.core.*
import com.google.gson.Gson
import io.conekta.conektasdk.Conekta
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import retrofit2.Call
import java.net.UnknownHostException


class PaymentDataSource {

    companion object {
        fun getData(
            request: PCPaymentRequest,
            conektaModel: ConektaCardModel
        ): DataResponse<PCPaymentResponse> = try {
            Conekta.setPublicKey(BuildConfig.CONEKTA_PUBLIC_KEY)
            Conekta.setApiVersion("2.0.0")
            var result = ""
            val nameValuePair: MutableList<NameValuePair> = ArrayList(6)
            nameValuePair.add(BasicNameValuePair("card[number]", conektaModel.numberCard))
            nameValuePair.add(BasicNameValuePair("card[name]", conektaModel.nameCard))
            nameValuePair.add(BasicNameValuePair("card[cvc]", conektaModel.cvc))
            nameValuePair.add(BasicNameValuePair("card[exp_month]", conektaModel.exp_month))
            nameValuePair.add(BasicNameValuePair("card[exp_year]", conektaModel.exp_year))
            nameValuePair.add(BasicNameValuePair("card[device_fingerprint]", getDeviceId()))
            val http: HttpClient = DefaultHttpClient()
            val httpRequest = HttpPost(Conekta.getBaseUri() + "/tokens")
            val encoding =
                Base64.encodeToString(Conekta.getPublicKey().toByteArray(), Base64.NO_WRAP)
            httpRequest.setHeader(
                "Accept",
                "application/vnd.conekta-v" + Conekta.getApiVersion() + "+json"
            )
            httpRequest.setHeader("Accept-Language", Conekta.getLanguage())
            httpRequest.setHeader(
                "Conekta-Client-User-Agent",
                "{\"agent\": \"Conekta Android SDK\"}"
            )
            httpRequest.setHeader("Authorization", "Basic $encoding")
            httpRequest.entity = UrlEncodedFormEntity(nameValuePair, "UTF-8")
            val response = http.execute(httpRequest)
            result = EntityUtils.toString(response.entity)
            val gson = Gson()
            try {
                val conektaErrorModel = gson.fromJson(
                    result,
                    ConektaErrorModel::class.java
                )
                DataResponse(
                    statusRequest = StatusRequestEnum.FAILURE,
                    null, errorData =
                    conektaErrorModel.details.map { it.message }.toString().replace("[", "")
                        .replace("]", "")
                )
            } catch (exception: Exception) {
                val conektaTokenModel = gson.fromJson(
                    result,
                    ConektaTokenModel::class.java
                )
                request.token = conektaTokenModel.id
                request.emailLocal = request.emailLocal.replace("/","")
                request.environmentLocal = request.environmentLocal.replace("/","")
                ValidResponse<PCPaymentResponse>(PCPaymentResponse::class).validationMethod(
                    InitServices<Call<PCPaymentResponse>, PCPaymentRequest>().postExecuteService(
                        request,
                        RemoteUrls.PAYMENT_TRANSACTION.url
                    )
                )
            }
        } catch (exception: Exception) {
            if (exception is UnknownHostException) {
                DataResponse(
                    statusRequest = StatusRequestEnum.FAILURE,
                    null,
                    errorData = "Por favor, revisa tu conexion a internet"
                )
            } else {
                DataResponse(
                    statusRequest = StatusRequestEnum.FAILURE,
                    null,
                    errorData = "No se pudo completar la transaccion"
                )
            }
        }
    }

}
