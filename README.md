# Android - Bluetooth & Wifi Data Collector

This is an Android application that collects Bluetooth and Wifi data. It is intended to be used for research purposes.

The data can be serialized to JSON and sent to a server. (*The server is not included in this repository*)

The server is an Apache Kafka or RedPanda instance. The data is sent to the server using the [Kafka REST Proxy](https://docs.confluent.io/platform/current/kafka-rest/index.html). This data is then consumed and stored in a [Neo4j](https://neo4j.com/) database.

## Classes
* `MainActivity` - The main activity of the application. It contains the UI and the logic for collecting data.
* `BeaconScanner` - A class that scans for Bluetooth and Wifi data. Uses [WifiManager](https://developer.android.com/reference/android/net/wifi/WifiManager) to scan for Wifi data, and [BluetoothAdapter](https://developer.android.com/reference/android/bluetooth/BluetoothAdapter) to scan for Bluetooth data.
* `TaggedBeaconPairCollection` - A class that stores a collection of `TaggedBeaconPair` objects. It also contains methods for serializing the data to JSON.
* `WifiBeacon` / `BluetoothBeacon` - Classes that store the data for a single Wifi or Bluetooth beacon. Parent class is `BeaconBase`.

* [Main Library Source](https://github.com/hwvs/AndroidBluetoothWifiCollector/tree/main/app/src/main/java/net/hunterwatson/wifidatacollector/beacon)