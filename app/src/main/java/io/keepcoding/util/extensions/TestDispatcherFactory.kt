package io.keepcoding.util.extensions

import io.keepcoding.util.DispatcherFactory
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TestDispatcherFactory : DispatcherFactory {

    override fun getMain(): CoroutineContext = Dispatchers.Unconfined

    override fun getIO(): CoroutineContext = Dispatchers.Unconfined
}