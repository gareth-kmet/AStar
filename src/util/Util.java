package util;

import java.util.Random;

/**
 * 
 * @author Gareth Kmet
 *
 */
public final class Util {
	/**
	 * No initializing
	 */
	private Util() {}
	
	/**
	 * Gets the i-th random float of a given seed 
	 * <br>Will reset the seed of the random generator beforehand but not after
	 * @param index - the index to retrieve the float
	 * @param random - the random generator to use
	 * @param seed - the seed to use for the random generator
	 * @param bound - the bound of the random float
	 * @return <b><code>float</code></b> - the i-th random float
	 */
	public static float getRandomFloatAtIndex(int index, Random random, long seed, float bound) {
		random.setSeed(seed);
		for(int i=0; i<=index; i++) {
			float f = random.nextFloat(bound);
			if(i==index) {
				return f;
			}
		}
		return -1; // DEAD CODE, only here to avoid syntax errors
	}
	
	/**
	 * Gets the i-th random int of a given seed 
	 * <br>Will reset the seed of the random generator beforehand but not after
	 * @param index - the index to retrieve the int
	 * @param random - the random generator to use
	 * @param seed - the seed to use for the random generator
	 * @param bound - the bound of the random int
	 * @return <b><code>float</code></b> - the i-th random float
	 */
	public static int getRandomIntAtIndex(int index, Random random, long seed, int bound) {
		random.setSeed(seed);
		for(int i=0; i<=index; i++) {
			int f = random.nextInt(bound);
			if(i==index) {
				return f;
			}
		}
		return -1; // DEAD CODE, only here to avoid syntax errors
	}
	
	/**
	 * Converts a Cartesian location into a unique positive integer on a spiral
	 * <p>
	 * A coordinate of <code>0,0</code> will result in the spiral index of <code>1</code>
	 * <br>All other coordinates will result in a positive natural number that is unique to the coordinate
	 * <br><i>Negative inputs will result in coordinates on the <code>y-axis</code></i>
	 * <p>
	 * Taken from <a href="https://stackoverflow.com/questions/9970134/get-spiral-index-from-location">here</a>
	 * @param x - The x location
	 * @param y - The y location
	 * @return <b><code>int</code></b> - the index of the location on the spiral
	 */
	public static int pointToSpiral(int x, int y) {
		int p;
		if(y*y >= x*x) {
			p = 4*y*y - y - x;
			if (y<x) {
				p-=2*(y-x);
			}
		}else {
			p = 4*x*x - y - x;
			if(y<x) {
				p+=2*(y-x);
			}
		}
		return p + 1;
	}
	
	/**
	 * Converts a unique positive integer spiral index to its unique Cartesian coordinate
	 * <p>
	 * An index of <code>1</code> will result in the coordinate pair <code>0,0</code>
	 * <br>Results in the same pair of values that would be given in {@link #pointToSpiral(int, int)} to obtain this index
	 * <p> 
	 * Taken from <a href="https://gamedev.stackexchange.com/questions/157291/find-id-from-spiral-x-y-positions-hard-programming-formula">here</a>
	 * @param s - the spiral index
	 * @return an <code>x,y</code> coordinate
	 */
	public static int[] spiralToPoint(int s) {
		int k = (int)Math.ceil((Math.sqrt(s)-1)/2.);
		int t = 2*k+1;
		int m = t*t;
		t-=1;
		
		if(s>=m-t) {
			int[] p = {-k, k-(m-s)};
			return p;
		}
		m-=t;
		
		if(s>=m-t) {
			int[] p = {-k+(m-s), -k};
			return p;
		}
		m-=t;
		
		if(s>=m-t) {
			int[] p = {k, -k+(m-s)};
			return p;
		}
		
		int[] p = {k-(m-s-t), k};
		return p;
		
	}

	/**
	 * Lerps two values together with a smoothing function given a proportional between 0-1.
	 * <br>Uses a smoothing function to result in a non-linear lerp so that the edges of a chunk will not have kinks<ul>
	 * @param val1 - the first value
	 * @param val2 - the second value
	 * @param aProp - the linear proportion of the location between the two values
	 * @return <b><code>Vectornf</code></b> - the resulting value
	 */
	public static Vectornf lerps(Vectornf val1, Vectornf val2, float aProp) {
		float a = (float)(6*Math.pow(aProp, 5)-15*Math.pow(aProp, 4)+10*Math.pow(aProp, 3));
		return Vectornf.lerp(val1, val2, a);
	}
	
}
