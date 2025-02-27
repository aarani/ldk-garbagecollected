package org.ldk.structs;

import org.ldk.impl.bindings;
import org.ldk.enums.*;
import org.ldk.util.*;
import java.util.Arrays;
import java.lang.ref.Reference;
import javax.annotation.Nullable;

public class Result_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ extends CommonBase {
	private Result_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ(Object _dummy, long ptr) { super(ptr); }
	protected void finalize() throws Throwable {
		if (ptr != 0) { bindings.CResult_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ_free(ptr); } super.finalize();
	}

	static Result_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ constr_from_ptr(long ptr) {
		if (bindings.CResult_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ_is_ok(ptr)) {
			return new Result_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ_OK(null, ptr);
		} else {
			return new Result_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ_Err(null, ptr);
		}
	}
	public static final class Result_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ_OK extends Result_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ {
		public final ThreeTuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZ res;
		private Result_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ_OK(Object _dummy, long ptr) {
			super(_dummy, ptr);
			long res = bindings.CResult_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ_get_ok(ptr);
			ThreeTuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZ res_hu_conv = new ThreeTuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZ(null, res);
			if (res_hu_conv != null) { res_hu_conv.ptrs_to.add(this); };
			this.res = res_hu_conv;
		}
	}

	public static final class Result_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ_Err extends Result_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ {
		private Result_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ_Err(Object _dummy, long ptr) {
			super(_dummy, ptr);
		}
	}

	/**
	 * Creates a new CResult_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ in the success state.
	 */
	public static Result_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ ok(org.ldk.structs.ThreeTuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZ o) {
		long ret = bindings.CResult_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ_ok(o != null ? o.ptr : 0);
		Reference.reachabilityFence(o);
		if (ret >= 0 && ret <= 4096) { return null; }
		Result_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ ret_hu_conv = Result_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

	/**
	 * Creates a new CResult_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ in the error state.
	 */
	public static Result_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ err() {
		long ret = bindings.CResult_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ_err();
		if (ret >= 0 && ret <= 4096) { return null; }
		Result_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ ret_hu_conv = Result_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

	/**
	 * Checks if the given object is currently in the success state
	 */
	public boolean is_ok() {
		boolean ret = bindings.CResult_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ_is_ok(this.ptr);
		Reference.reachabilityFence(this);
		return ret;
	}

	long clone_ptr() {
		long ret = bindings.CResult_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ_clone_ptr(this.ptr);
		Reference.reachabilityFence(this);
		return ret;
	}

	/**
	 * Creates a new CResult_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ which has the same data as `orig`
	 * but with all dynamically-allocated buffers duplicated in new buffers.
	 */
	public Result_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ clone() {
		long ret = bindings.CResult_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ_clone(this.ptr);
		Reference.reachabilityFence(this);
		if (ret >= 0 && ret <= 4096) { return null; }
		Result_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ ret_hu_conv = Result_C3Tuple_ThirtyTwoBytesRecipientOnionFieldsRouteParametersZNoneZ.constr_from_ptr(ret);
		return ret_hu_conv;
	}

}
