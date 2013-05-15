# OpenCV Image Manipulation

This application is a slightly modified version of the "Image Manipulation" sample that comes with the official OpenCV package. It has been modified to allow Google Glass users to swipe backwards and forwards to change the image manipulation type. This project does not serve any practical need, it is simply a test of OpenCV running on Google Glass. 

![Screenshot](https://raw.github.com/space150/google-glass-playground/master/OpenCVImageManipulation/screenshot.png)

## Setup

1. Install and configure the OpenCV4Android SDK. Thorough instructions can be found in the [OpenCV4Android SDK tutorial](http://docs.opencv.org/doc/tutorials/introduction/android_binary_package/O4A_SDK.html). 
2. Google Glass does not have the play store, so you will need to manually install the OpenCV Manager apk. Google Glass is running <code>armeabi-v7a</code>, so the <code>OpenCV_x.x.x_Manager_x.x_armv7a-neon.apk</code> manager apk is needed.
2. Update the library reference in this project to point to your OpenCV4Android library.
3. Build and run the project.

## Usage

* It is recommended you have some method for starting running the application on Google Glass, such as Mike DiGiovanni's [launchy](https://github.com/kaze0/launchy).
* When the application is launching swipe forward and backward to view the next image manipulation technique.
