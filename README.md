# image-comparison
**The program in Java that compares any 2 images and shows the differences visually.**

* Implementation is using only standard core language and platform features, no 3rd party libraries and plagiarized code is permitted.
* Pixels (with the same coordinates in two images) can be visually similar, but have
  different values of RGB. We are only consider 2 pixels to be &quot;different&quot; if the
  difference between them is more than 10%.
* The output of the comparison is a copy of one of the images image with
  differences outlined with red rectangles as shown below.
* No third party libraries and borrowed code are not using.


To run project write: 
```
$ git clone https://github.com/romankh3/image-comparison
$ cd image-comparison
$ ./gradlew run
```
You will get the result of comparing two images.
The images, which are using in project could find:
```
${projectDir}/src/main/resources/image1.png
${projectDir}/src/main/resources/image2.png
```
And get the result from:
```
${projectDir}/build/result.png
```
**Best regards, Roman.**
