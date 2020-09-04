package com.github.appiumtestdistribution.controller;

import com.github.android.AndroidManager;
import com.github.appiumtestdistribution.error.NoDeviceFoundException;
import com.github.appiumtestdistribution.modal.Device;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    }
}