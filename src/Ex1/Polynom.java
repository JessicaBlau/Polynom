package Ex1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Jessica Blau
 *
 */
public class Polynom implements Polynom_able{
	/**
	 * 
	 */
	private ArrayList <Monom> list = new ArrayList<Monom>(); 
	public ArrayList<Monom> getList(){
		return list;
	}
	/**
	 * Constructor that creates a default polynom - empty polynom.
	 */
	public Polynom() {
		this.list = new ArrayList<Monom>(0);
	}
	/**
	 * This a a constructed that sets our array list to the one that is sent.
	 * 
	 * @param list represents the arrays list of the polynom
	 * that is needed to be set to our polynom.
	 */
	public void setList(ArrayList<Monom> list) {
		this.list = list;
	}
	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4X^3-34x", "(2x^2-4)*(-1.2x-7.1)", "(3-3.4x+1)*((3.1x-1.2)-(3X^2-3.1))"};
	 * @param s: is a string represents a Polynom
	 */
	public Polynom(String s) {
		s = s.replaceAll(" ", "");
		String [] str = s.split("\\+");
		for (int i = 0; i < str.length; i++) {
			if(str[i].contains("-")) {
				String [] str1 = str[i].split("-");
				if(str1[0].equals("")) {
					for (int j = 1; j < str1.length; j++) {
						String tmp = "-"+str1[j];
						Monom m = new Monom(tmp);
						this.add(m);

					}
				}
				else {
					Monom m  = new Monom(str1[0]);
					this.add(m);
					for (int j = 1; j < str1.length; j++) {
						String tmp = "-"+str1[j];
						m = new Monom(tmp);
						this.add(m);

					}

				}

			}
			else {
				Monom m = new Monom(str[i]);
				this.add(m);
			}

		}

	}
	/**
	 * This interface represents a function y = f(x).
	 * 
	 * @param x represents a number that is given.
	 * @return the result of the function in point x, meaning y = f(x).
	 */
	@Override
	public double f(double x) {
		double d = 0;
		Iterator<Monom> run = list.iterator();
		while(run.hasNext()) {
			Monom m = run.next();
			d += m.get_coefficient()*Math.pow(x, m.get_power());
		}
		return d;
	}
	/**
	 * This function adds a polynom to our polynom, 
	 * it uses the function in the Monom class as well.
	 * 
	 * @param p1 represents the polynom that is added to our polynom.
	 * 
	 */
	@Override
	public void add(Polynom_able p1) {
		Iterator<Monom> run = p1.iteretor();
		while(run.hasNext()) {
			Monom m = run.next();
			add(m);
		}

	}
	/**
	 * This functions add the monom m1 to our polynom. This function uses an iterator
	 * that goes through all of the polynom and adds the monom m1 in the right place.
	 * Meaning if there is no other power that is a match it adds it to where it should be
	 * and if there is a power that matches it adds m1 to the monom with the same power.
	 * 
	 * @param m1 represents the monom that is added to our polynom.
	 */
	@Override
	public void add(Monom m1) {
		Iterator<Monom> run = list.iterator();
		while(run.hasNext()) {
			Monom_Comperator mc = new Monom_Comperator();
			Monom tmp = run.next();
			if(mc.compare(tmp, m1) == 0) {
				tmp.add(m1);
				if(tmp.get_coefficient() == 0) list.remove(tmp);
				return;
			}
			else if(mc.compare(tmp, m1) > 0) {
				list.add(m1);
				Collections.sort(list, mc);
				return;
			}
		}
		list.add(m1);	
	}
	/**
	 * This function subtracts polynom p1 from our polynom. First it times p1 by -1,
	 * changing all the signes in p1, then it adds it to our polynom.
	 * 
	 * @param p1 represents the polynom that is subtracted from our polynom.
	 */
	@Override
	public void substract(Polynom_able p1) {
		Iterator <Monom> iter = p1.iteretor();
		Iterator <Monom> ThisIter = this.iteretor();
		while(iter.hasNext()) {
			Monom runner = iter.next();
			Monom ThisRunner = ThisIter.next();
			if(runner.equals(ThisRunner)) {
				continue;
			}
			else {
				Monom m = new Monom(-1,0);
				Polynom p2 = (Polynom) p1.copy();
				p2.multiply(m);
				p1.add(p2);
				break;
			}
		}
		this.list.clear();
	}
	/**
	 * This function multiplies p1 by our polynom. It uses an iterator to go through
	 * the polynom that is sent. We copy our polynom into org and multiply it 
	 * each time by the next monom of p1. Each time we multiply by a monom of p1 we 
	 * add our answer to sum and then recopy our original polynom.
	 * 
	 * @param p1 represents the polynom that we are multiplying to our polynom.
	 */
	@Override
	public void multiply(Polynom_able p1) {
		Iterator<Monom> run = p1.iteretor();
		Polynom org = (Polynom)this.copy();
		Polynom sum = new Polynom();
		while(run.hasNext()) {
			org.multiply(run.next());
			sum.add(org);
			org = (Polynom)this.copy();
		}
		this.list=sum.list;
	}
	/**
	 * This function checks if p1 is equal to our polynom by running on two
	 * iterators one on p1 and the other on our polynom.
	 * 
	 * @param p1 represents the polynom we are checking if equal to our polynom
	 * @return true if the polynom p1 is equal to ours.
	 */
	@Override
	public boolean equals(Polynom_able p1) {
		Iterator<Monom> run = iteretor();
		Iterator<Monom> run1 = p1.iteretor();
		while(run.hasNext() && run1.hasNext()) {
			if(!run.next().equals(run1.next())){
				return false;
			}	
		}
		if(run.hasNext()==false && run1.hasNext()==false)
			return true;
		return false;
	}
	/**
	 * This function checks if our polynom is equal to zero y using isEmpty 
	 * from arraylist.
	 * 
	 * @return true if our polynom is empty.
	 */
	@Override
	public boolean isZero() {
		if(!list.isEmpty()) {
			System.out.println(1);
			System.out.println(list);
		}
		return list.isEmpty();
	}
	/**
	 * This function finds the point x of the polynom on the x axis, meaning f(x) = 0,
	 * betweens two points. This function uses 
	 * 
	 * @param x0 represents a starting point
	 * @param x0 represents an end point
	 * @param eps represents the step value 
	 * @return the x that was found 
	 */
	@Override
	public double root(double x0, double x1, double eps) {
		if(f(x0)*f(x1)>0) {
			throw new RuntimeException("Cannot be calculates");
		}
		else if(f(x0)==0) {
			return x0;

		}
		else if(f(x1) == 0) return x1;
		else {
			while(x1-x0 > 2*eps) {
				double mid = (x0+x1)/2;
				if(f(mid) == 0) return 0;
				if(f(x0)*f(mid) < 0) {
					x1 = mid;
				}
				else {
					x0 = mid;
				}
			}
			return (x0+x1)/2;
		}
	}
	/**
	 * This function is a deep copy of our polynom. The functions uses an iterator
	 * that goes throw our polynom and copies the monom in every step.
	 * 
	 * @return the polynom that is copied.
	 */
	@Override
	public Polynom_able copy() {
		Polynom ans = new Polynom();
		Iterator<Monom> run = iteretor();
		while(run.hasNext()) {
			Monom m = run.next();
			ans.add(new Monom(m));
		}
		return ans;
	}
	/**
	 * This function calculates the derivative of the polynom, using the function
	 * derivative of the monoming on each monom.
	 * 
	 * @return the derivative of the polynom.
	 */
	@Override
	public Polynom_able derivative() {
		Polynom ans = new Polynom();
		Iterator<Monom> iter = iteretor();
		while (iter.hasNext()) {
			ans.add(iter.next().derivative());
		}
		return ans;

	}
	/**
	 * This function calcualtes the area of the function between two point.
	 * In other words it calculates the integral of a function between two points.
	 * 
	 * @param x0 the first point of the area
	 * @param x1 the second point of the are
	 * @param eps a number that is very close to 0
	 * @return the approximate area above and under the x axis below or above this polynom, 
	 * 		   between the 2 points [x0,x1].
	 */
	@Override
	public double area(double x0, double x1, double eps) {
		double ans = 0;
		double i = 0;
		for (i = x0; i < x1; i = i + eps) {
			double y = i + (eps / 2);
			if (f(y) > 0)
				ans = ans + (f(y) * eps);
		}
		return ans;
	}
	/**
	 * This functions creates an Iterator on this polynom.
	 * 
	 * @return the iterator.
	 * 
	 */
	@Override
	public Iterator<Monom> iteretor() {
		Iterator<Monom> a=list.iterator();
		return a;
	}
	/**
	 * This function multiplies our polynom with the monom m1. It uses an iterator
	 * on our polynom to multiplie the monom m1 by each monom of our polynom.
	 * 
	 * @param m1 represents the monom that is being multiplies.
	 */
	@Override
	public void multiply(Monom m1) {
		if (m1.get_coefficient() == 0) {
			this.list.clear();
		}
		else {
			Iterator<Monom> run = iteretor();
			while(run.hasNext()) {
				run.next().multipy(m1);
			}
		}
	}
	/**
	 * This function creates a String of our polynom. It uses an Itarator 
	 * to go through all the monoms of our polynom.
	 * 
	 * @return a String of our polynom.
	 */
	@Override
	public String toString() {
		Iterator<Monom> run = iteretor();
		String ans = "";
		if(!run.hasNext()) return Monom.ZERO.toString();
		while(run.hasNext()) {
			Monom m = run.next();	
			if(list.indexOf(m) == 0) ans = m.toString();
			else {
				if(m.toString().charAt(0) == '-') ans += m.toString();
				else ans += "+"+m.toString();
			}

		}
		return ans;
	}
	/**
	 * This is a functions that helps remove any 0 from the polynom
	 * 
	 */
	public void RemoveZeroes(){
		Iterator<Monom> iter = iteretor();
		while(iter.hasNext()) {
			Monom runner = iter.next();
			if(runner.get_coefficient() == 0) {
				iter.remove();
			}
			Monom_Comperator s_c = new Monom_Comperator();
			this.list.sort(s_c);
		}
	}
	@Override
	public function initFromString(String s) {
		function f=new Polynom(s);
		return f;
	}
	
}
