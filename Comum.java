import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Comum {

	public static BigInteger gerarPrimo (int tamanho) {
		while(true) {
			BigInteger aleatorio = new BigInteger(tamanho, new Random());
			if(testeMR(aleatorio, 64)) return aleatorio;
		}
	}

	public static boolean testeMR(BigInteger testado, int qtde) {
		BigInteger q = testado.subtract(BigInteger.ONE);
		BigInteger k = BigInteger.ZERO;
		while(q.remainder(BigInteger.TWO).compareTo(BigInteger.ZERO) == 0) {
			k = k.add(BigInteger.ONE);
			q = q.divide(BigInteger.TWO);
		}

		mainLoop: for(int count = 0; count < qtde; count++) {
			Random random = new Random();
			BigInteger t = expoModular(BigInteger.valueOf(random.nextInt()+2), q , testado);
			if(t.compareTo(BigInteger.ONE) == 0 || t.compareTo(testado.subtract(BigInteger.ONE)) == 0)
				continue mainLoop;
			BigInteger i = BigInteger.ONE;
			while(i.compareTo(k) == -1) {
				t = expoModular(t, BigInteger.TWO, testado);
				if(t.compareTo(testado.subtract(BigInteger.ONE)) == 0)
					continue mainLoop;
				i = i.add(BigInteger.ONE);
			}
			return false;
		}
		return true;
	}

	public static BigInteger expoModular (BigInteger base, BigInteger expoente, BigInteger modulo) {
		BigInteger resultado = BigInteger.ONE;
		while(expoente.compareTo(BigInteger.ZERO) != 0) {
			if(expoente.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) != 0) {
				resultado = resultado.multiply(base).mod(modulo);
				expoente = expoente.subtract(BigInteger.ONE).divide(BigInteger.TWO);
			} else expoente = expoente.divide(BigInteger.TWO);
			base = base.multiply(base).mod(modulo);
		}
		return resultado;
	}

	public static ArrayList<List<BigInteger>> fatora(BigInteger num) {
		BigInteger fator = BigInteger.TWO;
		ArrayList<List<BigInteger>> fatores = new ArrayList<>();
		BigInteger count = BigInteger.ZERO;
		BigInteger[] result;
		
		if(num.mod(fator).compareTo(BigInteger.ZERO) == 0) {
			result = num.divideAndRemainder(fator);
			while(result[1].intValue() == 0) {
				num = result[0];
				count = count.add(BigInteger.ONE);
				result = num.divideAndRemainder(fator);
			}
			System.out.println(fator);
			fatores.add((List<BigInteger>) Arrays.asList(fator, count));
		}

		fator = fator.add(BigInteger.ONE);
		while(num.compareTo(BigInteger.ONE) != 0 && num.sqrt().compareTo(fator) >= 0) {
			if(num.mod(fator).compareTo(BigInteger.ZERO) == 0) {
				count = BigInteger.ZERO;
				result = num.divideAndRemainder(fator);
				while(result[1].intValue() == 0) {
					num = result[0];
					count = count.add(BigInteger.ONE);
					result = num.divideAndRemainder(fator);
				}
				System.out.println(fator);
				fatores.add((List<BigInteger>) Arrays.asList(fator, count));
			}
			fator = fator.add(BigInteger.TWO);
		}

		if(num.compareTo(BigInteger.ONE) != 0) fatores.add(Arrays.asList(num, BigInteger.ONE));
		return fatores;
	}

	public static List<BigInteger> euclid(BigInteger a, BigInteger b) {
		BigInteger x1 = BigInteger.ONE ,y2 = BigInteger.ONE;
		BigInteger x2 = BigInteger.ZERO,y1 = BigInteger.ZERO;
		BigInteger ax, ay, r1 = null, q;
		while(a.mod(b).compareTo(BigInteger.ZERO) != 0) {
			q = a.divide(b);

			ax = x1.subtract(x2.multiply(q));
			x1 = x2;
			x2 = ax;

			ay = y1.subtract(y2.multiply(q));
			y1 = y2;
			y2 = ay;

			r1 = a.subtract(b.multiply(q));
			a = b;
			b = r1;
		}

		return Arrays.asList(r1, x2, y2);
	}

	public static BigInteger gauss(BigInteger primo) {
		ArrayList<List<BigInteger>> fatores = fatora(primo.subtract(BigInteger.ONE));
		System.out.println(fatores);
		BigInteger g = BigInteger.ONE;
		BigInteger p = primo.subtract(BigInteger.ONE);
		for(int i = 0; i < fatores.size(); i++) {
			int a=2;
			while(expoModular(BigInteger.valueOf(a), p.divide(fatores.get(i).get(0)), primo).compareTo(BigInteger.ONE) == 0 ) a++;
			BigInteger h = expoModular(BigInteger.valueOf(a), p.divide(fatores.get(i).get(0).pow(fatores.get(i).get(1).intValue())), primo);
			g = g.multiply(h).mod(primo);
		}
		return g;
	}
}
