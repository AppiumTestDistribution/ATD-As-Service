package com.github.appiumtestdistribution.controller;

import com.github.android.AndroidManager;
import com.github.appiumtestdistribution.error.NoDeviceFoundException;
import com.github.appiumtestdistribution.modal.Device;
import com.github.appiumtestdistribution.modal.DeviceInfo;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AndroidDeviceManagerControllerTest {
    AndroidManager androidManager = mock(AndroidManager.class);

    @BeforeEach
    void setUp() {
        Mockito.reset(androidManager);
    }

    @Nested
    class UnitTests {
        @Nested
        class getAndroidDevice {
            @SneakyThrows
            @Test
            void get_device_from_manager() {
                doReturn(DeviceBuilder.builder().build()).when(androidManager).getDevice(eq("test-udid"));
                AndroidDeviceManagerController target = new AndroidDeviceManagerController(androidManager);

                target.getAndroidDevice("test-udid");

                verify(androidManager, times(1)).getDevice("test-udid");
            }

            @SneakyThrows
            @Test
            void return_device() {
                doReturn(DeviceBuilder.builder()
                        .withApiLevel("TEST-api-level")
                        .withBrand("TEST-brand")
                        .withAvailable(true)
                        .withCloud(true)
                        .withDevice(true)
                        .withDeviceManufacturer("TEST-device-manufacturer")
                        .withDeviceModel("TEST-device-model")
                        .withDeviceType("TEST-device-type")
                        .withName("TEST-name")
                        .withOs("TEST-os")
                        .withOsVersion("TEST-os-version")
                        .withScreenSize("TEST-screen-size")
                        .withState("TEST-state")
                        .withUdid("TEST-udid")
                        .build()
                ).when(androidManager).getDevice(eq("test-udid"));
                AndroidDeviceManagerController target = new AndroidDeviceManagerController(androidManager);

                Device actual = target.getAndroidDevice("test-udid");

                assertEquals("TEST-api-level", actual.getApiLevel());
                assertEquals("TEST-api-level", actual.getApiLevel());
                assertEquals("TEST-brand", actual.getBrand());
                assertTrue(actual.isAvailable());
                assertTrue(actual.isCloud());
                assertTrue(actual.isDevice());
                assertEquals("TEST-device-manufacturer", actual.getDeviceManufacturer());
                assertEquals("TEST-device-model", actual.getDeviceModel());
                assertEquals("TEST-device-type", actual.getDeviceType());
                assertEquals("TEST-name", actual.getName());
                assertEquals("TEST-os", actual.getOs());
                assertEquals("TEST-os-version", actual.getOsVersion());
                assertEquals("TEST-screen-size", actual.getScreenSize());
                assertEquals("TEST-state", actual.getState());
                assertEquals("TEST-udid", actual.getUdid());
            }

            @SneakyThrows
            @Test
            void throw_exception_if_runtime_exception_occurred() {
                doThrow(new RuntimeException()).when(androidManager).getDevice("test-udid");
                AndroidDeviceManagerController target = new AndroidDeviceManagerController(androidManager);

                NoDeviceFoundException exception = assertThrows(NoDeviceFoundException.class, () -> target.getAndroidDevice("test-udid"));
                assertEquals(exception.getMessage(), "No device with ID [test-udid] found on this machine.");
            }
        }

        @Nested
        class getAndroidDevices {
            @SneakyThrows
            @Test
            void get_devices_from_manager() {
                doReturn(singletonList(DeviceBuilder.builder().build())).when(androidManager).getDevices();
                AndroidDeviceManagerController target = new AndroidDeviceManagerController(androidManager);

                target.getAndroidDevices();

                verify(androidManager, times(1)).getDevices();
            }

            @SneakyThrows
            @Test
            void return_devices() {
                doReturn(singletonList(DeviceBuilder.builder()
                        .withApiLevel("TEST-api-level")
                        .withBrand("TEST-brand")
                        .withAvailable(true)
                        .withCloud(true)
                        .withDevice(true)
                        .withDeviceManufacturer("TEST-device-manufacturer")
                        .withDeviceModel("TEST-device-model")
                        .withDeviceType("TEST-device-type")
                        .withName("TEST-name")
                        .withOs("TEST-os")
                        .withOsVersion("TEST-os-version")
                        .withScreenSize("TEST-screen-size")
                        .withState("TEST-state")
                        .withUdid("TEST-udid")
                        .build())
                ).when(androidManager).getDevices();
                AndroidDeviceManagerController target = new AndroidDeviceManagerController(androidManager);

                List<Device> actual = target.getAndroidDevices().getDevices();

                assertEquals(1, actual.size());
                assertEquals("TEST-api-level", actual.get(0).getApiLevel());
                assertEquals("TEST-api-level", actual.get(0).getApiLevel());
                assertEquals("TEST-brand", actual.get(0).getBrand());
                assertTrue(actual.get(0).isAvailable());
                assertTrue(actual.get(0).isCloud());
                assertTrue(actual.get(0).isDevice());
                assertEquals("TEST-device-manufacturer", actual.get(0).getDeviceManufacturer());
                assertEquals("TEST-device-model", actual.get(0).getDeviceModel());
                assertEquals("TEST-device-type", actual.get(0).getDeviceType());
                assertEquals("TEST-name", actual.get(0).getName());
                assertEquals("TEST-os", actual.get(0).getOs());
                assertEquals("TEST-os-version", actual.get(0).getOsVersion());
                assertEquals("TEST-screen-size", actual.get(0).getScreenSize());
                assertEquals("TEST-state", actual.get(0).getState());
                assertEquals("TEST-udid", actual.get(0).getUdid());
            }

            @SneakyThrows
            @Test
            void throw_exception_if_getDevices_return_empty() {
                doReturn(emptyList()).when(androidManager).getDevices();
                AndroidDeviceManagerController target = new AndroidDeviceManagerController(androidManager);

                NoDeviceFoundException exception = assertThrows(NoDeviceFoundException.class, target::getAndroidDevices);
                assertEquals(exception.getMessage(), "No devices found on this machine.");
            }
        }

        @Nested
        class getDeviceInfo {
            @SneakyThrows
            @Test
            void get_device_info_from_manager() {
                doReturn(new JSONObject(Map.ofEntries(
                        entry("apiLevel", "TEST-api-level"),
                        entry("screenSize", "TEST-screen-size"),
                        entry("osVersion", "TEST-os-version"),
                        entry("os", "TEST-os"),
                        entry("name", "TEST-name"),
                        entry("isDevice", true),
                        entry("deviceModel", "TEST-device-model"),
                        entry("udid", "TEST-udid"),
                        entry("deviceManufacturer", "TEST-device-manufacturer"),
                        entry("brand", "TEST-brand")
                ))).when(androidManager).getDeviceInfo(eq("test-udid"));
                AndroidDeviceManagerController target = new AndroidDeviceManagerController(androidManager);

                target.getDeviceInfo("test-udid");

                verify(androidManager, times(1)).getDeviceInfo("test-udid");
            }

            @SneakyThrows
            @Test
            void return_device_info() {
                doReturn(new JSONObject(Map.ofEntries(
                        entry("apiLevel", "TEST-api-level"),
                        entry("screenSize", "TEST-screen-size"),
                        entry("osVersion", "TEST-os-version"),
                        entry("os", "TEST-os"),
                        entry("name", "TEST-name"),
                        entry("isDevice", true),
                        entry("deviceModel", "TEST-device-model"),
                        entry("udid", "TEST-udid"),
                        entry("deviceManufacturer", "TEST-device-manufacturer"),
                        entry("brand", "TEST-brand")
                ))).when(androidManager).getDeviceInfo(eq("test-udid"));
                AndroidDeviceManagerController target = new AndroidDeviceManagerController(androidManager);

                DeviceInfo actual = target.getDeviceInfo("test-udid");

                assertEquals("TEST-api-level", actual.getApiLevel());
                assertEquals("TEST-api-level", actual.getApiLevel());
                assertEquals("TEST-brand", actual.getBrand());
                assertTrue(actual.isDevice());
                assertEquals("TEST-device-manufacturer", actual.getDeviceManufacturer());
                assertEquals("TEST-device-model", actual.getDeviceModel());
                assertEquals("TEST-name", actual.getName());
                assertEquals("TEST-os", actual.getOs());
                assertEquals("TEST-os-version", actual.getOsVersion());
                assertEquals("TEST-screen-size", actual.getScreenSize());
                assertEquals("TEST-udid", actual.getUdid());
            }

            @SneakyThrows
            @Test
            void throw_exception_if_array_index_out_of_bounds_exception_occurred() {
                doThrow(new ArrayIndexOutOfBoundsException()).when(androidManager).getDeviceInfo("test-udid");
                AndroidDeviceManagerController target = new AndroidDeviceManagerController(androidManager);

                NoDeviceFoundException exception = assertThrows(NoDeviceFoundException.class, () -> target.getDeviceInfo("test-udid"));
                assertEquals(exception.getMessage(), "No device with ID [test-udid] found on this machine.");
            }
        }

        @Nested
        class startADBLog {
            @SneakyThrows
            @Test
            void call_startADBLog() {
                AndroidDeviceManagerController target = new AndroidDeviceManagerController(androidManager);

                target.startADBLog("test-udid", "test-file-path");

                verify(androidManager, times(1)).startADBLog("test-udid", "test-file-path");
            }

            @SneakyThrows
            @Test
            void return_start_message() {
                doReturn("test start message").when(androidManager).startADBLog(eq("test-udid"), eq("test-file-path"));
                AndroidDeviceManagerController target = new AndroidDeviceManagerController(androidManager);

                String actual = target.startADBLog("test-udid", "test-file-path");

                assertEquals("test start message", actual);
            }

            @SneakyThrows
            @Test
            void throw_exception_if_exception_occurred() {
                doThrow(new Exception()).when(androidManager).startADBLog(eq("test-udid"), eq("test-file-path"));
                AndroidDeviceManagerController target = new AndroidDeviceManagerController(androidManager);

                assertThrows(Exception.class, () -> target.startADBLog("test-udid", "test-file-path"));
            }
        }

        @Nested
        class stopADBLog {
            @SneakyThrows
            @Test
            void call_stopADBLog() {
                AndroidDeviceManagerController target = new AndroidDeviceManagerController(androidManager);

                target.stopADBLog("test-udid");

                verify(androidManager, times(1)).stopADBLog("test-udid");
            }

            @SneakyThrows
            @Test
            void return_stop_message() {
                doReturn("test stop message").when(androidManager).stopADBLog(eq("test-udid"));
                AndroidDeviceManagerController target = new AndroidDeviceManagerController(androidManager);

                String actual = target.stopADBLog("test-udid");

                assertEquals("test stop message", actual);
            }

            @SneakyThrows
            @Test
            void throw_exception_if_exception_occurred() {
                doThrow(new Exception()).when(androidManager).stopADBLog(eq("test-udid"));
                AndroidDeviceManagerController target = new AndroidDeviceManagerController(androidManager);

                assertThrows(Exception.class, () -> target.stopADBLog("test-udid"));
            }
        }
    }

    @Nested
    class WebTests {
        @Nested
        class getAndroidDevice {
            @SneakyThrows
            @Test
            void return_device_json_if_found() {
                com.github.device.Device device = DeviceBuilder.builder()
                        .withApiLevel("TEST-api-level")
                        .withBrand("TEST-brand")
                        .withAvailable(true)
                        .withCloud(true)
                        .withDevice(true)
                        .withDeviceManufacturer("TEST-device-manufacturer")
                        .withDeviceModel("TEST-device-model")
                        .withDeviceType("TEST-device-type")
                        .withName("TEST-name")
                        .withOs("TEST-os")
                        .withOsVersion("TEST-os-version")
                        .withScreenSize("TEST-screen-size")
                        .withState("TEST-state")
                        .withUdid("TEST-udid")
                        .build();
                doReturn(device).when(androidManager).getDevice(eq("test-udid"));
                AndroidDeviceManagerController target = new AndroidDeviceManagerController(androidManager);

                MockMvc mockMvc = MockMvcBuilders
                        .standaloneSetup(target)
                        .build();

                mockMvc
                        .perform(MockMvcRequestBuilders.get("/devices/android/test-udid"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.apiLevel").value("TEST-api-level"))
                        .andExpect(jsonPath("$.brand").value("TEST-brand"))
                        .andExpect(jsonPath("$.available").value(true))
                        .andExpect(jsonPath("$.cloud").value(true))
                        .andExpect(jsonPath("$.device").value(true))
                        .andExpect(jsonPath("$.deviceManufacturer").value("TEST-device-manufacturer"))
                        .andExpect(jsonPath("$.deviceModel").value("TEST-device-model"))
                        .andExpect(jsonPath("$.deviceType").value("TEST-device-type"))
                        .andExpect(jsonPath("$.name").value("TEST-name"))
                        .andExpect(jsonPath("$.os").value("TEST-os"))
                        .andExpect(jsonPath("$.osVersion").value("TEST-os-version"))
                        .andExpect(jsonPath("$.screenSize").value("TEST-screen-size"))
                        .andExpect(jsonPath("$.state").value("TEST-state"))
                        .andExpect(jsonPath("$.udid").value("TEST-udid"))
                        .andExpect(jsonPath("$.id").isNumber())
                ;
            }

        }

        @Nested
        class getAndroidDevices {
            @SneakyThrows
            @Test
            void return_devices_json_if_found() {
                com.github.device.Device device = DeviceBuilder.builder()
                        .withApiLevel("TEST-api-level")
                        .withBrand("TEST-brand")
                        .withAvailable(true)
                        .withCloud(true)
                        .withDevice(true)
                        .withDeviceManufacturer("TEST-device-manufacturer")
                        .withDeviceModel("TEST-device-model")
                        .withDeviceType("TEST-device-type")
                        .withName("TEST-name")
                        .withOs("TEST-os")
                        .withOsVersion("TEST-os-version")
                        .withScreenSize("TEST-screen-size")
                        .withState("TEST-state")
                        .withUdid("TEST-udid")
                        .build();
                doReturn(singletonList(device)).when(androidManager).getDevices();
                AndroidDeviceManagerController target = new AndroidDeviceManagerController(androidManager);

                MockMvc mockMvc = MockMvcBuilders
                        .standaloneSetup(target)
                        .build();

                mockMvc
                        .perform(MockMvcRequestBuilders.get("/devices/android"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.devices.[0].apiLevel").value("TEST-api-level"))
                        .andExpect(jsonPath("$.devices.[0].brand").value("TEST-brand"))
                        .andExpect(jsonPath("$.devices.[0].available").value(true))
                        .andExpect(jsonPath("$.devices.[0].cloud").value(true))
                        .andExpect(jsonPath("$.devices.[0].device").value(true))
                        .andExpect(jsonPath("$.devices.[0].deviceManufacturer").value("TEST-device-manufacturer"))
                        .andExpect(jsonPath("$.devices.[0].deviceModel").value("TEST-device-model"))
                        .andExpect(jsonPath("$.devices.[0].deviceType").value("TEST-device-type"))
                        .andExpect(jsonPath("$.devices.[0].name").value("TEST-name"))
                        .andExpect(jsonPath("$.devices.[0].os").value("TEST-os"))
                        .andExpect(jsonPath("$.devices.[0].osVersion").value("TEST-os-version"))
                        .andExpect(jsonPath("$.devices.[0].screenSize").value("TEST-screen-size"))
                        .andExpect(jsonPath("$.devices.[0].state").value("TEST-state"))
                        .andExpect(jsonPath("$.devices.[0].udid").value("TEST-udid"))
                        .andExpect(jsonPath("$.devices.[0].id").isNumber())
                ;
            }
        }

        @Nested
        class getDeviceInfo {
            @SneakyThrows
            @Test
            void return_device_info_json_if_found() {
                doReturn(new JSONObject(Map.ofEntries(
                        entry("apiLevel", "TEST-api-level"),
                        entry("screenSize", "TEST-screen-size"),
                        entry("osVersion", "TEST-os-version"),
                        entry("os", "TEST-os"),
                        entry("name", "TEST-name"),
                        entry("isDevice", true),
                        entry("deviceModel", "TEST-device-model"),
                        entry("udid", "TEST-udid"),
                        entry("deviceManufacturer", "TEST-device-manufacturer"),
                        entry("brand", "TEST-brand")
                ))).when(androidManager).getDeviceInfo("test-udid");
                AndroidDeviceManagerController target = new AndroidDeviceManagerController(androidManager);

                MockMvc mockMvc = MockMvcBuilders
                        .standaloneSetup(target)
                        .build();

                mockMvc
                        .perform(MockMvcRequestBuilders.get("/devices/android/test-udid/info"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.apiLevel").value("TEST-api-level"))
                        .andExpect(jsonPath("$.brand").value("TEST-brand"))
                        .andExpect(jsonPath("$.device").value(true))
                        .andExpect(jsonPath("$.deviceManufacturer").value("TEST-device-manufacturer"))
                        .andExpect(jsonPath("$.deviceModel").value("TEST-device-model"))
                        .andExpect(jsonPath("$.name").value("TEST-name"))
                        .andExpect(jsonPath("$.os").value("TEST-os"))
                        .andExpect(jsonPath("$.osVersion").value("TEST-os-version"))
                        .andExpect(jsonPath("$.screenSize").value("TEST-screen-size"))
                        .andExpect(jsonPath("$.udid").value("TEST-udid"))
                ;
            }
        }

        @Nested
        class startADBLog {
            @SneakyThrows
            @Test
            void return_start_message() {
                doReturn("test start message").when(androidManager).startADBLog(eq("test-udid"), eq("test-file-path"));
                AndroidDeviceManagerController target = new AndroidDeviceManagerController(androidManager);

                MockMvc mockMvc = MockMvcBuilders
                        .standaloneSetup(target)
                        .build();

                mockMvc
                        .perform(MockMvcRequestBuilders.get("/devices/adblog/start?uuid=test-udid&filePath=test-file-path"))
                        .andExpect(status().isOk())
                        .andExpect(content().string("test start message"))
                ;
            }
        }

        @Nested
        class stopADBLog {
            @SneakyThrows
            @Test
            void return_stop_message() {
                doReturn("test stop message").when(androidManager).stopADBLog(eq("test-udid"));
                AndroidDeviceManagerController target = new AndroidDeviceManagerController(androidManager);

                MockMvc mockMvc = MockMvcBuilders
                        .standaloneSetup(target)
                        .build();

                mockMvc
                        .perform(MockMvcRequestBuilders.get("/devices/adblog/stop?uuid=test-udid"))
                        .andExpect(status().isOk())
                        .andExpect(content().string("test stop message"))
                ;
            }
        }
    }
}