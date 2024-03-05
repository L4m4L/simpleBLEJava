package simpleBLE;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import jnr.ffi.LibraryLoader;
import jnr.ffi.LibraryOption;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;
import jnr.ffi.byref.ByteByReference;
import jnr.ffi.types.u_int8_t;

// From https://github.com/OpenBluetoothToolbox/SimpleBLE
public class simpleBLE {
	
	// Interface to define what functions we would like to use
	public interface libSimpleBLE {
		// from adapter.h
		// https://github.com/OpenBluetoothToolbox/SimpleBLE/blob/v0.7.1/simpleble/include/simpleble_c/adapter.h
		boolean simpleble_adapter_is_bluetooth_enabled();
		int simpleble_adapter_get_count();
		Pointer simpleble_adapter_get_handle(int index);
		void simpleble_adapter_release_handle(Pointer handle);
		String simpleble_adapter_identifier(Pointer handle);
		String simpleble_adapter_address(Pointer handle);
		boolean simpleble_adapter_scan_start(Pointer handle);
		boolean simpleble_adapter_scan_is_active(Pointer handle, boolean active);
		boolean simpleble_adapter_scan_for(Pointer handle, int timeout);
		int simpleble_adapter_scan_get_results_count(Pointer handle);
		Pointer simpleble_adapter_scan_get_results_handle(Pointer handle, int index);
		int simpleble_adapter_get_paired_peripherals_count(Pointer handle);
		Pointer simpleble_adapter_get_paired_peripherals_handle(Pointer handle, int index);
		// not going to set any callbacks at this stage
		
		// from peripheral.h
		// https://github.com/OpenBluetoothToolbox/SimpleBLE/blob/v0.7.1/simpleble/include/simpleble_c/peripheral.h
		void simpleble_peripheral_release_handle(Pointer handle);
		String simpleble_peripheral_identifier(Pointer handle);
		String simpleble_peripheral_address(Pointer handle);
		// 0 = PUBLIC, 1 = RANDOM, 2 = UNSPECIFIED
		int simpleble_peripheral_address_type(Pointer handle);
		int simpleble_peripheral_rssi(Pointer handle);
		int simpleble_peripheral_tx_power(Pointer handle);
		int simpleble_peripheral_mtu(Pointer handle);
		boolean simpleble_peripheral_connect(Pointer handle);
		boolean simpleble_peripheral_disconnect(Pointer handle);
		boolean simpleble_peripheral_is_connected(Pointer handle, boolean connected);
		boolean simpleble_peripheral_is_connectable(Pointer handle, boolean connectable);
		boolean simpleble_peripheral_is_paired(Pointer handle, boolean paired);
		boolean simpleble_peripheral_unpair(Pointer handle);
		int simpleble_peripheral_services_count(Pointer handle);
		boolean simpleble_peripheral_services_get(Pointer handle, int index, Pointer services);
		boolean simpleble_peripheral_write_command(Pointer handle, Pointer service, Pointer characteristic, Pointer data, int length);
		boolean simpleble_peripheral_write_request(Pointer handle, Pointer service, Pointer characteristic, Pointer data, int length);
	}
	
	// Library options
	static Map<LibraryOption, Object> libraryOptions = new HashMap<>();
	// Library
	static libSimpleBLE libsimpleble;
	
	static {
		// Load immediately
		libraryOptions.put(LibraryOption.LoadNow, true);
		// Calls shouldn't save last error number after call
		libraryOptions.put(LibraryOption.IgnoreError, true);
		File lib = new File("share/plugins/" + System.mapLibraryName("simpleble-c"));
		System.out.println("simpleBLE.java: Loading: " + lib.getAbsolutePath());
		String libName = lib.getAbsolutePath();
		
		libsimpleble = LibraryLoader.loadLibrary(
				libSimpleBLE.class, 
				libraryOptions, 
				libName
		);
	}
	
	// defining how to call the functions in the library
	public boolean isBluetoothEnabled() {
		return libsimpleble.simpleble_adapter_is_bluetooth_enabled();
	}

	public int getAdapterCount() {
		return libsimpleble.simpleble_adapter_get_count();
	}
	
	public Pointer getAdapterHandle(int index) {
		return libsimpleble.simpleble_adapter_get_handle(index);
	}
	
	public void releaseAdapterHandle(Pointer adapterHandle) {
		libsimpleble.simpleble_adapter_release_handle(adapterHandle);
	}
	
	public String getAdapterIdentifier(Pointer adapterHandle) {
		return libsimpleble.simpleble_adapter_identifier(adapterHandle);
	}
	
	public String getAdapterAddress(Pointer adapterHandle) {
		return libsimpleble.simpleble_adapter_address(adapterHandle);
	}
	
	public boolean scanStart(Pointer adapterHandle) {
		return libsimpleble.simpleble_adapter_scan_start(adapterHandle);
	}
	
	public boolean scanIsActive(Pointer adapterHandle, boolean active) {
		return libsimpleble.simpleble_adapter_scan_is_active(adapterHandle, active);
	}
	
	public boolean scanFor(Pointer adapterHandle, int timeout) {
		return libsimpleble.simpleble_adapter_scan_for(adapterHandle, timeout);
	}
	
	public int getScanResultsCount(Pointer adapterHandle) {
		return libsimpleble.simpleble_adapter_scan_get_results_count(adapterHandle);
	}
	
	public Pointer getPeripheralHandle(Pointer adapterHandle, int index) {
		return libsimpleble.simpleble_adapter_scan_get_results_handle(adapterHandle, index);
	}
	
	public int getPairedPeripheralsCount(Pointer adapterHandle) {
		return libsimpleble.simpleble_adapter_get_paired_peripherals_count(adapterHandle);
	}
	
	public Pointer getPairedPeripheralsHandle(Pointer adapterHandle, int index) {
		return libsimpleble.simpleble_adapter_get_paired_peripherals_handle(adapterHandle, index);
	}
	
	public void releasePeripheralHandle(Pointer peripheralHandle) {
		libsimpleble.simpleble_peripheral_release_handle(peripheralHandle);
	}
	
	public String getPeripheralIdentifier(Pointer peripheralHandle) {
		return libsimpleble.simpleble_peripheral_identifier(peripheralHandle);
	}
	
	public String getPeripheralAddress(Pointer peripheralHandle) {
		return libsimpleble.simpleble_peripheral_address(peripheralHandle);
	}
	
	public int getPeripheralAddressType(Pointer peripheralHandle) {
		return libsimpleble.simpleble_peripheral_address_type(peripheralHandle);
	}
	
	public int getPeripheralRssi(Pointer peripheralHandle) {
		return libsimpleble.simpleble_peripheral_rssi(peripheralHandle);
	}
	
	public int getPeripheralTxPower(Pointer peripheralHandle) {
		return libsimpleble.simpleble_peripheral_tx_power(peripheralHandle);
	}
	
	public int getPeripheralMtu(Pointer peripheralHandle) {
		return libsimpleble.simpleble_peripheral_mtu(peripheralHandle);
	}
	
	public boolean connectToPeripheral(Pointer peripheralHandle) {
		return libsimpleble.simpleble_peripheral_connect(peripheralHandle);
	}
	
	public boolean disconnectFromPeripheral(Pointer peripheralHandle) {
		return libsimpleble.simpleble_peripheral_disconnect(peripheralHandle);
	}
	
	public boolean isPeripheralConnected(Pointer peripheralHandle) {
		return libsimpleble.simpleble_peripheral_is_connected(peripheralHandle, true);
	}
	
	public boolean isPeripheralConnectable(Pointer peripheralHandle) {
		return libsimpleble.simpleble_peripheral_is_connectable(peripheralHandle, true);
	}
	
	public boolean isPeripheralPaired(Pointer peripheralHandle) {
		return libsimpleble.simpleble_peripheral_is_paired(peripheralHandle, true);
	}
	
	public boolean unpairFromPeripheral(Pointer peripheralHandle) {
		return libsimpleble.simpleble_peripheral_unpair(peripheralHandle);
	}
	
	public int peripheralServicesCount(Pointer peripheralHandle) {
		return libsimpleble.simpleble_peripheral_services_count(peripheralHandle);
	}
	
	public boolean getPeripheralServices(Pointer peripheralHandle, int index, Pointer services) {
		return libsimpleble.simpleble_peripheral_services_get(peripheralHandle, index, services);
	}
	
	public boolean writeCommand(Pointer peripheralHandle, Pointer serviceUuid, Pointer characteristicUuid, Pointer data, int length) {
		return libsimpleble.simpleble_peripheral_write_command(peripheralHandle, serviceUuid, characteristicUuid, data, length);
	}
	
	public boolean writeRequest(Pointer peripheralHandle, Pointer serviceUuid, Pointer characteristicUuid, Pointer data, int length) {
		return libsimpleble.simpleble_peripheral_write_request(peripheralHandle, serviceUuid, characteristicUuid, data, length);
	}
	
	// Structs simpleble_c/types.h
	public static class simpleble_uuid_t extends Struct {
		
		public char[] value = new char[37]; // 36 Char + null terminator
		
		public void set(char[] uuid) {
			if (uuid.length == 37) {
				value = (char[]) uuid;
			}
			else {
				System.out.println("Invalid UUID length");
			}
		}

		public simpleble_uuid_t(Runtime runtime) {
			super(runtime);
		}
	}
	
	public static class simpleble_descriptor_t extends Struct {

		public simpleble_uuid_t uuid;
		
		protected simpleble_descriptor_t() {
			super(Runtime.getRuntime(libsimpleble));
		}	
	}
	
	public static class simpleble_characteristic_t extends Struct {

		public simpleble_uuid_t uuid;
		public Struct.Boolean can_read;
		public Struct.Boolean can_write_request;
		public Struct.Boolean can_write_command;
		public Struct.Boolean can_notify;
		public Struct.Boolean can_indicate;
		public Struct.size_t descriptor_count;
		public simpleble_descriptor_t[] descriptors = new simpleble_descriptor_t[16];	// Descriptor max = 16
		
		public simpleble_characteristic_t(Runtime runtime) {
			super(runtime);
			System.out.println("Created simpleble_characteristic_t");
		}
	}
	
	public static class simpleble_service_t extends Struct {

		public simpleble_uuid_t uuid;
		public Struct.size_t data_length;
		public Struct.u_int8_t[] data = new Struct.u_int8_t[27];	// Manufacturer data 27 bytes
		public Struct.size_t characteristic_count;
		public simpleble_characteristic_t[] characteristics = new simpleble_characteristic_t[16];	// Characteristic max = 16
		
		public simpleble_service_t(Runtime runtime) {
			super(runtime);
			System.out.println("Created simpleble_service_t");
		}
	}
	
	public Runtime libsimplebleRuntime() {
		return Runtime.getRuntime(libsimpleble);
	}
	
	public simpleBLE() {
		System.out.println("simpleBLE jnr-ffi C++ BLE Bridge Init");
	}
}