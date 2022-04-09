package com.infinitepower.calculator.compose.buildsrc

object AndroidX {
    private const val coreKtxVersion = "1.9.0-alpha01"
    const val coreKtx = "androidx.core:core-ktx:$coreKtxVersion"

    private const val activityComposeVersion = "1.5.0-alpha04"
    const val activityCompose = "androidx.activity:activity-compose:$activityComposeVersion"

    private const val lifecycleVersion = "2.5.0-alpha05"

    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
    const val lifecycleLivedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"

    // Paging
    private const val pagingVersion = "3.1.1"

    // Paging Runtime Ktx
    const val pagingRuntimeKtx = "androidx.paging:paging-runtime-ktx:$pagingVersion"

    // Paging Jetpack Compose
    private const val pagingJetpackComposeVersion = "1.0.0-alpha14"
    const val pagingJetpackCompose = "androidx.paging:paging-compose:$pagingJetpackComposeVersion"
}