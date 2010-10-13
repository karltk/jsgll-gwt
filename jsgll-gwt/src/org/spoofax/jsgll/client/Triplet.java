package org.spoofax.jsgll.client;

public class Triplet<S1, S2, S3> {

	final S1 fst;
	public final S2 snd;
	public final S3 trd;

	public Triplet(S1 fst, S2 snd, S3 trd) {
		this.fst = fst;
		this.snd = snd;
		this.trd = trd;
	}

	@Override
	public int hashCode() {
		return fst.hashCode() * 104729 + snd.hashCode() * 1187 + trd.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Triplet))
			return false;
		@SuppressWarnings("unchecked")
		Triplet<S1, S2, S3> t = (Triplet<S1, S2, S3>) obj;
		return t.fst.equals(fst) && t.snd.equals(snd) && t.trd.equals(trd);
	}
}
