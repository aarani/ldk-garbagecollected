package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;
import java.lang.ref.Reference;
import javax.annotation.Nullable;


/**
 * A [`Router`] implemented using [`find_route`].
 */
@SuppressWarnings("unchecked") // We correctly assign various generic arrays
public class DefaultRouter extends CommonBase {
	DefaultRouter(Object _dummy, long ptr) { super(ptr); }
	@Override @SuppressWarnings("deprecation")
	protected void finalize() throws Throwable {
		super.finalize();
		if (ptr != 0) { bindings.DefaultRouter_free(ptr); }
	}

	/**
	 * Creates a new router.
	 */
	public static DefaultRouter of(org.ldk.structs.NetworkGraph network_graph, org.ldk.structs.Logger logger, org.ldk.structs.EntropySource entropy_source, org.ldk.structs.LockableScore scorer, org.ldk.structs.ProbabilisticScoringFeeParameters score_params) {
		long ret = bindings.DefaultRouter_new(network_graph == null ? 0 : network_graph.ptr, logger.ptr, entropy_source.ptr, scorer.ptr, score_params == null ? 0 : score_params.ptr);
		Reference.reachabilityFence(network_graph);
		Reference.reachabilityFence(logger);
		Reference.reachabilityFence(entropy_source);
		Reference.reachabilityFence(scorer);
		Reference.reachabilityFence(score_params);
		if (ret >= 0 && ret <= 4096) { return null; }
		org.ldk.structs.DefaultRouter ret_hu_conv = null; if (ret < 0 || ret > 4096) { ret_hu_conv = new org.ldk.structs.DefaultRouter(null, ret); }
		if (ret_hu_conv != null) { ret_hu_conv.ptrs_to.add(ret_hu_conv); };
		if (ret_hu_conv != null) { ret_hu_conv.ptrs_to.add(network_graph); };
		if (ret_hu_conv != null) { ret_hu_conv.ptrs_to.add(logger); };
		if (ret_hu_conv != null) { ret_hu_conv.ptrs_to.add(entropy_source); };
		if (ret_hu_conv != null) { ret_hu_conv.ptrs_to.add(scorer); };
		if (ret_hu_conv != null) { ret_hu_conv.ptrs_to.add(score_params); };
		return ret_hu_conv;
	}

	/**
	 * Constructs a new Router which calls the relevant methods on this_arg.
	 * This copies the `inner` pointer in this_arg and thus the returned Router must be freed before this_arg is
	 */
	public Router as_Router() {
		long ret = bindings.DefaultRouter_as_Router(this.ptr);
		Reference.reachabilityFence(this);
		if (ret >= 0 && ret <= 4096) { return null; }
		Router ret_hu_conv = new Router(null, ret);
		if (ret_hu_conv != null) { ret_hu_conv.ptrs_to.add(this); };
		return ret_hu_conv;
	}

	/**
	 * Constructs a new MessageRouter which calls the relevant methods on this_arg.
	 * This copies the `inner` pointer in this_arg and thus the returned MessageRouter must be freed before this_arg is
	 */
	public MessageRouter as_MessageRouter() {
		long ret = bindings.DefaultRouter_as_MessageRouter(this.ptr);
		Reference.reachabilityFence(this);
		if (ret >= 0 && ret <= 4096) { return null; }
		MessageRouter ret_hu_conv = new MessageRouter(null, ret);
		if (ret_hu_conv != null) { ret_hu_conv.ptrs_to.add(this); };
		return ret_hu_conv;
	}

}
