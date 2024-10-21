package proyecto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Quiz extends Actividad{
	

	//Atributos
	private double notaAprobacion;
	private double notaObtenida;
	private List<PreguntaMultiple> preguntas;

	//Constructor
	public Quiz(LearningPath lp, String descripcion, String objetivo, String nivelDificultad, int duracionEsperada,
			boolean obligatorio, double notaAprobacion, Profesor creador) {
		super(lp, descripcion, objetivo, nivelDificultad, duracionEsperada, obligatorio, creador);
		// TODO Auto-generated constructor stub
		this.notaAprobacion = notaAprobacion;
		this.notaObtenida = 0.0;
		this.preguntas = new ArrayList<PreguntaMultiple>();
	}
	
	//get and set
	public double getNotaAprobacion() {
		return notaAprobacion;
	}
	public double getNotaObtenida() {
		return notaObtenida;
	}

	
	//Metodos
    public void agregarPregunta(Scanner scanner) {
	    List<String> opciones = new ArrayList<String>();
	    List<String> explicaciones = new ArrayList<String>();
	    
	    System.out.print("Ingrese la pregunta: ");
	    String texto = scanner.nextLine();
	    for (int i = 0; i < 4; i++ ) {
		    System.out.print("Ingrese la opcion "+ (i+1)+" : ");
		    opciones.add(scanner.nextLine());
		    System.out.print("Ingrese la explicación de la opción "+ (i+1)+" : ");
		    explicaciones.add(scanner.nextLine());
	    }
	    System.out.print("Ingrese el número de la opción correcta: ");
	    int resp = Integer.parseInt(scanner.nextLine());
	    PreguntaMultiple p = new PreguntaMultiple(texto, opciones, resp, explicaciones);
        preguntas.add(p);
    }
  
	
	public String realizarQuiz(Scanner scanner) {
		double nota = 0;
        String resultado = "";
        
		for (PreguntaMultiple p: preguntas) {
			boolean rta = p.mostrarYResolver(scanner);
			if (rta) {
				nota += 1;
			}
		}
		double numPreguntas = preguntas.size();
		this.notaObtenida = (nota/numPreguntas)*100;
		
		System.out.println("Tu nota es: " + notaObtenida);
		if (notaObtenida >= notaAprobacion) {
			resultado = "Aprobada";
			System.out.println("Tu resultado es: " + resultado +"\n");
		} else {
			resultado = "Reprobada";
			System.out.println("Tu resultado es: " + resultado+"\n");
		}
		return resultado;
	}

	
	@Override
	public void realizar(ProgresoActividad progreso) {
		// TODO Auto-generated method stub
        Scanner sc = new Scanner(System.in);
		String rta = realizarQuiz(sc);
		progreso.marcarRealizada("Enviada", new Date());
		progreso.completarActividad(rta);
	}

	@Override
	public void editar(Profesor editor) {
		if (this.creador == editor) {
			Scanner scanner = new Scanner(System.in);
			agregarPregunta(scanner);
		} else {
			System.out.println("No tiene los permisos para editar la actividad");
		}
		
	}

}
