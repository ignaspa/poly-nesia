# POLY-NESIA

### General Summary
This is a project I undertook with Jonah Simpson (jonahs99) to learn
some image manipulation. We had several primary objectives...

1) synthetically blur an image
2) select areas within an image and cut them out
3) transform an image into a more primitive polygon composite, ie poly art

We have successfully implemented such processes.

### Examples : Blur, Select, "Polygonization"

pre-blur:

![blurone](https://raw.githubusercontent.com/ignaspa/poly-nesia/master/example/mountain.jpg)

post-blur:

![blurtwo](https://raw.githubusercontent.com/ignaspa/poly-nesia/master/example/mountain.png)

pre-select:

![selectone](https://raw.githubusercontent.com/ignaspa/poly-nesia/master/example/flower.jpg)

post-select:

![selecttwo](https://raw.githubusercontent.com/ignaspa/poly-nesia/master/example/Screen%20Shot%202018-04-16%20at%209.27.41%20PM.png)

pre-polygons (polygons works best when image is a little blurred before):

![polyone](https://raw.githubusercontent.com/ignaspa/poly-nesia/master/example/parrot.jpg)

![polytwo](https://raw.githubusercontent.com/ignaspa/poly-nesia/master/example/parrot.png)

post-polygons:

![polythree](https://raw.githubusercontent.com/ignaspa/poly-nesia/master/example/Screen%20Shot%202018-04-16%20at%209.20.19%20PM.png)

### How to Use

1) Make sure you have git and java functional on your computer
2) Clone the repository for local use
3) "cd" into the repository in your terminal
4) Move desired image into example folder, and adjust main.java line 24 to match
5) If desired, adjust window.java line 106 to increase or decrease polygon amount.
6) In the terminal "sh scripts/compile.sh"
7) In the terminal "sh scripts/run.sh"
8) +BLUR, -BLUR, POLYGONIZE work as probably inferred. However, to use
    select first click on the image to make whatever shape you desire, then
    click select and it will cut it out. (right click will undo a selected point)
9) To undo polygonize or select simply click +BLUR

### Going Forward / Improvements

We have finished our semester and are busy during the summer, but these are
things we made add or work on for this project in the future...

1) Saving Images in Output.
 (We currently have some code commented out that saves the image as blurred,
  however we should work on making a save button that saves whatever edited
  version of the image that is constructed by the user. This would hopefully
  include that in the case of cutouts the image would be a png that
  would literally be only the cutout and not the bordering black left over from
  the original image.)

2) Gradient Blur.
  (We could see it being that you click a button and then draw a line, and
    the image would increasingly blur along the line from the origin to the
    end, thus creating fake depth.)

3) Affecting the Selected and Non-selected Part Separately.
    (We could try to make it so you could blur just what's inside the
      selected portion, or polygonize it, or just do those things to
      the outside part of the selection. Also an invert selection button.)
