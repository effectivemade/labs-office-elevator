These files are taken from Ubuntu 21.04 aarm64, from the packages:

1. libi2c0; the /usr/lib/aarch64-linux-gnu/libi2c.so.0.1.1 file.
2. libgpiod2; the /usr/lib/aarch64-linux-gnu/libgpiod.so.2.2.2 file.

This is because the ktgpio library needs linker to have access to the gpiod and i2c .so libraries.

If you need to target the arm32 system, delete these files and replace them with 32bit
files downloaded from your Raspberry PI Linux.
