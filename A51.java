// SJSU | CS 166
// Written by: Kyle Domen
// A5/1 Algorithm
// Based around a combination of linear feedback shift registers (LFSRs)
// This program allows the user to initialize the three LFSRs and produce a
// specific amount of bits for the keystream

import java.util.Scanner;
import java.util.ArrayList;

class A51 {
    public static void main(String[] args) {
        int[] shiftx = new int[19];
        int[] shifty = new int[22];
        int[] shiftz = new int[23];
        int bitlength = 0;
        int[] keystream = new int[bitlength];

        // This is the manually initialize the registers instead of getting input from user
        //                0  1  2  3  4  5  6  7  8  9  x  1  2  3  4  5  6  7  8  9  x  1  2
        //int[] shiftx = {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1};
        //int[] shifty = {1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1};
        //int[] shiftz = {1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0};

        /*=====[User initialize the x, y, z registers and how many bits in the keystream]=====*/
        initialize_registers(shiftx, shifty, shiftz);
        bitlength = initialize_bitlength();
        //============================================================

        // A5/1 Algorithm
        keystream = calculate_keystream(bitlength, shiftx, shifty, shiftz);
        print_keystream(keystream);
    }

    // Global function to calculate majority bit, then step necessary registers, and then xor end bits
    public static int[] calculate_keystream(int bl, int[] rx, int[] ry, int[] rz) {
        int maj = 0;
        int[] ks = new int[bl];

        for (int i = 0; i < bl; i++) {
            maj = bit_majority(rx, ry, rz);
            step_registers(rx, ry, rz, maj);
            ks[i] = xor_registers(rx, ry, rz);

            print_registers(rx, ry, rz);
        }


        return ks;
    }

    // Return the bit majority between shiftx[8], shifty[10], and shiftz[10]
    // This will determine which registers to step (the ones with the calculated majority bit)
    public static int bit_majority(int[] rx, int[] ry, int[] rz) {
        int bitsum = rx[8] + ry[10] + rz[10];

        if (bitsum == 0 || bitsum == 1)
            return 0;

        return 1;
    }

    // Step the registers that contain the majority bit
    // XOR the tapped bits (special positions in the register array)
    // Shift the entire array to the right
    // And then put the XOR'ed value in the beginning of the array
    public static void step_registers(int[] rx, int[] ry, int[] rz, int mb) {
        int temp = 0;

        if (rx[8] == mb) {
            temp = rx[13] ^ rx[16] ^ rx[17] ^ rx[18];
            System.arraycopy(rx, 0, rx, 1, rx.length - 1);
            rx[0] = temp;
        }

        if (ry[10] == mb) {
            temp = ry[20] ^ ry[21];
            System.arraycopy(ry, 0, ry, 1, ry.length - 1);
            ry[0] = temp;
        }

        if (rz[10] == mb) {
            temp = rz[7] ^ rz[20] ^ rz[21] ^ rz[22];
            System.arraycopy(rz, 0, rz, 1, rz.length - 1);
            rz[0] = temp;
        }
    }

    // Return the keystream bit
    // XOR the ending bits in all three registers
    // This value is the next bit in the keystream
    public static int xor_registers(int[] rx, int[] ry, int[] rz) {
        return rx[rx.length - 1] ^ ry[ry.length - 1] ^ rz[rz.length - 1];
    }

    // Ask the user to initialize the bits in the three registers
    // Turn string value from user into integer values in their respective arrays
    public static void initialize_registers(int[] x, int[] y, int[] z) {
        Scanner s = new Scanner(System.in);

        System.out.println("> Type in initial 19-bit values of Register X.");
        String tempx = s.nextLine();
        System.out.println("> Type in initial 22-bit values of Register Y.");
        String tempy = s.nextLine();
        System.out.println("> Type in initial 23-bit values of Register Z.");
        String tempz = s.nextLine();

        for (int i = 0; i < 19; i++)
            x[i] = Integer.parseInt(Character.toString(tempx.charAt(i)));

        for (int i = 0; i < 22; i++)
            y[i] = Integer.parseInt(Character.toString(tempy.charAt(i)));

        for (int i = 0; i < 23; i++)
            z[i] = Integer.parseInt(Character.toString(tempz.charAt(i)));

    }

    // Ask the user how many bits they want to calculate for their keystream
     public static int initialize_bitlength() {
        int bl = 0;
        Scanner s = new Scanner(System.in);

        System.out.println("> How many bits of the keystream are desired?");
        bl = s.nextInt();

        return bl;
    }

    // Print the contents of shift[x], shift[y], shift[z]
    public static void print_registers(int[] x, int[] y, int[] z) {
        System.out.print("\n" + "shiftx: ");

        for (int i = 0; i < 19; i++)
            System.out.print(x[i]);

        System.out.print("\n" + "shifty: ");

        for (int i = 0; i < 22; i++)
            System.out.print(y[i]);

        System.out.print("\n" + "shiftz: ");

        for (int i = 0; i < 23; i++)
            System.out.print(z[i]);

        System.out.println();
    }

    // Print all the calculated bits in the keystream
    public static void print_keystream(int[] ks) {
        System.out.print("\n" + "keystream: ");
        for (int i = 0; i < ks.length; i++)
            System.out.print(ks[i]);
        System.out.println();
    }
}
