public class AudioCollage {

    // Returns a new array that rescales a[] by a multiplicative factor of alpha.
    public static double[] amplify(double[] a, double alpha) {

        //StdAudio.read("C:\\Users\\hemal\\IdeaProjects\\AudioCollage\\src\\beatbox.wav");

        double c[] = new double[a.length];

        for (int i = 0; i < a.length; i++) {
            c[i] = a[i] * alpha;
            //StdAudio.play(c);
        }
        //System.out.println("returning array");
        return c;
    }

    // Returns a new array that is the reverse of a[].
    public static double[] reverse(double[] a) {

        double[] reverseArray = new double[a.length];
        int j = a.length;
        for (int i = 0; i < a.length; i++) {
            reverseArray[j - 1] = a[i];
            j--;
        }
        //StdAudio.play(reverseArray);
        return reverseArray;
    }

    // Returns a new array that is the concatenation of a[] and b[].
    public static double[] merge(double[] a, double[] b) {

        double[] mergedArray = new double[a.length + b.length];

        for (int i = 0; i < a.length; i++) {
            mergedArray[i] = a[i];
        }

        //System.out.println("i = " + i);

        for (int j = 0; j < b.length; j++) {
            mergedArray[j + a.length] = b[j];
        }

        return mergedArray;
    }

    // Returns a new array that is the sum of a[] and b[],
    // padding the shorter arrays with trailing 0s if necessary.
    public static double[] mix(double[] a, double[] b) {

        int mArrayValue;
        double[] mixedArray;


        if (a.length > b.length) {

            mArrayValue = a.length;
            mixedArray = new double[a.length];

            for (int i = 0; i < b.length; i++) {
                mixedArray[i] = a[i] + b[i];
            }
            for (int j = 0; j < (a.length - b.length); j++) {
                mixedArray[j + b.length] = a[j + b.length];
            }

        } else {

            mArrayValue = b.length;
            mixedArray = new double[b.length];

            for (int i = 0; i < a.length; i++) {
                mixedArray[i] = a[i] + b[i];
            }
            for (int j = 0; j < (b.length - a.length); j++) {
                mixedArray[j + a.length] = b[j + a.length];
            }
        }

        return mixedArray;
    }

        // Returns a new array that changes the speed by the given factor.
    public static double[] changeSpeed(double[] a, double alpha) {

        int length = (int)(a.length * alpha);
        double[] spedUp = new double[length];

        for (int i = 0; i < length; i++) {

            double x = (i / alpha);
            spedUp[i] = a[(int)x];

        }
        return spedUp;
    }

        // Creates an audio collage and plays it on standard audio.
        // See below for the requirements.


    public static void main (String[]args) {

        int SAMPLE_RATE = StdAudio.SAMPLE_RATE;

        String wavFile1 = "C:\\Users\\hemal\\IdeaProjects\\AudioCollage\\src\\beatbox.wav";
        String wavFile2 = "C:\\Users\\hemal\\IdeaProjects\\AudioCollage\\src\\chimes.wav";
        String wavFile3 = "C:\\Users\\hemal\\IdeaProjects\\AudioCollage\\src\\cow.wav";
        String wavFile4 = "C:\\Users\\hemal\\IdeaProjects\\AudioCollage\\src\\harp.wav";
        String wavFile5 = "C:\\Users\\hemal\\IdeaProjects\\AudioCollage\\src\\piano.wav";

        double alpha = 0.5;

        //double values1[] = new double[SAMPLE_RATE + 1];
        double values1[];
        double values2[];
        double values3[];
        double values4[];
        double values5[];

        //System.out.println("SAMPLE_RATE = " + SAMPLE_RATE);
        values1 = StdAudio.read(wavFile1);
        values2 = StdAudio.read(wavFile2);
        values3 = StdAudio.read(wavFile3);
        values4 = StdAudio.read(wavFile4);
        values5 = StdAudio.read(wavFile5);

        /*for (int i = 0; i <= SAMPLE_RATE; i++) {
            //StdAudio.play(0.5 * Math.sin(2*Math.PI * freq * i / StdAudio.SAMPLE_RATE));
            //System.out.println("i = " + i);
            values = StdAudio.read(wavFile1);
        }*/
        //System.out.println("calling play");
        //StdAudio.play(amplify(values, alpha));
        //StdAudio.play(reverse(values1));

        StdAudio.play(changeSpeed(values5, alpha));
        StdAudio.play(mix(values2, values3));
        StdAudio.play(amplify(values1, alpha));
        StdAudio.play(reverse(values2));
        StdAudio.play(merge(values1, values4));
        StdAudio.play(changeSpeed(values3, alpha));
    }
}
