package com.enrique.iamhungry.core

sealed class Failure {
    object NetworkConnection : Failure()
    object GeneralFailure: Failure()
    abstract class FeatureFailure: Failure()
}