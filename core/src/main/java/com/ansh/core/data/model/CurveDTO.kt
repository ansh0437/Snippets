package com.ansh.core.data.model

data class CurveDTO(
    val color: Int,
    val noOfCurves: Int = 4,
    val outermostCurveRadius: Int = 100,
    val curveWidth: Int = 10,
    val distanceBetweenCurve: Int = 10,
    val curveSweepAngle: Float = 160.0f
)