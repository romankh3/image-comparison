# Release Notes

## 3.1.0
*   Added the ability to draw excluded areas on the result image. Rectangles with the differences drawing RED color.
    Rectangles of the excluded areas - GREEN color.

*   Fixed root problem on the algorithm.

*   Added returning `this` for setters in ImageComparison and ComparisonResult.

*   renamed image1 to expected and image2 to actual.

## 3.0.1
*   Fixed #98: Ignored area was not actually ignored.

## 3.0.0
*   Added ComparisonResult as a returning value for comparing. It contains:
    
    *   expected(ex image1)
    
    *   actual(ex image2)
    
    *   ComparisonState, with conditions MATCH, MISMATCH, SIZE_MISMATCH
    
    *   Result image, only if ComparisonState is MISMATCH. When it is MATCH or SIZE_MISMATCH no needs to create result image.
    
*   added minimalRectangleSize and maximalRectangleCount(sorted by rectangle size).

*   Added more tests to cover more test cases.

*   Refactored CommandLineUsage, moved main() method to separated class.

*   Added ExcludedAreas functionality, which helps to exclude some parts of the image.

## 2.2.0
*   Added ability to customize rectangle line width.

*   Moved the main method from Image Comparison to own class.

*   Made non-static threshold field.

*   Added Code of Conduct and Contributing pages.

*   Added Point model.

## 2.1.0
*   Added publishing to JCenter

## 2.0.2
*   fixed bug #11(finally!!)

*   fixed bug #43

## 2.0.1 
*   fixed bug #21

## 2.0
*   use as a library 

## 1.0
*   The program in Java that compares any 2 images and shows the differences visually by drawing rectangles.*

*   Implementation is using only standard core language and platform features, no 3rd party libraries and plagiarized code is permitted.

*   Pixels (with the same coordinates in two images) can be visually similar, but have
different values of RGB. We are only consider 2 pixels to be "different" if the
difference between them is more than 10%.

*   The output of the comparison is a copy of one of the images image with
differences outlined with red rectangles as shown below.

*   No third party libraries and borrowed code are not using.
