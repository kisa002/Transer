package com.haeyum.shared.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coroutineScopesModule = module {
    factory(named("MainScope")) { CoroutineScope(SupervisorJob() +  Dispatchers.Main) }
    factory(named("IOScope")) { CoroutineScope(SupervisorJob() + Dispatchers.IO) }
    factory(named("DefaultScope")) { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
    factory(named("UnconfinedScope")) { CoroutineScope(SupervisorJob() + Dispatchers.Unconfined) }
}