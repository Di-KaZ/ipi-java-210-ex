package com.ipiecoles.java.java210;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
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
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
		List<String> lignes = new ArrayList<String>();
		String ligne;

		ligne = sc.nextLine();
		while (true) {
            if (ligne.equals("FIN"))
                break;
			if (ligneSaisieEstCoherente(ligne) == false)
				continue;
			lignes.add(ligne);
			ligne = sc.nextLine();
		}
        sc.close();
        for (String string : lignes) {
            System.out.println(string);
        }
    }
}
