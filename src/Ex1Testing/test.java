package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Ex1.ComplexFunction;
import Ex1.Operation;
import Ex1.Polynom;
import Ex1.function;

class test {

	private static ComplexFunction cf;
	private static function f1;
	private static function f2;
	
	private static ComplexFunction cfDiv;
	private static ComplexFunction cfMul;
	private static ComplexFunction cfPlus;
	private static ComplexFunction cfMin;
	private static ComplexFunction cfMax;
	private static ComplexFunction cfComp;
	

	@BeforeAll
	public static void init(){
		cf = new ComplexFunction(new Polynom());
		f1 = cf.initFromString("3.1+2.4x^2-x^4");
		f2 = cf.initFromString("5+2x-3.3x+0.1x^5");
		
		cfDiv = new ComplexFunction("div", f1, f2);
		cfMul = new ComplexFunction("mul", f1, f2);
		cfPlus = new ComplexFunction("plus", f1, f2);
		cfMin = new ComplexFunction("min", f1, f2);
		cfMax = new ComplexFunction("max", f1, f2);
		cfComp = new ComplexFunction("comp", f1, f2);
	}

	@Test
	public void justTrue() {
		assertTrue(true);
	}

	@Test
	public void testDivConstructor(){
		assertEquals(cfDiv.toString(), "div(3.1+2.4x^2-x^4,5+2x-3.3x+0.1x^5)");
		assertEquals(cfDiv.toString(), new ComplexFunction(Operation.Divid, f1, f2).toString());
	}

	@Test
	public void testMulConstructor(){
		assertEquals(cfMul.toString(), "mul(3.1+2.4x^2-x^4,5+2x-3.3x+0.1x^5)");
		assertEquals(cfMul.toString(), new ComplexFunction(Operation.Times, f1, f2).toString());
	}

	@Test
	public void testPlusConstructor(){
		assertEquals(cfPlus.toString(), "plus(3.1+2.4x^2-x^4,5+2x-3.3x+0.1x^5)");
		assertEquals(cfPlus.toString(), new ComplexFunction(Operation.Plus, f1, f2).toString());
	}

	@Test
	public void testMinConstructor(){
		assertEquals(cfMin.toString(), "min(3.1+2.4x^2-x^4,5+2x-3.3x+0.1x^5)");
		assertEquals(cfMin.toString(), new ComplexFunction(Operation.Min, f1, f2).toString());
	}

	@Test
	public void testMaxConstructor(){
		assertEquals(cfMax.toString(), "max(3.1+2.4x^2-x^4,5+2x-3.3x+0.1x^5)");
		assertEquals(cfMax.toString(), new ComplexFunction(Operation.Max, f1, f2).toString());
	}

	@Test
	public void testCompConstructor(){
		assertEquals(cfComp.toString(), "comp(3.1+2.4x^2-x^4,5+2x-3.3x+0.1x^5)");
		assertEquals(cfComp.toString(), new ComplexFunction(Operation.Comp, f1, f2).toString());
	}

	@Test
	public void testFPlus() {
		ComplexFunction cf1 = new ComplexFunction(Operation.Plus, cf.initFromString("-5x^2+3x"), cf.initFromString("7x^2-x"));
		assertEquals(40, cf1.f(-5));
		assertEquals(0, cf1.f(-1));
		assertEquals(0, cf1.f(0));
		assertEquals(4, cf1.f(1));
		assertEquals(60, cf1.f(5));
	}

	@Test
	public void testFTimes(){
		ComplexFunction cf1 = new ComplexFunction(Operation.Times, cf.initFromString("-5x^2+3x"), cf.initFromString("7x^2-x"));
		assertEquals(-25200, cf1.f(-5));
		assertEquals(-64, cf1.f(-1));
		assertEquals(0, cf1.f(0));
		assertEquals(-12, cf1.f(1));
		assertEquals(-18700, cf1.f(5));
	}

	@Test
	public void testFDivid(){
		ComplexFunction cf1 = new ComplexFunction(Operation.Divid, cf.initFromString("-5x^2+3x"), cf.initFromString("7x^2-x"));
		assertEquals(-0.7777777777777778, cf1.f(-5));
		assertEquals(-1.0, cf1.f(-1));
		assertEquals(-1.0/3.0, cf1.f(1));
		assertEquals(-0.6470588235294118, cf1.f(5));
	}

	@Test
	public void testFMax(){
		ComplexFunction cf1 = new ComplexFunction(Operation.Max, cf.initFromString("7x^2-5"), cf.initFromString("x"));
		assertEquals(170, cf1.f(-5));
		assertEquals(2, cf1.f(-1));
		assertEquals(0, cf1.f(0));
		assertEquals(2., cf1.f(1));
		assertEquals(170, cf1.f(5));
	}

	@Test
	public void testFMin(){
		ComplexFunction cf1 = new ComplexFunction(Operation.Min, cf.initFromString("7x^2-5"), cf.initFromString("x"));
		assertEquals(-5, cf1.f(-5));
		assertEquals(-1, cf1.f(-1));
		assertEquals(-5, cf1.f(0));
		assertEquals(1., cf1.f(1));
		assertEquals(5, cf1.f(5));
	}

	@Test
	public void testFComp(){
		ComplexFunction cf1 = new ComplexFunction(Operation.Comp, cf.initFromString("-5x^2+3x"), cf.initFromString("7x^2-x"));
		assertEquals(-161460, cf1.f(-5));
		assertEquals(-296, cf1.f(-1));
		assertEquals(0, cf1.f(0));
		assertEquals(-162, cf1.f(1));
		assertEquals(-143990, cf1.f(5));
	}

	@Test
	public void testCopy() throws Exception {
		ComplexFunction cf1 = new ComplexFunction(Operation.Times, cf.initFromString("-5x^2+3x"), cf.initFromString("x"));
		ComplexFunction cf2 = (ComplexFunction) cf1.copy();
		assertEquals(cf1.toString(), cf2.toString());
		
		cf2.mul(cf1.initFromString("2x"));
		assertNotEquals(cf1.toString(), cf2.toString());
	}

	@Test
	public void testEquals(){
		assertTrue(cf.equals(new ComplexFunction(Operation.None, f1, f2)));
		assertTrue(cfDiv.equals(new ComplexFunction(Operation.Divid, f1, f2)));
		assertTrue(cfMul.equals(new ComplexFunction(Operation.Times, f1, f2)));
		assertTrue(cfPlus.equals(new ComplexFunction(Operation.Plus, f1, f2)));
		assertTrue(cfMin.equals(new ComplexFunction(Operation.Min, f1, f2)));
		assertTrue(cfMax.equals(new ComplexFunction(Operation.Max, f1, f2)));
		assertTrue(cfComp.equals(new ComplexFunction(Operation.Comp, f1, f2)));
	}


	@Test
	public void testLeft() {
		assertEquals(f1.toString(), cfDiv.left().toString());
	}

	@Test
	public void testRight() {
		assertEquals(f2.toString(), cfDiv.right().toString());
	}
	
	@Test
	public void testGetOp() {
		assertEquals(Operation.Divid.toString(), cfDiv.getOp().toString());
	}
}