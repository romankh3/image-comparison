![logo-trans](https://user-images.githubusercontent.com/16310793/42029324-df117c42-7ad7-11e8-8d3e-9c6cd8822d6c.png)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.romankh3/image-comparison.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.romankh3%22%20AND%20a:%22image-comparison%22)
[![Gitter](https://badges.gitter.im/image-comparison/community.svg)](https://gitter.im/image-comparison/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![Javadocs](http://www.javadoc.io/badge/com.github.romankh3/image-comparison.svg?color=green)](http://www.javadoc.io/doc/com.github.romankh3/image-comparison)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e4fd1c61d0f147358f8c5df212256491)](https://app.codacy.com/app/romankh3/image-comparison?utm_source=github.com&utm_medium=referral&utm_content=romankh3/image-comparison&utm_campaign=Badge_Grade_Dashboard)
[![Build Status](https://travis-ci.org/romankh3/image-comparison.svg?branch=master)](https://travis-ci.org/romankh3/image-comparison) [![Coverage Status](https://coveralls.io/repos/github/romankh3/image-comparison/badge.svg?branch=master)](https://coveralls.io/github/romankh3/image-comparison?branch=master) [![BCH compliance](https://bettercodehub.com/edge/badge/romankh3/image-comparison?branch=master)](https://bettercodehub.com/) 
[![Bintray](https://api.bintray.com/packages/romankh3/image-comparison/image-comparison/images/download.svg) ](https://bintray.com/romankh3/image-comparison/image-comparison/_latestVersion)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/romankh3/image-comparison/pulls)

*   [About](#about)

*   [Configurations](#configurations)

*   [Release Notes](#release-notes)

*   [Usage](#usage)

*   [Building](#building)

*   [Demo](#demo)

*   [License](#license)

*   [Contributing](#contributing)

*   [Code of Conduct](#code-of-conduct)

*   [Other Projects](#also-if-youre-interesting---see-my-other-repositories)

## About
Published on Maven Central and jCenter Java Library that compares 2 images with the same sizes and shows the differences visually by drawing rectangles. Some parts of the image can be excluded from the comparison. Can be used for automation qa tests. The Usages of the `image-comparison` can be found here [Usage Image Comparison](https://github.com/romankh3/usage-image-comparison)

*   Implementation is using only standard core language and platform features, no 3rd party libraries and plagiarized code is permitted.

*   Pixels (with the same coordinates in two images) can be visually similar, but have different values of RGB. 2 pixels are considered to be "different" if they differ more than 10% from each other.

*   The output of the comparison is a copy of `actual` images. The differences are outlined with red rectangles as shown below.

*   No third party libraries or borrowed code are in usage.

*   Some parts of the image can be excluded from the comparison and drawn in the result image.

## Configurations
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

## Release Notes

Can be found in [RELEASE_NOTES](RELEASE_NOTES.md).

## Usage

#### Maven
```xml
<dependency>
    <groupId>com.github.romankh3</groupId>
    <artifactId>image-comparison</artifactId>
    <version>4.2.1</version>
</dependency>
```
#### Gradle
```groovy
compile 'com.github.romankh3:image-comparison:4.2.1'
```

#### To compare two images programmatically
```java
class Example {
    public static void main( String[] args ) {
       // load the images to be compared
               BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources("expected.png");
               BufferedImage actualImage = ImageComparisonUtil.readImageFromResources("actual.png");
       
               // where to save the result (leave null if you want to see the result in the UI)
               File resultDestination = new File( "result.png" );
       
               //Create ImageComparison object for it.
               ImageComparison imageComparison = new ImageComparison( expectedImage, actualImage, resultDestination );
       
               //Can be used another constructor for it, without destination.
               new ImageComparison("expected.png", "actual.png");
               //or
               new ImageComparison(expectedImage, actualImage);
       
               //Also can be configured BEFORE comparing next properties:
       
               //Threshold - it's the max distance between non-equal pixels. By default it's 5.
               imageComparison.setThreshold(10);
               imageComparison.getThreshold();
       
               //RectangleListWidth - Width of the line that is drawn in the rectangle. By default it's 1.
               imageComparison.setRectangleLineWidth(5);
               imageComparison.getRectangleLineWidth();
       
               //DifferenceRectangleFilling - Fill the inside the difference rectangles with a transparent fill. By default it's false and 20.0% opacity.
               imageComparison.setDifferenceRectangleFilling(true, 30.0);
               imageComparison.isFillDifferenceRectangles();
               imageComparison.getPercentOpacityDifferenceRectangles();
       
               //ExcludedRectangleFilling - Fill the inside the excluded rectangles with a transparent fill. By default it's false and 20.0% opacity.
               imageComparison.setExcludedRectangleFilling(true, 30.0);
               imageComparison.isFillExcludedRectangles();
               imageComparison.getPercentOpacityExcludedRectangles();
       
               //Destination. Before comparing also can be added destination file for result image.
               imageComparison.setDestination(resultDestination);
               imageComparison.getDestination();
       
               //MaximalRectangleCount - It means that would get first x biggest rectangles for drawing.
               // by default all the rectangles would be drawn.
               imageComparison.setMaximalRectangleCount(10);
               imageComparison.getMaximalRectangleCount();
       
               //MinimalRectangleSize - The number of the minimal rectangle size. Count as (width x height).
               // by default it's 1.
               imageComparison.setMinimalRectangleSize(100);
               imageComparison.getMinimalRectangleSize();

               //Change the level of the pixel tolerance:
               imageComparison.setPixelToleranceLevel(0.2);
               imageComparison.getPixelToleranceLevel();
       
               //After configuring the ImageComparison object, can be executed compare() method:
               ImageComparisonResult imageComparisonResult = imageComparison.compareImages();
       
               //Can be found ComparisonState.
               ImageComparisonState imageComparisonState = imageComparisonResult.getImageComparisonState();
               
               //And Result Image
               BufferedImage resultImage = imageComparisonResult.getResult();
       
               //Image can be saved after comparison, using ImageComparisonUtil.
               ImageComparisonUtil.saveImage(resultDestination, resultImage); 
    }
}
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
