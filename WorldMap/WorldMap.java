public class WorldMap {

    public static void main(String[] args) {

        String a[] = new String[10000000];
        int i = 0;
        int width;
        int height;


        while (!StdIn.isEmpty()) {

            a[i] = StdIn.readString();
            i++;
        }

        width = Integer.parseInt(a[0]);
        height = Integer.parseInt(a[1]);

        StdDraw.setCanvasSize(width, height);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);

        //boolean newArea = false;
        int verticesCount;
        //int vertices[] = new int[100000];
        //int count = 0;


        for (int j = 2; j < i; j++) {

            // ".*\\d.*" used to check if element contains numbers
            // ".*[a-z].*" used to check if element contains letters [a-z]

            if (a[j] != null) {

                if ((a[j].matches(".*[a-z].*")) || (!a[j].matches(".*\\d.*")) || (a[j].matches("\\d{5}"))) {

                    //System.out.println(a[j]);
                    verticesCount = Integer.parseInt(a[j + 1]);

                    double x[] = new double[verticesCount];
                    double y[] = new double[verticesCount];
                    int xLength = 0;
                    int yLength = 0;


                    for (int k = 0; k < (verticesCount * 2); k++) {

                        if ((j + k + 2) % 2 == 0) {

                            //System.out.println("x = " + a[j + 2 + k]);

                            x[xLength] = Double.parseDouble(a[j + 2 + k]);
                            xLength++;

                        } else {

                            //System.out.println("y = " + a[j + 2 + k]);

                            y[yLength] = Double.parseDouble(a[j + 2 + k]);
                            yLength++;
                        }

                    }
                    /*for (int q = 0; q < xLength; q++){
                        System.out.println("x[" + q + "] = " + x[q]);
                        System.out.println("y[" + q + "] = " + y[q]);
                    }*/
                    StdDraw.polygon(x, y);
                }
            }
        }
    }
}

