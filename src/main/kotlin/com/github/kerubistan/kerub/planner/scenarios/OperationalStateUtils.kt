package com.github.kerubistan.kerub.planner.scenarios

import com.github.kerubistan.kerub.common.testHost
import com.github.kerubistan.kerub.common.testVm
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


fun OperationalState.withVmsUp(range: IntRange) = this.copy(
		vms = vms.values.sortedBy { it.stat.name }.mapIndexed { idx, item ->
			if (idx in range) item.copy(
					dynamic = VirtualMachineDynamic(
							id = item.id,
							status = VirtualMachineStatus.Up,
							memoryUsed = BigInteger((1024.toLong() * 1024.toLong() * 1024.toLong()).toString()),
							hostId = TODO()
					)
			) else item
		}.associateBy { it.id }
)