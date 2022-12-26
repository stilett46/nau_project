package com.example.amphibians.data

import com.example.amphibians.model.Amphibian

data class AmphibianUiState(
    val choosedAmphibian: Amphibian = Amphibian(
        "", "", "", ""
    )
)
