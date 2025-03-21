package com.skyd.rays.ext

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.skyd.rays.model.preference.BasePreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "App")

suspend fun <T> DataStore<Preferences>.put(key: Preferences.Key<T>, value: T) {
    this.edit {
        withContext(Dispatchers.IO) {
            it[key] = value
        }
    }
}

@Suppress("UNCHECKED_CAST")
fun <T> DataStore<Preferences>.getOrNull(key: Preferences.Key<T>): T? {
    return runBlocking {
        this@getOrNull.data.catch { exception ->
            if (exception is IOException) {
                exception.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            it[key]
        }.first() as T
    }
}

fun <T> DataStore<Preferences>.getOrDefault(pref: BasePreference<T>): T {
    return runBlocking {
        this@getOrDefault.data.catch { exception ->
            if (exception is IOException) {
                exception.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map {
            pref.fromPreferences(it)
        }.first()
    }
}

operator fun <T> Preferences.get(pref: BasePreference<T>): T {
    return get(pref.key) ?: pref.default
}

fun <T> DataStore<Preferences>.flowOf(pref: BasePreference<T>): Flow<T> =
    data.map { it[pref] }.distinctUntilChanged()

fun <T1, T2> DataStore<Preferences>.flowOf(
    pref1: BasePreference<T1>,
    pref2: BasePreference<T2>,
): Flow<Pair<T1, T2>> = data.map {
    Pair(it[pref1], it[pref2])
}.distinctUntilChanged()

fun <T1, T2, T3> DataStore<Preferences>.flowOf(
    pref1: BasePreference<T1>,
    pref2: BasePreference<T2>,
    pref3: BasePreference<T3>,
): Flow<Triple<T1, T2, T3>> = data.map {
    Triple(it[pref1], it[pref2], it[pref3])
}.distinctUntilChanged()