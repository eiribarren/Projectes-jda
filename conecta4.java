import java.io.*;

public class conecta4{
  final static int MAXI=7;
  final static int MAXJ=8;
  final static String vacio="   ";
  final static String fichaj1=" X ";
  final static String fichaj2=" O ";
  public static void main(String[] args) throws IOException, InterruptedException{
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	  String tablero[][];
	  String nombre_jugador1;
	  String nombre_jugador2;
	  String jugador;
	  String partida;
	  String ficha;
	  int posicion;
	  int fila;
	  int i;
	  int j;
	  int contador=0;
	  int comprobacion;
	  String os= System.getProperty("os.name");
	  tablero = new String[MAXI][MAXJ];
	  partida = "si";
	  fila = 0;
	  jugador = " ";

	  //Pide el nombre de los jugadores
	  System.out.print("Introduce el nombre del jugador1: ");
	  nombre_jugador1 = reader.readLine();
	  do{
      System.out.print("Introduce el nombre del jugador2: ");
	  nombre_jugador2 = reader.readLine();
	  if (nombre_jugador1.equals(nombre_jugador2)) {
		  System.out.println("Error. Los nombres de los jugadores no pueden ser iguales");
	  }
	  } while (nombre_jugador1.equals(nombre_jugador2));

	  //Inicializo el tablero a 0
	  for (i=0;i<MAXI;i++){
		  for (j=0;j<MAXJ;j++){
			  if (i==0) {
				  tablero[0][j]="["+j+"]";
			  } else if (j==0){
				  tablero[i][0]="["+i+"]";
			  } else {
			  tablero[i][j]=vacio;
			  }
		  }
	  }
	  do {
		if (os.contains("Windows")) {
		      new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            System.out.print("\u001b[2J" + "\u001b[H");
            System.out.flush();
        }

		  //Cambia el turno del jugador
		  if (jugador.equals(nombre_jugador1)) {
			  jugador = nombre_jugador2;
			  ficha = fichaj2;
		  } else {
			  jugador = nombre_jugador1;
			  ficha = fichaj1;
		  }

		  //Imprimo el tablero
		  for (i=0;i<MAXI;i++){
			  for (j=0;j<MAXJ;j++){
				  System.out.print(tablero[i][j]);
			  }
			  System.out.println();
		  }

		  //El jugador1 selecciona una columna para meter una ficha
		  do {
			  System.out.print(jugador + " introduce la columna donde introducir la ficha: ");
			  posicion=Integer.parseInt(reader.readLine());
			  if (posicion>MAXJ-1 || posicion<1) {
				  System.out.println("No existe la columna donde has intentado introducir la ficha.");
				  continue;
			  }

			  /*Comprueba si la columna esta llena y
			   * si no lo esta introduce la ficha */
			  for (i=MAXI-1;i>0;i--){
					if (!tablero[1][posicion].equals(vacio)) {
						System.out.println("La columna esta llena ");
						posicion=-1;
						break;
					} else if (tablero[i][posicion].equals(vacio)) {
						tablero[i][posicion]=ficha;
						fila=i;
						break;
					}
				}
			  } while (posicion>MAXJ-1 || posicion<1);

			  //Comprobacion
			  for (comprobacion=0;comprobacion<5;comprobacion++) {
				  contador = 0;
				  switch (comprobacion) {
					  case 0:
						  /* Comprueba en la columna donde se introdujo la ficha
						   *  si hay un 4 en raya */
						  for (i=1;i<MAXI;i++){
							    if (contador==4) {break;}
								if (tablero[i][posicion].equals(ficha)) {
									contador++;
								} else if (!tablero[i][posicion].equals(ficha)) {
									contador=0;
								}
						  }
					  	  break;

					  case 1:
						  /* Comprueba en la fila donde se introdujo la ficha
						   * si hay un 4 en raya */
						  for (j=1;j<MAXJ;j++){
							    if (contador==4) {break;}
								if (tablero[fila][j].equals(ficha)) {
									contador++;
								} else if (!tablero[fila][j].equals(ficha)) {
									contador=0;
								}
						  }
						  break;

					  case 2:
						  /* Comprueba la diagonal inversa \ de la posicion donde
						   * se introdujo la ficha si hay un 4 en raya */
						  if (fila>=posicion) {
							  for (i=fila-posicion;i<MAXI;i++){
							        if (contador==4) {break;}
									if (tablero[i][i-(fila-posicion)].equals(ficha)) {
										contador++;
									} else if (!tablero[i][i-(fila-posicion)].equals(ficha)) {
										contador=0;
									}
								}
						  } else {
								for (j=posicion-fila;j<MAXJ;j++) {
							        if (contador==4) {break;}
									if (tablero[j-(posicion-fila)][j].equals(ficha)) {
										contador++;
									} else if (!tablero[j-(posicion-fila)][j].equals(ficha)) {
										contador=0;
									}
								}
						  }
						  break;

					  case 3:
						/* Comprueba la diagonal directa / si hay un 4 en
						 * raya */
						  if ((MAXI-fila)>=posicion) {
							for (i=0;i<fila+posicion;i++){
							        if (contador==4) {break;}
									if (tablero[i][(fila+posicion)-i].equals(ficha)) {
										contador++;
									} else if (!tablero[i][(fila+posicion)-i].equals(ficha)) {
										contador=0;
									}
							}
						  } else {
							for (j=MAXJ-1;j>posicion-(MAXI-fila);j--) {
								 if (contador==4) {break;}
								 if (tablero[(fila+1)-(j-(MAXI-fila))][j].equals(ficha)) {
										contador++;
									} else if (!tablero[(fila+1)-(j-(MAXI-fila))][j].equals(ficha)) {
										contador=0;
									}
							  }
						  }
						  break;
					}
					if (contador==4){
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
						  System.out.println();
					  }
					  System.out.println(jugador + " ha ganado!");
					  partida="finalizada";
					  break;
					}
				}
			if (partida.equals("finalizada")) {
			  for (i=0;i<MAXI;i++){
				  for (j=0;j<MAXJ;j++){
					  if (i==0) {
						  tablero[0][j]="["+j+"]";
					  } else if (j==0){
						  tablero[i][0]="["+i+"]";
					  } else {
					  tablero[i][j]=vacio;
					  }
				  }
			  }
			  jugador = " ";
			  System.out.print("Quieres volver a jugar? ");
			  partida = reader.readLine();
			}
	  } while (partida.equals("si"));
 }
}
