package tht.core.ui.exception

import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

fun isNetworkException(e: Throwable) = e is HttpException

fun isTokenExpiredException(e: Throwable) = e is HttpException && e.code() == 401

fun isApiNotFoundException(e: Throwable) = e is HttpException && e.code() == 404

fun isNetworkUnavailableException(e: Throwable) = e is UnknownHostException || e is ConnectException
