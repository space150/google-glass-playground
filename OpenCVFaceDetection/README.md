# OpenCV Face Detection

This application is a slightly modified version of the "Face Detection" sample that comes with the official OpenCV package. This is just another test of the capabilities of OpenCV + Google Glass.

![Screenshot](https://raw.github.com/space150/google-glass-playground/master/OpenCVFaceDetection/screenshot.png)

## Setup

1. Install and configure the OpenCV4Android SDK. Thorough instructions can be found in the [OpenCV4Android SDK tutorial](http://docs.opencv.org/doc/tutorials/introduction/android_binary_package/O4A_SDK.html). 
2. Google Glass does not have the Google Play store installed, so you will need to manually install the OpenCV Manager apk. Google Glass is running <code>armeabi-v7a</code>, so the <code>OpenCV_x.x.x_Manager_x.x_armv7a-neon.apk</code> manager apk is needed.
2. Update the library reference in this project to point to your OpenCV4Android library.
3. Build and run the project.

## Usage

* It is recommended you have some method for starting running the application on Google Glass, such as Mike DiGiovanni's [launchy](https://github.com/kaze0/launchy).
* When the application has launched swipe forward and backward to change the detected face size.
