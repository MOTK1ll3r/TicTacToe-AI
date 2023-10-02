import java.util.Scanner;

public class TicTacToe {
	public static boolean isNum(String str) {
		boolean isNum;
		String d = "0123456789";
		for (int i = 0; i < str.length(); i++) {
			isNum = false;
			for (int j = 0; j < 10; j++) {
				if (str.charAt(i) == d.charAt(j)) {
					isNum = true;
					break;
				}
			}
			if (!isNum) {
				return false;
			}
		}
		return true;
	}
	public static void print(char[] s, boolean h) {
		String l = " | ";
		String E = "\n  ";
		String H = "      ";
		if (h) {
			System.out.println(
				E + s[0] + l + s[1] + l + s[2] +
				H + "1 | 2 | 3" +
				E + "--+---+--" + H + "--+---+--" +
				E + s[3] + l + s[4] + l + s[5] +
				H + "4 | 5 | 6" +
				E + "--+---+--" + H + "--+---+--" +
				E + s[6] + l + s[7] + l + s[8] +
				H + "7 | 8 | 9\n"
			);
		} else {
			System.out.println(
				E + s[0] + l + s[1] + l + s[2] +
				E + "--+---+--" +
				E + s[3] + l + s[4] + l + s[5] +
				E + "--+---+--" +
				E + s[6] + l + s[7] + l + s[8]
			);
		}
	}
	public static void help() {
		System.out.print("0 for exit\n10 for help\n\nYou can choose a number (1-9) as\nshown below, and your symbol \nwill be placed in the specified\nsquare");
	}
	public static int getInp(char c, char[] s) {
		String raw;
		int x;
		System.out.print(c + " >> ");
		Scanner sc = new Scanner(System.in);
		raw = sc.nextLine();
		while (!isNum(raw)) {
			System.out.printf("input must be an number...\n10 for help\n" + c + " >> ");
			raw = sc.nextLine();
		}
		x = Integer.parseInt(raw);
		while (x < 0 || x > 10 || (x != 0 && x != 10 && s[x - 1] != ' ')) {
			System.out.print("invalid input...\n10 for help\n" + c + " >> ");
			raw = sc.nextLine();
			while (!isNum(raw)) {
				System.out.printf("input must be an number...\n10 for help\n" + c + " >> ");
				raw = sc.nextLine();
			}
			x = Integer.parseInt(raw);
		}
		return x;
	}
	public static boolean checkWin(char c, char[] s) {
		return
			((s[0] == c && s[1] == c && s[2] == c) ||
			 (s[3] == c && s[4] == c && s[5] == c) ||
			 (s[6] == c && s[7] == c && s[8] == c) ||
			 (s[0] == c && s[3] == c && s[6] == c) ||
			 (s[1] == c && s[4] == c && s[7] == c) ||
			 (s[2] == c && s[5] == c && s[8] == c) ||
			 (s[0] == c && s[4] == c && s[8] == c) ||
			 (s[2] == c && s[4] == c && s[6] == c));
	}
	public static boolean checkDraw(char[] s) {
		for (int i = 0; i < 9; i++) {
			if (s[i] == ' ') {
				return false;
			}
		}
		return true;
	}
	public static int eval(int pos, char[] s, int d) {
		int score = 0;
		if (checkWin('O', s)) {
			score = 2;
		} else if (checkWin('X', s)) {
			score = -3;
		} else if (checkDraw(s)) {
			score = -1;
		}
		if (d > 4 && score > -3) {
			score--;
		} else if (d < 2) {
			score++;
		}
		return score;
	}
	public static void main(String[] args) {
		char[] s = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
		char c;
		int inp = 0;
		int maxInp;
		int turn = 1;
		int score;
		int maxScore;
		help();
		print(s, true);
		while (true) {
			if (turn % 2 == 1) {
				c = 'X';
				inp = getInp(c, s);
			} else {
				c = 'O';
				maxScore = -4;
				maxInp = -1;
				for (int pos = 0; pos < 9; pos++) {
					if (s[pos] == ' ') {
						s[pos] = 'O';
						if (checkWin('O', s) || checkWin('X', s) || checkDraw(s)) {
							score = eval(pos, s, 0);
						} else {
							score = minimax(s, 8, false, -4, 4);
						}
						s[pos] = ' ';
						if (score > maxScore) {
							maxScore = score;
							maxInp = pos + 1;
						}
					}
				}
				inp = maxInp;
			}
			if (inp == 0) {
				break;
			} else if (inp == 10) {
				help();
				print(s, true);
				continue;
			}
			s[inp - 1] = c;
			if (turn % 2 == 0) {
				System.out.printf("\nO >> " + inp + "\n");
				print(s, true);
			} else {
				print(s, false);
			}
			if (checkWin(c, s)) {
				System.out.println(c + " wins");
				break;
			} else if (checkDraw(s)) {
				System.out.println("\ndraw");
				break;
			}
			turn++;
		}
	}
	public static int minimax(char[] s, int d, boolean isMax, int a, int b) {
		int score = 0;
		int minScore = 4;
		int maxScore = -4;
		for (int pos = 0; pos < 9; pos++) {
			if (s[pos] == ' ') {
				if (isMax) {
					s[pos] = 'O';
				} else {
					s[pos] = 'X';
				}
				if (checkWin('O', s) || checkWin('X', s) || checkDraw(s) || d == 0) {
					if (d == 0) {
						score = 0;
					} else {
						score = eval(pos, s, 8 - d);
					}
				} else {
					if (isMax) {
						score = minimax(s, d - 1, false, a, b);
					} else {
						score = minimax(s, d - 1, true, a, b);
					}
				}
				s[pos] = ' ';
				if (isMax) {
					if (score > maxScore) {
						maxScore = score;
					}
				} else {
					if (score < minScore) {
						minScore = score;
					}
				}
			}
			if (isMax) {
				if (a > score) {
					a = score;
				}
			} else {
				if (b < score) {
					b = score;
				}
			}
			if (b <= a) {
				break;
			}
		}
		if (isMax) {
			return maxScore;
		} else {
			return minScore;
		}
	}
}