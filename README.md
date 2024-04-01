# simpleBLEJava
Java Native Access Bindings for SimpleBLE

# Requirements
Java Native Access Libraries: jna-platform, jna (https://github.com/java-native-access/jna/tree/master)
SimpleBLE .dylib, .dll or .so files (in /SimpleBLE/build/install/lib/): https://github.com/OpenBluetoothToolbox/SimpleBLE/releases

Tested with:
  jna-platform and jna v5.14.0
  simpleble v0.7.1 (macos-x86_64)

# To run
Change line 16 of SimpleBLE.java file to point to the location of the simpleble-c files

Create a new instance of SimpleBLE in your main Java program:
  SimpleBLE simpleble = new SimpleBLE();

Call functions defined in SimpleBLE.java lines 135 onwards using the simpleble instance:
  - simpleble.getAdapterCount()
  - adapter = simpleble.getAdapterHandle(0)
  - peripheral = simpleble.getPeripheralhandle(adapter, selectedDeviceInt)
