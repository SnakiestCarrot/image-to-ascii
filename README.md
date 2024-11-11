
<div align="center">

## Java program to convert an image to ASCII text.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

</div>


Inspired by https://github.com/isaksolheim/image-to-ascii

This is a small project that I put together in one evening that takes an image and generates a text file that tries to resemble the input image with ascii characters.

## Usage

1. Navigate to the directory containing this repo.

2. Compile ImageToAscii.java
  
```
javac ImageToAscii.java
```

3a. Run the compiled file

```
java ImageToAscii path\to\your\image.png
```

3b. (optional) Run the compiled file with a downscaling applied, must be an integer number

```
java ImageToAscii path\to\your\image.png (downscaling coefficient)
```

This will downscale the image to half its original size:

```
java ImageToAscii path\to\your\image.png 2
```
## Examples

Will print the help documentation to console:

```
java ImageToAscii help
```

This will create ascii representation of the provided example image "me.png" and output it in "me.txt":
```
java ImageToAscii me.png
```







