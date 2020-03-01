# atd-dm-api

Appium test distribution device manager as a service

## End Point

`http://<host-name>:8888`

## Supported methods

### GET `/devices`

Will return all the devices connected to the system.

### GET `/devices/{udid}`

Will return the device for the corresponding UDID connected on the system.

### GET `/devices/android`

Will return all Android devices connected to the system.

### GET `/devices/android/{udid}`

Will return Android device for the corresponding UDID connected on the system.

### GET `/devices/android/{udid}/info`

Will return Android device info for the corresponding UDID connected on the system.