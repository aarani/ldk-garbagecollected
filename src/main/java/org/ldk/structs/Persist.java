package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;
import java.lang.ref.Reference;
import javax.annotation.Nullable;

/**
 * `Persist` defines behavior for persisting channel monitors: this could mean
 * writing once to disk, and/or uploading to one or more backup services.
 * 
 * Persistence can happen in one of two ways - synchronously completing before the trait method
 * calls return or asynchronously in the background.
 * 
 * # For those implementing synchronous persistence
 * 
 * If persistence completes fully (including any relevant `fsync()` calls), the implementation
 * should return [`ChannelMonitorUpdateStatus::Completed`], indicating normal channel operation
 * should continue.
 * 
 * If persistence fails for some reason, implementations should consider returning
 * [`ChannelMonitorUpdateStatus::InProgress`] and retry all pending persistence operations in
 * the background with [`ChainMonitor::list_pending_monitor_updates`] and
 * [`ChainMonitor::get_monitor`].
 * 
 * Once a full [`ChannelMonitor`] has been persisted, all pending updates for that channel can
 * be marked as complete via [`ChainMonitor::channel_monitor_updated`].
 * 
 * If at some point no further progress can be made towards persisting the pending updates, the
 * node should simply shut down.
 * 
 * If the persistence has failed and cannot be retried further (e.g. because of an outage),
 * [`ChannelMonitorUpdateStatus::UnrecoverableError`] can be used, though this will result in
 * an immediate panic and future operations in LDK generally failing.
 * 
 * # For those implementing asynchronous persistence
 * 
 * All calls should generally spawn a background task and immediately return
 * [`ChannelMonitorUpdateStatus::InProgress`]. Once the update completes,
 * [`ChainMonitor::channel_monitor_updated`] should be called with the corresponding
 * [`MonitorUpdateId`].
 * 
 * Note that unlike the direct [`chain::Watch`] interface,
 * [`ChainMonitor::channel_monitor_updated`] must be called once for *each* update which occurs.
 * 
 * If at some point no further progress can be made towards persisting a pending update, the node
 * should simply shut down. Until then, the background task should either loop indefinitely, or
 * persistence should be regularly retried with [`ChainMonitor::list_pending_monitor_updates`]
 * and [`ChainMonitor::get_monitor`] (note that if a full monitor is persisted all pending
 * monitor updates may be marked completed).
 * 
 * # Using remote watchtowers
 * 
 * Watchtowers may be updated as a part of an implementation of this trait, utilizing the async
 * update process described above while the watchtower is being updated. The following methods are
 * provided for bulding transactions for a watchtower:
 * [`ChannelMonitor::initial_counterparty_commitment_tx`],
 * [`ChannelMonitor::counterparty_commitment_txs_from_update`],
 * [`ChannelMonitor::sign_to_local_justice_tx`], [`TrustedCommitmentTransaction::revokeable_output_index`],
 * [`TrustedCommitmentTransaction::build_to_local_justice_tx`].
 * 
 * [`TrustedCommitmentTransaction::revokeable_output_index`]: crate::ln::chan_utils::TrustedCommitmentTransaction::revokeable_output_index
 * [`TrustedCommitmentTransaction::build_to_local_justice_tx`]: crate::ln::chan_utils::TrustedCommitmentTransaction::build_to_local_justice_tx
 */
@SuppressWarnings("unchecked") // We correctly assign various generic arrays
public class Persist extends CommonBase {
	final bindings.LDKPersist bindings_instance;
	Persist(Object _dummy, long ptr) { super(ptr); bindings_instance = null; }
	private Persist(bindings.LDKPersist arg) {
		super(bindings.LDKPersist_new(arg));
		this.ptrs_to.add(arg);
		this.bindings_instance = arg;
	}
	@Override @SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		if (ptr != 0) { bindings.Persist_free(ptr); } super.finalize();
	}
	/**
	 * Destroys the object, freeing associated resources. After this call, any access
	 * to this object may result in a SEGFAULT or worse.
	 *
	 * You should generally NEVER call this method. You should let the garbage collector
	 * do this for you when it finalizes objects. However, it may be useful for types
	 * which represent locks and should be closed immediately to avoid holding locks
	 * until the GC runs.
	 */
	public void destroy() {
		if (ptr != 0) { bindings.Persist_free(ptr); }
		ptr = 0;
	}
	public static interface PersistInterface {
		/**
		 * Persist a new channel's data in response to a [`chain::Watch::watch_channel`] call. This is
		 * called by [`ChannelManager`] for new channels, or may be called directly, e.g. on startup.
		 * 
		 * The data can be stored any way you want, but the identifier provided by LDK is the
		 * channel's outpoint (and it is up to you to maintain a correct mapping between the outpoint
		 * and the stored channel data). Note that you **must** persist every new monitor to disk.
		 * 
		 * The `update_id` is used to identify this call to [`ChainMonitor::channel_monitor_updated`],
		 * if you return [`ChannelMonitorUpdateStatus::InProgress`].
		 * 
		 * See [`Writeable::write`] on [`ChannelMonitor`] for writing out a `ChannelMonitor`
		 * and [`ChannelMonitorUpdateStatus`] for requirements when returning errors.
		 * 
		 * [`ChannelManager`]: crate::ln::channelmanager::ChannelManager
		 * [`Writeable::write`]: crate::util::ser::Writeable::write
		 */
		ChannelMonitorUpdateStatus persist_new_channel(OutPoint channel_id, ChannelMonitor data, MonitorUpdateId update_id);
		/**
		 * Update one channel's data. The provided [`ChannelMonitor`] has already applied the given
		 * update.
		 * 
		 * Note that on every update, you **must** persist either the [`ChannelMonitorUpdate`] or the
		 * updated monitor itself to disk/backups. See the [`Persist`] trait documentation for more
		 * details.
		 * 
		 * During blockchain synchronization operations, and in some rare cases, this may be called with
		 * no [`ChannelMonitorUpdate`], in which case the full [`ChannelMonitor`] needs to be persisted.
		 * Note that after the full [`ChannelMonitor`] is persisted any previous
		 * [`ChannelMonitorUpdate`]s which were persisted should be discarded - they can no longer be
		 * applied to the persisted [`ChannelMonitor`] as they were already applied.
		 * 
		 * If an implementer chooses to persist the updates only, they need to make
		 * sure that all the updates are applied to the `ChannelMonitors` *before
		 * the set of channel monitors is given to the `ChannelManager`
		 * deserialization routine. See [`ChannelMonitor::update_monitor`] for
		 * applying a monitor update to a monitor. If full `ChannelMonitors` are
		 * persisted, then there is no need to persist individual updates.
		 * 
		 * Note that there could be a performance tradeoff between persisting complete
		 * channel monitors on every update vs. persisting only updates and applying
		 * them in batches. The size of each monitor grows `O(number of state updates)`
		 * whereas updates are small and `O(1)`.
		 * 
		 * The `update_id` is used to identify this call to [`ChainMonitor::channel_monitor_updated`],
		 * if you return [`ChannelMonitorUpdateStatus::InProgress`].
		 * 
		 * See [`Writeable::write`] on [`ChannelMonitor`] for writing out a `ChannelMonitor`,
		 * [`Writeable::write`] on [`ChannelMonitorUpdate`] for writing out an update, and
		 * [`ChannelMonitorUpdateStatus`] for requirements when returning errors.
		 * 
		 * [`Writeable::write`]: crate::util::ser::Writeable::write
		 * 
		 * Note that update (or a relevant inner pointer) may be NULL or all-0s to represent None
		 */
		ChannelMonitorUpdateStatus update_persisted_channel(OutPoint channel_id, ChannelMonitorUpdate update, ChannelMonitor data, MonitorUpdateId update_id);
	}
	private static class LDKPersistHolder { Persist held; }
	public static Persist new_impl(PersistInterface arg) {
		final LDKPersistHolder impl_holder = new LDKPersistHolder();
		impl_holder.held = new Persist(new bindings.LDKPersist() {
			@Override public ChannelMonitorUpdateStatus persist_new_channel(long channel_id, long data, long update_id) {
				org.ldk.structs.OutPoint channel_id_hu_conv = null; if (channel_id < 0 || channel_id > 4096) { channel_id_hu_conv = new org.ldk.structs.OutPoint(null, channel_id); }
				if (channel_id_hu_conv != null) { channel_id_hu_conv.ptrs_to.add(this); };
				org.ldk.structs.ChannelMonitor data_hu_conv = null; if (data < 0 || data > 4096) { data_hu_conv = new org.ldk.structs.ChannelMonitor(null, data); }
				org.ldk.structs.MonitorUpdateId update_id_hu_conv = null; if (update_id < 0 || update_id > 4096) { update_id_hu_conv = new org.ldk.structs.MonitorUpdateId(null, update_id); }
				if (update_id_hu_conv != null) { update_id_hu_conv.ptrs_to.add(this); };
				ChannelMonitorUpdateStatus ret = arg.persist_new_channel(channel_id_hu_conv, data_hu_conv, update_id_hu_conv);
				Reference.reachabilityFence(arg);
				return ret;
			}
			@Override public ChannelMonitorUpdateStatus update_persisted_channel(long channel_id, long update, long data, long update_id) {
				org.ldk.structs.OutPoint channel_id_hu_conv = null; if (channel_id < 0 || channel_id > 4096) { channel_id_hu_conv = new org.ldk.structs.OutPoint(null, channel_id); }
				if (channel_id_hu_conv != null) { channel_id_hu_conv.ptrs_to.add(this); };
				org.ldk.structs.ChannelMonitorUpdate update_hu_conv = null; if (update < 0 || update > 4096) { update_hu_conv = new org.ldk.structs.ChannelMonitorUpdate(null, update); }
				if (update_hu_conv != null) { update_hu_conv.ptrs_to.add(this); };
				org.ldk.structs.ChannelMonitor data_hu_conv = null; if (data < 0 || data > 4096) { data_hu_conv = new org.ldk.structs.ChannelMonitor(null, data); }
				org.ldk.structs.MonitorUpdateId update_id_hu_conv = null; if (update_id < 0 || update_id > 4096) { update_id_hu_conv = new org.ldk.structs.MonitorUpdateId(null, update_id); }
				if (update_id_hu_conv != null) { update_id_hu_conv.ptrs_to.add(this); };
				ChannelMonitorUpdateStatus ret = arg.update_persisted_channel(channel_id_hu_conv, update_hu_conv, data_hu_conv, update_id_hu_conv);
				Reference.reachabilityFence(arg);
				return ret;
			}
		});
		return impl_holder.held;
	}
	/**
	 * Persist a new channel's data in response to a [`chain::Watch::watch_channel`] call. This is
	 * called by [`ChannelManager`] for new channels, or may be called directly, e.g. on startup.
	 * 
	 * The data can be stored any way you want, but the identifier provided by LDK is the
	 * channel's outpoint (and it is up to you to maintain a correct mapping between the outpoint
	 * and the stored channel data). Note that you **must** persist every new monitor to disk.
	 * 
	 * The `update_id` is used to identify this call to [`ChainMonitor::channel_monitor_updated`],
	 * if you return [`ChannelMonitorUpdateStatus::InProgress`].
	 * 
	 * See [`Writeable::write`] on [`ChannelMonitor`] for writing out a `ChannelMonitor`
	 * and [`ChannelMonitorUpdateStatus`] for requirements when returning errors.
	 * 
	 * [`ChannelManager`]: crate::ln::channelmanager::ChannelManager
	 * [`Writeable::write`]: crate::util::ser::Writeable::write
	 */
	public ChannelMonitorUpdateStatus persist_new_channel(org.ldk.structs.OutPoint channel_id, org.ldk.structs.ChannelMonitor data, org.ldk.structs.MonitorUpdateId update_id) {
		ChannelMonitorUpdateStatus ret = bindings.Persist_persist_new_channel(this.ptr, channel_id == null ? 0 : channel_id.ptr, data == null ? 0 : data.ptr, update_id == null ? 0 : update_id.ptr);
		Reference.reachabilityFence(this);
		Reference.reachabilityFence(channel_id);
		Reference.reachabilityFence(data);
		Reference.reachabilityFence(update_id);
		if (this != null) { this.ptrs_to.add(channel_id); };
		if (this != null) { this.ptrs_to.add(data); };
		if (this != null) { this.ptrs_to.add(update_id); };
		return ret;
	}

	/**
	 * Update one channel's data. The provided [`ChannelMonitor`] has already applied the given
	 * update.
	 * 
	 * Note that on every update, you **must** persist either the [`ChannelMonitorUpdate`] or the
	 * updated monitor itself to disk/backups. See the [`Persist`] trait documentation for more
	 * details.
	 * 
	 * During blockchain synchronization operations, and in some rare cases, this may be called with
	 * no [`ChannelMonitorUpdate`], in which case the full [`ChannelMonitor`] needs to be persisted.
	 * Note that after the full [`ChannelMonitor`] is persisted any previous
	 * [`ChannelMonitorUpdate`]s which were persisted should be discarded - they can no longer be
	 * applied to the persisted [`ChannelMonitor`] as they were already applied.
	 * 
	 * If an implementer chooses to persist the updates only, they need to make
	 * sure that all the updates are applied to the `ChannelMonitors` *before
	 * the set of channel monitors is given to the `ChannelManager`
	 * deserialization routine. See [`ChannelMonitor::update_monitor`] for
	 * applying a monitor update to a monitor. If full `ChannelMonitors` are
	 * persisted, then there is no need to persist individual updates.
	 * 
	 * Note that there could be a performance tradeoff between persisting complete
	 * channel monitors on every update vs. persisting only updates and applying
	 * them in batches. The size of each monitor grows `O(number of state updates)`
	 * whereas updates are small and `O(1)`.
	 * 
	 * The `update_id` is used to identify this call to [`ChainMonitor::channel_monitor_updated`],
	 * if you return [`ChannelMonitorUpdateStatus::InProgress`].
	 * 
	 * See [`Writeable::write`] on [`ChannelMonitor`] for writing out a `ChannelMonitor`,
	 * [`Writeable::write`] on [`ChannelMonitorUpdate`] for writing out an update, and
	 * [`ChannelMonitorUpdateStatus`] for requirements when returning errors.
	 * 
	 * [`Writeable::write`]: crate::util::ser::Writeable::write
	 * 
	 * Note that update (or a relevant inner pointer) may be NULL or all-0s to represent None
	 */
	public ChannelMonitorUpdateStatus update_persisted_channel(org.ldk.structs.OutPoint channel_id, @Nullable org.ldk.structs.ChannelMonitorUpdate update, org.ldk.structs.ChannelMonitor data, org.ldk.structs.MonitorUpdateId update_id) {
		ChannelMonitorUpdateStatus ret = bindings.Persist_update_persisted_channel(this.ptr, channel_id == null ? 0 : channel_id.ptr, update == null ? 0 : update.ptr, data == null ? 0 : data.ptr, update_id == null ? 0 : update_id.ptr);
		Reference.reachabilityFence(this);
		Reference.reachabilityFence(channel_id);
		Reference.reachabilityFence(update);
		Reference.reachabilityFence(data);
		Reference.reachabilityFence(update_id);
		if (this != null) { this.ptrs_to.add(channel_id); };
		if (this != null) { this.ptrs_to.add(update); };
		if (this != null) { this.ptrs_to.add(data); };
		if (this != null) { this.ptrs_to.add(update_id); };
		return ret;
	}

}
