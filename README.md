# a51-algorithm

### Description

A5/1 is a stream cipher that was used to provide OTA communication privacy in the GSM standard.

It uses three Linear-Feedback Shift Registers (LFSRs) with a secret 64-bit secret key.

| LSFR | Length in Bits |   Tapped Bits   | Clocking Bit |
| ---- | -------------- |   -----------   | ------------ |
| R1   | 19             | 13, 16, 17, 18  | 8            |
| R2   | 22             | 20, 21          | 10           |
| R3   | 23             | 7, 20, 21, 22   | 10           |

## Task
Allow user to initialize the three LFSRs and produce a specific amount of bits for the keystream using the A5/1 Algorithm.
