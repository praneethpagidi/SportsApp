package praneeth.com.sportsapp.domain.service

/**
 * Created by Praneeth on 2019-11-18.
 */
sealed class ServiceResult <out R> {
    data class Success<out T>(val data: T): ServiceResult<T>()
    data class Error(val exception: ServiceException): ServiceResult<Nothing>()

    data class ServiceException(val code: String?, val string: String)
}
