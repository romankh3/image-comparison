# Image Comparison
**The program in Java that compares any 2 images and shows the differences visually by drawing rectandles.**

## Requirements
* Implementation is using only standard core language and platform features, no 3rd party libraries and plagiarized code is permitted.
* Pixels (with the same coordinates in two images) can be visually similar, but have
  different values of RGB. We are only consider 2 pixels to be &quot;different&quot; if the
  difference between them is more than 10%.
* The output of the comparison is a copy of one of the images image with
  differences outlined with red rectangles as shown below.
* No third party libraries and borrowed code are not using.

## Getting Started
To run project write: 
```
$ git clone https://github.com/romankh3/image-comparison
$ cd image-comparison
$ ./gradlew run
```

You will get the result of comparing two images.
The images, which are using:

### Image1

![image1](https://user-images.githubusercontent.com/16310793/28955567-52edeabe-78f0-11e7-8bb2-d435c8df23ff.png)

### Image2

![image2](https://user-images.githubusercontent.com/16310793/28955566-52ead892-78f0-11e7-993c-847350da0bf8.png)

Do you see the difference?

### Result

![result](https://user-images.githubusercontent.com/16310793/28955568-52f23e02-78f0-11e7-92c5-07602b6a0887.png)

Also you can get them from:

```
${projectDir}/src/main/resources/image1.png
${projectDir}/src/main/resources/image2.png
```
```
${projectDir}/build/result.png
```

## License:
This project is unlicense - see the [LICENSE](LICENSE) file for details
