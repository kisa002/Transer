package com.haeyum.common.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coroutineScopesModule = module {
    single(named("MainScope")) { CoroutineScope(SupervisorJob() +  Dispatchers.Main) }
    single(named("IOScope")) { CoroutineScope(SupervisorJob() + Dispatchers.IO) }
    single(named("DefaultScope")) { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
    single(named("UnconfinedScope")) { CoroutineScope(SupervisorJob() + Dispatchers.Unconfined) }
}