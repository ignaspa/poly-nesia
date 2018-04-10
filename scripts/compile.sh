#!/usr/bin/env bash
javac *.java -d bin -cp lib/DT1.2.jar:.
# compiles, all java files, directory to bin, class path, files to look
# for classes in, colon is a separator, and dot is current directory

# note to self: got delaunay_triangulation from
# https://www.cs.bgu.ac.il/~benmoshe/DT/
