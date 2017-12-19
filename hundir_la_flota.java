import java.io.*;

public class hundir_la_flota{
	final static int MAXI=11;
	final static int MAXJ=11;
	public static void main(String[] args) throws IOException, InterruptedException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String tablero[][], tablero2[][], nombre_jugador1, posiciones[];
		String tableromaq[][], tableromaq2[][];
		String os= System.getProperty("os.name");
		String fila="/ABCDEFGHIJ";
		String posicion, posicionf, validacion;
		String tocado="no";
		
		int Ypos, Yneg, Xpos, Xneg, contador, turno, barcos_hun, barcos_hunr;
		int Y, X;
		int i, j, g, i2, j2;
		posiciones = new String[4];
		tablero = new String[MAXI][MAXJ];
		tablero2 = new String[MAXI][MAXJ];
		tableromaq = new String[MAXI][MAXJ];
		tableromaq2 = new String[MAXI][MAXJ];
		turno=0;
		Y=0;
		X=0;
		barcos_hun=0;
		barcos_hunr=0;
		posicion="0";
		Ypos = 0;
		Yneg = 0;
		Xpos = 0;
		Xneg = 0;		
		//Pide el nombre de los jugadores
		System.out.print("Introduce el nombre del jugador: ");
		nombre_jugador1 = reader.readLine();

		//Inicializo los tablero a 0
		for (i=0;i<MAXI;i++){
			for (j=0;j<MAXJ;j++){
				if (i==0 && j>0) {
					if (j<10) {
						tablero[0][j]="[ "+(j)+"]";
						tablero2[0][j]="[ "+(j)+"]";
					} else {
						tablero[0][j]="["+(j)+"]";
						tablero2[0][j]="["+(j)+"]";						
					}
				} else if (j==0 && i>0){
					tablero[i][0]="["+fila.charAt(i)+"]";
					tablero2[i][0]="["+fila.charAt(i)+"]";
				} else {
					tablero[i][j]="|  |";
					tablero2[i][j]="|  |";
				}
			}
		}
		tablero[0][0]="   ";
		tablero2[0][0]="   ";
		for (i=0;i<MAXI;i++){
			for (j=0;j<MAXJ;j++){
					tableromaq[i][j]="| |";
					tableromaq2[i][j]="| |";
			}
		}
		
		//Limpiar la consola
		if (os.contains("Windows")) {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} else {
            System.out.print("\u001b[2J" + "\u001b[H");
            System.out.flush();
		}

		//Imprimo el tablero
		for (i=0;i<MAXI;i++){
			for (j=0;j<MAXJ;j++){
				System.out.print(tablero[i][j]);
			}
			System.out.print("     ");
			for (j=0;j<MAXJ;j++){
				System.out.print(tablero2[i][j]);
			}
			System.out.println();
		}
		
		//El jugador pone 2 portaaviones(4 casillas), 3 buques(2 casillas) y 4 lanchas(1 casilla)
		for (g=1;g<3;g++){
			for (i=0;i<4;i++) {
				posiciones[i]="0";
			}
			validacion = "no-valido";
			
			/* El formato de posición es letra+número, otro tipo de dato
			 * introducido no se permitirá, el valor máximo de letra será
			 * J y el valor máximo de número será 10 */
			do {
				Ypos = 0;
				Yneg = 0;
				Xpos = 0;
				Xneg = 0;				
				System.out.print("Introduce la primera casilla del portaaviones "+g+": ");
				posicion=reader.readLine();
				if (posicion.length()<2 || posicion.length()>2 && !posicion.substring(1).equals("10") || fila.indexOf(posicion.charAt(0))==-1 || !Character.isDigit(posicion.charAt(1))) {
					System.out.print("Error, el formato valido es letra+numero ej.A1");
				} else{
						Y = fila.indexOf(posicion.charAt(0));
						X = Integer.parseInt(posicion.substring(1));
						if (!tablero[Y][X].equals("|  |")){
							System.out.print("Error, la casilla ya tiene un barco.");
						}
				}
				
				/* Comprueba que en el eje vertical y en el eje horizontal
				 * quepa el portaaviones */				
				for (i=0;i<4;i++){
					if ((Y+3)<11) {
						if (!tablero[Y+i][X].equals("|  |")) {
							Ypos=1;
						}
					} else {
						Ypos=1;
					}
					if ((Y-3)>0) {
						if (!tablero[Y-i][X].equals("|  |")) {
							Yneg=1;
						}
					} else {
						Yneg=1;
					}
					if ((X+3)<11) {
						if (!tablero[Y][X+i].equals("|  |")) {
							Xpos=1;
						}
					} else {
						Xpos=1;
					}
					if ((X-3)>0) {
						if (!tablero[Y][X-i].equals("|  |")) {
							Xneg=1;
						}
					} else {
						Xneg=1;
					}
					if (Ypos==1 && Yneg==1 && Xpos==1 && Ypos==1) {
						System.out.print("Error, el portaaviones no va a caber. ");
					}	
				}					
			} while ((posicion.length()<2 || posicion.length()>2 && !posicion.substring(1).equals("10") || fila.indexOf(posicion.charAt(0))==-1 || !Character.isDigit(posicion.charAt(1)) || !tablero[Y][X].equals("|  |")) || (Ypos==1 && Yneg==1 && Xpos==1 && Ypos==1));
			
			/* Guarda las posiciones posibles donde poner la otra punta
			 * del portaaviones */			
			if (Xpos==0){
				posiciones[0]=String.valueOf(fila.charAt(Y))+String.valueOf(X+3);
			}
			if (Ypos==0){
				posiciones[1]=String.valueOf(fila.charAt(Y+3))+String.valueOf(X);
			}
			if (Xneg==0){
				posiciones[2]=String.valueOf(fila.charAt(Y))+String.valueOf(X-3);
			}
			if (Yneg==0){
				posiciones[3]=String.valueOf(fila.charAt(Y-3))+String.valueOf(X);
			}
			System.out.println();
			do {
				
				/* Pide donde colocar la otra punta del portaaviones
				 * indicando las posiciones posibles */
				System.out.print("Selecciona donde colocar la otra punta del portaaviones: ");
				for (i=0;i<4;i++) {
					if (posiciones[i]!="0") {
						System.out.print(posiciones[i]+" ");
					}
				}
				posicionf=reader.readLine();
				for (i=0;i<4;i++) {
					if (posicionf.equals(posiciones[i]) && !posicionf.equals("0")) {
						validacion="valido";
						break;
					}
				}
				
			/* Esto se repite hasta que el jugador ponga una de las 
			 * posiciones posibles indicadas */
			} while (validacion!="valido");
			
			/* Si se selecciona una de las posibles posiciones se
			 * coloca el portaaviones */
			for (i=0;i<4;i++){
				if (posicionf.equals(posiciones[i])) {
					if (posicion.charAt(0)<posicionf.charAt(0)) {
						for (j=Y;j<=Y+3;j++) {
							tablero[j][X]="|P"+g+"|";
						}
						break;
					} else if (posicion.charAt(0)>posicionf.charAt(0)) {
						for (j=Y;j>=Y-3;j--) {
							tablero[j][X]="|P"+g+"|";
						}
						break;
					} else if (X<Integer.parseInt(posicionf.substring(1,posicionf.length()))) {
						for (j=X;j<=X+3;j++) {
							tablero[Y][j]="|P"+g+"|";
						}
						break;
					} else if (X>Integer.parseInt(posicionf.substring(1,posicionf.length()))) {
						for (j=X;j>=X-3;j--) {
							tablero[Y][j]="|P"+g+"|";
						}
						break;
					}
				} 
			}
			
			//Limpio pantalla e imprimo tableros
			if (os.contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				System.out.print("\u001b[2J" + "\u001b[H");
				System.out.flush();
			}
			for (i=0;i<MAXI;i++){
				for (j=0;j<MAXJ;j++){
					System.out.print(tablero[i][j]);
				}
				System.out.print("     ");
				for (j=0;j<MAXJ;j++){
					System.out.print(tablero2[i][j]);
				}
				System.out.println();
			}
		}

		/* buques (El código es igual al del portaaviones pero ajustado
		 * al tamaño del buque */		
		for (g=1;g<4;g++){
			for (i=0;i<4;i++) {
				posiciones[i]="0";
			}
			validacion = "no-valido";
			do {
				Ypos = 0;
				Yneg = 0;
				Xpos = 0;
				Xneg = 0;				
				System.out.print("Introduce la primera casilla del buque "+g+": ");
				posicion=reader.readLine();
				if (posicion.length()<2 || posicion.length()>2 && !posicion.substring(1).equals("10") || fila.indexOf(posicion.charAt(0))==-1 || !Character.isDigit(posicion.charAt(1))) {
					System.out.print("Error, el formato valido es letra+numero ej.A1");
				} else {
					Y = fila.indexOf(posicion.charAt(0));
					X = Integer.parseInt(posicion.substring(1));
					if (!tablero[Y][X].equals("|  |")){
						System.out.print("Error, la casilla ya tiene un barco.");
					}
				}
				for (i=0;i<3;i++){
					if ((Y+2)<11) {
						if (!tablero[Y+i][X].equals("|  |")) {
							Ypos=1;
						}
					} else {
						Ypos=1;
					}
					if ((Y-2)>0) {
						if (!tablero[Y-i][X].equals("|  |")) {
							Yneg=1;
						}
					} else {
						Yneg=1;
					}
					if ((X+2)<11) {
						if (!tablero[Y][X+i].equals("|  |")) {
							Xpos=1;
						}
					} else {
						Xpos=1;
					}
					if ((X-2)>0) {
						if (!tablero[Y][X-i].equals("|  |")) {
							Xneg=1;
						}
					} else {
						Xneg=1;
					}
				}
				if (Ypos==1 && Yneg==1 && Xpos==1 && Ypos==1) {
					System.out.print("Error, el buque no va a caber ");
				}						
			} while ((posicion.length()<2 || posicion.length()>2 && !posicion.substring(1).equals("10") || fila.indexOf(posicion.charAt(0))==-1 || !Character.isDigit(posicion.charAt(1)) || !tablero[Y][X].equals("|  |")) || (Ypos==1 && Yneg==1 && Xpos==1 && Ypos==1));			
			if (Xpos==0){
				posiciones[0]=String.valueOf(fila.charAt(Y))+String.valueOf(X+2);
			}
			if (Ypos==0){
				posiciones[1]=String.valueOf(fila.charAt(Y+2))+String.valueOf(X);
			}
			if (Xneg==0){
				posiciones[2]=String.valueOf(fila.charAt(Y))+String.valueOf(X-2);
			}
			if (Yneg==0){
				posiciones[3]=String.valueOf(fila.charAt(Y-2))+String.valueOf(X);
			}
			System.out.println();
			do {
				System.out.print("Selecciona donde colocar la otra punta del buque: ");
				for (i=0;i<4;i++) {
					if (posiciones[i]!="0") {
						System.out.print(posiciones[i]+" ");
					}
				}
				posicionf=reader.readLine();
				for (i=0;i<4;i++) {
					if (posicionf.equals(posiciones[i]) && !posicionf.equals("0")) {
						validacion="valido";
						break;
					}
				}
			} while (validacion!="valido");
			for (i=0;i<4;i++){
				if (posicionf.equals(posiciones[i])) {
					if (posicion.charAt(0)<posicionf.charAt(0)) {
						for (j=Y;j<=Y+2;j++) {
							tablero[j][X]="|B"+g+"|";
						}
						break;
					} else if (posicion.charAt(0)>posicionf.charAt(0)) {
						for (j=Y;j>=Y-2;j--) {
							tablero[j][X]="|B"+g+"|";
						}
						break;
					} else if (X<Integer.parseInt(posicionf.substring(1,posicionf.length()))) {
						for (j=X;j<=X+2;j++) {
							tablero[Y][j]="|B"+g+"|";
						}
						break;
					} else if (X>Integer.parseInt(posicionf.substring(1,posicionf.length()))) {
						for (j=X;j>=X-2;j--) {
							tablero[Y][j]="|B"+g+"|";
						}
						break;
					}
				} 
			}
			if (os.contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				System.out.print("\u001b[2J" + "\u001b[H");
				System.out.flush();
			}
			for (i=0;i<MAXI;i++){
				for (j=0;j<MAXJ;j++){
					System.out.print(tablero[i][j]);
				}
				System.out.print("     ");
				for (j=0;j<MAXJ;j++){
					System.out.print(tablero2[i][j]);
				}
				System.out.println();
			}
		}

		//lanchas
		for (g=1;g<5;g++){
			validacion = "no-valido";
			Ypos = 0;
			Yneg = 0;
			Xpos = 0;
			Xneg = 0;
			
			/* Pide una posición y si es valida pone la lancha */
			do {
				System.out.print("Introduce la casilla donde colocar la lancha "+g+": ");
				posicion=reader.readLine();
				if (posicion.length()<2 || posicion.length()>2 && !posicion.substring(1).equals("10") || fila.indexOf(posicion.charAt(0))==-1 || !Character.isDigit(posicion.charAt(1))) {
					System.out.print("Error, el formato valido es letra+numero ej.A1");
				} else {
						Y = fila.indexOf(posicion.charAt(0));
						X = Integer.parseInt(posicion.substring(1));
						if (!tablero[Y][X].equals("|  |")){
							System.out.print("Error, la casilla ya tiene un barco.");
						}
				}
			} while (posicion.length()<2 || posicion.length()>2 && !posicion.substring(1).equals("10") || fila.indexOf(posicion.charAt(0))==-1 || !Character.isDigit(posicion.charAt(1)) || !tablero[Y][X].equals("|  |"));
			tablero[Y][X]="|L"+g+"|";
			if (os.contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
	            System.out.print("\u001b[2J" + "\u001b[H");
				System.out.flush();
			}
			for (i=0;i<MAXI;i++){
				for (j=0;j<MAXJ;j++){
					System.out.print(tablero[i][j]);
				}
				System.out.print("     ");
				for (j=0;j<MAXJ;j++){
					System.out.print(tablero2[i][j]);
				}
				System.out.println();
			}
		} 
		
		//La máquina coloca los barcos
		//Portaaviones
		for (g=1;g<3;g++){
			for (i=0;i<4;i++) {
				posiciones[i]="0";
			}
			validacion = "no-valido";
			
			/* Genera una posición aleatoria y si es posible continua */
			do {
				Ypos = 0;
				Yneg = 0;
				Xpos = 0;
				Xneg = 0;	
				posicion=fila.charAt((int)(Math.random()*(11-1)+1))+String.valueOf((int)(Math.random()*(11-1)+1)); 
				Y = fila.indexOf(posicion.charAt(0));
				X = Integer.parseInt(posicion.substring(1));
				for (i=0;i<4;i++){
					if ((Y+3)<11) {
						if (!tableromaq[Y+i][X].equals("| |")) {
							Ypos=1;
						}
					} else {
						Ypos=1;
					}
					if ((Y-3)>0) {
						if (!tableromaq[Y-i][X].equals("| |")) {
							Yneg=1;
						}
					} else {
						Yneg=1;
					}
					if ((X+3)<11) {
						if (!tableromaq[Y][X+i].equals("| |")) {
							Xpos=1;
						}
					} else {
						Xpos=1;
					}
					if ((X-3)>0) {
						if (!tableromaq[Y][X-i].equals("| |")) {
							Xneg=1;
						}
					} else {
						Xneg=1;
					}
				}					
			}while (!tableromaq[Y][X].equals("| |") || (Ypos!=1 && Yneg!=1 && Xpos!=1 && Ypos!=1));	
			
			/* Guarda las posibles posiciones para la otra punta */		
			if (Xpos==0){
				posiciones[0]=String.valueOf(fila.charAt(Y))+String.valueOf(X+3);
			}
			if (Ypos==0){
				posiciones[1]=String.valueOf(fila.charAt(Y+3))+String.valueOf(X);
			}
			if (Xneg==0){
				posiciones[2]=String.valueOf(fila.charAt(Y))+String.valueOf(X-3);
			}
			if (Yneg==0){
				posiciones[3]=String.valueOf(fila.charAt(Y-3))+String.valueOf(X);
			}
			
			/* Elige aleatoriamente una de las 4 posibles posiciones para
			 * la otra punta */
			do {
				posicionf=posiciones[(int)(Math.random()*4)];
				for (i=0;i<4;i++) {
					if (posicionf.equals(posiciones[i]) && !posicionf.equals("0")) {
						validacion="valido";
						break;
					}
				}
			} while (validacion!="valido");
			
			/* Coloca el portaaviones */
			for (i=0;i<4;i++){
				if (posicionf.equals(posiciones[i])) {
					if (posicion.charAt(0)<posicionf.charAt(0)) {
						for (j=Y;j<=Y+3;j++) {
								tableromaq[j][X]="|P"+g;
						}
						break;
					} else if (posicion.charAt(0)>posicionf.charAt(0)) {
						for (j=Y;j>=Y-3;j--) {
								tableromaq[j][X]="|P"+g;
						}
						break;
					} else if (X<Integer.parseInt(posicionf.substring(1,posicionf.length()))) {
						for (j=X;j<=X+3;j++) {
								tableromaq[Y][j]="|P"+g;
						}
						break;
					} else if (X>Integer.parseInt(posicionf.substring(1,posicionf.length()))) {
						for (j=X;j>=X-3;j--) {
								tableromaq[Y][j]="|P"+g;
						}
						break;
					}
				} 
			}
		}//for_portaaviones_maq
		
		/* buques, el código es igual al de portaaviones ajustado para el
		 * tamaño de los buques */
		for (g=1;g<4;g++){
			for (i=0;i<4;i++) {
				posiciones[i]="0";
			}
			validacion = "no-valido";
			do {
				Ypos = 0;
				Yneg = 0;
				Xpos = 0;
				Xneg = 0;	
				posicion=fila.charAt((int)(Math.random()*(11-1)+1))+String.valueOf((int)(Math.random()*(11-1)+1)); 
				Y = fila.indexOf(posicion.charAt(0));
				X = Integer.parseInt(posicion.substring(1));
				for (i=0;i<3;i++){
					if ((Y+2)<11) {
						if (!tableromaq[Y+i][X].equals("| |")) {
							Ypos=1;
						}
					} else {
						Ypos=1;
					}
					if ((Y-2)>0) {
						if (!tableromaq[Y-i][X].equals("| |")) {
							Yneg=1;
						}
					} else {
						Yneg=1;
					}
					if ((X+2)<11) {
						if (!tableromaq[Y][X+i].equals("| |")) {
							Xpos=1;
						}
					} else {
						Xpos=1;
					}
					if ((X-2)>0) {
						if (!tableromaq[Y][X-i].equals("| |")) {
							Xneg=1;
						}
					} else {
						Xneg=1;
					}
				}				
			}while (!tableromaq[Y][X].equals("| |") || (Ypos!=1 && Yneg!=1 && Xpos!=1 && Ypos!=1));				
			if (Xpos==0){
				posiciones[0]=String.valueOf(fila.charAt(Y))+String.valueOf(X+2);
			}
			if (Ypos==0){
				posiciones[1]=String.valueOf(fila.charAt(Y+2))+String.valueOf(X);
			}
			if (Xneg==0){
				posiciones[2]=String.valueOf(fila.charAt(Y))+String.valueOf(X-2);
			}
			if (Yneg==0){
				posiciones[3]=String.valueOf(fila.charAt(Y-2))+String.valueOf(X);
			}
			do {
				posicionf=posiciones[(int)(Math.random()*4)];
				for (i=0;i<4;i++) {
					if (posicionf.equals(posiciones[i]) && !posicionf.equals("0")) {
						validacion="valido";
						break;
					}
				}
			} while (validacion!="valido");
			for (i=0;i<4;i++){
				if (posicionf.equals(posiciones[i])) {
					if (posicion.charAt(0)<posicionf.charAt(0)) {
						for (j=Y;j<=Y+2;j++) {
								tableromaq[j][X]="|B"+g;
						}
						break;
					} else if (posicion.charAt(0)>posicionf.charAt(0)) {
						for (j=Y;j>=Y-2;j--) {
								tableromaq[j][X]="|B"+g;
						}
						break;
					} else if (X<Integer.parseInt(posicionf.substring(1,posicionf.length()))) {
						for (j=X;j<=X+2;j++) {
								tableromaq[Y][j]="|B"+g;
						}
						break;
					} else if (X>Integer.parseInt(posicionf.substring(1,posicionf.length()))) {
						for (j=X;j>=X-2;j--) {
								tableromaq[Y][j]="|B"+g;
						}
						break;
					}
				} 
			}
		}
		
		//lanchas
		for (g=1;g<5;g++){
			do {
				posicion=fila.charAt((int)(Math.random()*(11-1)+1))+String.valueOf((int)(Math.random()*(11-1)+1)); 
				Y = fila.indexOf(posicion.charAt(0));
				X = Integer.parseInt(posicion.substring(1));
			}while (!tableromaq[Y][X].equals("| |")); 
			tableromaq[Y][X]="|L"+g;
		}
		
		//PARTIDA	
		while(barcos_hun<9 && barcos_hunr<9) {
			
			//Este switch alterna los turnos entre el jugador y la máquina
			switch (turno) {
				
				//Turno jugador
				case 0:
					
					//limpiar la consola
					if (os.contains("Windows")) {
							new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
					} else {
							System.out.print("\u001b[2J" + "\u001b[H");
							System.out.flush();
					}
					
					//Imprimir tableros
					for (i=0;i<MAXI;i++){
						for (j=0;j<MAXJ;j++){
							System.out.print(tablero[i][j]);
						}
						System.out.print("     ");
						for (j=0;j<MAXJ;j++){
							System.out.print(tablero2[i][j]);
						}
						System.out.println();
					}
					
					do {
						//El jugador selecciona una casilla para atacar
						System.out.print(nombre_jugador1+" es tu turno! Elige una casilla para atacar al enemigo: ");
						posicion=reader.readLine();			
						
						//Muestra error en caso de introducir posición no valida
						if (posicion.length()<2 || posicion.length()>2 && !posicion.substring(1).equals("10") || fila.indexOf(posicion.charAt(0))==-1 || !Character.isDigit(posicion.charAt(1))) {
							System.out.print("Error, el formato valido es letra+numero ej.A1");
						} 
						
						//Guardo la posicion en 2 enteros para
						 else {
							Y = fila.indexOf(posicion.charAt(0));
							X = Integer.parseInt(posicion.substring(1));
						}
					} while (posicion.length()<2 || posicion.length()>2 && !posicion.substring(1).equals("10") || fila.indexOf(posicion.charAt(0))==-1 || !Character.isDigit(posicion.charAt(1)) || !tablero2[Y][X].equals("| |") && !tablero2[Y][X].equals("|  |"));
					contador=0;
					
					// Comprueba si hay un barco en la posición indicada					
					if (!tableromaq[Y][X].equals("| |")) {
						System.out.print("Tocado");
						tablero2[Y][X]="| T|";
						
						/*Compruebo que tipo de barco es por la letra
						 * con la que comienza*/
						 
						 //lancha
						if (tableromaq[Y][X].startsWith("|L")) {
							tableromaq[Y][X]="|H|";
							System.out.print(" y hundido!");
							tablero2[Y][X]="| H|";
							barcos_hun++;
							
						 //portaaviones
						} else if (tableromaq[Y][X].startsWith("|P")) {
							for (g=1;g<3;g++) {
								if (tableromaq[Y][X].endsWith(String.valueOf(g))) {
									tableromaq[Y][X]="TP"+g;
									
									/* Compruebo las posiciones en vertical
									 * y horizontal y guardo en un contador
									 * las cantidad de veces que se ha
									 * tocado el portaaviones */
									for (i=1;i<4;i++) {
										if ((Y+i)<11) {
											if (tableromaq[Y+i][X].equals("TP"+g)) {
												contador++;
											}
										}
										if ((Y-i)>0) {
											if (tableromaq[Y-i][X].equals("TP"+g)) {
												contador++;
											}
										}
										if ((X+i)<11) {
											if (tableromaq[Y][X+i].equals("TP"+g)) {
												contador++;
											}
										}
										if ((X-i)>0) {
											if (tableromaq[Y][X-i].equals("TP"+g)) {
												contador++;
											}
										}
									}
								}
								
								/* Compruebo ayudandome del contador
								 * si se ha hundido el barco que se ha
								 * tocado */
								if (contador==3) {
									System.out.print(" y hundido!");
									barcos_hun++;
									for (i=0;i<4;i++) {
										if ((Y+i)<11) {
											if (tableromaq[Y+i][X].equals("TP"+g)) {
												tablero2[Y+i][X]="| H|";
											}
										}
										if ((Y-i)>0) {
											if (tableromaq[Y-i][X].equals("TP"+g)) {
												tablero2[Y-i][X]="| H|";
											}
										}
										if ((X+i)<11) {
											if (tableromaq[Y][X+i].equals("TP"+g)) {
												tablero2[Y][X+i]="| H|";
											}
										}
										if ((X-i)>0) {
											if (tableromaq[Y][X-i].equals("TP"+g)) {
												tablero2[Y][X-i]="| H|";
											}
										}
									}
								}
								//Reinicio el contador	
								contador=0;	
							}
						
						 //buques							
						} else if (tableromaq[Y][X].startsWith("|B")) {
							for (g=1;g<4;g++) {
								if (tableromaq[Y][X].endsWith(String.valueOf(g))) {
									tableromaq[Y][X]="TB"+g;
									
									/* Compruebo las posiciones en vertical
									 * y horizontal y guardo en un contador
									 * las cantidad de veces que se ha
									 * tocado el buque */
									for (i=1;i<3;i++) {
										if ((Y+i)<11) {
											if (tableromaq[Y+i][X].equals("TB"+g)) {
												contador++;
											}
										}
										if ((Y-i)>0) {
											if (tableromaq[Y-i][X].equals("TB"+g)) {
												contador++;
											}
										}
										if ((X+i)<11) {
											if (tableromaq[Y][X+i].equals("TB"+g)) {
												contador++;
											}
										}
										if ((X-i)>0) {
											if (tableromaq[Y][X-i].equals("TB"+g)) {
												contador++;
											}
										}
									}
								}
					
								/* Compruebo ayudandome del contador
								 * si se ha hundido el barco que se ha
								 * tocado */
								if (contador==2) {
									System.out.print(" y hundido!");
									barcos_hun++;
									for (i=0;i<3;i++) {
										if ((Y+i)<11) {
											if (tableromaq[Y+i][X].equals("TB"+g)) {
												tablero2[Y+i][X]="| H|";
											}
										}
										if ((Y-i)>0) {
											if (tableromaq[Y-i][X].equals("TB"+g)) {
												tablero2[Y-i][X]="| H|";
											}
										}
										if ((X+i)<11) {
											if (tableromaq[Y][X+i].equals("TB"+g)) {
												tablero2[Y][X+i]="| H|";
											}
										}
										if ((X-i)>0) {
											if (tableromaq[Y][X-i].equals("TB"+g)) {
												tablero2[Y][X-i]="| H|";
											}
										}
									}
								}
								//Reinicio el contador
								contador=0;		
								}						
							}
						} 
						
						//Si no hay nada en la casilla se marca agua
						else {
							System.out.print("Agua");
							tablero2[Y][X]="| A|";
						}
						System.out.println();
						break;
						
				//Turno máquina	
				case 1:
					do {
						
						/* Reaprobecho la variable g para controlar
						 * el flujo más adelante */
						g=0;
						
						/* La máquina comprueba si ha tocado algun barco
						 * en los anteriores turnos */
						for (i=1;i<MAXI;i++) {
							for(j=1;j<MAXJ;j++) {
								if (tableromaq2[i][j].equals("|T|")) {
									
									/* Asigno el valor 10 a "g" si se ha
									 * encontrado algún barco tocado
									 * para luego evitar que genere una
									 * posición aleatoria */
									g=10;					
									
									/* Guarda las posiciones, siempre
									 * que sean validas, de el eje
									 * vertical y el horizontal, en caso
									 * de haber dos tocados seguidos
									 * comprueba si estan en vertical o
									 * horizontal y lo marca */				
									if (i+1<11 && (Ypos!=1)) {	
										posiciones[0]=fila.charAt(i+1)+String.valueOf(j);
										if (tableromaq2[i+1][j].equals("|T|")) {
											tocado="vertical";
										}
									} else {
										posiciones[0]="0";
									}
									if (i-1>0 && (Yneg!=1)) {
										posiciones[1]=fila.charAt(i-1)+String.valueOf(j);
										if (tableromaq2[i-1][j].equals("|T|")) {
											tocado="vertical";
										}
									} else {
										posiciones[1]="0";
									}
									if (j+1<11 && (Xpos!=1)) {
										posiciones[2]=fila.charAt(i)+String.valueOf(j+1);
										if (tableromaq2[i][j+1].equals("|T|")) {
											tocado="horizontal";
										}
									} else {
										posiciones[2]="0";
									}
									if (j-1>0 && (Xneg!=1)) {
										posiciones[3]=fila.charAt(i)+String.valueOf(j-1);
										if (tableromaq2[i][j-1].equals("|T|")) {
											tocado="horizontal";
										}
									} else {
										posiciones[3]="0";
									}
									
									/* Reinicio las siguientes variables
									 * que reutilizaré para controlar el
									 * flujo */
									Yneg=0;
									Ypos=0;
									Xneg=0;
									Xpos=0;	
									
									/* Si hay más de un tocado en el
									 * tablero de comprobación de la
									 * máquina y estan posicionados
									 * verticalmente entrará aqui */									
									if (tocado.equals("vertical")) {
										do {
											
											/* Ypos será 1 si desde la
											 * posicion para arriba es
											 * imposible que continue el
											 * barco y Yneg lo mismo para
											 * abajo, en caso de que sean
											 * los dos 0 se empezará a 
											 * probar aleatoriamente en
											 * el eje vertical*/
											if (Ypos==1) {
												j2=1;
											} else if (Yneg==1) {
												j2=0;
											} else {
												j2=(int)(Math.random()*2);
											}																																		
											switch (j2) {											
												case 0:	
												
													/* La máquina prueba
													 * las posiciones
													 * hacia arriba 
													 * si es posible */
													for (i2=i+1;i2<i+4;i2++) {		
														if (i2>10) {
															Ypos=1;
															break;
														} else if(tableromaq2[i2][j].equals("|T|") && i2!=i+3){
															continue;
														} else if(tableromaq2[i2][j].equals("| |")) {
															posicion=fila.charAt(i2)+String.valueOf(j);
															Yneg=0;
															Ypos=0;
															break;
														} else {
															Ypos=1;
															break;
														}
													}
													break;
													
												case 1:		
													/* La máquina prueba
													 * las posiciones
													 * hacia abajo
													 * si es posible */
													for (i2=i-1;i2>i-4;i2--) {
														if (i2<1) {
															Yneg=1;
															break;
														} else if(tableromaq2[i2][j].equals("|T|") && i2!=i-3) {
															continue;
														} else if(tableromaq2[i2][j].equals("| |")) {
															posicion=fila.charAt(i2)+String.valueOf(j);
															Yneg=0;
															Ypos=0;
															break;
														} else {
															Yneg=1;
															break;														
														}
													}
													break;
											}
										/* El bucle continua mientras
										 * una de las direcciones sea
										 * posible */ 
										} while (Yneg!=Ypos);
										
										/* Si ninguna dirección es posible,
										 * la máquina marcará horizontal
										 * para hacer pruebas en el otro eje */
										if (Yneg==1 && Ypos==1) {
											tocado="horizontal";
											j--;
											continue;
										}
									}
									/* Si hay más de un tocado en el
									 * tablero de comprobación de la
									 * máquina y estan posicionados
									 * horizontalmente entrará aqui */									
									 else if (tocado.equals("horizontal")) {
										do {
											/* Xpos será 1 si desde la
											 * posicion para arriba es
											 * imposible que continue el
											 * barco y Xneg lo mismo para
											 * abajo, en caso de que sean
											 * los dos 0 se empezará a 
											 * probar aleatoriamente en
											 * el eje vertical*/											
											if (Xpos==1) {
												j2=1;
											} else if (Xneg==1) {
												j2=0;
											} else {
												j2=(int)(Math.random()*2);
											}
											switch (j2) {
												case 0:
													/* La máquina prueba
													 * las posiciones
													 * hacia la derecha
													 * si es posible */
													for (i2=j+1;i2<j+4;i2++) {
														if (i2>10) {
															Xpos=1;
															break;
														} else if(tableromaq2[i][i2].equals("|T|") && i2!=j+3){
															continue;
														} else if(tableromaq2[i][i2].equals("| |")) {
															posicion=fila.charAt(i)+String.valueOf(i2);
															Xneg=0;
															Xpos=0;																
															break;
														} else {
															Xpos=1;
															break;															
														}
													}
													break;
													
													/* La máquina prueba
													 * las posiciones
													 * hacia la izquierda
													 * si es posible */													
												case 1:
													for (i2=j-1;i2>j-4;i2--) {
														if (i2<1) {
															Xneg=1;
															break;
														} else if(tableromaq2[i][i2].equals("|T|") && i2!=j-3) {
															continue;
														} else if(tableromaq2[i][i2].equals("| |")) {
															posicion=fila.charAt(i)+String.valueOf(i2);
															Xneg=0;
															Xpos=0;																
															break;
														} else { 
															Xneg=1;
															break;
														}
													}
													break;
											} 
											
										/* El bucle continua mientras
										 * una de las direcciones sea
										 * posible */ 											
										} while (Xneg!=Xpos);

										/* Si ninguna dirección es posible,
										 * la máquina marcará vertical
										 * para hacer pruebas en el otro eje */										
										if (Xpos==1 && Xneg==1) {
											tocado="vertical";
											j--;
											continue;
										}
								  	}
								  	 /* Si la máquina no sabe si el barco
								  	  * esta en vertical o horizontal
								  	  * prueba una de las cuatro posibles
								  	  * posiciones de ambos ejes */
								  	 else {
											do {
												posicion=posiciones[(int)(Math.random()*4)];
												
											/* Cuando una posicion no era
											 * posible se guardaba como 0,
											 * asi que esta parte se repite
											 * hasta que posicion no sea 0 */
											} while (posicion.equals("0"));	
										}
									}
									
									//Si todo es correcto se sale del bucle
									if (tableromaq2[fila.indexOf(posicion.charAt(0))][Integer.parseInt(posicion.substring(1))].equals("| |") && g==10) {
										break;
									}
								}
								if (tableromaq2[fila.indexOf(posicion.charAt(0))][Integer.parseInt(posicion.substring(1))].equals("| |") && g==10) {
									break;
								}
							}
							
						/* Si g no es igual a 10 significa que no hay
						 * ningún barco tocado, asi que se genera una
						 * posición aleatoria */
						if (g!=10) {											
							posicion=fila.charAt((int)(Math.random()*(11-1)+1))+String.valueOf((int)(Math.random()*(11-1)+1)); 
						}
						Y = fila.indexOf(posicion.charAt(0));
						X = Integer.parseInt(posicion.substring(1));
					} while (!tableromaq2[Y][X].equals("| |"));
					contador=0;
					System.out.println("El rival ha elegido la casilla "+posicion);
					
					//Compruebo si la máquina ha tocado algún barco					
					if (!tablero[Y][X].equals("|  |")) {						
						tableromaq2[Y][X]="|T|";
						tocado="si";
						
						//lancha, si toca una lancha la hunde directamente
						if (tablero[Y][X].startsWith("|L")) {
							tablero[Y][X]="| H|";	
							tableromaq2[Y][X]="|H|";
							System.out.println("El rival te ha hundido una lancha!");
							barcos_hunr++;
							tocado="no";
						}
						
						//portaaviones
						else if (tablero[Y][X].startsWith("|P")) {
							System.out.println("Te han tocado un portaaviones");
							
							/* Compruebo cuantas veces han tocado al mismo
							 * portaaviones */
							for (g=1;g<3;g++) {
								if (tablero[Y][X].endsWith(String.valueOf(g)+"|")) {
									tablero[Y][X]="|TP"+g;
									for (i=1;i<4;i++) {
										if ((Y+i)<11) {
											if (tablero[Y+i][X].equals("|TP"+g)) {
												contador++;
											}
										}
										if ((Y-i)>0) {
											if (tablero[Y-i][X].equals("|TP"+g)) {
												contador++;
											}
										}
										if ((X+i)<11) {
											if (tablero[Y][X+i].equals("|TP"+g)) {
												contador++;
											}
										}
										if ((X-i)>0) {
											if (tablero[Y][X-i].equals("|TP"+g)) {
												contador++;
											}
										}
									}
								}
								
								/* Si han tocado a un portaaviones 4,
								 * (el contador no cuenta la ultima posicion
								 * puesta por la máquina)
								 * veces significa que lo han hundido */
								if (contador==3) {
									for (i=0;i<4;i++) {
										if ((Y+i)<11) {
											if (tablero[Y+i][X].equals("|TP"+g)) {
												tablero[Y+i][X]="| H|";
												tableromaq2[Y+i][X]="|H|";
											}
										}
										if ((Y-i)>0) {
											if (tablero[Y-i][X].equals("|TP"+g)) {
												tablero[Y-i][X]="| H|";
												tableromaq2[Y-i][X]="|H|";
											}
										}
										if ((X+i)<11) {
											if (tablero[Y][X+i].equals("|TP"+g)) {
												tablero[Y][X+i]="| H|";
												tableromaq2[Y][X+i]="|H|";
											}
										}
										if ((X-i)>0) {
											if (tablero[Y][X-i].equals("|TP"+g)) {
												tablero[Y][X-i]="| H|";
												tableromaq2[Y][X-i]="|H|"; 
											}
										}
									}
									
									/* Incremento un contador de barcos
									 * hundidos con el que se determinará
									 * quien gana */
									System.out.println("El rival te ha hundido un portaaviones!");
									barcos_hunr++;
									tocado="no";
								}	
								contador=0;	
							}
							
						//buque						
						} else if (tablero[Y][X].startsWith("|B")) {
							System.out.println("Te han tocado un buque");
							
							/* Compruebo cuantas veces han tocado al mismo
							 * buque */							
							for (g=1;g<4;g++) {
								if (tablero[Y][X].endsWith(String.valueOf(g)+"|")) {
									tablero[Y][X]="|TB"+g;
									for (i=1;i<3;i++) {
										if ((Y+i)<11) {
											if (tablero[Y+i][X].equals("|TB"+g)) {
												contador++;
											}
										}
										if ((Y-i)>0) {
											if (tablero[Y-i][X].equals("|TB"+g)) {
												contador++;
											}
										}
										if ((X+i)<11) {
											if (tablero[Y][X+i].equals("|TB"+g)) {
												contador++;
											}
										}
										if ((X-i)>0) {
											if (tablero[Y][X-i].equals("|TB"+g)) {
												contador++;
											}
										}
									}
								}
								
								/* Si han tocado un mismo buque 3 veces
								 * (el contador no cuenta la ultima posicion
								 * puesta por la máquina)
								 * significa que lo han hundido */
								if (contador==2) {
									for (i=0;i<4;i++) {
										if ((Y+i)<11) {
											if (tablero[Y+i][X].equals("|TB"+g)) {
												tablero[Y+i][X]="| H|";
												tableromaq2[Y+i][X]="|H|";
											}
										}
										if ((Y-i)>0) {
											if (tablero[Y-i][X].equals("|TB"+g)) {
												tablero[Y-i][X]="| H|";
												tableromaq2[Y-i][X]="|H|";
											}
										}
										if ((X+i)<11) {
											if (tablero[Y][X+i].equals("|TB"+g)) {
												tablero[Y][X+i]="| H|";
												tableromaq2[Y][X+i]="|H|";
											}
										}
										if ((X-i)>0) {
											if (tablero[Y][X-i].equals("|TB"+g)) {
												tablero[Y][X-i]="| H|";
												tableromaq2[Y][X-i]="|H|"; 
											}
										}
									}
									
									/* Incremento un contador de barcos
									 * hundidos con el que se determinará
									 * quien gana */									
									System.out.println("El rival te ha hundido un buque!");
									barcos_hunr++;
									tocado="no";
								}	
								contador=0;	
							}						
						}
					
					/* Si no toca ningún barco es agua */	 
					} else {
						tableromaq2[Y][X]="|A|";
					}
						
					/* Reinicio las posiciones si se ha hundido el barco */
					if (tocado.equals("no")) {
						for (i=0;i<4;i++) {
							posiciones[i]="0";
						}
					}
						
					/* Hago una pausa para que el jugador pueda leer
					 * los mensajes */
					System.out.println("Pulsa Enter para continuar");
					reader.readLine();
					break;				
		}
		
		//Se cambia el turno con la siguiente condición
		if (turno==0) {
			turno=1;
		} else {
			turno=0;
		}		
	}
	
	//Si barcos_hun es 9 gana el jugador, si lo es barcos_hunr la máquina
	if (barcos_hun==9) {
		System.out.println("Has ganado!");
	} else {
		System.out.println("La máquina ha ganado");
	}
}
}
