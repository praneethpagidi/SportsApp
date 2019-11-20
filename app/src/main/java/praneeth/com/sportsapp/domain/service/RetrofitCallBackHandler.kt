package praneeth.com.sportsapp.domain.service

import android.util.Log
import retrofit2.Response

/**
 * Created by Praneeth on 2019-11-18.
 */

object RetrofitCallbkHandler {
    suspend fun <T> processCall(
        call: suspend () -> Response<T>
    ): ServiceResult<T> {
        return try {
            val serviceCallResponse = call()
            val body = serviceCallResponse.body()
            if (serviceCallResponse.isSuccessful && body != null) {
                ServiceResult.Success(body)
            } else {
                logExceptionMessage(
                    serviceCallResponse.message().orEmpty(),
                    serviceCallResponse.code().toString()
                )
                generateServiceError("${serviceCallResponse.code()}")
            }

        } catch (exception: Exception) {
            logExceptionMessage(exception.localizedMessage ?: "", null)
            generateServiceError(null)
        }
    }

    private fun generateServiceError(
        errorCode: String?
    ): ServiceResult.Error {
        return ServiceResult.Error(
            ServiceResult.ServiceException(errorCode, "Sorry, there was service issue, please try again")
        )
    }

    private fun logExceptionMessage(message: String, code: String?) {
        val codeMessage = if (code == null) "" else "with code: $code; "
        Log.d("exception", "$codeMessage$message")
    }
}
