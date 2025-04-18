package com.skyd.rays.base.mvi

import android.os.Bundle

interface MviViewState

/**
 * An interface that converts a [MviViewState] to a [Bundle] and vice versa.
 */
interface MviViewStateSaver<S : MviViewState> {
    fun S.toBundle(): Bundle
    fun restore(bundle: Bundle?): S
}
