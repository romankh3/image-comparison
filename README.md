![logo-trans](https://user-images.githubusercontent.com/16310793/42029324-df117c42-7ad7-11e8-8d3e-9c6cd8822d6c.png)
![Maven_Workflow](https://github.com/romankh3/image-comparison/actions/workflows/maven.yml/badge.svg)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.romankh3/image-comparison.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.romankh3%22%20AND%20a:%22image-comparison%22)
[![Javadocs](http://www.javadoc.io/badge/com.github.romankh3/image-comparison.svg?color=green)](http://www.javadoc.io/doc/com.github.romankh3/image-comparison)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/romankh3/image-comparison/pulls)

*   [About](#about)

*   [Configurations](#configurations)

*   [Release Notes](#release-notes)

*   [Usage](#usage)

*   [Demo](#demo)

*   [License](#license)

*   [Contributing](#contributing)

*   [Code of Conduct](#code-of-conduct)

*   [Other Projects](#also-if-youre-interesting---see-my-other-repositories)

## About
Published on Maven Central and jCenter Java Library that compares 2 images with the same sizes and shows the differences visually by drawing rectangles. Some parts of the image can be excluded from the comparison. Can be used for automation qa tests. The Usages of the `image-comparison` can be found here [Usage Image Comparison](https://github.com/romankh3/usage-image-comparison)

*   Implementation is using only standard core language and platform features, no 3rd party libraries and plagiarized code is permitted.

*   Pixels (with the same coordinates in two images) can be visually similar, but have different values of RGB. 2 pixels are considered to be "different" if they differ more than `pixelToleranceLevel`(this configuration described below) from each other.

*   The output of the comparison is a copy of `actual` images. The differences are outlined with red rectangles as shown below.

*   Some parts of the image can be excluded from the comparison and drawn in the result image.

Article about growing `image-comparison` on habr: [How did the test task become a production library](https://habr.com/ru/post/475482/)

## Configuration
All these configurations can be updated based on your needs.
| *Property* | *Description* |
| --- | --- |
| `threshold` | The threshold which means the max distance between non-equal pixels. Could be changed according size and requirements to the image. |
| `rectangleLineWidth` | Width of the line that is drawn the rectangle. |
| `destination` | File of the result destination. |
| `minimalRectangleSize` | The number of the minimal rectangle size. Count as (width x height). By default it's 1. |
| `maximalRectangleCount` | Maximal count of the Rectangles, which would be drawn. It means that would get first x biggest rectangles. Default value is -1, that means that all the rectangles would be drawn. |
| `pixelToleranceLevel` | Level of the pixel tolerance. By default it's 0.1 -> 10% difference. The value can be set from 0.0 to 0.99. |
| `excludedAreas` | ExcludedAreas contains a List of Rectangles to be ignored when comparing images. |
| `drawExcludedRectangles` | Flag which says draw excluded rectangles or not. |
| `fillExcludedRectangles` | Flag which says fill excluded rectangles or not. |
| `percentOpacityExcludedRectangles` | The desired opacity of the excluded rectangle fill. |
| `fillDifferenceRectangles` | Flag which says fill difference rectangles or not. |
| `percentOpacityDifferenceRectangles` | The desired opacity of the difference rectangle fill. |
| `allowingPercentOfDifferentPixels` | The percent of the allowing pixels to be different to stay MATCH for comparison. E.g. percent of the pixels, which would ignore in comparison. Value can be from 0.0 to 100.00 |
| `differenceRectangleColor` | Rectangle color of image difference. By default, it's red. |
| `excludedRectangleColor` | Rectangle color of excluded part. By default, it's green. |


## Release Notes

Can be found in [RELEASE_NOTES](RELEASE_NOTES.md).

## Usage

#### Maven
```xml
<dependency>
    <groupId>com.github.romankh3</groupId>
    <artifactId>image-comparison</artifactId>
    <version>4.4.0</version>
</dependency>
```
#### Gradle
```groovy
compile 'com.github.romankh3:image-comparison:4.4.0'
```

#### To compare two images programmatically
##### Default way to compare two images looks like:
```java
        //load images to be compared:
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources("expected.png");
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources("actual.png");

        //Create ImageComparison object and compare the images.
        ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage).compareImages();
        
        //Check the result
        assertEquals(ImageComparisonState.MATCH, imageComparisonResult.getImageComparisonState());
```

##### Save result image
To save result image, can be used two ways:
1. add a file to save to constructor. ImageComparison will save the result image in this case.
```java
        //load images to be compared:
        BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources("expected.png");
        BufferedImage actualImage = ImageComparisonUtil.readImageFromResources("actual.png");
        
        // where to save the result (leave null if you want to see the result in the UI)
        File resultDestination = new File( "result.png" );

        //Create ImageComparison object with result destination and compare the images.
        ImageComparisonResult imageComparisonResult = new ImageComparison(expectedImage, actualImage, resultDestination).compareImages();
```
2. execute ImageComparisonUtil.saveImage static method
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
