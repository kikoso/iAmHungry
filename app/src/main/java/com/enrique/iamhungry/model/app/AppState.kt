package com.enrique.iamhungry.model.app

sealed class AppState {
    object Exploration : AppState()
    object Navigation : AppState()
    object Finish : AppState()
}