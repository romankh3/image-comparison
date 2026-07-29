![logo-trans](https://user-images.githubusercontent.com/16310793/42029324-df117c42-7ad7-11e8-8d3e-9c6cd8822d6c.png)
![Maven_Workflow](https://github.com/romankh3/image-comparison/actions/workflows/maven.yml/badge.svg)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.romankh3/image-comparison.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.romankh3%22%20AND%20a:%22image-comparison%22)
[![Javadocs](http://www.javadoc.io/badge/com.github.romankh3/image-comparison.svg?color=green)](http://www.javadoc.io/doc/com.github.romankh3/image-comparison)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/romankh3/image-comparison/pulls)

A lightweight Java library that compares two images of the same size, pixel by pixel, and highlights the differences by drawing rectangles around them. Commonly used for visual regression / automation QA tests. Published on Maven Central.

*   [About](#about)
*   [Requirements](#requirements)
*   [Configuration](#configuration)
*   [Release Notes](#release-notes)
*   [Usage](#usage)
*   [Demo](#demo)
*   [License](#license)
*   [Contributing](#contributing)
*   [Code of Conduct](#code-of-conduct)
*   [Other Projects](#also-if-youre-interesting---see-my-other-repositories)

## About
`image-comparison` compares an `expected` and an `actual` image and returns an `ImageComparisonState` of `MATCH`, `MISMATCH`, or `SIZE_MISMATCH`. On a mismatch, it produces a `result` image — a copy of `actual` with the differing regions outlined in rectangles.

Key points:

*   **No third-party runtime dependencies** — implementation uses only standard JDK/AWT features.
*   **Tolerant pixel comparison** — two pixels at the same coordinates are only considered "different" if their RGB distance exceeds `pixelToleranceLevel` (configurable, see below).
*   **Excludable regions** — parts of the image can be excluded from comparison (and optionally still drawn on the result image), useful for ignoring dynamic content like timestamps.
*   Real-world usage examples can be found in [Usage Image Comparison](https://github.com/romankh3/usage-image-comparison).

Background on the project: [How did the test task become a production library](https://habr.com/ru/post/475482/) (habr.com).

## Requirements
*   Java 8 or higher.

## Configuration
All configuration is set via fluent setters on `ImageComparison` (e.g. `.setThreshold(10)`), each returning `this` so calls can be chained.
| *Property* | *Description* | *Default* |
| --- | --- | --- |
| `threshold` | Max pixel distance for two differing pixels to be grouped into the same rectangle. Increase for images with sparse/scattered differences, decrease for tightly-packed diffs. | `5` |
| `rectangleLineWidth` | Width, in pixels, of the drawn rectangle outline. | `1` |
| `destination` | `File` to write the result image to. If `null`, the result is only available via `ImageComparisonResult.getResult()`. | `null` |
| `minimalRectangleSize` | Minimum rectangle size (`width x height`) to be included in the result; smaller diffs are discarded. | `1` |
| `maximalRectangleCount` | Max number of rectangles drawn, keeping the largest first. `-1` draws all rectangles. | `-1` |
| `pixelToleranceLevel` | RGB distance tolerance for two pixels to be considered equal. Range `0.0`-`0.99`. | `0.1` (10%) |
| `excludedAreas` | `List<Rectangle>` of regions to skip during comparison. | empty list |
| `drawExcludedRectangles` | Whether to draw the `excludedAreas` outlines on the result image. | `false` |
| `fillExcludedRectangles` | Whether to fill the `excludedAreas` rectangles on the result image. | `false` |
| `percentOpacityExcludedRectangles` | Fill opacity for excluded rectangles, `0.0` (transparent) to `100.0` (opaque). | `20.0` |
| `fillDifferenceRectangles` | Whether to fill the difference rectangles on the result image. | `false` |
| `percentOpacityDifferenceRectangles` | Fill opacity for difference rectangles, `0.0` (transparent) to `100.0` (opaque). | `20.0` |
| `allowingPercentOfDifferentPixels` | Percent of differing pixels (`0.0`-`100.0`) still allowed while staying `MATCH`. | `0.0` |
| `differenceRectangleColor` | Outline/fill color for difference rectangles. | `Color.RED` |
| `excludedRectangleColor` | Outline/fill color for excluded rectangles. | `Color.GREEN` |

## Release Notes

Can be found in [RELEASE_NOTES](RELEASE_NOTES.md).

## Usage

### Installation

**Maven**
```xml
<dependency>
    <groupId>com.github.romankh3</groupId>
    <artifactId>image-comparison</artifactId>
    <version>4.4.0</version>
</dependency>
```

**Gradle**
```groovy
implementation 'com.github.romankh3:image-comparison:4.4.0'
```

### Compare two images programmatically

Basic usage — compare two images and check the resulting state:
```java
        //load images to be compared:
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources("expected.png");
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources("actual.png");

        //Create ImageComparison object and compare the images.
        ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage).compareImages();
        
        //Check the result
        assertEquals(ImageComparisonState.MATCH, imageComparisonResult.getImageComparisonState());
```

### Save the result image

There are two ways to save the result image:

**1. Pass a destination file to the constructor** — `ImageComparison` saves the result image itself.
```java
        //load images to be compared:
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources("expected.png");
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources("actual.png");
        
        // where to save the result (leave null if you want to see the result in the UI)
        File resultDestination = new File( "result.png" );

        //Create ImageComparison object with result destination and compare the images.
        ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage, resultDestination).compareImages();
```
**2. Save it explicitly with `ImageComparisonUtil.saveImage`.**
```java
        //load images to be compared:
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources("expected.png");
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources("actual.png");

        //Create ImageComparison object with result destination and compare the images.
        ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage).compareImages();

        //Image can be saved after comparison, using ImageComparisonUtil.
        ImageComparisonUtil.saveImage(resultDestination, imageComparisonResult.getResult()); 
```

## Demo
Demo shows how `image-comparison` works.

### Expected Image
![expected](https://user-images.githubusercontent.com/16310793/28955567-52edeabe-78f0-11e7-8bb2-d435c8df23ff.png)

### Actual Image
![actual](https://user-images.githubusercontent.com/16310793/28955566-52ead892-78f0-11e7-993c-847350da0bf8.png)

### Result
![result](https://user-images.githubusercontent.com/16310793/28955568-52f23e02-78f0-11e7-92c5-07602b6a0887.png)

## Contributing
Please, follow [Contributing](CONTRIBUTING.md) page.

## Code of Conduct
Please, follow [Code of Conduct](CODE_OF_CONDUCT.md) page.

## License
This project is Apache License 2.0 - see the [LICENSE](LICENSE) file for details

#### Thanks [@dee-y](https://github.com/dee-y) for designing this logo

#### Also if you're interesting - see my other repositories
*   [skyscanner-flight-api-clinet](https://github.com/romankh3/skyscanner-flight-api-client) - Client for a Skyscanner Flight Search API hosted in Rapid API
*   [Flights Monitoring](https://github.com/romankh3/flights-monitoring) - app for monitoring flights cost based on Skyscanner API
*   [Raspberry home ecosystem](https://github.com/romankh3/raspberrypi-home-ecosystem) - home ecosystem based on raspberry pi.
*   [Movie Tracking](https://github.com/romankh3/movietracking) - Simple API for tracking movies with favorite actors for the specific time.
*   [Tic Tac Toe NxM](https://github.com/romankh3/tictactoe) - Own implementation. For any rectangle and any winner line count. 
