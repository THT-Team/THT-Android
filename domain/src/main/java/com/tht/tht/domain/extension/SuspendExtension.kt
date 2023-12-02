package com.tht.tht.domain.extension

suspend fun <T> retry(retryCount: Int, block: suspend () -> T): T {
    var tryCount = 0
    while (tryCount <= retryCount - 1) {
        try {
            return block()
        } catch (e: Exception) {
            tryCount++
        }
    }
    return block()
}

suspend fun <T> resultRetry(retryCount: Int, block: suspend () -> Result<T>): Result<T> {
    var tryCount = 0
    while (tryCount <= retryCount - 1) {
        try {
            return block()
                .also { if (it.isFailure) it.getOrThrow() }
        } catch (e: Exception) {
            tryCount++
        }
    }
    return block()
}
