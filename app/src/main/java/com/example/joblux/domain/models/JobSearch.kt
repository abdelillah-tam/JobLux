package com.example.joblux.domain.models

data class JobSearch(
    val __CLASS__: String,
    val count: Int,
    val mean: Double,
    val results: List<Result>
)