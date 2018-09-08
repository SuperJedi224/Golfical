Golfical
---


In Golfical, the 2-dimensional program space is encoded as an image file.

The language uses a bidirectionally unbounded tape, a single register, and a stack to store data as 32-bit signed integers.

The instruction pointer starts at the northwesternmost pixel, going east.

Instructions:

```
00xxxx Set target of pointer to 256g+b
01xxxx Set target of pointer to -(256g+b)
02xxxx Increment target of pointer by 256g+b
03xxxx Decrement target of pointer by 256g+b

04xxxx Increment pointer by 256g+b
05xxxx Decrement pointer by 256g+b

06xxxx Copy target of pointer 256g+b positions to the right
07xxxx Copy target of pointer 256g+b positions to the left
08xxxx Copy target of pointer to all 256g+b next positions
09xxxx Copy target of pointer to all 256g+b previous positions

0A0000 Read a number into the target of the pointer
0A0001 Read a character into the target of the pointer

0A0100 Print the target of the pointer as a number
0A0101 Print the target of the pointer as a character

0A0200 Print the register as a number

0B0000 Go N
0B0001 Go E
0B0002 Go S
0B0003 Go W

0B0100 Turn right
0B0101 Turn left
0B0102 Reverse

0B0200 If the target of the pointer is zero, turn left
0B0201 If the target of the pointer is nonzero, turn left
0B0202 If the target of the pointer is zero, turn right
0B0203 If the target of the pointer is nonzero, turn right

0B0300 If the target of the pointer equals the value in the next cell, turn right
0B0301 If the target of the pointer is less than the value in the next cell, turn right
0B0302 If the target of the pointer is less than or equal to the value in the next cell, turn right

0B0400 If the register is zero, turn left
0B0401 If the register is nonzero, turn left
0B0402 If the register is zero, turn right
0B0403 If the register is nonzero, turn right

0C0000 Push the target of the pointer onto the stack
0C0001 Set the target of the pointer to a value popped from the stack
0C0002 Set the register to a value popped from the stack
0C0003 Without popping, set the target of the pointer to the value at the top of the stack
0C0004 Without popping, set the register to the value at the top of the stack
0C0005 Pop a value from the stack and discard it
0C0006 Duplicate the top value of the stack
0C0007 Reverse the stack
0C0008 Fill from the selected cell rightwards with values popped from the stack, until the stack is empty
0C0009 Set the target of the pointer to the number of elements currently on the stack
0C000A If the stack is empty, turn right

0D0000 Set the target of the pointer to a random value over the entire 32-bit signed integer range
0D0001 Set the target of the pointer to a random value between 0, inclusive, and its previous value, exclusive.
0D0002 Reset the RNG, seeding with the target of the pointer

0E0000 Increment the target of the pointer by the value in the next cell
0E0001 Decrement the target of the pointer by the value in the next cell
0E0002 Multiply the target of the pointer by the value in the next cell
0E0003 Divide the target of the pointer by the value in the next cell, rounding towards zero
0E0004 Find the greatest common denominator of the target of the pointer and the value in the next cell
0E0005 Find the least common multiple of the target of the pointer and the value in the next cell
0E0006 Take the target of the pointer modulo the value in the next cell
0E0100 Take the target of the pointer bitwise or the value in the next cell
0E0101 Take the target of the pointer bitwise and the value in the next cell
0E0102 Take the target of the pointer bitwise xor the value in the next cell
0E0103 Perform a non-wrapping bitwise left shift of the target of the pointer by the value in the next cell
0E0104 Perform a non-wrapping bitwise right shift of the target of the pointer by the value in the next cell
0E0105 Perform a wrapping bitwise left shift of the target of the pointer by the value in the next cell
0E0106 Perform a wrapping bitwise right shift of the target of the pointer by the value in the next cell

0E0200 Multiply the target of the pointer by -1
0E0201 Set the target of the pointer to the bitwise negation of its previous value

0E0300 If the target of the pointer is prime, turn right
0E0301 If the target of the pointer is coprime to the value in the next cell, turn right

0F0000 Splice out the current cell, selecting the previous cell
0F0001 Splice out the current cell, selecting the next cell
0F0002 Splice in a zero cell before the current cell
0F0003 Splice in a zero cell after the current cell

100000 Store the target of the pointer in the register
100001 Exchange the target of the pointer with the register
100002 Push the register onto the stack
100003 Zero the register

FFFFFF Reserved as a no-op
```

---

![](https://i.creativecommons.org/l/by-sa/4.0/88x31.png)

This work is licensed under a [Creative Commons Attribution-ShareAlike 4.0 International License](http://creativecommons.org/licenses/by-sa/4.0/).
