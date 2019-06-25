![logo-trans](https://user-images.githubusercontent.com/16310793/42029324-df117c42-7ad7-11e8-8d3e-9c6cd8822d6c.png)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.romankh3/image-comparison.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.romankh3%22%20AND%20a:%22image-comparison%22)
[![Javadocs](http://www.javadoc.io/badge/com.github.romankh3/image-comparison.svg?color=green)](http://www.javadoc.io/doc/com.github.romankh3/image-comparison)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e4fd1c61d0f147358f8c5df212256491)](https://app.codacy.com/app/romankh3/image-comparison?utm_source=github.com&utm_medium=referral&utm_content=romankh3/image-comparison&utm_campaign=Badge_Grade_Dashboard)
[![Build Status](https://travis-ci.org/romankh3/image-comparison.svg?branch=master)](https://travis-ci.org/romankh3/image-comparison) [![Coverage Status](https://coveralls.io/repos/github/romankh3/image-comparison/badge.svg?branch=master)](https://coveralls.io/github/romankh3/image-comparison?branch=master) [![BCH compliance](https://bettercodehub.com/edge/badge/romankh3/image-comparison?branch=master)](https://bettercodehub.com/) 
[![Bintray](https://api.bintray.com/packages/romankh3/image-comparison/image-comparison/images/download.svg) ](https://bintray.com/romankh3/image-comparison/image-comparison/_latestVersion)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/romankh3/image-comparison/pulls)

*   [About](#about)

*   [Building](#building)

*   [Release Notes](#release-notes)

*   [Usage](#usage)
    *   [Using the command-line](#using-the-command-line)
    
    *   [Using as a Java library](#using-as-a-java-library)
*   [Demo](#demo)

*   [License](#license)

*   [Contributing](#contributing)

*   [Code of Conduct](#code-of-conduct)

*   [Other Projects](#also-if-youre-interesting---see-my-other-repositories)

## About
Published on Maven Central Java Library that compares 2 images with the same sizes and shows the differences visually by drawing rectangles. Some parts of the image can be excluded from the comparison. Can be used for automation qa tests. The Usages of the `image-comparison` can be found here [Usage Image Comparison](https://github.com/romankh3/usage-image-comparison)

*   Implementation is using only standard core language and platform features, no 3rd party libraries and plagiarized code is permitted.

*   Pixels (with the same coordinates in two images) can be visually similar, but have different values of RGB. 2 pixels are considered to be "different" if they differ more than 10% from each other.

*   The output of the comparison is a copy of `actual` images. The differences are outlined with red rectangles as shown below.

*   No third party libraries or borrowed code are in usage.

*   Some parts of the image can be excluded from the comparison and drawn in the result image.

## Building
To clone and build this project, run the following commands:
 
```bash
git clone https://github.com/romankh3/image-comparison
cd image-comparison
./gradlew check jar
```

This will compile, run the tests, and create a runnable jar at `${projectDir}/build/libs`.

## Release Notes

Can be found in [RELEASE_NOTES](RELEASE_NOTES.md).

## Usage

### Using the command-line

This library can be used as a command-line utility to compare two images.

After building with `./gradlew jar`, you will find the runnable jar at `${projectDir}/build/libs`.

To compare two images in files `a.png` and `b.png`, for example, run:

```bash
java -jar image-comparison.jar a.png b.png
```

To save the result image in a third file, say `comparison.png`, just give that file as a third argument:

```bash
java -jar image-comparison.jar a.png b.png comparison.png
```

To show more usage details, run:

```bash
java -jar image-comparison.jar -h
```

### Using as a Java library

#### Dependency
##### Maven
```xml
<dependency>
    <groupId>com.github.romankh3</groupId>
    <artifactId>image-comparison</artifactId>
    <version>3.1.1</version>
</dependency>
```
##### Gradle
```groovy
compile 'com.github.romankh3:image-comparison:3.1.1'
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
               ComparisonResult comparisonResult = imageComparison.compareImages();
       
               //Can be found ComparisonState.
               ComparisonState comparisonState = comparisonResult.getComparisonState();
               
               //And Result Image
               BufferedImage resultImage = comparisonResult.getResult();
       
               //Image can be saved after comparison, using ImageComparisonUtil.
               ImageComparisonUtil.saveImage(resultDestination, resultImage); 
    }
}
```

## Demo

Run the `./run.sh` script to run the demo.

You will get the result of comparing two images.
The images, which are using:

### Expected Image(ex image1)

![expected](https://user-images.githubusercontent.com/16310793/28955567-52edeabe-78f0-11e7-8bb2-d435c8df23ff.png)

### Actual Image(ex image 2)

![actual](https://user-images.githubusercontent.com/16310793/28955566-52ead892-78f0-11e7-993c-847350da0bf8.png)

### Result

![result](https://user-images.githubusercontent.com/16310793/28955568-52f23e02-78f0-11e7-92c5-07602b6a0887.png)

## Contributing
Please, follow [Contributing](CONTRIBUTING.md) page.

## Code of Conduct
Please, follow [Code of Conduct](CODE_OF_CONDUCT.md) page.

## License
This project is unlicense - see the [LICENSE](LICENSE) file for details

#### Thanks [@dee-y](https://github.com/dee-y) for designing this logo

#### Also if you're interesting - see my other repositories
*   [Raspberry home ecosystem](https://github.com/romankh3/raspberrypi-home-ecosystem) - home ecosystem based on raspberry pi.
*   [Movie Tracking](https://github.com/romankh3/movietracking) - Simple API for tracking movies with favorite actors for the specific time.
*   [Tic Tac Toe NxM](https://github.com/romankh3/tictactoe) - Own implementation. For any rectangle and any winner line count. 
