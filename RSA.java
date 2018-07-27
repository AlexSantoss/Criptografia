import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSA {
	
	private BigInteger n;
	private BigInteger e;
	private BigInteger d;
	
	public RSA(int tamanho) {
		gerarChaves(tamanho);
	}
	
	public RSA(BigInteger n, BigInteger e, BigInteger d) {
		this.n = n;
		this.e = e;
		this.d = d;
	}

	private void gerarChaves(int tamanho) {
		BigInteger p = Comum.gerarPrimo(tamanho);
		BigInteger q = Comum.gerarPrimo(tamanho);

		n = p.multiply(q);
		BigInteger fi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

		e = Comum.gerarPrimo(tamanho);
		List<BigInteger> euclidiano = Comum.euclid(e, fi); 
		if(euclidiano.get(0).compareTo(BigInteger.ONE) != 0) {
			e = Comum.gerarPrimo(tamanho);
			euclidiano = Comum.euclid(e, fi);
		}
		
		d = euclidiano.get(1).mod(fi);
	}
	
	public String toString() {
		return "Public keys:\n"+
				"\tn: " + n + "\n"+
				"\te: " + e + "\n"+
				"Private keys:\n"+
				"\tn: " + n + "\n"+
				"\td: " + d + "\n";
	}
	
	public List<BigInteger> encripta(List<BigInteger> blocos){
		List<BigInteger> encriptado = new ArrayList<BigInteger>();
		for(BigInteger bloco : blocos) encriptado.add(Comum.expoModular(bloco, e, n));
		return encriptado;
	}
	
	public List<BigInteger> decripta(List<BigInteger> blocos){
		List<BigInteger> decriptado = new ArrayList<BigInteger>();
		for(BigInteger bloco : blocos) decriptado.add(Comum.expoModular(bloco, d, n));
		return decriptado;
	}

}
//String.format("%064X", d)