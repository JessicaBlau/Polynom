package Ex1;

import java.util.Comparator;

/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Jessica Blau
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	/**
	 * This constructor copies the monom.
	 * 
	 * @param ot represents the monom that is needed to copy.
	 */
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}

	public double get_coefficient() {
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}
	/** 
	 * @return This function method returns the derivative monom of this.
	 * 
	 */
	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	/**
	 * This function calculates the calculates the value of y.
	 * 
	 * @param x represents the the point on the x axis.
	 * @return This functions returns the value of y when entering x to this monom.
	 */
	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 
	/**
	 * 
	 * @return This functions tells you if this monom is 0 or not.
	 */
	public boolean isZero() {return this.get_coefficient() == 0;}
	// ***************** add your code below **********************
	/**
	 * this a constructor that receive a string monom for example: a*x^b, a, a*x, x 
	 * a represents the coefficient as a decimal and b represents the power as a positive integer.
	 * 
	 * @param s is a String that represents a monom.
	 */
	public Monom(String s) {
		s.replaceAll(" ", "");
		s = s.toLowerCase();
		if(!s.contains("x")) {
			try {
				this._coefficient = Double.parseDouble(s);
			} catch (Exception e) {
				throw new RuntimeException("The coefficient is not a valid number.");
			}
			this.set_power(0);
		}
		else {
			String [] str0 = s.split("x");
			String [] str3=s.split("\\^");
			if(s.charAt(0)=='x'&&s.length()>=2) {
				this.set_coefficient(1);
				try {
					this.set_power(Integer.parseInt(str3[1]));
				} catch (Exception e) {
					throw new RuntimeException("The power is not a valid number.");
				}
			}
			else if(str0.length == 0) {
				this.set_coefficient(1);
				this.set_power(1);
			}
			else if(str0.length == 1) {
				if(str0[0].equals("-")) {
					this.set_coefficient(-1); 
					this.set_power(1);;
				}
				else {
					try {
						this._coefficient = Double.parseDouble(str0[0]);
					} catch (Exception e) {
						throw new RuntimeException("The coeddicient is not a valid number.");
					}
					this.set_power(1); 
				}
			}
			else {
				if(str0[0].equals("-")) {
					this.set_coefficient(-1); 
					try {
						this.set_power(Integer.parseInt(str3[1]));
					} catch (Exception e) {
						throw new RuntimeException("The power is not a valid number.");
					}

				}
				else {
					try {
						this.set_coefficient(Double.parseDouble(str0[0]));
					} catch (Exception e) {
						throw new RuntimeException("The coefficient is not a valid number.");
					}
					try {
						this.set_power(Integer.parseInt(str3[1]));
					} catch (Exception e) {
						throw new RuntimeException("The power is not a valid number.");
					}
					if(this.get_power() < 0) {
						this.set_coefficient(-999);
						this.set_power(0);	
					}
				}
			}

		}
	}
	/**
	 * This function add a new monom to our monom. If the powers do not match 
	 * it will throw an exception.
	 * 
	 * @param m represents the monom that is add to our monom.
	 */
	public void add(Monom m) {
		if(this._power != m._power) {
			throw new RuntimeException("The powers do not match.");
		}
		else {
			this.set_coefficient(this.get_coefficient()+m.get_coefficient());
		}		
	}
	/**
	 * This function multiplies a new monom to our monom.
	 * 
	 * @param d represents the new monom that we are multiplying to our monom.
	 */
	public void multipy(Monom d) {
		this.set_coefficient(this.get_coefficient()*d.get_coefficient());
		this.set_power(this.get_power()+d.get_power());
	}
	/**
	 * @return a String that represents the monom.
	 */
	public String toString() {
		if(this.get_power() < 0) throw new RuntimeException("Invalid power.");
		if(this.get_coefficient()==0) return "0";
		if(this.get_power()==0) return ""+this.get_coefficient();
		if(this.get_power()==1) return ""+this.get_coefficient()+"x";
		String ans = this.get_coefficient()+"x^"+this.get_power();
		return ans;
	}
	public boolean equals(Object other) {

		if(other instanceof Monom) {
			Monom e = ((Monom)other);
			if(Math.abs(e.get_coefficient()) < EPSILON && Math.abs(this.get_coefficient()) < EPSILON) return true;
			return Math.abs(e.get_coefficient()-this.get_coefficient())<EPSILON && Math.abs(e.get_power()-this.get_power()) < EPSILON;
		}
		return false;
	}

	// you may (always) add other methods.

	//****************** Private Methods and Data *****************

	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;
	/**
	 * @return f = return new Monom with the s string  
	 */
	@Override
	public function initFromString(String s) {
		try {
		function f=new Monom(s);
		return f;
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * return f = new Monom(other Monom) 
	 */
	@Override
	public function copy() {
		function f=new Monom(this._coefficient,this.get_power());
		return f;
	}
}

