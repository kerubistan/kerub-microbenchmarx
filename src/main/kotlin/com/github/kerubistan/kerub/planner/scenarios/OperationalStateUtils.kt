package com.github.kerubistan.kerub.planner.scenarios

import com.github.kerubistan.kerub.common.testHost
import com.github.kerubistan.kerub.common.testVm
import com.github.kerubistan.kerub.model.Host
import com.github.kerubistan.kerub.model.VirtualMachine
import com.github.kerubistan.kerub.model.VirtualMachineStatus
import com.github.kerubistan.kerub.model.collection.HostDataCollection
import com.github.kerubistan.kerub.model.collection.VirtualMachineDataCollection
import com.github.kerubistan.kerub.model.config.HostConfiguration
import com.github.kerubistan.kerub.model.dynamic.HostDynamic
import com.github.kerubistan.kerub.model.dynamic.HostStatus
import com.github.kerubistan.kerub.model.dynamic.VirtualMachineDynamic
import com.github.kerubistan.kerub.planner.OperationalState
import java.math.BigInteger
import java.util.UUID

fun OperationalState.withHosts(nr: Int) = this.copy(
		hosts = (1..nr).map { hostNr ->
			HostDataCollection(
					stat = testHost.copy(
							id = UUID.randomUUID(),
							address = "host-$hostNr.example.com"
					)
			)
		}.associateBy { it.id }
)

fun OperationalState.withHostsUp(range: IntRange) = this.copy(
		hosts = this.hosts.values.sortedBy { it.stat.address }.mapIndexed { index, host ->
			if (index in range)
				host.copy(
						dynamic = HostDynamic(
								status = HostStatus.Up,
								id = host.id
						)
				)
			else
				host
		}.associateBy { it.id }
)

fun OperationalState.withHostPubKeys(range: IntRange) = this.copy(
		hosts = this.hosts.values.sortedBy { it.stat.address }.mapIndexed { index, host ->
			if (index in range) {
				host.copy(
						config = (host.config ?: HostConfiguration(id = host.id)).copy(
								publicKey = "host-public-key-${host.stat.address}"
						)
				)
			} else host
		}.associateBy { it.id }
)

fun OperationalState.withHostPubKeysInstalled(fromRange: IntRange, toRange: IntRange) = this.copy(
		hosts = this.hosts.values.sortedBy { it.stat.address }.mapIndexed { index, host ->
			if (index in toRange) {
				host.copy(
						config = (host.config ?: HostConfiguration(id = host.id)).copy(
								acceptedPublicKeys = this.hosts.values.toList().subList(
										fromRange.first,
										fromRange.last).mapNotNull { it.config?.publicKey }
						)
				)
			} else host
		}.associateBy { it.id }
)

fun OperationalState.withWms(nr: Int) = this.copy(
		vms = (1..nr).map { vmNr ->
			VirtualMachineDataCollection(
					stat = testVm.copy(
							name = "vm-$vmNr",
							id = UUID.randomUUID()
					),
					dynamic = null
			)
		}.associateBy { it.id }
)

fun OperationalState.withRunningWm(vm: VirtualMachine) = this.copy(
		vms = (this.vms.values + VirtualMachineDataCollection(
				stat = vm,
				dynamic = VirtualMachineDynamic(
						id = vm.id,
						hostId = this.index.runningHosts.first().id,
						memoryUsed = vm.memory.max,
						status = VirtualMachineStatus.Up
				))
				).associateBy { it.id }
)


fun OperationalState.withVmsUp(
		range: IntRange,
		hosts: List<Host> = this.index.runningHosts.map { it.stat }
): OperationalState = this.copy(
		vms = vms.values.sortedBy { it.stat.name }.mapIndexed { idx, item ->
			if (idx in range) item.copy(
					dynamic = VirtualMachineDynamic(
							id = item.id,
							status = VirtualMachineStatus.Up,
							memoryUsed = BigInteger((1024.toLong() * 1024.toLong() * 1024.toLong()).toString()),
							hostId = hosts[idx % hosts.size].id
					)
			) else item
		}.associateBy { it.id }
)