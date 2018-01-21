package com.github.kerubistan.kerub.common

import com.github.kerubistan.kerub.model.Host
import com.github.kerubistan.kerub.model.VirtualMachine
import com.github.kerubistan.kerub.model.VirtualMachineStatus
import com.github.kerubistan.kerub.model.VirtualStorageDevice
import com.github.kerubistan.kerub.model.config.HostConfiguration
import com.github.kerubistan.kerub.model.dynamic.HostDynamic
import com.github.kerubistan.kerub.model.dynamic.VirtualMachineDynamic
import com.github.kerubistan.kerub.model.dynamic.VirtualStorageDeviceDynamic
import com.github.kerubistan.kerub.planner.OperationalState
import com.github.kerubistan.kerub.utils.toSize
import java.util.UUID


val testVm = VirtualMachine(
		id = UUID.randomUUID(),
		nrOfCpus = 1,
		name = "test-vm"
)

val testDisk = VirtualStorageDevice(
		id = UUID.randomUUID(),
		name = "test-disk",
		size = "100 GB".toSize()
)

val testHost = Host(
		id = UUID.randomUUID(),
		address = "test-host.example.com",
		dedicated = true,
		recycling = false,
		publicKey = ""
)

val blankState = OperationalState.fromLists()

val tinyState = OperationalState.fromLists(
		vms = listOf(testVm),
		vmDyns = listOf(
				VirtualMachineDynamic(id = testVm.id, status = VirtualMachineStatus.Up, memoryUsed = "1 GB".toSize(),
									  hostId = testHost.id)),
		vStorage = listOf(testDisk),
		vStorageDyns = listOf(VirtualStorageDeviceDynamic(id = testDisk.id, allocations = listOf())),
		hosts = listOf(testHost),
		hostDyns = listOf(HostDynamic(id = testHost.id)),
		hostCfgs = listOf(HostConfiguration(id = testHost.id))
)