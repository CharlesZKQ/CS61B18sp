import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
import java.awt.Color;

public class SeamCarver {
    private Picture pic;
    private int width;
    private int height;

    public SeamCarver(Picture picture) {
        pic = picture;
        width = pic.width();
        height = pic.height();
    }

    // current picture
    public Picture picture() {
        return pic;
    }

    // width of current picture
    public int width() {
        return pic.width();
    }

    // height of current picture
    public int height() {
        return pic.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || y < 0 || x > width() - 1 || y > height() - 1) {
            throw new IndexOutOfBoundsException("the input of column x or row y is out of bound!");
        }
        Color[] colorNeighbors = colorAtRightAndLeft(x, y);
        Color colorXLeft = colorNeighbors[0];
        Color colorXRight = colorNeighbors[1];
        Color colorYTop = colorNeighbors[2];
        Color colorYBot = colorNeighbors[3];

        //compute square of the x-gradient
        int rX = abs(colorXRight.getRed(), colorXLeft.getRed());
        int bX = abs(colorXRight.getBlue(), colorXLeft.getBlue());
        int gX = abs(colorXRight.getGreen(), colorXLeft.getGreen());
        double deltaX = rX * rX + bX * bX + gX * gX;

        //compute square of the y-gradient
        int rY = abs(colorYTop.getRed(), colorYBot.getRed());
        int bY = abs(colorYTop.getBlue(), colorYBot.getBlue());
        int gY = abs(colorYTop.getGreen(), colorYBot.getGreen());
        double deltaY = rY * rY + bY * bY + gY * gY;

        double energy = deltaY + deltaX;

        return energy;
    }

    private Color[] colorAtRightAndLeft(int x, int y) {
        Color[] colors = new Color[4]; // cash from xLeft, xRight, yTop, yBot
        Color[] colorsY;

        // compute the color neighbor of x coordinate, be careful about the border.
        if (x == width() - 1) {
            colors[0] = pic.get(x - 1, y); // get color of xLeft(same as below)
            colors[1] = pic.get(0, y);// get color of xRight(same as below)
            colorsY = checkY(x, y);
            colors[2] = colorsY[0];// get color of y top(same as below)
            colors[3] = colorsY[1];// get color of y bottom(same as below)
        } else if (x == 0) {
            colors[0] = pic.get(width() - 1, y);
            colors[1] = pic.get(x + 1, y);
            colorsY = checkY(x, y);
            colors[2] = colorsY[0];
            colors[3] = colorsY[1];
        } else {
            colors[0] = pic.get(x - 1, y);
            colors[1] = pic.get(x + 1, y);
            colorsY = checkY(x, y);
            colors[2] = colorsY[0];
            colors[3] = colorsY[1];
        }
        return colors;
    }

    private Color[] checkY (int x, int y) {
        Color[] colorsY = new Color[2];// cash color y from Top to bottom
        if (y == height() - 1) {
            Color colorYBot = pic.get(x, 0);
            Color colorYTop = pic.get(x, y - 1);
            colorsY[0] = colorYTop;
            colorsY[1] = colorYBot;
        } else if (y == 0) {
            Color colorYTop = pic.get(x, height() - 1);
            Color colorYBot = pic.get(x, y + 1);
            colorsY[0] = colorYTop;
            colorsY[1] = colorYBot;
        } else {
            Color colorYTop = pic.get(x, y - 1);
            Color colorYBot = pic.get(x, y + 1);
            colorsY[0] = colorYTop;
            colorsY[1] = colorYBot;
        }
        return colorsY;
    }

    private int abs (int first, int second) {
        if (first >= second) {
            return first - second;
        }
        return second - first;
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
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                temp.set(r, c, pic.get(c, r));
            }
        }

        pic = temp;
        int t = width;
        width = height;
        height = t;
    }


    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int[] seam = new int[height];
        double totalEnergy = Double.MAX_VALUE;
        //compute the
        for (int col = 0; col < width; col++) {
            int x = col;
            int y = 0;
            int[] tempSeam = new int[height];
            double tempEnergy = energy(x, y);
            tempSeam[y] = x;

            y = y + 1;//start from row 1

            double energyL = 0.0, energyM = 0.0, energyR = 0.0;
            while (y < height) {
                int left = x - 1;
                int middle = x;
                int right = x + 1;

                if (left < 0) {
                    energyL = Double.MAX_VALUE;
                } else {
                    energyL = energy(left, y);
                }

                if (right > width - 1) {
                    energyR = Double.MAX_VALUE;
                } else {
                    energyR = energy(right, y);
                }
                energyM = energy(middle, y);
                //get the samllest energy and the index of column(left, middle, right) at row y
                if (energyL <= energyM && energyL <= energyR) {
                    tempEnergy += energyL;
                    tempSeam[y] = left;
                    x = left;
                } else if (energyR <= energyL && energyR <= energyM) {
                    tempEnergy += energyR;
                    tempSeam[y] = right;
                    x = right;
                } else {
                    tempEnergy += energyM;
                    tempSeam[y] = middle;
                    x = middle;
                }
                y = y + 1;
            }
            if (tempEnergy <= totalEnergy) {
                totalEnergy = tempEnergy;
                seam = tempSeam;
            }

        }

        return seam;
    }


    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam.length != width) {
            throw new IllegalArgumentException("the length of the seam is incorrect, should get " + width
                    + " but got " + seam.length);
        }
        if (!checkConsecutive(seam)) {
            throw new IllegalArgumentException("the the array is not a valid seam " +
                    " i.e., two consecutive entries differ by more than 1");
        }
        pic = SeamRemover.removeHorizontalSeam(pic, seam);
    }

    public boolean checkConsecutive(int[] seam) {
        for (int i = 0; i < seam.length; i++) {
            if (abs(seam[i], seam[i+1]) > 1) {
                return false;
            }
        }
        return true;
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        if (seam.length != height) {
            throw new IllegalArgumentException("the length of the seam is incorrect, should get " + height
                    + " but got " + seam.length);
        }
        if (!checkConsecutive(seam)) {
            throw new IllegalArgumentException("the the array is not a valid seam " +
                    " i.e., two consecutive entries differ by more than 1");
        }
        pic = SeamRemover.removeVerticalSeam(pic, seam);
    }
}
