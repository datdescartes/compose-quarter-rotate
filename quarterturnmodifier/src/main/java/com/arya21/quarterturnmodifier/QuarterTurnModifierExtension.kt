package com.arya21.quarterturnmodifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.node.LayoutModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo
import androidx.compose.ui.unit.Constraints

private enum class QuarterTurnType {
    NONE, LEFT, RIGHT, UPSIDE_DOWN;

    fun shouldUpdateSize(): Boolean = this == LEFT || this == RIGHT

    fun toDegree(): Float = when (this) {
        NONE -> 0f
        LEFT -> -90f
        RIGHT -> 90f
        UPSIDE_DOWN -> 180f
    }

    companion object {
        fun fromQuarterTurns(quarterTurns: Int): QuarterTurnType {
            return when (quarterTurns % 4) {
                1, -3 -> RIGHT
                -1, 3 -> LEFT
                2, -2 -> UPSIDE_DOWN
                else -> NONE
            }
        }
    }
}

/**
 * Apply a rotation by a integral number of quarter turns to the content.
 * This modifier affects the layout, unlike [Modifier.rotate]
 * which only applies a transformation just prior to painting.
 *
 * @param quarterTurns The number of clockwise quarter turns.
 * @return The Modifier for chaining.
 */
fun Modifier.quarterRotate(quarterTurns: Int): Modifier {
    val type = QuarterTurnType.fromQuarterTurns(quarterTurns)
    if (type == QuarterTurnType.NONE) return this
    val degree = type.toDegree()
    return if (type.shouldUpdateSize()) {
        this
            .rotate(degree)
            .then(LayoutElement)
    } else {
        this.rotate(degree)
    }
}

private object LayoutElement : ModifierNodeElement<LayoutModifierImpl>() {
    // Since we cannot call the superclass' implementation of a member extension function,
    // we need to use an intermediate object.
    // Reference: https://youtrack.jetbrains.com/issue/KT-11488
    val originalModifier: LayoutModifier = object : LayoutModifier {
        override fun MeasureScope.measure(
            measurable: Measurable,
            constraints: Constraints,
        ): MeasureResult {
            val placeable = measurable.measure(constraints)
            return layout(placeable.width, placeable.height) {
                placeable.placeRelative(0, 0)
            }
        }
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "rotated_element"
    }

    override fun create(): LayoutModifierImpl {
        return LayoutModifierImpl(originalModifier)
    }

    override fun equals(other: Any?): Boolean {
        return other != null && other is LayoutElement
    }

    override fun hashCode(): Int = 1

    override fun update(node: LayoutModifierImpl) {
        // no-op
    }
}

internal class LayoutModifierImpl(
    private val originalModifier: LayoutModifier
) : LayoutModifierNode, Modifier.Node() {
    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints,
    ): MeasureResult {
        val wrappedConstraints = Constraints(
            minWidth = if (constraints.hasBoundedHeight) constraints.minHeight else 0,
            minHeight = if (constraints.hasBoundedWidth) constraints.minWidth else 0,
            maxWidth = constraints.maxHeight,
            maxHeight = constraints.maxWidth,
        )
        val placeable = measurable.measure(wrappedConstraints)
        return layout(placeable.height, placeable.width) {
            placeable.place(
                x = -(placeable.width / 2 - placeable.height / 2),
                y = -(placeable.height / 2 - placeable.width / 2),
            )
        }
    }

    override fun IntrinsicMeasureScope.minIntrinsicHeight(
        measurable: IntrinsicMeasurable,
        width: Int,
    ): Int = with(originalModifier) { minIntrinsicWidth(measurable, width) }

    override fun IntrinsicMeasureScope.minIntrinsicWidth(
        measurable: IntrinsicMeasurable,
        height: Int,
    ): Int = with(originalModifier) { minIntrinsicHeight(measurable, height) }

    override fun IntrinsicMeasureScope.maxIntrinsicWidth(
        measurable: IntrinsicMeasurable,
        height: Int,
    ): Int = with(originalModifier) { maxIntrinsicHeight(measurable, height) }

    override fun IntrinsicMeasureScope.maxIntrinsicHeight(
        measurable: IntrinsicMeasurable,
        width: Int,
    ): Int = with(originalModifier) { maxIntrinsicWidth(measurable, width) }
}
