package com.ipiecoles.java.java210;

import java.io.Console;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JPopupMenu.Separator;

public class Sudoku {
	public static final String FIN_SAISIE = "FIN";
	boolean resolu = false;
	short[][] sudokuAResoudre;
	/**
	 * Constructeur par défaut
	 */
	public Sudoku() {
		this.sudokuAResoudre = new short[9][9];
	}

	public static boolean ligneSaisieEstCoherente(String ligneSaisie) {
		if (ligneSaisie == null ||
			ligneSaisie.equals("") ||
			ligneSaisie.trim().length() == 0) {
				System.out.print("Les coordonnées du chiffre et/ou sa valeur ne peuvent pas être nulles, vides ou remplies avec des espaces");
				return false;
			}
			if (ligneSaisie.length() != 3) {
				System.out.print("Les coordonnées du chiffre et/ou sa valeur doit faire 3 caractères");
				return false;
			}
			try {
				Integer.parseInt(ligneSaisie);
				char[] xyz = ligneSaisie.toCharArray();
				int x = Character.getNumericValue(xyz[0]);
				int y = Character.getNumericValue(xyz[1]);
				int z = Character.getNumericValue(xyz[2]);
				if (x > 8 || x < 1 ||
					y > 8 || y < 1 ||
					z > 8 || z < 1) {
						System.out.print("L'abscisse et l'ordonnée doivent être compris entre 0 et 8, la valeur entre 1 et 9");
						return false;
					}
			} catch (Exception e) {
				System.out.print("L'abscisse et l'ordonnée doivent être compris entre 0 et 8, la valeur entre 1 et 9");
				return false;
			}
			// System.out.print("L'abscisse et l'ordonnée doivent être compris entre 0 et 8, la valeur entre 1 et 9");
			return true;
	}

	public short[][] getSudokuAResoudre() {
		return sudokuAResoudre;
	}

	public void setSudokuAResoudre(short[][] sudokuAResoudre) {
		this.sudokuAResoudre = sudokuAResoudre;
	}
	/**
	 * Cette méthode invite l'utilisateur à saisir un ensemble de coordonnées pour initialiser un sudoku à résoudre.
	 * Les coordonnées prennent la forme XYZ avec X correspondant à l'abscisse, Y l'ordonnée et Z la valeur. Seules les
	 * chiffres présents sont à saisir et l'utilisateur doit appuyer sur entrée après chaque saisie.
	 * Lorsqu'il a terminé sa saisie, il entre la chaîne FIN. La fonction remplit au fur et à mesure un tableau de String
	 * comportant les coordonnées des chiffres saisis.
	 *
	 * A noter que pour chaque ligne saisie, sa cohérence est vérifiée en appelant la méthode ligneSaisieEstCoherente
	 * En cas de mauvaise saisie, la saisie ne doit pas être prise en compte et l'utilisateur doit pouvoir saisie une nouvelle ligne
	 * La fonction doit également gérer le cas où l'utilisateur ne rentre rien mais appuye sur Entrée
	 *
	 * @return Un tableau comportant les coordonnées des chiffres présents dans le sudoku à résoudre
	 */
	public static String[] demandeCoordonneesSudoku() {
		System.out.println("Loool");
		Scanner sc = new Scanner(System.in);
		List<String> lignes = new ArrayList<String>();
		String ligne;
		do {
			try {
				ligne = sc.nextLine();
			} catch (NoSuchElementException e) {
				sc.close();
				break;
			}
			if (ligne.equals(FIN_SAISIE))
            	break;
			lignes.add(ligne);
		} while (true);
        sc.close();
		return lignes.stream().toArray(String[]::new);
	}

	/**
	 * La méthode prend un tableau de coordonnées de chiffre soud la forme XYZ avec X correspondant
	 * à l'abscisse, Y l'ordonnée et Z la valeur et remplit le tableau sudokuAResoudre avec les bonnes valeurs
	 * au bon endroit. Ex 012, première ligne deuxième colonne, on met la valeur 2. Lorsqu'une valeur nulle est
	 * rencontrée dans le tableau, on arrête le traitement
	 *
	 * Pour passer d'une String à un short, on pourra utiliser la méthode stringToInt(string)
	 *
	 * @param tableauCoordonnees
	 */
	public void remplitSudokuATrous(String[] tableauCoordonnees) {
		for (String string : tableauCoordonnees) {
			char[] xyz = string.toCharArray();
			int x = Character.getNumericValue(xyz[0]);
			int y = Character.getNumericValue(xyz[1]);
			int z = Character.getNumericValue(xyz[2]);
			this.sudokuAResoudre[x][y] = (short)z;
		}
    }

	private int stringToInt(String s) {
		return Integer.parseInt(s);
	}

	/**
	 * Cette méthode affiche un sudoku de manière formatée sur la console.
	 * Cela doit ressembler exactement à :
	 * -----------------------
	 * |   8   | 4   2 |   6   |
	 * |   3 4 |       | 9 1   |
	 * | 9 6   |       |   8 4 |
	 *  -----------------------
	 * |       | 2 1 6 |       |
	 * |       |       |       |
	 * |       | 3 5 7 |       |
	 *  -----------------------
	 * | 8 4   |       |   7 5 |
	 * |   2 6 |       | 1 3   |
	 * |   9   | 7   1 |   4   |
	 *  -----------------------
	 *
	 * @param sudoku tableau de short représentant les valeurs d'un sudoku (résolu ou non).
	 * Ce tableau fait 9 par 9 et contient des chiffres de 0 à 9, 0 correspondant à une valeur
	 * non trouvée (dans ce cas, le programme affiche un blanc à la place de 0
	 */

	public void ecrireSudoku(short[][] sudoku) {
		List<String> lignes = new ArrayList<String>();
		String ligne;

		for (int i = 0; i < 9; i += 1) {
			if (i % 3 == 0)
				lignes.add("-----------------------\n");
			lignes.add(createLigneArray(sudoku[i]));
		}
		lignes.add("-----------------------\n");
		for (String string : lignes) {
			System.out.print(string);
		}

    }

	private String createLigneArray(short[] ligne) {
		CharBuffer buf = CharBuffer.allocate(13 * 3 + 4);

		buf.append('|');
		for  (int i = 0; i < 9; i += 1) {
			if (i % 3 == 0 && i != 0)
				buf.append(" |");
			if (ligne[i] == 0) {
				buf.append("  ");
			}
			else {
				buf.append(' ');
				buf.append(String.valueOf(ligne[i]));
			}
			}
		buf.append(" |\n");
		buf.rewind();
		return buf.toString();
	}
	/**
	 * Cette méthode vérifie si un chiffre est autorisé à la position d'abscisse et
	 * d'ordonnée donnés dans le sudoku en appliquant les règles suivantes :
	 *
	 * 1 : Si la valeur est déjà dans la ligne, le chiffre n'est pas autorisé
	 * 2 : Si le valeur est déjà dans la colone, le chiffre n'est pas autorisé
	 * 3 : Si la valeur est est déjà dans la boite, le chiffre n'est pas autorisé
	 *
	 * @param abscisse
	 * @param ordonnee
	 * @param chiffre
	 * @param sudoku
	 * @return
	 */
	public static boolean estAutorise(int abscisse, int ordonnee, short chiffre, short[][] sudoku) {
		if (sudoku[abscisse][ordonnee] != 0)
			return false;
		for (short s : sudoku[abscisse]) {
			if (s == chiffre)
				return false;
		}
		for (int i = 0; i < 9; i += 1) {
			if (sudoku[i][ordonnee] == chiffre)
				return false;
		}
		int[] boxCoord = getCoordBoxOf(abscisse, ordonnee);

		for (int i = boxCoord[0]; i < boxCoord[0] + 2; i += 1) {
			for (int j = boxCoord[1]; j < boxCoord[1] + 2; j += 1) {
				if (sudoku[i][j] == chiffre)
					return false;
			}
		}
		return true;
    }

	private static int[] getCoordBoxOf(int abscisse, int ordonnee) {
		int boxCoord[] = new int[2];

		if (abscisse < 3)
			boxCoord[0] = 0;
		else if (abscisse >= 3 && abscisse < 6)
			boxCoord[0] = 3;
		else
			boxCoord[0] = 6;

		if (ordonnee < 3)
			boxCoord[1] = 0;
		else if (ordonnee >= 3 && ordonnee < 6)
			boxCoord[1] = 3;
		else
			boxCoord[1] = 6;
		System.out.println(abscisse);
		System.out.println('-');
		System.out.println(ordonnee);
		System.out.println('-');
		System.out.println(boxCoord[0]);
		System.out.println('-');
		return boxCoord;
	}

	public boolean resoudre(int abscisse, int ordonnee, short[][] sudoku) {
		return true;
    }
}