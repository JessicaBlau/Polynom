package Ex1;

public class ComplexFunction implements complex_function {
	private function left;
	private function right;
	private Operation oper;
	/**
	 * Constructor that receives a function and returns it as a complex function.
	 * @param f is a function. 
	 */
	public ComplexFunction(function f) {
		this.left = f;
		this.right = null;
		oper = Operation.None;
	}
	/**
	 * Constructor that receives a string as a operation and 2 functions, Left side of operation and right side.
	 * @param str - the operation in a string.
	 * @param p1 - the function on the left.
	 * @param p2 - the function on the right.
	 */
	public ComplexFunction(String str, function p1, function p2) {
		if(p2 != null) {
			this.oper = StrToOper(str);			
		}
		else if(p2 == null) {
			this.oper = Operation.None;
		}
		this.left = p1;
		this.right = p2;
	}
	/**
	 * Constructor that receives on operation and left side of complex function and the right.
	 * @param op - the operation between the two functions
	 * @param p1 - left side of complex function.
	 * @param p2 - right side of complex function.
	 */
	public ComplexFunction(Operation op, function p1, function p2) {
		if(p2 == null) {
			this.oper = Operation.None;
		}else {
			this.oper = op;
		}
		this.left = p1;
		this.right = p2;
	}
	/**
	 * An empty complex function for a default.
	 */
	public ComplexFunction() {
		this.left = null;
		this.right = null;
		this.oper = Operation.None;
	}
	/**
	 * This function receives an x which is some number and will enter this number into 
	 * the complex function and returns the result of f(x).
	 */
	@Override
	 public double f(double x) {
        switch (oper) {
            case Plus:
                return left.f(x) + right.f(x);
            case Times:
                return left.f(x) * right.f(x);
            case Divid:
                if(right.f(x) != 0 )
                    return left.f(x) / right.f(x);
                else
                    throw new RuntimeException("Can't Divide with zero");
            case Min:
                return Math.min(left.f(x),right.f(x));
            case Max:
                return Math.max(left.f(x),right.f(x));
            case Comp:
                return left.f(right.f(x));
            case None:
                return left.f(x);
            default:
                throw new RuntimeException("Operation is undefined!");
        }
    }
	
	@Override
	public function initFromString(String s) {
		if(!isBalanced(s))
			throw new IllegalArgumentException("The delimiters are incorrect");

		if(s.indexOf('(')==-1) {
			function f=new Polynom(s);
			return f;
		}
		int ind1 = s.indexOf('(');
		int ind2 = findTheRightComma(s); 
		int ind3 = s.lastIndexOf(')');

		String right = s.substring(ind2+2,ind3);
		String left = s.substring(ind1+1,ind2);
		String oper = s.substring(0,ind1);

		function p1=initFromString(left);
		function p2=initFromString(right);
		return new ComplexFunction(oper,p1,p2);		
	}
	private int findTheRightComma(String s) {
		int counter=0,i=0;
		for (; i < s.length() && s.charAt(i)!=')' && s.charAt(i+1)!=','; i++) {
			if(s.charAt(i)=='(')
				counter++;
		}

		for (; i < s.length() && counter!=1; i++) {
			if(s.charAt(i)==')')
				counter--;
			if(s.charAt(i)=='(')
				counter++;
		}

		return i+1;
	}

	@Override
	public function copy() throws Exception {
		ComplexFunction temp = new ComplexFunction(oper,left,right);
		return temp;
	}
	@Override
	public void plus(function f1) {
		if(f1!=null) {
			ComplexFunction temp = new ComplexFunction(this.oper,this.left,this.right);
			this.left=temp;
			this.right=f1;
		}else {
			throw new IllegalArgumentException("The operation can't null");
		}
		this.oper=Operation.Plus;
	}
	/*
	 * this.ComlexFuntion * f1
	 */
	@Override
	public void mul(function f1) {
		if(f1!=null) {
			ComplexFunction temp = new ComplexFunction(this.oper,this.left,this.right);
			this.left=temp;
			this.right=f1;
		}else {
			throw new IllegalArgumentException("The operation can't null");
		}
		this.oper=Operation.Times;			
	}
	/*
	 * this.ComlexFuntion / f1
	 */
	@Override
	public void div(function f1) {
		if(f1!=null) {
			ComplexFunction temp = new ComplexFunction(this.oper,this.left,this.right);
			this.left=temp;
			this.right=f1;
		}else {
			throw new IllegalArgumentException("The operation can't null");
		}
		this.oper=Operation.Divid;
	}
	/*
	 * Max(this.ComlexFuntion,f1)
	 */
	@Override
	public void max(function f1) {
		if(f1!=null) {
			ComplexFunction temp = new ComplexFunction(this.oper,this.left,this.right);
			this.left=temp;
			this.right=f1;
		}else {
			throw new IllegalArgumentException("The operation can't null");
		}
		this.oper=Operation.Max;
	}
	/*
	 * Min(this.ComlexFuntion,f1)
	 */
	@Override
	public void min(function f1) {
		if(f1!=null) {
			ComplexFunction temp = new ComplexFunction(this.oper,this.left,this.right);
			this.left=temp;
			this.right=f1;
		}else {
			throw new IllegalArgumentException("The operation can't null");
		}
		this.oper=Operation.Min;
	}
	/*
	 * this.ComlexFuntion(f1(x))
	 */
	@Override
	public void comp(function f1) {
		if(f1!=null) {
			ComplexFunction temp = new ComplexFunction(this.oper,this.left,this.right);
			this.left=temp;
			this.right=f1;
		}else {
			throw new IllegalArgumentException("The operation can't null");
		}
		this.oper=Operation.Comp;
	}
	/*
	 * return left function
	 */
	@Override
	public function left() {
		return this.left;
	}
	/*
	 * return right function
	 */
	@Override
	public function right() {
		return this.right;
	}

	/*
	 * return Operation
	 */
	@Override
	public Operation getOp() {
		return this.oper;
	}
	
	
	@Override
	public String toString() {
		StringBuilder ans = new StringBuilder();
		if(this.oper == Operation.None) {
			ans.append(left.toString());
		}else {
			ans.append(this.oper + "(" + this.left.toString() 
			+ " , " + this.right.toString() + ")");
		}

		return ans.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ComplexFunction))
			return false;
		ComplexFunction temp = (ComplexFunction) obj;
		for (int i = -5; i < 10 ;i++){
			try {
				if(this.f(i)!= temp.f(i))
					return false;

			}catch (ArithmeticException ae){
				ae.getCause();
			}
		}
		return true;
	}
	
	private boolean isBalanced(String s) {
		int counter = 0;
		for (int i = 0; i < s.length(); i++) {
			if(s.charAt(i) == '(') counter++;
			else if(s.charAt(i) == ')') counter--;
			if(counter < 0) return false;
		}
		return counter == 0;
	}
	
	private Operation StrToOper(String operator) {
		String s = operator.toLowerCase();

		switch(s){

		case "plus":
			return Operation.Plus;

		case "div":
			return Operation.Divid;

		case "divid":
			return Operation.Divid;

		case "mul":
			return Operation.Times;

		case "times":
			return Operation.Times;

		case "max":
			return Operation.Max;

		case "min":
			return Operation.Min;

		case "comp":
			return Operation.Comp;

		case "none":
			return Operation.None;

		case "error":
			return Operation.Error;

		default:
			throw new RuntimeException("bad operation");
		}
	}
}
