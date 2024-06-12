# Quarter turn modifier - A Jetpack Compose Library

## Overview

This library provides a `Modifier` extension function to rotate UI elements by integral quarter turns (90 degrees). Unlike `Modifier.rotate`, which only applies a transformation during painting, this modifier affects the layout, ensuring proper size adjustments.

## Features

- Rotate UI elements by 90-degree increments (quarter turns).
- Adjust layout size based on rotation to maintain proper UI alignment.
- Easy integration with Jetpack Compose.

## Installation

Add the following dependency to your `build.gradle` file:

```groovy
dependencies {
    implementation "com.arya21:compose-quarter-rotate:1.0.0"
}
```

## Usage

### Applying Rotation

Use the `Modifier.quarterRotate` function to apply a rotation to any composable. The rotation is specified in terms of the number of 90-degree clockwise quarter turns.

```kotlin
import com.arya21.quarterturnmodifier.quarterRotate

Box(
    modifier = Modifier
        .size(100.dp)
        .background(Color.Blue)
        .quarterRotate(1) // Rotate 90 degrees clockwise
) {
    Text(text = "Rotated")
}
```

## API Documentation

### `QuarterTurnType`

An internal enum class representing the possible quarter turn rotations:

- `NONE`
- `LEFT` (-90 degrees)
- `RIGHT` (90 degrees)
- `UPSIDE_DOWN` (180 degrees)

#### Methods

- `shouldUpdateSize()`: Returns `true` if the rotation affects the size (LEFT or RIGHT).
- `toDegree()`: Converts the quarter turn type to its corresponding degree value.
- `fromQuarterTurns(quarterTurns: Int)`: Maps an integer number of quarter turns to a `QuarterTurnType`.

### `Modifier.quarterRotate`

Extension function to apply quarter turn rotations to a modifier.

#### Parameters

- `quarterTurns` (Int): The number of clockwise quarter turns to rotate.

#### Returns

- `Modifier`: The modified `Modifier` for chaining.

## Example

Here's a complete example demonstrating the use of the `quarterRotate` modifier:

```kotlin
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.arya21.quarterturnmodifier.quarterRotate

@Composable
fun RotatedBox() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Blue)
            .quarterRotate(2) // Rotate 180 degrees
    ) {
        Text(text = "Upside Down")
    }
}
```

## License

This library is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

---

Feel free to contribute to the library or report any issues on the [GitHub repository](https://github.com/yourusername/compose-quarter-rotate). Happy coding!