
import edu.princeton.cs.algs4.Picture;

import java.awt.Color;



public class SeamCarver {

    private Picture picture;

    private int width;

    private int height;





    public SeamCarver(Picture picture) {

        this.picture = new Picture(picture);

        height = picture.height();

        width = picture.width();



    }



    // current picture

    public Picture picture()  {

        return new Picture(this.picture);

    }



    // width of current picture

    public int width() {

        return width;

    }



    // height of current picture

    public int height() {

        return height;

    }



    private double calculateEnergiesX(int x, int y) {

        int leftX = changeX(x, -1);

        int rightX = changeX(x, 1);



        Color left = picture.get(leftX, y);

        Color right = picture.get(rightX, y);



        double rx = Math.abs(left.getRed() - right.getRed());

        double bx = Math.abs(left.getBlue() - right.getBlue());

        double gx = Math.abs(left.getGreen() - right.getGreen());



        return (rx * rx) + (bx * bx) + (gx * gx);



    }



    private double calculateEnergiesY(int x, int y) {

        int upperY = changeY(y, -1);

        int lowerY = changeY(y, 1);



        Color up = picture.get(x, upperY);

        Color down = picture.get(x, lowerY);



        double ry = Math.abs(up.getRed() - down.getRed());

        double by = Math.abs(up.getBlue() - down.getBlue());

        double gy = Math.abs(up.getGreen() - down.getGreen());



        return (ry * ry) + (by * by) + (gy * gy);

    }







    private int changeX(int x, int diff) {

        if (x + diff == width) {

            return 0;

        } else if (x + diff < 0) {

            return width - 1;

        }



        return x + diff;

    }



    private int changeY(int y, int diff) {

        if (y + diff == height) {

            return 0;

        } else if (y + diff < 0) {

            return height - 1;

        }



        return y + diff;

    }





    // energy of pixel at column x and row y

    public double energy(int x, int y) {

        if (x < 0 || x >= width) {

            throw new java.lang.IndexOutOfBoundsException();

        }



        if (y < 0 || y >= height) {

            throw new java.lang.IndexOutOfBoundsException();

        }



        return calculateEnergiesX(x, y) + calculateEnergiesY(x, y);

    }



    // sequence of indices for horizontal seam

    public int[] findHorizontalSeam() {

        transpose();

        int[] seam = findVerticalSeam();

        transpose();

        return seam;

    }



    private void transpose() {

        Picture temp = new Picture(height, width);

        for (int row = 0; row < height; row++) {

            for (int col = 0; col < width; col++) {

                temp.set(row, col, picture.get(col, row));

            }

        }



        picture = temp;

        int t = width;

        width = height;

        height = t;

    }



    // sequence of indices for vertical seam

    public int[] findVerticalSeam() {

        int[] seam = new int[height];

        double totalEnergy = Double.MAX_VALUE;



        for (int col = 0; col < width; col++) {

            int y = 0;

            int x = col;

            int[] temp = new int[height];

            double tempEnergy = energy(x, y);

            temp[y] = x;

            y++;



            double topE = 0.0, leftE = 0.0, rightE = 0.0;





            while (y < height) {

                int top = x;

                int left = x - 1;

                int right = x + 1;



                topE = energy(top, y);

                if (left >= 0) {

                    leftE = energy(left, y);

                } else {

                    leftE = Double.MAX_VALUE;

                }



                if (right < width) {

                    rightE = energy(right, y);

                } else {

                    rightE = Double.MAX_VALUE;

                }



                if (topE <= leftE && topE <= rightE) {

                    tempEnergy += topE;

                    temp[y] = top;

                    x = top;

                } else if (leftE <= topE && leftE <= rightE) {

                    tempEnergy += leftE;

                    temp[y] = left;

                    x = left;

                } else {

                    tempEnergy += rightE;

                    temp[y] = right;

                    x = right;

                }



                y++;

            }



            if (tempEnergy <= totalEnergy) {

                totalEnergy = tempEnergy;

                seam = temp;

            }

        }



        return seam;

    }



    // remove horizontal seam from picture

    public void removeHorizontalSeam(int[] seam) {

        if (checkSeam(seam)) {

            this.picture = new Picture(SeamRemover.removeHorizontalSeam(this.picture, seam));

            height--;

        } else {

            throw new IllegalArgumentException();

        }

    }



    // remove vertical seam from picture

    public void removeVerticalSeam(int[] seam) {

        if (checkSeam(seam)) {

            this.picture = new Picture(SeamRemover.removeVerticalSeam(this.picture, seam));

            width--;

        } else {

            throw new IllegalArgumentException();

        }

    }



    private boolean checkSeam(int[] seam) {

        for (int i = 0; i < seam.length - 1; i++) {

            if (Math.abs(seam[i] - seam[i + 1]) > 1) {

                return false;

            }

        }



        return true;

    }

}

//import edu.princeton.cs.algs4.Picture;
//import edu.princeton.cs.algs4.StdOut;
//import java.awt.Color;
//
//public class SeamCarver {
//    private Picture pic;
//    private int width;
//    private int height;
//
//    public SeamCarver(Picture picture) {
//        pic = new Picture(picture);
//        width = pic.width();
//        height = pic.height();
//    }
//
//    // current picture
//    public Picture picture() {
//        return new Picture(this.pic);
//    }
//
//    // width of current picture
//    public int width() {
//        return pic.width();
//    }
//
//    // height of current picture
//    public int height() {
//        return pic.height();
//    }
//
//    // energy of pixel at column x and row y
//    public double energy(int x, int y) {
//        if (x < 0 || y < 0 || x > width() - 1 || y > height() - 1) {
//            throw new IndexOutOfBoundsException("the input of column x or row y is out of bound!");
//        }
//        int xLeft = changeX(x, -1);
//        int xRight = changeX(x, 1);
//        int yTop = changeY(y, -1);
//        int yBot = changeY(y, 1);
//
//        Color colorXLeft = pic.get(xLeft, y);
//        Color colorXRight = pic.get(xRight, y);
//        Color colorYTop = pic.get(x, yTop);
//        Color colorYBot = pic.get(x, yBot);
//
//        //compute square of the x-gradient
//        int rX = abs(colorXRight.getRed(), colorXLeft.getRed());
//        int bX = abs(colorXRight.getBlue(), colorXLeft.getBlue());
//        int gX = abs(colorXRight.getGreen(), colorXLeft.getGreen());
//        double deltaX = rX * rX + bX * bX + gX * gX;
//
//        //compute square of the y-gradient
//        int rY = abs(colorYTop.getRed(), colorYBot.getRed());
//        int bY = abs(colorYTop.getBlue(), colorYBot.getBlue());
//        int gY = abs(colorYTop.getGreen(), colorYBot.getGreen());
//        double deltaY = rY * rY + bY * bY + gY * gY;
//
//        double energy = deltaY + deltaX;
//
//        return energy;
//    }
//
//    private int changeX(int x, int diff) {
//        if (x + diff == width) {
//            return 0;
//        } else if (x + diff < 0) {
//            return width - 1;
//        }
//        return x + diff;
//    }
//
//    private int changeY(int y, int diff) {
//        if (y + diff == height) {
//            return 0;
//        } else if (y + diff < 0) {
//            return height - 1;
//        }
//        return y + diff;
//    }
//
//    private int abs (int first, int second) {
//        if (first >= second) {
//            return first - second;
//        }
//        return second - first;
//    }
//
//    // sequence of indices for horizontal seam
//    public int[] findHorizontalSeam() {
//        transpose();
//        int[] seam = findVerticalSeam();
//        transpose();
//        return seam;
//    }
//
//    private void transpose() {
//        Picture temp = new Picture(height, width);
//        for (int r = 0; r < height; r++) {
//            for (int c = 0; c < width; c++) {
//                temp.set(r, c, pic.get(c, r));
//            }
//        }
//
//        pic = temp;
//        int t = width;
//        width = height;
//        height = t;
//    }
//
//
//    // sequence of indices for vertical seam
//    public int[] findVerticalSeam() {
////        int[] seam = new int[height];
////        double totalEnergy = Double.MAX_VALUE;
////
////        for (int col = 0; col < width; col++) {
////            int x = col;
////            int y = 0;
////            int[] tempSeam = new int[height];
////            double tempEnergy = energy(x, y);
////            tempSeam[y] = x;
////
////            y = y + 1;//start from row 1
////
////            double energyL = 0.0, energyM = 0.0, energyR = 0.0;
////            while (y < height) {
////                int left = x - 1;
////                int middle = x;
////                int right = x + 1;
////
////                if (left < 0) {
////                    energyL = Double.MAX_VALUE;
////                } else {
////                    energyL = energy(left, y);
////                }
////
////                if (right > width - 1) {
////                    energyR = Double.MAX_VALUE;
////                } else {
////                    energyR = energy(right, y);
////                }
////                energyM = energy(middle, y);
////                //get the samllest energy and the index of column(left, middle, right) at row y
////                if (energyL <= energyM && energyL <= energyR) {
////                    tempEnergy += energyL;
////                    tempSeam[y] = left;
////                    x = left;
////                } else if (energyR <= energyL && energyR <= energyM) {
////                    tempEnergy += energyR;
////                    tempSeam[y] = right;
////                    x = right;
////                } else {
////                    tempEnergy += energyM;
////                    tempSeam[y] = middle;
////                    x = middle;
////                }
////                y = y + 1;
////            }
////            if (tempEnergy <= totalEnergy) {
////                totalEnergy = tempEnergy;
////                seam = tempSeam;
////            }
////
////        }
////
////        return seam;
//
//        int[] seam = new int[height];
//
//        double totalEnergy = Double.MAX_VALUE;
//
//
//
//        for (int col = 0; col < width; col++) {
//
//            int y = 0;
//
//            int x = col;
//
//            int[] temp = new int[height];
//
//            double tempEnergy = energy(x, y);
//
//            temp[y] = x;
//
//            y++;
//
//
//
//            double topE = 0.0, leftE = 0.0, rightE = 0.0;
//
//
//
//
//
//            while (y < height) {
//
//                int top = x;
//
//                int left = x - 1;
//
//                int right = x + 1;
//
//
//
//                topE = energy(top, y);
//
//                if (left >= 0) {
//
//                    leftE = energy(left, y);
//
//                } else {
//
//                    leftE = Double.MAX_VALUE;
//
//                }
//
//
//
//                if (right < width) {
//
//                    rightE = energy(right, y);
//
//                } else {
//
//                    rightE = Double.MAX_VALUE;
//
//                }
//
//
//
//                if (topE <= leftE && topE <= rightE) {
//
//                    tempEnergy += topE;
//
//                    temp[y] = top;
//
//                    x = top;
//
//                } else if (leftE <= topE && leftE <= rightE) {
//
//                    tempEnergy += leftE;
//
//                    temp[y] = left;
//
//                    x = left;
//
//                } else {
//
//                    tempEnergy += rightE;
//
//                    temp[y] = right;
//
//                    x = right;
//
//                }
//
//
//
//                y++;
//
//            }
//
//
//
//            if (tempEnergy <= totalEnergy) {
//
//                totalEnergy = tempEnergy;
//
//                seam = temp;
//
//            }
//
//        }
//
//
//
//        return seam;
//    }
//
//
//    // remove horizontal seam from picture
//    public void removeHorizontalSeam(int[] seam) {
//        if (seam.length != width) {
//            throw new IllegalArgumentException("the length of the seam is incorrect, should get " + width
//                    + " but got " + seam.length);
//        }
//        if (!checkConsecutive(seam)) {
//            throw new IllegalArgumentException("the the array is not a valid seam " +
//                    " i.e., two consecutive entries differ by more than 1");
//        }
//        pic = SeamRemover.removeHorizontalSeam(pic, seam);
//        width -= 1;
//    }
//
//    private boolean checkConsecutive(int[] seam) {
//        for (int i = 0; i < seam.length - 1; i++) {
//            if (abs(seam[i], seam[i+1]) > 1) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    // remove vertical seam from picture
//    public void removeVerticalSeam(int[] seam) {
//        if (seam.length != height) {
//            throw new IllegalArgumentException("the length of the seam is incorrect, should get " + height
//                    + " but got " + seam.length);
//        }
//        if (!checkConsecutive(seam)) {
//            throw new IllegalArgumentException("the the array is not a valid seam " +
//                    " i.e., two consecutive entries differ by more than 1");
//        }
//        pic = SeamRemover.removeVerticalSeam(pic, seam);
//        height -= 1;
//    }
//}
