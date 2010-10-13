package org.spoofax.jsgll.client;

public class Pair<T1, T2> {

	private final T1 fst;
	private final T2 snd;

	public Pair(T1 fst, T2 snd) {
		this.fst = fst;
		this.snd = snd;
	}

	@Override
	public int hashCode() {
		return fst.hashCode() * 1337 + snd.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Pair))
			return false;
		@SuppressWarnings("unchecked")
		Pair<T1, T2> p = (Pair<T1, T2>) obj;
		return p.fst.equals(fst) && p.snd.equals(snd); 
	}
}
