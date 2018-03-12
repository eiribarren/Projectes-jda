#coding=UTF-8
import datetime

now = datetime.datetime.now()

def prepararHtml(archivo):
	try:
		archivo.write('<html><head><meta charset="utf-8" /></head><body><table>')
		return 
		
	except:
		error = "No s'ha pogut escriure en el fitxer destí"
		log.write(now.strftime("%d-%m-%Y %H:%M") + " " + error + "\n")
		print error
		
def convertirCabeceras(origen,destino):
	try:
		cabecera_html = '<tr id="cabeceras">'
		linia_origen = origen.readline()
		cabeceras = linia_origen.split('\t')
		for cabecera in cabeceras:
			cabecera_html += "<th>" + cabecera + "</th>"
		cabecera_html += "</tr>"
		destino.write(cabecera_html)
		return 
		
	except:
		error = "No s'ha pogut obtenir les capçaleres"
		log.write(now.strftime("%d-%m-%Y %H:%M") + " " + error + "\n")
		print error		
		
def convertirFilas(origen, destino):
	try:
		linea_destino = "<tr>"
		linea_origen = origen.readline()
		fila = linea_origen.split('\t')
		while (linea_origen != ""):
			for celda in fila:
				linea_destino += "<td>" + celda + "</td>"
			linea_destino += "</tr>"
			destino.write(linea_destino)
			linea_origen = origen.readline()
			fila = linea_origen.split('\t')
		return
		
	except:
		error = "No s'ha pogut escriure les linies"
		log.write(now.strftime("%d-%m-%Y %H:%M") + " " + error + "\n")
		print error		

def finalizarHtml(archivo):
	try:
		archivo.write('</table></body></html>')				
		return
		
	except:
		error = "No s'ha pogut escriure l'última línia"
		log.write(now.strftime("%d-%m-%Y %H:%M") + " " + error + "\n")
		print error
	
try:
	log = open("errors.log","a")
except:
	print "No s'ha pogut crear el fitxer log"
	
while True:
	try:
		origen = raw_input("Introdueix el fitxer d'origen sense extensió: ") + ".tsv"
		archivo_origen = open(origen)
		break
		
	except IOError:
		error = "El fitxer no existeix"
		log.write(now.strftime("%d-%m-%Y %H:%M") + " " + error + "\n")
		print error		
		
	except:
		error = "Error desconegut"
		log.write(now.strftime("%d-%m-%Y %H:%M") + " " + error + "\n")
		print error		
		
while True:	
	try:
		destino = raw_input("Introdueix el fitxer destí sense extensió: ") + ".html"
		archivo_destino = open(destino, "w")
		break
	except IOError:
		error = "No s'ha pogut crear el fitxer destí"
		log.write(now.strftime("%d-%m-%Y %H:%M") + " " + error + "\n")
		print error
	
	except:
		error = "Error desconegut"
		log.write(now.strftime("%d-%m-%Y %H:%M") + " " + error + "\n")
		print error		
		
try:
	prepararHtml(archivo_destino)
	convertirCabeceras(archivo_origen, archivo_destino)
	convertirFilas(archivo_origen, archivo_destino)
	finalizarHtml(archivo_destino)
	print "Conversió finalitzada"
	
except:
	error = "No s'ha pogut realitzar la conversió"
	log.write(now.strftime("%d-%m-%Y %H:%M") + " " + error + "\n")
	print error
		
