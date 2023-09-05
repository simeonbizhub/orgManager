import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class Exercise1Test {
	@Test
	public void testMultiplyTwoNumbersReturnsCorrectResult() {
		// Given
		int num1 = 5, num2 = 10;
		Exercise1 EX1 = new Exercise1();
		// When
		int result = EX1.multiply(num1, num2);
		// Then
		Assert.assertEquals(50, result);
	}
}
