package course.security;

public class Playfair {
	
	public static void main(String[] args) {
		String enc = encrypt("ls", "");
		String dec = decrypt(enc, "");
		System.out.println(enc);
		System.out.println(dec);
	}
	
	public static String encrypt(String plaintext, String key) {
		return encrypt(plaintext, key, 1);
	}
	
	private static String encrypt(String plaintext, String key, int direction) {
		// Character case doesn't matter
		key = key.toUpperCase();
		plaintext = plaintext.toUpperCase();
		
		StringBuilder sb = new StringBuilder();
		
		// Initialize Matrix with key
		char[][] Matrix = new char[5][5];
		int index = 0;
		for(int i = 0; i < 5; ++i) {
			for(int j = 0; j < 5; ++j) {
				if(index < key.length())
					insert(Matrix, key.charAt(index++));
			}
		}
		
		// Fill remaining characters in Matrix
		for(char i = 'A'; i <= 'Z'; ++i) {
			if(i != 'J')
				insert(Matrix, i);
		}
		
		// Zabbat el plaintext
		StringBuilder ptsb = new StringBuilder(plaintext);
		
		for(int i = 1; i < ptsb.length(); i += 2) {
			if(ptsb.charAt(i) == ptsb.charAt(i - 1)) {
				ptsb.insert(i, 'X');
			}
		}
		if(ptsb.length() % 2 != 0) ptsb.append('X');
		
		// Geeb el ciphertext for every pair
		
		for(int i = 0; i < ptsb.length(); i += 2) {
			int[] x = find(Matrix, ptsb.charAt(i));
			int[] y = find(Matrix, ptsb.charAt(i + 1));
			int x1 = x[0], x2 = x[1], y1 = y[0], y2 = y[1];
			
			if(x1 == y1) {
				sb.append(Matrix[x1][(x2 + direction) % 5]);
				sb.append(Matrix[y1][(y2 + direction) % 5]);
			} else if(x2 == y2) {
				sb.append(Matrix[(x1 + direction) % 5][x2]);
				sb.append(Matrix[(y1 + direction) % 5][y2]);
			} else {
				sb.append(Matrix[x1][y2]);
				sb.append(Matrix[y1][x2]);
			}
		}
		
		return sb.toString();
	}
	
	public static String decrypt(String ciphertext, String key) {
		return encrypt(ciphertext, key, 4);
	}
	
	private static void insert(char[][] Matrix, char alpha) {
		if(exists(Matrix, alpha)) return;
		for(int i = 0; i < 5; ++i) {
			for(int j = 0; j < 5; ++j) {
				if(Matrix[i][j] == '\u0000') {
					Matrix[i][j] = alpha;
					return;
				}
			}
		}
	}
	
	private static boolean exists(char[][] Matrix, char alpha) {
		for(int i = 0; i < Matrix.length; ++i) {
			for(int j = 0; j < Matrix[0].length; ++j)
				if(Matrix[i][j] == alpha)
					return true;
		}
		return false;
	}
	
	private static int[] find(char[][] Matrix, char alpha) {
		if(alpha == 'J') return find(Matrix, 'I');
		int[] position = new int[2];
		for(int i = 0; i < Matrix.length; ++i) {
			for(int j = 0; j < Matrix[0].length; ++j)
				if(Matrix[i][j] == alpha) {
					position[0] = i;
					position[1] = j;
					return position;
				}
		}
		return position;
	}

}
