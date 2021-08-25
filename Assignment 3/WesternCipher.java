import java.util.Scanner;
import java.io.*;
/**
 * @author Lisa Jia
 * 
 *         Date Completed: 03/16/2021
 * 
 *         CS1027 ASSIGN 3: This Program computes a program that allows the use
 *         to encode and decode any string of characters using a set of rules
 *         and limitations that was provided. This class programs the cipher
 *         that encodes and decodes any message using the specifications
 *         provided.
 */
public class WesternCipher {
	private CircularArrayQueue<Character> encodingQueue; // the encoding Queue
	private CircularArrayQueue<Character> decodingQueue; // the decoding Queue

	// initializes both the encoding and decoding Queue to have an initial capacity
	// of 10
	public WesternCipher() {
		encodingQueue = new CircularArrayQueue<Character>(10);
		decodingQueue = new CircularArrayQueue<Character>(10);
	}

	/**
	 * Initializes both the encoding decoding Queue to have a maximum capacity that
	 * is given by the user
	 * 
	 * @param userCapacity
	 */
	public WesternCipher(int userCapacity) {
		encodingQueue = new CircularArrayQueue<Character>(userCapacity);
		decodingQueue = new CircularArrayQueue<Character>(userCapacity);

	}

	// method will take in a given string and encode it, returning a string
	public String encode(String input) {
		char previousChar = '\0'; // the previous character is initialized
		char enChar;
		int previousNum = 0; // previous number is initialized to zero
		int queueSize;
		int posNum = 0; // index or position number in the string is initialized to be zero
		char[] origArray = input.toCharArray();

		// build queue
		for (int i = 0; i < origArray.length; i++) {
			encodingQueue.enqueue(origArray[i]);
		}
		// the queue size is initialized using a constant variable
		queueSize = encodingQueue.size();
		for (int i = 0; i < queueSize; i++) {
			char origChar = encodingQueue.dequeue();
			enChar = ' '; // enChar is initialized

			// program checks if the inputed character is a vowel; does it pass rule three?
			if (origChar == 'A' || origChar == 'E' || origChar == 'I' || origChar == 'O' || origChar == 'U'
					|| origChar == 'Y') {

				// program checks if the previous character has been encoded to a numerical
				// value
				if (previousChar == 'A' || previousChar == 'E' || previousChar == 'I' || previousChar == 'O'
						|| previousChar == 'U' || previousChar == 'Y') {
					// if previous character is a numerical value; rule five is called
					enChar = ruleFive(origChar);
				} else {
					// otherwise, rule three is called
					enChar = ruleThree(origChar);
				}
				// previousNum is converted from the ASCII value to the actual numerical value
				// that is stored
				previousNum = Character.getNumericValue(enChar);

				// checks if there is whitespace; will continue if the inputed value is not a
				// space
			} else if (origChar != ' ') {

				// rule one is called
				enChar = ruleOne(origChar);

				// rule two is called
				enChar = ruleTwo(enChar, posNum);

				// program checks if rule four is applicable
				if (previousChar == 'A' || previousChar == 'E' || previousChar == 'I' || previousChar == 'O'
						|| previousChar == 'U' || previousChar == 'Y')
					// rule four is then applied
					enChar = ruleFour(previousNum, enChar);

			}

			encodingQueue.enqueue(enChar); // the encoded value is enqueued to the encoding queue
			previousChar = origChar; // the original value becomes stored as the previous value
			posNum++; // the position number is incremented

		}

		// the string is re-built with the new encoded characters
		String str = "";
		queueSize = encodingQueue.size();
		for (int i = 0; i < queueSize; i++) {
			str += encodingQueue.dequeue();
		}
		return str;

	}

	// rule one
	private char ruleOne(char origChar) {
		char enChar;
		// shifts the value forward five character and loops it back to A (if goes past
		// z)
		enChar = (char) (((origChar - 'A' + 5) % 26) + 'A');
		return enChar; // encoded value is returned

	}

	// rule two
	private char ruleTwo(char origChar, int posNum) {
		char enChar;
		// shifts the value forward by two additional steps from the value's position
		// number and loops it back to a (if it goes past z)
		enChar = (char) (((origChar - 'A' + (posNum * 2)) % 26) + 'A');
		return enChar; // encoded value is returned
	}

	// rule three
	private char ruleThree(char origChar) {
		char enChar = '\0';
		// vowels are encoded with their respective numerical values
		if (origChar == 'A') {
			enChar = '1';
		} else if (origChar == 'E') {
			enChar = '2';
		} else if (origChar == 'I') {
			enChar = '3';
		} else if (origChar == 'O') {
			enChar = '4';
		} else if (origChar == 'U') {
			enChar = '5';
		} else if (origChar == 'Y') {
			enChar = '6';
		}
		return enChar; // encoded value is returned

	}

	// rule four
	private char ruleFour(int previousNum, char origChar) {
		char enChar = '0';
		// shifts the value backwards by two times the previous values and loops it back
		// to a (if it goes past z)
		enChar = (char) (((origChar - 'A' + (previousNum * -2) + 26) % 26) + 'A');
		return enChar; // encoded value is returned

	}

	// rule five
	private char ruleFive(char origChar) {
		char enChar = '\0';
		// vowels are encoded with their respective numerical values
		if (origChar == 'A') {
			enChar = '3';
		} else if (origChar == 'E') {
			enChar = '4';
		} else if (origChar == 'I') {
			enChar = '5';
		} else if (origChar == 'O') {
			enChar = '6';
		} else if (origChar == 'U') {
			enChar = '1';
		} else if (origChar == 'Y') {
			enChar = '2';
		}
		return enChar; // encoded value is returned

	}

	// method will take in a given string and encode it, returning a string
	public String decode(String input) {
		char previousChar = '\0'; // the previous character is initialized
		char deChar;
		int previousNum = 0; // previous number is initialized to zero
		int queueSize;
		int posNum = 0; // index or position number in the string is initialized to be zero
		char[] origArray = input.toCharArray();

		// build decoding queue
		for (int i = 0; i < origArray.length; i++) {
			decodingQueue.enqueue(origArray[i]);
		}

		// the queue size is initialized using a constant variable
		queueSize = decodingQueue.size();
		for (int i = 0; i < queueSize; i++) {
			char origChar = decodingQueue.dequeue();
			// deChar is initialized
			deChar = ' ';

			// program checks if the inputed value is a number; was it encoded with rule
			// three?
			if (origChar == '1' || origChar == '2' || origChar == '3' || origChar == '4' || origChar == '5'
					|| origChar == '6') {
				// program checks if the previous value is a number
				if (previousChar == '1' || previousChar == '2' || previousChar == '3' || previousChar == '4'
						|| previousChar == '5' || previousChar == '6') {
					// if so; rule five is called
					deChar = ruleFiveDecode(origChar);
				} else {
					// if not; rule three is called
					deChar = ruleThreeDecode(origChar);
				}
				// previousNum is converted from the ASCII value to the actual numerical value
				// that is stored
				previousNum = Character.getNumericValue(origChar);

				// checks to see i the inputed value is whitespace; rules 1,2,4 are decoded in
				// opposite order that they were encoded in
			} else if (origChar != ' ') {
				if (previousChar == '1' || previousChar == '2' || previousChar == '3' || previousChar == '4'
						|| previousChar == '5' || previousChar == '6')
					// if the previous value was a number, rule four was applied
					deChar = ruleFourDecode(previousNum, origChar);
				else
					// if rule four was not applied, there is no decoded value; origChar is used as
					// the parameter
					deChar = origChar;

				// rule two is called
				deChar = ruleTwoDecode(deChar, posNum);

				// rule one is called
				deChar = ruleOneDecode(deChar);

			}

			// decoded values is enqueued to the decodingQueue
			decodingQueue.enqueue(deChar);
			previousChar = origChar;
			posNum++; // position number is incremented

		}

		// the string is re-built with decoded values
		String str = "";
		queueSize = decodingQueue.size();
		for (int i = 0; i < queueSize; i++) {
			str += decodingQueue.dequeue();
		}
		return str;
	}

	// decoding rule one
	private char ruleOneDecode(char origChar) {
		char deChar;
		// shifts the value backwards five character and loops it back to A (if goes
		// past z)
		deChar = (char) (((origChar - 'A' - 5 + 26) % 26) + 'A');
		return deChar; // return decoded value
	}

	// decoding rule two
	private char ruleTwoDecode(char origChar, int posNum) {
		char deChar;
		// shifts the value forward by two additional steps from the value's position
		// number and loops it back to a (if it goes past z)
		deChar = (char) ((((origChar - 'A' - (posNum * 2)) % 26) + 26) % 26 + 'A');
		return deChar; // return decoded value
	}

	// decoding rule three
	private char ruleThreeDecode(char origChar) {
		char deChar = '\0';
		// numerical values are decoded to their respective vowels
		if (origChar == '1') {
			deChar = 'A';
		} else if (origChar == '2') {
			deChar = 'E';
		} else if (origChar == '3') {
			deChar = 'I';
		} else if (origChar == '4') {
			deChar = 'O';
		} else if (origChar == '5') {
			deChar = 'U';
		} else if (origChar == '6') {
			deChar = 'Y';
		}
		return deChar; // return decoded value

	}

	// decode rule four
	private char ruleFourDecode(int previousNum, char origChar) {
		char deChar = '0';
		// shifts the value forward by two times the previous values and loops it back
		// to a (if it goes past z)
		deChar = (char) (((origChar - 'A' + (previousNum * 2)) % 26) + 'A');
		return deChar; // return decoded value

	}

	// decode rule five
	private char ruleFiveDecode(char origChar) {
		char deChar = '\0';
		// numerical values are decoded to their respective vowels
		if (origChar == '3') {
			deChar = 'A';
		} else if (origChar == '4') {
			deChar = 'E';
		} else if (origChar == '5') {
			deChar = 'I';
		} else if (origChar == '6') {
			deChar = 'O';
		} else if (origChar == '1') {
			deChar = 'U';
		} else if (origChar == '2') {
			deChar = 'Y';
		}
		return deChar; // return decoded value

	}

	public static void main(String[] args) {
		WesternCipher wc = new WesternCipher();
		Scanner data = new Scanner(System.in); // scanner is used to take in input
		
		//String input = ((new BufferedReader(new InputStreamReader(System.in)).readLine()));

		System.out.println("Please enter 1 for encode and 2 for decode:");
		// user is prompted to input their choice to to encode or decode
		int input = data.nextInt();
		data.nextLine();

		// if 1 is inputed, program encodes
		if (input == 1) {
			String str = "";

			System.out.println("Please enter a string for the program to encode:");
			str = data.nextLine();
			System.out.println(wc.encode(str));

			System.exit(0);
		}

		// if two is inputed, program decodes
		if (input == 2) {
			String str = "";

			System.out.println("Please enter a string for the program to decode:");
			str = data.nextLine();
			System.out.println(wc.decode(str));

			System.exit(0);

		}

	}
}
